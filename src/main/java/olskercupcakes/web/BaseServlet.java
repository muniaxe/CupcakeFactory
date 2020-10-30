package olskercupcakes.web;

import olskercupcakes.api.OlskerCupcakes;
import olskercupcakes.infrastructure.CupcakeDBDAO;
import olskercupcakes.infrastructure.Database;
import olskercupcakes.infrastructure.UserDBDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseServlet extends HttpServlet {
    protected static final OlskerCupcakes api;

    static {
        OlskerCupcakes tmp = null;
        try {
            tmp = createOlskerCupcakes();
        } catch(RuntimeException e) {
            e.printStackTrace();
        }
        api = tmp;
    }

    private static OlskerCupcakes createOlskerCupcakes() {
        Database db = new Database();
        return new OlskerCupcakes(new UserDBDAO(db), new CupcakeDBDAO(db));
    }
    protected void render(String title, String content, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("title", title);
        req.setAttribute("content", content);
        req.getRequestDispatcher("/WEB-INF/base.jsp").forward(req, resp);
    }

    public boolean isUser(HttpServletRequest req) {
        return req.getSession().getAttribute("user") != null;
    }

}
