package olskercupcakes.web.pages.admin;

import olskercupcakes.domain.user.User;
import olskercupcakes.domain.user.UserNotFoundException;
import olskercupcakes.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet("/admin/users")
public class AdminUserPageServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = super.getUser(req);
        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        List<User> users = api.getAllUsers();
        users.sort(Comparator.comparing(User::getId));
        req.setAttribute("users", users);
        super.render("Admin panel - All users", "admin/users", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = super.getUser(req);
        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        try {
            String action = req.getParameter("action");
            String newBalanceString = req.getParameter("new-balance");
            User userToUpdate = api.findUser(Integer.parseInt(req.getParameter("user-id")));
            if(action == null)
                throw new IllegalArgumentException();

            //Probably change this to a Switch Case...
            if(action.equals("update-balance")) {
                newBalanceString = newBalanceString.replaceAll("\\.", "");
                newBalanceString = newBalanceString.replaceAll(",", ".");
                double newBalanceDouble = Double.parseDouble(newBalanceString);
                int newBalance = (int) (newBalanceDouble * 100);
                userToUpdate.setBalance(newBalance);

                User updatedUser = api.updateUser(userToUpdate);
                resp.getWriter().println("{\"balance\": " + updatedUser.getBalance() + "}");
            }
            else {
                throw new IllegalArgumentException();
            }

        } catch (IllegalArgumentException | UserNotFoundException e) {
            e.printStackTrace();
            resp.sendError(500,"Mistakes were made..");
        }
    }
}
