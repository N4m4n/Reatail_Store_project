package com.example.checking;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.shape.HLineTo;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class profilePage {
    public static void show(Stage stage) throws SQLException {


        Pane main = new Pane();

        Label addr = new Label("Address");
        addr.setLayoutY(20);
        addr.setLayoutX(100);

        Button back = new Button("Back");
        back.setLayoutY(20);
        back.setLayoutX(20);
        back.setOnAction(e->{
            HomePage.show(stage);
        });

        Button changePasswrd = new Button("Change Password");
        changePasswrd.setLayoutX(670);
        changePasswrd.setLayoutY(30);
        main.getChildren().add(changePasswrd);
        changePasswrd.setOnAction(e->{
            changePassword.show(stage);
        });


        TableView addresses = getAddressesTable(HelloApplication.customerID);
        ArrayList<address> addList = getAddresses(HelloApplication.customerID);
        addresses.getItems().addAll(addList);
        addresses.setLayoutX(100);
        addresses.setLayoutY(60);
        addresses.setPrefHeight(150);
        addresses.setPrefWidth(600);

        getAddresses(HelloApplication.customerID);

        Label Cart = new Label("Cart");
        Cart.setLayoutY(232);
        Cart.setLayoutX(100);

        TableView cartsList = getItemsInCartTable(HelloApplication.customerID);
        ArrayList<orderedProducts> temp = getItemsInCart(HelloApplication.customerID);


        cartsList.getItems().addAll(temp);

        cartsList.setLayoutX(100);
        cartsList.setLayoutY(250);
        cartsList.setPrefHeight(150);
        cartsList.setPrefWidth(600);

        Label cartTotal = new Label();
        cartTotal.setLayoutX(720);
        cartTotal.setLayoutY(300);
        cartTotal.setText(getCartTotal(HelloApplication.customerID));

        Label ordersLabel = new Label("Orders");
        ordersLabel.setLayoutY(405);
        ordersLabel.setLayoutX(100);

        TableView ordersTable = getOrdersTable(HelloApplication.customerID);
        ordersTable.setLayoutX(100);
        ordersTable.setLayoutY(420);
        ordersTable.setEditable(false);
        ordersTable.setPrefHeight(150);
        ordersTable.setPrefWidth(600);
        ordersTable.getItems().addAll(getOrdersList(HelloApplication.customerID));

        ordersTable.setRowFactory( tv -> {
            TableRow<order> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    order rowData = row.getItem();
                    int orderIDchosen = rowData.getOrderID();
                    System.out.println(orderIDchosen);
                    try {
                        orderPage.show(orderIDchosen, stage);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            });
            return row ;
        });

        Button orderButton = new Button("Order Now");
        orderButton.setLayoutY(270);
        orderButton.setLayoutX(5);
        orderButton.setOnAction(e->{
            try {
                orderConfirmation.show(stage);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        main.getChildren().addAll(cartTotal, orderButton, addr, cartsList, Cart, ordersLabel, ordersTable, back, addresses);
        Scene profileScene = new Scene(main, 800, 600);
        stage.setScene(profileScene);


    }
    public static ArrayList<address> getAddresses(int custID) throws SQLException {
        String query = "{call getAddressList("+custID+")}";
        ResultSet rs = HelloApplication.callFunction(query, 0);
        ArrayList<address> toRet = new ArrayList<>();
        while(rs.next()){
            address toAdd = new address(rs.getInt("addressID"), rs.getString("addressLine1"), rs.getInt("pincode"));
            System.out.println(rs.getInt("addressID")+" "+ rs.getString("addressLine1")+" "+rs.getInt("pincode"));
            toRet.add(toAdd);
        }
        return toRet;
    }

    public static TableView getAddressesTable(int customerId){
        TableView addr = new TableView();

        TableColumn<address, Integer> addID = new TableColumn();
        addID.setText("Address ID");
        addID.setPrefWidth(200);
        addID.setResizable(false);
        addID.setCellValueFactory(new PropertyValueFactory<>("addressID"));

        TableColumn<address, String> addressLine = new TableColumn();
        addressLine.setText("Address");
        addressLine.setPrefWidth(200);
        addressLine.setResizable(false);
        addressLine.setCellValueFactory(new PropertyValueFactory<>("addressLine"));


        TableColumn<address, Integer> pincode = new TableColumn();
        pincode.setText("Pincode");
        pincode.setPrefWidth(200);
        pincode.setResizable(false);
        pincode.setCellValueFactory(new PropertyValueFactory<>("pinCode"));


        addr.getColumns().addAll(addID, addressLine, pincode);

        return addr;
    }
    public static String getCartTotal(int custID) throws SQLException {

        ResultSet rs = HelloApplication.callFunction("select getOrderAmount("+custID+")", 0);
        rs.next();
        return String.valueOf(rs.getFloat("getorderAmount("+custID+")"));
    }
    public static ArrayList<orderedProducts> getItemsInCart(int custID) throws SQLException {
        String query = """
                with product_list (productID, productName, price) as (select productID, productName, price from products) 
                select product_list.productID, productName, quantity, price*quantity as subtotal from product_list natural join cart where cart.customerID = """+custID;

        ResultSet rs = HelloApplication.retrieveData(query, 1);
        ArrayList<orderedProducts> allItems = new ArrayList<>();
        int totalCartCost = 0;
        while(rs.next()){
            orderedProducts toAdd = new orderedProducts(rs.getInt("productID"), rs.getString("productName"), rs.getInt("quantity"), rs.getInt("subTotal"));
            allItems.add(toAdd);
            totalCartCost += Double.parseDouble(rs.getString("subTotal"));
        }
        return allItems;
    }
    public static TableView getOrdersTable(int customerId){
        TableView orderTable = new TableView();

        TableColumn<order, String> orderID = new TableColumn();
        orderID.setText("Order ID");
        orderID.setPrefWidth(200);
        orderID.setResizable(false);
        orderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));

        TableColumn<order, String> mop = new TableColumn();
        mop.setText("Mode of Payment");
        mop.setPrefWidth(200);
        mop.setResizable(false);
        mop.setCellValueFactory(new PropertyValueFactory<>("modeOfPayment"));


        TableColumn<order, String> date = new TableColumn();
        date.setText("Date");
        date.setPrefWidth(200);
        date.setResizable(false);
        date.setCellValueFactory(new PropertyValueFactory<>("date"));


        orderTable.getColumns().addAll(orderID, mop, date);
        return orderTable;
    }
    public static ArrayList<order> getOrdersList(int customerID) throws SQLException {
        String query = "select orderID, orders.modeOfPayment, dateAndTime from place join orders using(orderID) where place.customerID = "+customerID;
        ArrayList<order> toRet = new ArrayList<>();
        ResultSet rs = HelloApplication.retrieveData(query, 0);
        while(rs.next()) {
            toRet.add(new order(rs.getInt("orderID"), rs.getString("modeOfPayment"), rs.getString("dateAndTime")));
        }
        return toRet;

    }
    public static TableView getItemsInCartTable(int customerId){
        TableView cartTable = new TableView();

        TableColumn<orderedProducts, String> orderID = new TableColumn();
        orderID.setText("Prod ID");
        orderID.setPrefWidth(100);
        orderID.setResizable(false);
        orderID.setCellValueFactory(new PropertyValueFactory<>("productID"));

        TableColumn<orderedProducts, String> date = new TableColumn();
        date.setText("Name of Product");
        date.setPrefWidth(200);
        date.setResizable(false);
        date.setCellValueFactory(new PropertyValueFactory<>("productName"));


        TableColumn<orderedProducts, String> price = new TableColumn();
        price.setText("Quantity");
        price.setPrefWidth(100);
        price.setResizable(false);
        price.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<orderedProducts, String> subTotal = new TableColumn();
        subTotal.setText("Sub total");
        subTotal.setPrefWidth(100);
        subTotal.setResizable(false);
        subTotal.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        cartTable.getColumns().addAll(orderID, date, price, subTotal);

        return cartTable;
    }


}
