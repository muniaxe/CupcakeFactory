package olskercupcakes.web.pages;

import olskercupcakes.domain.user.User;
import olskercupcakes.domain.user.UserExistsException;
import olskercupcakes.domain.user.UserInvalidPasswordException;
import olskercupcakes.domain.user.UserNotFoundException;
import olskercupcakes.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authentication")
public class Authentication extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "login":
                login(req, resp);
                break;
            case "register":
                register(req, resp);
                break;
            case "logout":
                logout(req, resp);
                break;
            default:
                resp.sendError(400, "Wrong Input");
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            User user = api.authorizeUser(email, password);
            if (user == null){
                throw new UserNotFoundException();
            }
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (UserNotFoundException | UserInvalidPasswordException e) {
            resp.sendRedirect(req.getContextPath() + "/authentication?err=Email og password matcher ikke");
        } catch (NullPointerException e){
            resp.sendRedirect(req.getContextPath() + "/authentication");
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.render("Registrer eller Login - Olsker Cupcakes", "login", req, resp);
    }
}
