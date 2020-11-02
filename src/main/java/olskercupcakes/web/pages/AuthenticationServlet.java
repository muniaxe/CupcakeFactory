package olskercupcakes.web.pages;

import olskercupcakes.domain.user.*;
import olskercupcakes.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authentication")
public class AuthenticationServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null) {
            resp.sendError(400, "Wrong input, action not set.");
            return;
        }
        switch (action) {
            case "login" -> login(req, resp);
            case "register" -> register(req, resp);
            case "logout" -> logout(req, resp);
            default -> resp.sendError(400, "Wrong Input");
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if(super.isUser(req)) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            User user = api.authorizeUser(email, password);
            if (user == null){
                throw new UserNotFoundException();
            }
            redirect(req, resp, user);

        } catch (UserNotFoundException | UserNonMatchingPasswordException e) {
            req.setAttribute("error", "Der fandtes ingen bruger med denne email / adgangskode kombination.");
            doGet(req, resp);
        } catch (NullPointerException e){
            req.setAttribute("error", "Der skete en intern fejl i systemet, prøv igen.");
            doGet(req, resp);
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(super.isUser(req)) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String passwordVerify =req.getParameter("password_verify");
            User user = api.createUser(email, password, passwordVerify);
            if (user == null){
                throw new UserPasswordVerifyException();
            }
            redirect(req, resp, user);
        } catch (UserPasswordVerifyException e){
            req.setAttribute("error", "Adgangskoderne matchede ikke, prøv igen.");
            doGet(req, resp);
        } catch (UserExistsException e){
            req.setAttribute("error", "Denne email er allerede i brug.");
            doGet(req, resp);
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute("user", null);
        resp.sendRedirect(req.getContextPath() + "/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(super.isUser(req)) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        String urlRedirect;
        if((urlRedirect = req.getParameter("redirect")) != null) {
            req.getSession().setAttribute("redirect", urlRedirect);
        }
        super.render("Registrer eller Login - Olsker Cupcakes", "login", req, resp);
    }

    private void redirect(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        req.getSession().setAttribute("user", user);

        String urlRedirect;
        if((urlRedirect = (String) req.getSession().getAttribute("redirect")) != null) {
            resp.sendRedirect(req.getContextPath() + urlRedirect);
            req.getSession().setAttribute("redirect", null);
        } else
            resp.sendRedirect(req.getContextPath() + "/");
    }
}
