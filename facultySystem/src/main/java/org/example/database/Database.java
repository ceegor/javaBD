package org.example.database;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static final Properties props = new Properties();

    static {
        try (var in = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) throw new IllegalStateException("db.properties не найдены");
            props.load(new InputStreamReader(in, StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
        try { Class.forName("org.postgresql.Driver"); } catch (ClassNotFoundException ignored) {}
    }

    private Database() {}

    public static Connection open() throws SQLException {
        return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
        );
    }
}
