package olskercupcakes.web.pages;

import olskercupcakes.domain.user.*;
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

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            User user = api.authorizeUser(email, password);
            if (user == null){
                throw new UserNotFoundException();
            }
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (UserNotFoundException | UserNonMatchingPasswordException e) {
            req.setAttribute("error", "Der fandtes ingen bruger med denne email / adgangskode kombination.");
            doGet(req, resp);
        } catch (NullPointerException e){
            req.setAttribute("error", "Der skete en intern fejl i systemet, prøv igen.");
            doGet(req, resp);
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String passwordVerify =req.getParameter("password_verify");
            User user = api.createUser(email, password, passwordVerify);
            if (user == null){
                throw new UserPasswordVerifyException();
            }
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (UserPasswordVerifyException e){
            req.setAttribute("error", "Adgangskoderne matchede ikke, prøv igen.");
            doGet(req, resp);
        } catch (UserExistsException e){
            req.setAttribute("error", "Denne email er allerede i brug.");
            doGet(req, resp);
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.render("Registrer eller Login - Olsker Cupcakes", "login", req, resp);
    }
}
