package main.ecommerce;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;


public class ProductServiceController {

    private int productID;
    private String proname;
    private String prodescription;


    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private Connection connection = null;
    private ConnectionController controller;
    ResultSet resultset = null;
    Scanner scan = new Scanner(System.in);

    ProductServiceController() {
        this.controller = new ConnectionController();
        }


        // the getters and setters for the product columns

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getProdescription() {
        return prodescription;
    }

    public void setProdescription(String prodescription) {
        this.prodescription = prodescription;
    }


    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", proname='" + proname + '\'' +
                ", prodescription='" + prodescription + '\'' +
                '}';
    }



    // Where the product controller starts

    public static void main(String[] args) throws Exception {

        ProductServiceController service = new ProductServiceController();
        ECom_Controller eCom_controller = new ECom_Controller();

        ConnectionController.getConnection();

        System.out.println("Press 1 to see the database options");
       int m = service.scan.nextInt();

        if (m == 1){

           eCom_controller.menu();
        }

        try {

            do {
                eCom_controller.switchstatementproduct();

            }while ( service.scan.next() != null);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void productinsert() throws Exception {

        try {

            connection = controller.getConnection();


            System.out.println("Enter product name");
            String product_name = scan.next();

            System.out.println("Enter the product description");
            String product_description = scan.next();

            preparedStatement = connection.prepareStatement("INSERT INTO product(product_name, product_description)"+"VALUES(?,?) ");

            preparedStatement.setString(1,product_name);
            preparedStatement.setString(2,product_description);

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



    public void productList() throws Exception {

        statement = controller.getStatement();
        resultset = statement.executeQuery("SELECT * FROM tahoe_db.product;");

        ArrayList<ProductServiceController> products;

        products = mapResultSetToProducts(resultset);

        for (ProductServiceController p : products) {

            System.out.println(p.toString());
        }

        try {
            controller.closeConnection(connection, statement);
        }catch (Exception e) {

            e.printStackTrace();
        }


    }

    private ArrayList<ProductServiceController> mapResultSetToProducts(ResultSet resultSet) {


        ArrayList<ProductServiceController> productList = new ArrayList();

        // ResultSet is initially before the first data set
        try {
            while (resultSet.next()) {
                ProductServiceController p = new ProductServiceController();
                p.setProdescription(resultSet.getString("product_description"));
                p.setProname(resultSet.getString("product_name"));
                p.setProductID(resultSet.getInt("idproducts"));
                productList.add(p);
            }
        } catch (Exception e) {
            System.out.println("error mapping products from resultset - " + e.getMessage());
        }

        return productList;
    }


    public void deleteproductID() throws Exception {

        System.out.println("Which product ID would you like to delete?");
        int productID = scan.nextInt();

        connection = controller.getConnection();

        preparedStatement =connection.prepareStatement("DELETE FROM tahoe_db.product WHERE idproducts = ? ; ");

        preparedStatement.setInt(1, productID);


        preparedStatement.executeUpdate();


       try {
           controller.closeConnection(connection, preparedStatement);
       }catch (Exception e){ e.printStackTrace();}

    }

    public void update() throws Exception{

        System.out.println("for which ID number would you like to update?");
        int id = scan.nextInt();
        System.out.println("update for product name");
        String name = scan.next();
        System.out.println("update product description");
        String description = scan.next();

        connection = controller.getConnection();
        preparedStatement = connection.prepareStatement("UPDATE tahoe_db.product SET product_name = ?, product_description = ? WHERE idproducts = ?");

        preparedStatement.setString(1,name);
        preparedStatement.setString(2,description);
        preparedStatement.setInt(3,id);

        try{
            controller.closeConnection(connection,preparedStatement);
        }catch (Exception e){

            e.printStackTrace();
        }

    }

}

