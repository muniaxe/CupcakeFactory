package olskercupcakes.integration;

import olskercupcakes.api.OlskerCupcakes;
import olskercupcakes.domain.user.*;
import olskercupcakes.infrastructure.Database;
import olskercupcakes.infrastructure.UserDBDAO;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

        UserRepository userRepository = new UserDBDAO(db);
        api = new OlskerCupcakes(userRepository, null, null);
    }

    /*
     *
     * [Som kunde kan jeg oprette en konto/profil for at..]
     * [As a client, I can create an account/profile to..]
     *
     * This test expects that all inputs are correct.
     *
     * Test will test that an user can be create and signed into.
     *
     */
    @Test
    void userStory2() throws UserExistsException, UserPasswordVerifyException, UserNotFoundException, UserNonMatchingPasswordException {
        //Our default user values...
        String email = "test@olskercupcakes.dk";
        String password = "mypassword123";

        //User registers our service.
        User userRegistered = api.createUser(email, password, password);

        //We expect User object to not be null.
        assertNotNull(userRegistered);

        //User signs in.
        User userSignedIn = api.authorizeUser(email, password);

        //We expect User object to not be null.
        assertNotNull(userSignedIn);

        //We expect our two users to be the same.
        assertEquals(userRegistered, userSignedIn);
    }
}
