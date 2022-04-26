package com.example.checking;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.security.auth.callback.TextOutputCallback;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class searchResult {
    public static void show(Stage stage, ResultSet rs) throws SQLException {


        ArrayList search = getSearchedProducts(rs);
        TableView toShow = getProductsTableCategory(0);
        Pane main = new Pane();
        Scene searchResult = new Scene(main, 800, 600);
        toShow.getItems().addAll(search);
        toShow.setLayoutX(100);
        toShow.setLayoutY(200);
        toShow.setPrefHeight(200);
        toShow.setPrefWidth(600);

        Button back = new Button("Back");
        back.setLayoutY(20);
        back.setLayoutX(20);
        back.setOnAction(e->{
            HomePage.show(stage);
        });
        main.getChildren().addAll(toShow, back);
        stage.setScene(searchResult);



    }

    public static ArrayList<availableProducts> getSearchedProducts(ResultSet rs) throws SQLException {
        ArrayList<availableProducts> toRet = new ArrayList<>();
        while (rs.next()){

            availableProducts toAdd = new availableProducts(rs.getInt("productID"), rs.getString("productName"), rs.getFloat("price"), rs.getString("productDetails"), rs.getInt("quantityAvailable"));
            toRet.add(toAdd);
        }
        return toRet;
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

        return productsTable;
    }




}
