package migration;

import olskercupcakes.infrastructure.Database;

import java.io.*;
import org.apache.ibatis.jdbc.ScriptRunner;
import java.sql.Connection;
import java.sql.SQLException;

public class DBMigration {

    private final Database db;

    public DBMigration() {
        this.db = new Database(true);
    }

    public static void main(String[] args) throws IOException, SQLException {
        DBMigration migrate = new DBMigration();
        migrate.db.runMigrations();
    }

}