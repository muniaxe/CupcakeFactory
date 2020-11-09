package olskercupcakes.integration;

import olskercupcakes.api.OlskerCupcakes;
import olskercupcakes.domain.cupcake.Cupcake;
import olskercupcakes.domain.cupcake.CupcakeNoCakeFoundException;
import olskercupcakes.domain.cupcake.CupcakeNoToppingFoundException;
import olskercupcakes.domain.cupcake.CupcakeRepository;
import olskercupcakes.domain.order.*;
import olskercupcakes.domain.order.cart.Cart;
import olskercupcakes.domain.user.*;
import olskercupcakes.domain.validation.ValidationErrorException;
import olskercupcakes.infrastructure.CupcakeDBDAO;
import olskercupcakes.infrastructure.Database;
import olskercupcakes.infrastructure.OrderDBDAO;
import olskercupcakes.infrastructure.UserDBDAO;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


/*


Integration tests assumes you've setup a database with restart.sql (NOT restartTest.sql) before usage.


 */
@Tag("integration-test")
public class MainIntegrationTest {

    OlskerCupcakes api;

    static void resetTestDatabase() {
        String URL = "jdbc:mysql://localhost:3306/?serverTimezone=CET";
        String USER = "olskercupcakes_test";

        InputStream stream = OlskerCupcakes.class.getClassLoader().getResourceAsStream("migrate/restartTest.sql");
        if (stream == null) throw new RuntimeException("restartTest.sql");
        try (Connection conn = DriverManager.getConnection(URL, USER, null)) {
            conn.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setLogWriter(null);
            runner.setStopOnError(true);
            runner.runScript(new BufferedReader(new InputStreamReader(stream)));
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Done running restart migration.");
    }

    @BeforeEach
    void setupAPI() throws IOException, SQLException {
        resetTestDatabase();

        String url = "jdbc:mysql://localhost:3306/olskercupcakes_test?serverTimezone=CET";
        String user = "olskercupcakes_test";

        Database db = new Database(url, user);
        db.runMigrations();

        UserDBDAO userDBDAO = new UserDBDAO(db);
        CupcakeDBDAO cupcakeDBDAO = new CupcakeDBDAO(db);

        api = new OlskerCupcakes(userDBDAO, cupcakeDBDAO, new OrderDBDAO(db, userDBDAO, cupcakeDBDAO));
    }

    //All main tests expects all input to be correct...

    /*
     *
     * #2 User Story
     *
     * [Som kunde kan jeg oprette en konto/profil for at..]
     * [As a client, I can create an account/profile to..]
     *
     *
     * Test will test that an user can be create and signed into.
     *
     */
    @Test
    void userStory2() throws UserExistsException, UserNotFoundException, UserNonMatchingPasswordException, ValidationErrorException {
        //Our default user values...
        String email = "test@olskercupcakes.dk";
        String password = "mypassword123";

        //User registers our service.
        UserFactory userFactory = api.createUser();
        userFactory.setEmail(email);
        userFactory.setPassword(password);
        userFactory.setPasswordConfirm(password);

        User userRegistered = userFactory.validateAndCommit();

        //We expect User object to not be null.
        assertNotNull(userRegistered);

        //User signs in.
        User userSignedIn = api.authorizeUser(email, password);

        //We expect User object to not be null.
        assertNotNull(userSignedIn);

        //We expect our two users to be the same.
        assertEquals(userRegistered, userSignedIn);
    }

    /*
     *
     * #4 User Story
     *
     * [Som kunde kan jeg se mine valgte ordrelinier i en indkøbskurv... se samlede pris]
     * [As a client, I can see my chosen products in a cart... see total price]
     *
     *
     * Test will create items and populate a cart, then check card..
     *
     */
    @Test
    void userStory4()  {
        //Create cart...
        Cart cart = new Cart();

        //Make a few toppings and cakes.
        Cupcake.Cake cakeVanilla = new Cupcake.Cake(1, "Vanilla", 200);
        Cupcake.Cake cakeBlueberry = new Cupcake.Cake(2, "Blueberry", 200);

        Cupcake.Topping toppingChocolate = new Cupcake.Topping(1, "Chocolate", 200);
        Cupcake.Topping toppingLemon = new Cupcake.Topping(2, "Lemon", 200);

        //Make a few cupcakes.
        Cupcake c1 = new Cupcake(cakeBlueberry, toppingChocolate);
        Cupcake c2 = new Cupcake(cakeVanilla, toppingChocolate);
        Cupcake c3 = new Cupcake(cakeVanilla, toppingLemon);

        //Create the items with their quantity.
        Cart.Item item = new Cart.Item(c1, 2);
        Cart.Item item2 = new Cart.Item(c2, 1);
        Cart.Item item3 = new Cart.Item(c3, 4);

        //Populate our cart with items.
        cart.addItem(item);
        cart.addItem(item2);
        cart.addItem(item3);

        //We expect to be 3 items added to the order.
        assertEquals(3, cart.getItems().size());

        //We expect 7 cupcakes added to cart (quantity)
        assertEquals(7, cart.getQuantity());

        //Expected price:
        //4*2kr +4*1kr +4*4kr = 28kr = 2800 cents.

        assertEquals(2800, cart.getTotalPrice());
    }

    /*
     *
     * #1 User Story
     *
     * [Som kunde kan jeg bestille og betale cupcakes med en valgfri bund og top...]
     * [As a client, i can order and pay for cupcakes with cake and topping...]
     *
     *
     * Test will create user, set users balance and update it, retrieve a cake and topping, populate cart, create order, and retrieve order to see if they're the same.
     *
     */
    @Test
    void userStory1() throws UserExistsException, ValidationErrorException, OrderExistsException, UserNotFoundException, CupcakeNoCakeFoundException, CupcakeNoToppingFoundException, OrderNotFoundException {
        //User values.
        String email = "test@olskercupcakes.dk";
        String password = "mypassword123";

        //User registers our service.
        UserFactory userFactory = api.createUser();

        userFactory.setEmail(email);
        userFactory.setPassword(password);
        userFactory.setPasswordConfirm(password);

        User user = userFactory.validateAndCommit();

        //Give user some balance.
        user.setBalance(200000); // 2000 kr
        user = api.updateUser(user);

        //Set our UUID
        UUID uuid = UUID.randomUUID();

        //Fetch a topping and a cake.
        Cupcake.Cake cakeChocolate = api.findCupcakeCake(1); // This should be chocolate
        Cupcake.Topping cakeBlueberry = api.findCupcakeTopping(2); // This should be blueberry.

        //Create cupcake
        Cupcake cupcake = new Cupcake(cakeChocolate, cakeBlueberry);

        //Create our item.
        Cart.Item item = new Cart.Item(cupcake, 6);

        //Create our cart and populate it.
        Cart cart = new Cart();
        cart.addItem(item);

        //Create order with our values.
        OrderFactory orderFactory = api.createOrder();

        orderFactory.setUuid(uuid);
        orderFactory.setUser(user);
        orderFactory.setCart(cart);

        Order createdOrder = orderFactory.validateAndCommit();

        Order foundOrder = api.findOrder(createdOrder.getUuid());

        assertEquals(createdOrder, foundOrder);

    }
}
