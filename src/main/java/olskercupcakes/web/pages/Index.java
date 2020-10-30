package olskercupcakes.web.pages;

import olskercupcakes.domain.cupcake.Cupcake;
import olskercupcakes.domain.cupcake.CupcakeNoCakeFoundException;
import olskercupcakes.domain.cupcake.CupcakeNoToppingsFoundException;
import olskercupcakes.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("")
public class Index extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Cupcake.Topping> toppings;
        List<Cupcake.Cake> cakes;
        try {
            toppings = api.findAllCupcakeToppings();
            cakes = api.findAllCupcakeCakes();
        } catch (CupcakeNoCakeFoundException | CupcakeNoToppingsFoundException e){
            //e.ddsadsa
            return;
        }
        req.setAttribute("toppings", toppings);
        req.setAttribute("cakes", cakes);
        super.render("Shopping - Olsker Cupcakes", "shop", req, resp);
    }
}
