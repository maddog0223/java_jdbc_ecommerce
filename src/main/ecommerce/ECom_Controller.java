package main.ecommerce;

import java.util.Scanner;

public class ECom_Controller {

    Scanner scan = new Scanner(System.in);
    ProductServiceController pscontroller = new ProductServiceController();
    PlatfromServiceController platscontorller = new PlatfromServiceController();
    Prod_Plat_PriceServiceController pppconteroller = new Prod_Plat_PriceServiceController();


    // General Description method for key input in a switch statement
    // Direction to which number does what to the database
    public void menu() {

        System.out.println("Press 2 for LIST");
        System.out.println("Press 3 for INSERT");
        System.out.println("Press 4 for DELETE");
        System.out.println("Press 5 for UPDATE");
        return;
    }

    // Switch Controller Statement for Product

    public void switchstatementproduct() throws Exception {

        int input = scan.nextInt();

        switch (input) {

            case 2:
                pscontroller.productList();

                return;


            case 3:
                pscontroller.productinsert();

                return;

            case 4:
                pscontroller.deleteproductID();

                return;


            case 5:
                pscontroller.update();


        }
    }

    // Switch Controller Statement for Platform

    public void switchstatementplatform() throws Exception{

        int platinput = scan.nextInt();
        switch (platinput){

            case 2:
                platscontorller.platformList();

                return;

            case 3:
                platscontorller.platformInsert();

                return;

            case 4:
                platscontorller.platformDeleteID();

                return;

            case 5:
                platscontorller.platformUpdate();

                return;
        }
    }

    // Switch Controller Statement for PPP

    public void switchstatementppp() throws Exception {

        int input = scan.nextInt();

        switch (input) {

            case 2:
                pppconteroller.pppList();

                return;


            case 3:
                pppconteroller.pppinsert();

                return;

            case 4:
                pppconteroller.pppdeleteID();

                return;


            case 5:
                pppconteroller.pppupdate();


        }
    }


}
