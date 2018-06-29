package main.ecommerce;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Prod_Plat_Price {

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

    Prod_Plat_Price() {

            this.controller = new ConnectionController();

        }


        public static void main(String[] args) throws Exception {

            Prod_Plat_Price prod_plat_price = new Prod_Plat_Price();
            ECom_Controller eCom_controller = new ECom_Controller();

            ConnectionController.getConnection();

            System.out.println("Press 1 to see the database options");
            int m = prod_plat_price.scan.nextInt();

            if (m == 1){

                eCom_controller.menu();
            }

            try {

                do {
                    eCom_controller.switchstatementproduct();

                }while ( prod_plat_price.scan.next() != null);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void pppinsert() throws Exception {

            try {

                connection = controller.getConnection();


                System.out.println("Enter product id name");
                int product_id = scan.nextInt();

                System.out.println("Enter the platform id");
                int platform_id = scan.nextInt();

                System.out.println("Enter the the price");
                double price = scan.nextDouble();
                preparedStatement = connection.prepareStatement("INSERT INTO product(product_id, platform_id, price)"+"VALUES(?,?,?) ");

                preparedStatement.setInt(1,product_id);
                preparedStatement.setInt(2,platform_id);
                preparedStatement.setDouble(3,price);


                preparedStatement.executeUpdate();

            }catch (SQLException e) {
                e.printStackTrace();}

            try {

                controller.closeConnection(connection, preparedStatement);

            }catch (Exception e) {
                e.printStackTrace(); }

        }



        public void pppList() throws Exception {

            statement = controller.getStatement();
            resultset = statement.executeQuery("SELECT * FROM tahoe_db.product_platform_prices;");

            ArrayList<Prod_Plat_Price> ppp = new ArrayList();

            ppp = mapResultSetToPPP(resultset);

            for (Prod_Plat_Price p : ppp) {

                System.out.println(p.toString());
            }

            try {
                controller.closeConnection(connection, statement);
            }catch (Exception e) {

                e.printStackTrace();
            }


        }

        private ArrayList<Prod_Plat_Price> mapResultSetToPPP(ResultSet resultSet) {


            ArrayList<Prod_Plat_Price> List = new ArrayList();

            // ResultSet is initially before the first data set
            try {
                while (resultSet.next()) {
                    Prod_Plat_Price p = new Prod_Plat_Price();
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


        public void deletepppID() throws Exception {

            resultset = statement.executeQuery("select * from tahoe_db.product_platform_prices");

            System.out.println("Which product ID would you like to delete?");
            int pppID = scan.nextInt();

            preparedStatement =connection.prepareStatement("DELETE FROM tahoe_db.product_platform_prices WHERE idproduct_platform_prices = ? ; ");

            preparedStatement.setInt(1, pppID);


            preparedStatement.executeUpdate();


            try {
                controller.closeConnection(connection, preparedStatement);
            }catch (Exception e){ e.printStackTrace();}

        }

        public void update() throws Exception{

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

