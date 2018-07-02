package main.ecommerce;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PlatfromServiceController {

    // getters and setters for platform
    private int platid;
    private String platname;
    private String platurl;
    private double platsalesfee;

    Connection connection = null;
    ResultSet resultset = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;

    Scanner scan = new Scanner(System.in);
    ConnectionController controller = new ConnectionController();


    public int getPlatid() {
        return platid;
    }

    public void setPlatid(int platid) {
        this.platid = platid;
    }

    public String getPlatname() {
        return platname;
    }

    public void setPlatname(String platname) {
        this.platname = platname;
    }

    public String getPlaturl() {
        return platurl;
    }

    public void setPlaturl(String platurl) {
        this.platurl = platurl;
    }

    public double getPlatsalesfee() {
        return platsalesfee;
    }

    public void setPlatsalesfee(double platsalesfee) {
        this.platsalesfee = platsalesfee;
    }


    @Override
    public String toString() {
        return "Platform{" +
                "platid=" + platid +
                ", platname='" + platname + '\'' +
                ", platurl='" + platurl + '\'' +
                ", platsalesfee=" + platsalesfee +
                '}';
    }

    //Where the method starts

    //main method
    public static void main(String[] args) throws Exception {

        PlatfromServiceController service = new PlatfromServiceController();
        ECom_Controller eCom_controller = new ECom_Controller();

        //Accessing the connection from the connection controller
        ConnectionController.getConnection();

        // code to see the database controlling options
        System.out.println("Press 1 to see the database options");
        int m = service.scan.nextInt();

        if (m == 1) {

            eCom_controller.menu();
        }

        try {

            do {
                eCom_controller.switchstatementplatform();

            } while (service.scan.next() != null);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // Method controller for the database

    //Platform Insert Controller Method
    public void platformInsert() throws Exception {

        try {

            //Setting the connection in this class to the connection method in the the ConnectionController
            connection = controller.getConnection();

            //Asking the user for the new platform name
            System.out.println("Enter platform name");
            String platform_name = scan.next();

            //Asking the user for the new url
            System.out.println("Enter the platform url");
            String platform_url = scan.next();

            // Asking the user in insert the sales fee
            System.out.println("Enter the sales fee");

            double sales_fee = scan.nextDouble();


            //Connecting the prepared statement and putting the SQL query
            preparedStatement = connection.prepareStatement("INSERT INTO platform(platform_name, platform_url, sales_fee)" + "VALUES(?,?,?) ");

            //Setting and locating where each variable goes
            preparedStatement.setString(1, platform_name);
            preparedStatement.setString(2, platform_url);
            preparedStatement.setDouble(3, sales_fee);


            preparedStatement.executeUpdate();

            System.out.println();
            System.out.println("Inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {


            try {

                controller.closeConnection(connection, preparedStatement);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //Platform List Controller Method

    public void platformList() throws Exception {

        try {
            statement = controller.getStatement();
            resultset = statement.executeQuery("SELECT * FROM tahoe_db.platform;");

            ArrayList<PlatfromServiceController> platform;

            platform = mapResultSetToPlatform(resultset);

            for (PlatfromServiceController plat : platform) {

                System.out.println(plat.toString());
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {

        }
        try {
            controller.closeConnection(connection, statement);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    private ArrayList<PlatfromServiceController> mapResultSetToPlatform(ResultSet resultSet) {


        ArrayList<PlatfromServiceController> platformList = new ArrayList();

        try {
            while (resultSet.next()) {
                PlatfromServiceController platform = new PlatfromServiceController();

                platform.setPlatname(resultSet.getString("platform_name"));
                platform.setPlaturl(resultSet.getString("platform_url"));
                platform.setPlatsalesfee(resultSet.getInt("sales_fee"));
                platformList.add(platform);
            }
        } catch (Exception e) {
            System.out.println("error mapping products from resultset - " + e.getMessage());
        }

        return platformList;
    }

    //Platform Delete Controller Method

    public void platformDeleteID() throws Exception {

        try {
            System.out.println("Which platform ID would you like to delete?");

            //Taking users input of an id they want to delete

            int platformID = scan.nextInt();

            connection = controller.getConnection();

            //The SQL deletion query

            preparedStatement = connection.prepareStatement("DELETE FROM tahoe_db.platform WHERE idplatform = ? ; ");

            //Locating and setting the platform ID to "?"
            preparedStatement.setInt(1, platformID);


            //executing prepared statement
            preparedStatement.executeUpdate();
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            //Executing the close Connection and prepared statement no matter what
            try {
                controller.closeConnection(connection, preparedStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //Platform Update Controller Method

    public void platformUpdate() throws Exception {

        try {

            //Asking for the ID the user wants to update

            System.out.println("For which ID number would you like to update?");
            int id = scan.nextInt();

            // Asking the user what kind of name they want to change it to

            System.out.println("Update for platform name");
            String platformname = scan.next();

            // Asking the user for the new url

            System.out.println("Update platform url");
            System.out.println("If url does not exist, enter n/a");
            String platformurl = scan.next();

            // Asking the user for the new sales fee

            System.out.println("Update sales fee");
            System.out.println("If no sales fee, keep blank");
            double updatesalesfee = scan.nextDouble();


            connection = controller.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE tahoe_db.product SET platform_name = ?," +
                    " platform_url = ?, sales_fee = ? WHERE idplatform = ?");

            // Labeling and locating what each "?" is in order
            // The first "?" takes in the platformname from the user
            preparedStatement.setString(1, platformname);

            //The second is setting a string value of url
            preparedStatement.setString(2, platformurl);

            //THe third one is setting a Double of sales fee
            preparedStatement.setDouble(3, updatesalesfee);

            //The last one is setting an int value which is the id
            preparedStatement.setInt(4, id);

        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            // Closing the connection no matter what from preventing memory leak
            try {
                controller.closeConnection(connection, preparedStatement);
            } catch (Exception e) {

                e.printStackTrace();
            }

        }

    }
}
