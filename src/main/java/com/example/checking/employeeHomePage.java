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
    public static void show(Stage stage) throws Exception{
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

        Button Complaints = new Button("GO TO COMPLAINTS SECTION");
        Complaints.setLayoutX(600);
        Complaints.setLayoutY(200);

        Complaints.setOnAction(e->{
            try {
                complaintsPage.show(stage);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Button query1 = new Button("List all unused coupons");
        query1.setLayoutX(200);
        query1.setLayoutY(200);

        query1.setOnAction(e->{
            String query = "select couponID, minOrder, validTillDate, discount from coupons natural left outer join applies where orderID is null and validTillDate >= curdate()";
            ResultSet rs = HelloApplication.retrieveData(query, 4);
            ArrayList<String> arr = new ArrayList<>();
            while(true){
                try {
                    if (!rs.next()) {
                        break;
                    }else{
                        arr.add(rs.getString("couponID"));
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
            HelloApplication.showPopup(stage, arr);

        });

        Button query2 = new Button("Coupons used atleast twice");
        query2.setLayoutX(200);
        query2.setLayoutY(230);
        query2.setOnAction(e->{
            String query = "select couponID from coupons where 2 <= (select count(couponID) from applies where coupons.couponID = applies.couponID)";
            ResultSet rs = HelloApplication.retrieveData(query, 4);
            ArrayList<String> arr = new ArrayList<>();
            while(true){
                try {
                    if (!rs.next()) {
                        break;
                    }else{
                        arr.add(rs.getString("couponID"));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            HelloApplication.showPopup(stage, arr);
        });

        Button query4 = new Button("Average spending _ orderID");
        query4.setLayoutX(200);
        query4.setLayoutY(290);
        query4.setOnAction(e->{
            String query = "select orderID as OrderNo, avg(amount) as Average over (order by orderID rows unbounded preceding) as AvgSale from orders";
            ResultSet rs = HelloApplication.retrieveData(query, 4);
            ArrayList<String> arr = new ArrayList<>();
            while(true){
                try {
                    if (!rs.next()) {
                        break;
                    }else{
                        arr.add(rs.getString("orderNo")+" "+rs.getString("average"));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
            HelloApplication.showPopup(stage, arr);


        });

        Button query6 = new Button("Get category wise profit");
        query6.setLayoutX(200);
        query6.setLayoutY(350);
        query6.setOnAction(e->{

            String query = "with category_purchase (category, purchaseAmount) as (select category, sum(supplies.quantity*costPerProduct) from supplies natural join products group by products.category) select category, category_sold.sellingAmount - category_purchase.purchaseAmount as profit from (select category, sum(orderincludes.quantity*price) from orderincludes natural join products group by products.category) as category_sold(category, sellingAmount) natural join category_purchase";
            ArrayList<String> arr = new ArrayList<>();
            ResultSet rs = HelloApplication.retrieveData(query, 4);
            while(true){
                try {
                    if (!rs.next()) {
                        break;
                    }else{
                        arr.add(rs.getString("category")+" "+rs.getString("profit"));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
            HelloApplication.showPopup(stage, arr);

        });


        main.getChildren().addAll(query1, query2, query4, query6);


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

        main.getChildren().addAll(back, customerSearch, supplierSearch, SupplierSearch, CustomerSearch, Complaints);







        Scene employeeHome = new Scene(main, 800, 600);
        stage.setScene(employeeHome);

    }
}
