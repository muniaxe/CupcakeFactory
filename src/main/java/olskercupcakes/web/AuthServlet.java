package olskercupcakes.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends BaseServlet {
    @Override
    protected void render(String title, String content, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Is user logged in.
        if(false) {
            super.render(title, content, req, resp);
        }
    }
}
