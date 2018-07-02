package main.ecommerce;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Prod_Plat_PriceServiceController{

    //Getters and Setter of PPP
    private int pppid;
    private int proid;



    private int platid;
    private double price;

    public int getPppid() {
        return pppid;
    }

    public void setPppid(int pppid) {
        this.pppid = pppid;
    }

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }

    public int getPlatid() {
        return platid;
    }

    public void setPlatid(int platid) {
        this.platid = platid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Prod_Plat_Price{" +
                "pppid=" + pppid +
                ", proid=" + proid +
                ", platid=" + platid +
                ", price=" + price +
                '}';
    }

    //PPP Method Controller starts from here

        private Statement statement = null;
        private PreparedStatement preparedStatement = null;
        private Connection connection = null;
        private ConnectionController controller;
        ResultSet resultset = null;
        Scanner scan = new Scanner(System.in);

    Prod_Plat_PriceServiceController() {

            this.controller = new ConnectionController();

        }


        //The main method
        //Helps connect with the witch statement in the ECom_Controller
        public static void main(String[] args) throws Exception {

            Prod_Plat_PriceServiceController prod_plat_price = new Prod_Plat_PriceServiceController();
            ECom_Controller eCom_controller = new ECom_Controller();

            //Getting connection
            ConnectionController.getConnection();

            //Informing the user to press the key one to see the direction
            System.out.println("Press 1 to see the database options");
            int m = prod_plat_price.scan.nextInt();

            //An if statement saying if the user presses the key 1, the direction will show up
            if (m == 1){

                eCom_controller.menu();
            }

            try {

                do {
                    eCom_controller.switchstatementppp();

                }while ( prod_plat_price.scan.next() != null);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void pppinsert() throws Exception {

            try {

                connection = controller.getConnection();


                //Asking the user to enter the product id
                System.out.println("Enter product id name");
                int product_id = scan.nextInt();

                //Asking the user to enter the platform id
                System.out.println("Enter the platform id");
                int platform_id = scan.nextInt();

                //Asking the user to enter the price
                System.out.println("Enter the the price");
                double price = scan.nextDouble();

                // Connecting the  insert SQL query with the prepared statement
                preparedStatement = connection.prepareStatement("INSERT INTO product(product_id, platform_id, price)"+"VALUES(?,?,?) ");

                // Adding information to the '?' above
                //By informing which ? is what variable
                // The first ? is the product_id
                preparedStatement.setInt(1,product_id);
                //The second ? is the platform id
                preparedStatement.setInt(2,platform_id);
                //The third ? is the price variable
                preparedStatement.setDouble(3,price);


                //Executing the prepared statement
                preparedStatement.executeUpdate();

            }catch (SQLException e) {
                e.printStackTrace();}

            try {

                controller.closeConnection(connection, preparedStatement);

            }catch (Exception e) {
                e.printStackTrace(); }

        }



        //The Lisy method for the ppp
        public void pppList() throws Exception {

        //setting the statement in the ppp class to the getstatement method in the ConnectionController
            // Not a prepared statement because we did not have to set and inform any values/ variables
            //We didn't have to PREPARE the statement
            statement = controller.getStatement();
            //Setting the resultset to the SQL query of SELECT*FROM
            resultset = statement.executeQuery("SELECT * FROM tahoe_db.product_platform_prices;");

            ArrayList<Prod_Plat_PriceServiceController> ppp = new ArrayList();

            ppp = mapResultSetToPPP(resultset);

            //Print out the resultset in each Array List
            for (Prod_Plat_PriceServiceController p : ppp) {

                System.out.println(p.toString());
            }

            try {
                controller.closeConnection(connection, statement);
            }catch (Exception e) {

                e.printStackTrace();
            }



        }

        private ArrayList<Prod_Plat_PriceServiceController> mapResultSetToPPP(ResultSet resultSet) {


            ArrayList<Prod_Plat_PriceServiceController> List = new ArrayList();

            // ResulSet is initially before the first data set
            try {

                while (resultSet.next()) {
                    Prod_Plat_PriceServiceController p = new Prod_Plat_PriceServiceController();
                    p.setPppid(resultSet.getInt("idproduct_platform_prices"));
                    p.setPrice(resultSet.getDouble("price"));
                    p.setPlatid(resultSet.getInt("platform_id"));
                    p.setProid(resultSet.getInt("product_id"));

                    List.add(p);
                }
            } catch (Exception e) {
                System.out.println("error mapping products from resultset - " + e.getMessage());
            }

            return List;
        }


        // THe deletion method for ppp
        public void pppdeleteID() throws Exception {


            System.out.println("Which product ID would you like to delete?");
            int pppID = scan.nextInt();

            preparedStatement =connection.prepareStatement("DELETE FROM tahoe_db.product_platform_prices WHERE idproduct_platform_prices = ? ; ");

            preparedStatement.setInt(1, pppID);


            preparedStatement.executeUpdate();


            try {
                controller.closeConnection(connection, preparedStatement);
            }catch (Exception e){ e.printStackTrace();}

        }

        // THe update method for ppp
        public void pppupdate() throws Exception{

            System.out.println("for which ID would you like to update?");
           int pppid = scan.nextInt();
            System.out.println("update for product ID");
            int productid = scan.nextInt();
            System.out.println("update platform ID");
            int platformid = scan.nextInt();
            System.out.println("update for prices");
            double prices = scan.nextDouble();

            preparedStatement = connection.prepareStatement("UPDATE tahoe_db.product_platform_prices SET product_id = ?, platform_id = ?, prices = ? WHERE idproduct_platform_prices = ?");

            preparedStatement.setInt(1,productid);
            preparedStatement.setInt(2,platformid);
            preparedStatement.setDouble(3,prices);
            preparedStatement.setInt(4,pppid);

            try{
                controller.closeConnection(connection,preparedStatement);
            }catch (Exception e){

                e.printStackTrace();
            }

        }

    }

