package main.ecommerce;

import javax.xml.ws.Service;
import java.sql.*;
import java.util.Scanner;


public class ConnectionController {

    Scanner scan = new Scanner(System.in);
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private Connection connection = null;


    public static Connection getConnection() throws Exception {

        ConnectionController controller = new ConnectionController();

        try {

            String Driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(Driver);

            controller.connection = DriverManager.getConnection("jdbc:mysql://localhost/tahoe_db?" +
                    "user=root&password=&useSSL=false&serverTimezone=UTC");
            System.out.println("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return controller.connection;
    }

    public Connection getConnection(String url, String user, String password) throws Exception {

        Class.forName("com.mysql.jdbc.Driver");

        // create connection with dynamic arguments
        connection = DriverManager.getConnection(url, user, password);

        return connection;
    }


    public Statement getStatement() throws Exception {

        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/tahoe_db?" +
                    "user=root&password=&useSSL=false&serverTimezone=UTC");

            statement = connection.createStatement();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statement;
    }



    public void closeConnection(Connection connection, PreparedStatement preparedStatement) {

        try {

            connection.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void closeConnection(Connection connection, Statement statement) {

        try {
            connection.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
