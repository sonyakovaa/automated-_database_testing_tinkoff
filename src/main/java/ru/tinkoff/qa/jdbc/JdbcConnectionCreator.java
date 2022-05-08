package ru.tinkoff.qa.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnectionCreator {

    private static final String host = "jdbc:h2:mem:myDb";
    private static final String user = "sa";
    private static final String password = "sa";

    public static Connection createConnection() {
        try {
            return DriverManager.getConnection(host, user, password);
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
