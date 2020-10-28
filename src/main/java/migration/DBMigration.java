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
        migrate.runMigrations();
    }

    public void runMigrations() throws IOException, SQLException {
        int version = db.getCurrentVersion();
        while (version < db.getVersion()) {
            System.out.printf("Current DB version %d is smaller than expected %d\n", version, db.getVersion());
            runMigration(version + 1);
            int new_version = db.getCurrentVersion();
            if (new_version > version) {
                version = new_version;
                System.out.println("Updated database to version: " + new_version);
            } else {
                throw new RuntimeException("Something went wrong, version not increased: " + new_version);
            }
        }
    }

    public void runMigration(int i) throws IOException, SQLException {
        String migrationFile = String.format("migrate/%d.sql", i);
        System.out.println("Running migration: " + migrationFile);
        InputStream stream = DBMigration.class.getClassLoader().getResourceAsStream(migrationFile);
        if (stream == null) {
            System.out.println("Migration file, does not exist: " + migrationFile);
            throw new FileNotFoundException(migrationFile);
        }
        try(Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setStopOnError(true);
            runner.runScript(new BufferedReader(new InputStreamReader(stream)));
            conn.commit();
        }
        System.out.println("Done running migration");
    }

}