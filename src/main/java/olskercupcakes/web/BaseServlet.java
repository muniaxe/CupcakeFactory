package olskercupcakes.web;

import olskercupcakes.api.OlskerCupcakes;
import olskercupcakes.api.Utils;
import olskercupcakes.domain.order.Cart;
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
    protected static final Utils utils;

    protected static final Cart cart;

    static {
        OlskerCupcakes tmp = null;
        try {
            tmp = createOlskerCupcakes();
        } catch(RuntimeException e) {
            e.printStackTrace();
        }
        api = tmp;

        cart = new Cart();

        utils = new Utils();
    }

    private static OlskerCupcakes createOlskerCupcakes() {
        Database db = new Database();
        return new OlskerCupcakes(new UserDBDAO(db), new CupcakeDBDAO(db));
    }
    protected void render(String title, String content, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("title", title);
        req.setAttribute("content", content);
        req.setAttribute("utils", utils);

        // Cart initiation:
        req.getSession().setAttribute("cart", cart);

        req.getRequestDispatcher("/WEB-INF/base.jsp").forward(req, resp);
    }

    public boolean isUser(HttpServletRequest req) {
        return req.getSession().getAttribute("user") != null;
    }
}