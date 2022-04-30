package com.example.checking;

import javafx.beans.property.ReadOnlySetProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.security.KeyStore;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class complaintsPage {
    public static void show(Stage stage) throws SQLException {
        Pane main = new Pane();
        TableView complaints = getComplaintsTable();
        complaints.setLayoutY(100);
        complaints.setLayoutX(100);
        complaints.getItems().addAll(getComplaints());


        Button back = new Button("Back");
        back.setLayoutY(20);
        back.setLayoutX(20);
        back.setOnAction(e->{
            try {
                employeeHomePage.show(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        main.getChildren().addAll(back);


        complaints.setRowFactory( tv -> {
            TableRow<complaint> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    complaint rowData = row.getItem();
                    String comment = rowData.getComment();
                    ArrayList<String> temp = new ArrayList<>();
                    temp.add(comment);
                    HelloApplication.showPopup(stage, temp);
                }
            });
            return row ;
        });

        TextField ans = new TextField();
        ans.setLayoutY(500);
        ans.setLayoutX(300);


        Button resolve = new Button("Resolve");
        resolve.setLayoutX(600);
        resolve.setLayoutY(500);
        resolve.setOnAction(e->{
            complaint curr = (complaint) complaints.getSelectionModel().getSelectedItem();

            String query = "update into complainthandles(employeeID, outcome, dateOfClosure) values ("+employeeLogin.employeeID+", \'"+ans.getText()+"\', curdate()) where customerID = "+curr.getCustomerID()+" and productID = "+curr.getProductID()+" and comments = \'"+curr.getComment()+"\' and complaintDate = \'"+curr.getDate()+"\'";
            HelloApplication.sendData(query, 4);
        });


        
        
        
        main.getChildren().addAll(complaints, resolve, ans);
        Scene complaintScene = new Scene(main, 800, 600);
        stage.setScene(complaintScene);

    }
    public static TableView getComplaintsTable() throws SQLException {
        TableView productsTable = new TableView();

        TableColumn<complaint, String> customerID = new TableColumn();
        customerID.setText("Customer id");
        customerID.setPrefWidth(100);
        customerID.setResizable(false);
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        TableColumn<complaint, String> productID = new TableColumn();
        productID.setText("Product id");
        productID.setPrefWidth(120);
        productID.setResizable(false);
        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));


        TableColumn<complaint, String> productName = new TableColumn();
        productName.setText("Product Name");
        productName.setPrefWidth(200);
        productName.setResizable(false);
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<complaint, String> date = new TableColumn();
        date.setText("Date");
        date.setPrefWidth(100);
        date.setResizable(false);
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        productsTable.getColumns().addAll(customerID, productID, productName, date);
        return productsTable;
    }

    public static ArrayList<complaint> getComplaints() throws SQLException {
        String query = "select customerID, productID, productName, comments, complaintDate from complaints natural join products";
        ResultSet rs = HelloApplication.retrieveData(query, 4);
        ArrayList<complaint> toRet = new ArrayList<>();
        if(rs.next()){
            complaint toAdd = new complaint(rs.getInt("customerID"),rs.getInt("productID"),rs.getString("productName"), rs.getString("comments"), rs.getString("complaintDate"));
            toRet.add(toAdd);
        }
        return toRet;
    }

}
