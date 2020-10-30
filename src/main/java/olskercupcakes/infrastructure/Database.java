package olskercupcakes.infrastructure;

import java.sql.*;

public class Database {

    private final String URL = "jdbc:mysql://localhost:3306/olskercupcakes?serverTimezone=CET";
    private final String USER = "olskercupcakes";

    private final int version = 2;

    public Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (getCurrentVersion() != getVersion()) {
                throw new IllegalStateException("Database in wrong state, expected:"
                        + getVersion() + ", got: " + getCurrentVersion());
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Database(boolean isMigrating) {
        if(isMigrating) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, null);
    }

    public int getVersion() {
        return version;
    }

    public int getCurrentVersion() {
        try (Connection conn = getConnection()) {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT value FROM properties WHERE name = 'version';");
            if (rs.next()) {
                String column = rs.getString("value");
                return Integer.parseInt(column);
            } else {
                System.err.println("No version in properties.");
                return -1;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

}
