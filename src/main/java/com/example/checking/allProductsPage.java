package com.example.checking;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class allProductsPage {

    public static void show(Stage stage, int category){
        Pane main = new Pane();
        TableView categoryWiseProducts = getProductsTableCategory(category);
        categoryWiseProducts.setLayoutY(100);
        categoryWiseProducts.setLayoutX(100);
        categoryWiseProducts.setPrefHeight(300);
        categoryWiseProducts.setPrefWidth(600);



        main.getChildren().addAll(categoryWiseProducts);
        Scene productsScene = new Scene(main, 800, 600);
        stage.setScene(productsScene);
    }

    public static TableView getProductsTableCategory(int category){
        TableView productsTable = new TableView();

        TableColumn<availableProducts, String> productID = new TableColumn();
        productID.setText("Product ID");
        productID.setPrefWidth(20);
        productID.setResizable(false);
        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));

        TableColumn<availableProducts, String> prodName = new TableColumn();
        prodName.setText("Product Name");
        prodName.setPrefWidth(150);
        prodName.setResizable(false);
        prodName.setCellValueFactory(new PropertyValueFactory<>("prodName"));


        TableColumn<availableProducts, String> description = new TableColumn();
        description.setText("Description");
        description.setPrefWidth(230);
        description.setResizable(false);
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<availableProducts, String> price = new TableColumn();
        price.setText("Price");
        price.setPrefWidth(100);
        price.setResizable(false);
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<availableProducts, String> quantityAvailable = new TableColumn();
        quantityAvailable.setText("Quantity Available");
        quantityAvailable.setPrefWidth(100);
        quantityAvailable.setResizable(false);
        quantityAvailable.setCellValueFactory(new PropertyValueFactory<>("quantityAvailable"));


        productsTable.getColumns().addAll(productID, prodName, description, price, quantityAvailable);

        //TODO: Make a function that returns an Arraylist of product for this category.
        productsTable.getItems().addAll(new availableProducts(1, "Halwa", 35.5F, "Very nice", 30));


        return productsTable;
    }
}
