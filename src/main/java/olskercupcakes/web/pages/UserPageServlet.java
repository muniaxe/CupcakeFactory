package olskercupcakes.web.pages;

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
        Database db = new Database();
        UserDBDAO conn = new UserDBDAO(db);
        req.setAttribute("users", conn.getAllUsers());
        super.render("All users", "admin/users", req, resp);
    }
}
