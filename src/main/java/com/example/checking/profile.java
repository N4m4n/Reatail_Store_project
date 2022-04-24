package com.example.checking;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class profile {
    public static void show(Stage stage){


        Pane main = new Pane();

        Label addr = new Label("Address");
        addr.setLayoutY(20);
        addr.setLayoutX(100);

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
        carts.add("cart");
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



        main.getChildren().addAll(addresses, addr, cartsList, Cart, ordersLabel, ordersTable);
        Scene profileScene = new Scene(main, 800, 600);
        stage.setScene(profileScene);


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

        //TODO: Make a function that returns an Arraylist of orders.
        orderTable.getItems().addAll(new order(2,"3",4));


        return orderTable;
    }

}
