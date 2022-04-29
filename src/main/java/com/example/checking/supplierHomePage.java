package com.example.checking;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class supplierHomePage {

    public static void show(Stage stage) throws SQLException {
        Pane main = new Pane();

        TableView categoryWiseProducts = getProductsTableCategory(getCategory());
        categoryWiseProducts.setLayoutY(100);
        categoryWiseProducts.setLayoutX(100);
        categoryWiseProducts.setPrefHeight(300);
        categoryWiseProducts.setPrefWidth(600);
        Label Add = new Label("Sell");
        Add.setLayoutX(200);
        Add.setLayoutY(450);

        Button back = new Button("Logout");
        back.setLayoutX(20);
        back.setLayoutY(20);
        back.setOnAction(e->{
            supplierLogin.show(stage);
        });

        TextField quant = new TextField();
        quant.setLayoutY(450);
        quant.setLayoutX(240);
        quant.setPrefWidth(60);

        Label items = new Label("selected items to the retail store for .");
        items.setLayoutY(450);
        items.setLayoutX(310);

        TextField price = new TextField();
        price.setLayoutY(450);
        price.setLayoutX(500);
        main.getChildren().add(price);


        Button addItems = new Button();
        addItems.setText("Sell");
        addItems.setLayoutX(450);
        addItems.setLayoutY(490);

        addItems.setOnAction(e->{
            availableProducts curr = (availableProducts) categoryWiseProducts.getSelectionModel().getSelectedItem();
            int quantityToAdd = 0;
            float sellingPrice = 0.0F;
            try{
                quantityToAdd = Integer.valueOf(quant.getText());
                sellingPrice = Float.valueOf(price.getText());

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
            if(quantityToAdd <0){
                HelloApplication.showError("Insufficient", "Invalid entry");
            }else{

                String query = "insert into supplies(supplierID, productID, costPerProduct, quantity) values ("+supplierLogin.supplierID+", "+curr.getProductID()+", "+sellingPrice+", "+quantityToAdd+")";
                HelloApplication.sendData(query, 3);
            }

        });

        main.getChildren().addAll(categoryWiseProducts, Add, quant, items, addItems, back);
        Scene productsScene = new Scene(main, 800, 600);
        stage.setScene(productsScene);
    }

    public static int getCategory() throws SQLException {
        String query = "select category from supplierinfo where supplierID = "+supplierLogin.supplierID;
        ResultSet rs = HelloApplication.retrieveData(query, 2);
        if(rs.next()){
            String category = rs.getString("category");
            if(category.equals("Eatables")){
                return 1;
            }else if(category.equals("Apparels")){
                return 2;
            }else if(category.equals("Furniture")){
                return 3;
            }else if(category.equals("Electronics")){
                return 4;
            }
        }else{
            return -1;
        }


        return -1;
    }

    public static TableView getProductsTableCategory(int category) throws SQLException {
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


        productsTable.getColumns().addAll(productID, prodName, description);




        switch(category){
            case 1:
                productsTable.getItems().addAll(getListOfProducts("Eatables"));
                break;
            case 2:
                productsTable.getItems().addAll(getListOfProducts("Apparels"));
                break;
            case 3:
                productsTable.getItems().addAll(getListOfProducts("Furniture"));
                break;
            case 4:
                productsTable.getItems().addAll(getListOfProducts("Electronics"));
                break;
        }


        return productsTable;
    }

    public static ArrayList<availableProducts> getListOfProducts(String category) throws SQLException {
        String query = "select * from products where category = \'"+category+"\'";
        ResultSet rs = HelloApplication.retrieveData(query, 3);
        ArrayList<availableProducts> toRet = new ArrayList<>();
        while (rs.next()) {
            availableProducts toAdd = new availableProducts(rs.getInt("productID"), rs.getString("productName"), rs.getFloat("price"), rs.getString("productDetails"), rs.getInt("quantityAvailable"));
            toRet.add(toAdd);
        }
        return toRet;

    }
}
