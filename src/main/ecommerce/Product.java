package main.ecommerce;

public class Product {


    private int productID;
    private String proname;
    private String prodescription;

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





}
