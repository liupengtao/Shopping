package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: liupengtao
 * Date: 12-8-27
 * Time: 下午3:23
 * To change this template use File | Settings | File Templates.
 */
public class Utils {


    private static String url = "jdbc:mysql://localhost:3306/Shopping_development?useUnicode=true&characterEncoding=utf-8";
    private static String username = "root";
    private static String password = "";
    private static Connection connection;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    public static Connection getConn() {
        try {
            return connection == null ? connection = DriverManager.getConnection(url, username, password) : connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void colseConn() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
