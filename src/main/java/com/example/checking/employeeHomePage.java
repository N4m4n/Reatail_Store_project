package com.example.checking;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class employeeHomePage {
    public static void show(Stage stage){
        Pane main = new Pane();

        Button back = new Button("Back");
        back.setLayoutX(20);
        back.setLayoutY(20);
        back.setOnAction(e->{
            employeeLogin.show(stage);
        });


        TextField customerSearch = new TextField();
        customerSearch.setLayoutX(100);
        customerSearch.setLayoutY(60);
        customerSearch.setPrefWidth(100);
        
        Button CustomerSearch = new Button("Search customer by ID");
        CustomerSearch.setLayoutX(220);
        CustomerSearch.setLayoutY(60);

        
        TextField supplierSearch = new TextField();
        supplierSearch.setLayoutX(100);
        supplierSearch.setLayoutY(90);
        supplierSearch.setPrefWidth(100);

        Button SupplierSearch = new Button("Search supplier by ID");
        SupplierSearch.setLayoutX(220);
        SupplierSearch.setLayoutY(90);


        CustomerSearch.setOnAction(e->{
            String query = "select * from customerg_employee_view where customerID = "+customerSearch.getText();
            ResultSet rs = HelloApplication.retrieveData(query, 4);
            ArrayList<String> custDetails = new ArrayList<>();
            try {
                if(rs.next()){
                    custDetails.add("Customer ID: "+ rs.getInt("customerID"));
                    custDetails.add("First Name: "+ rs.getString("customerID"));
                    custDetails.add("Last Name: "+ rs.getString("customerID"));
                    custDetails.add("Phone: "+ rs.getString("phoneNo"));
                    custDetails.add("Email: "+ rs.getString("emailID"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            HelloApplication.showPopup(stage, custDetails);

        });

        SupplierSearch.setOnAction(e->{
            String query = "select * from supplierg_employee_view where supplierID = "+supplierSearch.getText();
            ResultSet rs = HelloApplication.retrieveData(query, 4);
            ArrayList<String> suppDetails = new ArrayList<>();
            try {
                if(rs.next()){
                    suppDetails.add("Supplier ID: "+ rs.getInt("supplierID"));
                    suppDetails.add("Supplier Name: "+ rs.getString("supplierName"));
                    suppDetails.add("Category: "+ rs.getString("category"));
                    suppDetails.add("Email ID: "+ rs.getString("emailID"));
                    suppDetails.add("Phone: "+ rs.getString("phoneNo"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            HelloApplication.showPopup(stage, suppDetails);

        });

        main.getChildren().addAll(back, customerSearch, supplierSearch, SupplierSearch, CustomerSearch);







        Scene employeeHome = new Scene(main, 800, 600);
        stage.setScene(employeeHome);

    }
}
