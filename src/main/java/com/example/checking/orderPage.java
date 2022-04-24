package com.example.checking;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class orderPage {

    public static void show(int orderID, Stage stage){
        Pane main = new Pane();
        Scene orderScene = new Scene(main, 800, 600);
        TableView productList = getProductsTable(orderID);
        productList.setLayoutX(100);
        productList.setLayoutY(150);
        productList.setPrefHeight(300);
        productList.setPrefWidth(600);


        main.getChildren().addAll(productList);
        stage.setScene(orderScene);


    }



    public static TableView getProductsTable(int orderId){
        TableView productsTable = new TableView();

        TableColumn<orderedProducts, String> prodName = new TableColumn();
        prodName.setText("Product Name");
        prodName.setPrefWidth(200);
        prodName.setResizable(false);
        prodName.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<orderedProducts, String> quantity = new TableColumn();
        quantity.setText("Quantity");
        quantity.setPrefWidth(200);
        quantity.setResizable(false);
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));


        TableColumn<orderedProducts, String> cost = new TableColumn();
        cost.setText("Cost");
        cost.setPrefWidth(200);
        cost.setResizable(false);
        cost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));


        productsTable.getColumns().addAll(prodName, quantity, cost);

        //TODO: Make a function that returns an Arraylist of product for this order ID.
        productsTable.getItems().addAll(new orderedProducts("Halwa",2,50));


        return productsTable;
    }

}

