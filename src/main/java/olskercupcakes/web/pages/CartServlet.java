package olskercupcakes.web.pages;

import olskercupcakes.domain.cupcake.Cupcake;
import olskercupcakes.domain.cupcake.CupcakeNoCakeFoundException;
import olskercupcakes.domain.cupcake.CupcakeNoToppingFoundException;
import olskercupcakes.domain.order.cart.Cart;
import olskercupcakes.web.BaseServlet;
import olskercupcakes.web.Notification;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.render("Kurv - Olsker Cupcakes", "cart", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        if(action == null) {
            resp.sendError(400, "Wrong input, action not set.");
            return;
        }
        switch (action) {
            case "addItem" -> addItem(req, resp);
            case "removeItem" -> removeItem(req, resp);
            default -> resp.sendError(400, "Wrong Input");
        }
    }

    private void addItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            //Receive cupcake parameters; topping and cake & quantity.
            int cakeId = Integer.parseInt(req.getParameter("cake"));
            int toppingId = Integer.parseInt(req.getParameter("topping"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            if(quantity < 1 || quantity > 100)
                throw new IllegalArgumentException();

            //Fetch information about cupcake from database;
            Cupcake.Cake cake = api.findCupcakeCake(cakeId);
            Cupcake.Topping topping = api.findCupcakeTopping(toppingId);

            //Create cupcake
            Cupcake cupcake = new Cupcake(cake, topping);

            //Create an item for our cart.
            Cart.Item item = new Cart.Item(cupcake, quantity);

            //Get current cart.
            Cart cartTmp = (Cart) req.getSession().getAttribute("cart");

            //Add item to cart.
            cartTmp.addItem(item);

            //Update session cart.
            req.getSession().setAttribute("cart", cartTmp);

            req.getSession().setAttribute("notification", new Notification(Notification.Type.SUCCESS,
                    "Din cupcake med " + item.getCupcake().getCake().getName() +
                            " bund og " + item.getCupcake().getTopping().getName() +
                            " top er blevet tilføjet til kurven!"
            ));
            //Redirect to shop
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (CupcakeNoCakeFoundException e) {
            req.getSession().setAttribute("notification", new Notification(Notification.Type.DANGER,
                    "Vi kunne ikke finde den bund, til den cupcake, du prøvede at bestille. Prøv igen."
            ));
            resp.sendRedirect(req.getContextPath() + "/");
        }
        catch (CupcakeNoToppingFoundException e) {
            req.getSession().setAttribute("notification", new Notification(Notification.Type.DANGER,
                    "Vi kunne ikke finde den topping, til den cupcake, du prøvede at bestille. Prøv igen."
            ));
            resp.sendRedirect(req.getContextPath() + "/");
        }
        catch (NumberFormatException e) {
            req.getSession().setAttribute("notification", new Notification(Notification.Type.DANGER,
                    "Der skete en fejl når vi prøvede at tilføje varen til kurven. Prøv igen."
            ));
            resp.sendRedirect(req.getContextPath() + "/");
        }
        catch (IllegalArgumentException e) {
            req.getSession().setAttribute("notification", new Notification(Notification.Type.DANGER,
                    "Du kan kun bestille mellem 1-100 antal af samme cupcake."
            ));
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    private void removeItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int index = Integer.parseInt(req.getParameter("index"));

            //Get current cart.
            Cart cartTmp = (Cart) req.getSession().getAttribute("cart");

            //Remove item to cart.
            Cart.Item item = cartTmp.getItems().get(index);
            cartTmp.getItems().remove(index);

            //Update session cart.
            req.getSession().setAttribute("cart", cartTmp);

            req.getSession().setAttribute("notification", new Notification(Notification.Type.WARNING,
                    "Din cupcake med " + item.getCupcake().getCake().getName() +
                            " bund og " + item.getCupcake().getTopping().getName() +
                            " top er blevet fjernet fra kurven!"
            ));
            resp.sendRedirect(req.getContextPath() + "/cart");
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            req.getSession().setAttribute("notification", new Notification(Notification.Type.DANGER,
                    "Der skete en fejl når vi prøvede at fjerne varen fra kurven. Prøv igen."
            ));
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}
