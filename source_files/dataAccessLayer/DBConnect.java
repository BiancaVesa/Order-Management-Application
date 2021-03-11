package dataAccessLayer;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa DBConnect realizeaza conexiunea cu baza de date cu care vrem sa lucram.
 */
public class DBConnect {
    private static final Logger LOGGER = Logger.getLogger(DBConnect.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3307/warehouse?useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "123456654321";
    private static DBConnect singleInstance = new DBConnect();

    /**
     * Cauta baza de date cu care dorim sa lucram.
     */
    private DBConnect() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creeaza o conexiune intre program si baza de date cu care vrem sa lucram.
     * @return conexiunea cu baza de date; null in cazul in care conexiunea nu se poate realiza
     */
    private Connection createConnection() {
        Connection c = null;
        try {
            c = DriverManager.getConnection(DBURL, USER, PASS);
            return c;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database ");
            return null;
        }
    }

    /**
     * Returneaza conexiunea realizata cu ajutorul obiectului DBConnect.
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Inchide conexiunea cu baza de date.
     * @param connection conexiunea cu baza de date
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }

    /**
     * Inchide un statement in limbajul SQL.
     * @param statement statement-ul pe care dorim sa il inchidem
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }

    /**
     * Inchide un ResulSet returnat de un statement in limbajul SQL.
     * @param resultSet ResultSet-ul pe care dorim sa il inchidem
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }
}
