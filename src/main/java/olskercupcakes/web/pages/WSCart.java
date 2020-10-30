package olskercupcakes.web.pages;

import olskercupcakes.domain.cupcake.Cupcake;
import olskercupcakes.domain.cupcake.CupcakeNoCakeFoundException;
import olskercupcakes.domain.cupcake.CupcakeNoToppingFoundException;
import olskercupcakes.domain.order.Cart;
import olskercupcakes.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cart")
public class WSCart extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.render("Kurv - Olsker Cupcakes", "cart", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            //Receive cupcake parameters; topping and cake & quantity.
            int cakeId = Integer.parseInt(req.getParameter("cake"));
            int toppingId = Integer.parseInt(req.getParameter("topping"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            //Fetch information about cupcake from database;
            Cupcake.Cake cake = api.findCupcakeCake(cakeId);
            Cupcake.Topping topping = api.findCupcakeTopping(toppingId);

            //Create cupcake
            Cupcake cupcake = new Cupcake(cake, topping);

            //Create an item for our cart.
            Cart.Item item = new Cart.Item(cupcake, quantity);

            //Add item to our cart
            Cart cartTmp = (Cart) req.getSession().getAttribute("cart");
            cartTmp.addItem(item);

            //Update session cart.
            req.getSession().setAttribute("cart", cartTmp);

            req.getSession().setAttribute("successMessage",
                    "Din cupcake med " + item.getCupcake().getCake().getName() +
                            " bund og " + item.getCupcake().getTopping().getName() +
                            " top er blevet tilføjet til kurven!"
            );
            //Redirect to shop
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (CupcakeNoCakeFoundException | CupcakeNoToppingFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }



    }
}
