package utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbClient {
    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void clearTables() throws SQLException {
        var runner = new QueryRunner();

        try (var conn = getConnection()) {
            runner.update(conn, "DELETE FROM order_entity;");
            runner.update(conn, "DELETE FROM payment_entity;");
            runner.update(conn, "DELETE FROM credit_request_entity;");
        }
    }

    public static String findPaymentStatus() throws SQLException {
        return getData("SELECT status FROM payment_entity;");
    }

    public static String findCreditStatus() throws SQLException {
        return getData("SELECT status FROM credit_request_entity;");
    }

    public static String countRecords() throws SQLException {
        return getData("SELECT COUNT(*) FROM order_entity;");
    }

    private static String getData(String query) throws SQLException {
        var runner = new QueryRunner();
        try (var conn = getConnection()) {
            Object result = runner.query(conn, query, new ScalarHandler<>());
            return result == null ? null : result.toString();
        }
    }
}
