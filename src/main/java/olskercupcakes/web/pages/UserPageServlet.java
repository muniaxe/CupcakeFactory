package olskercupcakes.web.pages;

import olskercupcakes.domain.user.User;
import olskercupcakes.infrastructure.Database;
import olskercupcakes.infrastructure.UserDBDAO;
import olskercupcakes.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/users")
public class UserPageServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", api.getAllUsers());
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        super.render("Admin panel - All users", "admin/users", req, resp);
    }
}
