package com.example.checking;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class profilePage {
    public static void show(Stage stage) throws SQLException {


        Pane main = new Pane();

        Label addr = new Label("Address");
        addr.setLayoutY(20);
        addr.setLayoutX(100);

        Button back = new Button("Back");
        back.setLayoutY(20);
        back.setLayoutX(20);
        back.setOnAction(e->{
            HomePage.show(stage);
        });

        ListView<String> addresses = new ListView<String>();
        ArrayList<String> allAddresses = new ArrayList<>();
        allAddresses.add("lol");
        //        TODO: add the addresses
        addresses.getItems().addAll(allAddresses);
        addresses.setLayoutX(100);
        addresses.setLayoutY(60);
        addresses.setPrefHeight(150);
        addresses.setPrefWidth(600);

        Label Cart = new Label("Cart");
        Cart.setLayoutY(232);
        Cart.setLayoutX(100);

        ListView<String> cartsList = new ListView<String>();
        ArrayList<String> carts = new ArrayList<>();
//        TODO: add the items to the cart
//        carts.add("cart");
        ArrayList<String> temp = getItemsInCart(HelloApplication.customerID);
        int total = Integer.valueOf(temp.get(temp.size()-1));
        temp.remove(temp.size()-1);
        carts.addAll(temp);
        cartsList.getItems().addAll(carts);
        cartsList.setLayoutX(100);
        cartsList.setLayoutY(250);
        cartsList.setPrefHeight(150);
        cartsList.setPrefWidth(600);

        Label ordersLabel = new Label("Orders");
        ordersLabel.setLayoutY(405);
        ordersLabel.setLayoutX(100);

        TableView ordersTable = getOrdersTable(HelloApplication.customerID);
        ordersTable.setLayoutX(100);
        ordersTable.setLayoutY(420);
        ordersTable.setEditable(false);
        ordersTable.setPrefHeight(150);
        ordersTable.setPrefWidth(600);

        ordersTable.setRowFactory( tv -> {
            TableRow<order> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    order rowData = row.getItem();
                    int orderIDchosen = rowData.getOrderID();
                    System.out.println(orderIDchosen);
                    orderPage.show(orderIDchosen, stage);

                }
            });
            return row ;
        });

        Button orderButton = new Button("Order Now");
        orderButton.setLayoutY(270);
        orderButton.setLayoutX(5);
        orderButton.setOnAction(e->{
            orderConfirmation.show(stage);
        });

        main.getChildren().addAll(orderButton, addresses, addr, cartsList, Cart, ordersLabel, ordersTable, back);
        Scene profileScene = new Scene(main, 800, 600);
        stage.setScene(profileScene);


    }



    public static ArrayList<String> getItemsInCart(int custID) throws SQLException {
        String query = """
                with product_list (productID, productName, price) as (select productID, productName, price from products) 
                select product_list.productID, productName, quantity, price*quantity as subtotal from product_list natural join cart where cart.customerID = """+custID;

        ResultSet rs = HelloApplication.retrieveData(query, 1);
        ArrayList<String> allItems = new ArrayList<>();
        int totalCartCost = 0;
        while(rs.next()){
            allItems.add(rs.getString("productName")+" x "+rs.getString("quantity")+" = "+rs.getString("subTotal"));
            totalCartCost += Double.parseDouble(rs.getString("subTotal"));
        }

        allItems.add(""+totalCartCost);
        return allItems;
    }
    public static TableView getOrdersTable(int customerId){
        TableView orderTable = new TableView();

        TableColumn<order, String> orderID = new TableColumn();
        orderID.setText("Order ID");
        orderID.setPrefWidth(200);
        orderID.setResizable(false);
        orderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));

        TableColumn<order, String> date = new TableColumn();
        date.setText("Date Of Order");
        date.setPrefWidth(200);
        date.setResizable(false);
        date.setCellValueFactory(new PropertyValueFactory<>("dateOfPurchase"));


        TableColumn<order, String> price = new TableColumn();
        price.setText("Price");
        price.setPrefWidth(200);
        price.setResizable(false);
        price.setCellValueFactory(new PropertyValueFactory<>("price"));


        orderTable.getColumns().addAll(orderID, date, price);

        //TODO: Make a function that returns an Arraylist of orders for this customer.
        orderTable.getItems().addAll(new order(2,"3",4));


        return orderTable;
    }

}
