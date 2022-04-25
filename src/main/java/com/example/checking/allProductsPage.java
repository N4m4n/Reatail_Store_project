package com.example.checking;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Queue;

public class allProductsPage {

    public static void show(Stage stage, int category){
        Pane main = new Pane();
        TableView categoryWiseProducts = getProductsTableCategory(category);
        categoryWiseProducts.setLayoutY(100);
        categoryWiseProducts.setLayoutX(100);
        categoryWiseProducts.setPrefHeight(300);
        categoryWiseProducts.setPrefWidth(600);
        Label Add = new Label("Add");
        Add.setLayoutX(200);
        Add.setLayoutY(450);

        Button back = new Button("Back");
        back.setLayoutX(20);
        back.setLayoutY(20);
        back.setOnAction(e->{
            HomePage.show(stage);
        });

        TextField quant = new TextField();
        quant.setLayoutY(450);
        quant.setLayoutX(240);
        quant.setPrefWidth(60);

        Label items = new Label("selected items to the cart.");
        items.setLayoutY(450);
        items.setLayoutX(310);

        Button addItems = new Button();
        addItems.setText("Add to cart");
        addItems.setLayoutX(450);
        addItems.setLayoutY(450);

        addItems.setOnAction(e->{
            availableProducts curr = (availableProducts) categoryWiseProducts.getSelectionModel().getSelectedItem();
            int quantityToAdd = 0;
            try{
                quantityToAdd = Integer.valueOf(quant.getText());

            }catch(Exception e2){
                Dialog<String> dialog = new Dialog<String>();

                dialog.setTitle("Invalid");
                dialog.setHeight(200);
                dialog.setWidth(400);
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                dialog.setContentText("Please enter a valid quantity");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();

            }

            System.out.println(curr.getDescription()+" "+quantityToAdd);
        });

        main.getChildren().addAll(categoryWiseProducts, Add, quant, items, addItems, back);
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
