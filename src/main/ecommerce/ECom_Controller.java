package main.ecommerce;

import java.util.Scanner;

public class ECom_Controller {

    Scanner scan = new Scanner(System.in);
    ProductServiceController pscontroller = new ProductServiceController();

    public void menu(){

            System.out.println("Press 2 for LIST");
            System.out.println("Press 3 for INSERT");
            System.out.println("Press 4 for DELETE");
            System.out.println("Press 5 for UPDATE");
            return;
        }

        public void switchstatementproduct()throws Exception{

        int input = scan.nextInt();

        switch (input){

            case 2: pscontroller.productList();

            return;


            case 3: pscontroller.productinsert();

            return;

            case 4: pscontroller.deleteproductID();

            return;


            case 5: pscontroller.update();


            }
        }
    }
