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

            ConnectionController.getConnection();

            System.out.println("Press 1 to see the database options");
            int m = service.scan.nextInt();

            if (m == 1){

                eCom_controller.menu();
            }

            try {

                do {
                    eCom_controller.switchstatementplatform();

                }while ( service.scan.next() != null);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


      // Method controller for the database

        public void platformInsert() throws Exception {

            try {

                connection = controller.getConnection();


                System.out.println("Enter platform name");
                String platform_name = scan.next();

                System.out.println("Enter the platform url");
                String platform_url = scan.next();

                    System.out.println("Enter the sales fee");

                double sales_fee = scan.nextDouble();



                preparedStatement = connection.prepareStatement("INSERT INTO platform(platform_name, platform_url, sales_fee)"+"VALUES(?,?,?) ");

                preparedStatement.setString(1,platform_name);
                preparedStatement.setString(2,platform_url);
                preparedStatement.setDouble(3,sales_fee);


                preparedStatement.executeUpdate();

                System.out.println();
                System.out.println("Inserted");
            }catch (SQLException e) {
                e.printStackTrace();}

            try {

                controller.closeConnection(connection, preparedStatement);

            }catch (Exception e) {
                e.printStackTrace(); }

        }



        public void platformList() throws Exception {

            statement = controller.getStatement();
            resultset = statement.executeQuery("SELECT * FROM tahoe_db.platform;");

            ArrayList<PlatfromServiceController> platform;

           platform = mapResultSetToPlatform(resultset);

            for (PlatfromServiceController plat : platform) {

                System.out.println(plat.toString());
            }

            try {
                controller.closeConnection(connection, statement);
            }catch (Exception e) {

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


        public void platformDeleteID() throws Exception {

            System.out.println("Which platform ID would you like to delete?");
            int platformID = scan.nextInt();

            connection = controller.getConnection();

            preparedStatement =connection.prepareStatement("DELETE FROM tahoe_db.platform WHERE idplatform = ? ; ");

            preparedStatement.setInt(1, platformID);


            preparedStatement.executeUpdate();


            try {
                controller.closeConnection(connection, preparedStatement);
            }catch (Exception e){ e.printStackTrace();}

        }

        public void platformUpdate() throws Exception{

            System.out.println("For which ID number would you like to update?");
            int id = scan.nextInt();

            System.out.println("Update for platform name");
            String platformname = scan.next();

            System.out.println("Update platform url");
            System.out.println("If url does not exist, enter n/a");
            String platformurl = scan.next();

            System.out.println("Update sales fee");
            System.out.println("If no sales fee, keep blank");
            double updatesalesfee = scan.nextDouble();


            connection = controller.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE tahoe_db.product SET platform_name = ?," +
                    " platform_url = ?, sales_fee = ? WHERE idplatform = ?");

            preparedStatement.setString(1,platformname);
            preparedStatement.setString(2,platformurl);
            preparedStatement.setDouble(3,updatesalesfee);
            preparedStatement.setInt(4,id);

            try{
                controller.closeConnection(connection,preparedStatement);
            }catch (Exception e){

                e.printStackTrace();
            }

        }

    }
