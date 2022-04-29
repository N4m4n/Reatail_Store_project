package com.example.checking;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class orderPage {

    public static void show(int orderID, Stage stage) throws SQLException {
        Pane main = new Pane();
        Scene orderScene = new Scene(main, 800, 600);
        TableView productList = getProductsTable(orderID);
        productList.setLayoutX(100);
        productList.setLayoutY(150);
        productList.setPrefHeight(300);
        productList.setPrefWidth(600);

        Button back = new Button("Back");
        back.setLayoutX(20);
        back.setLayoutY(20);
        back.setOnAction(e->{
            try {
                profilePage.show(stage);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        main.getChildren().addAll(productList, back);
        stage.setScene(orderScene);
    }





    public static TableView getProductsTable(int orderId) throws SQLException {
        TableView productsTable = new TableView();
        TableColumn<orderedProducts, String> prodID = new TableColumn();
        prodID.setText("Product ID");
        prodID.setPrefWidth(200);
        prodID.setResizable(false);
        prodID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        TableColumn<orderedProducts, String> quantity = new TableColumn();
        quantity.setText("Quantity");
        quantity.setPrefWidth(200);
        quantity.setResizable(false);
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productsTable.getColumns().addAll(prodID, quantity);
        productsTable.getItems().addAll(getOrderedProds(orderId));
        return productsTable;
    }

    public static ArrayList<orderedProducts> getOrderedProds(int orderID) throws SQLException {
        ArrayList<orderedProducts> toRet = new ArrayList<>();
        String query = "select productID, quantity from orderIncludes where orderIncludes.orderID = "+orderID;
        ResultSet rs = HelloApplication.retrieveData(query, 0);
        while(rs.next()){
            System.out.println(rs.getInt("productID")+" "+ rs.getInt("quantity"));
            toRet.add(new orderedProducts(rs.getInt("productID"), rs.getInt("quantity")));
        }
        return toRet;
    }

}

