package com.example.checking;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.security.spec.EllipticCurve;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class orderConfirmation {
    public static void show(Stage stage) throws SQLException {
        Pane main = new Pane();
        ComboBox<String> addresses = new ComboBox();

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

        ArrayList<address> completeAdd = profilePage.getAddresses(HelloApplication.customerID);
        addresses.getItems().addAll(getAddressLines(completeAdd));
        addresses.setLayoutX(100);
        addresses.setLayoutY(100);
        addresses.setPrefWidth(600);


        ComboBox<String> cardDetails = new ComboBox();
        cardDetails.getItems().addAll("COD");
        cardDetails.getItems().addAll(getCardsInfo(HelloApplication.customerID));
        cardDetails.setLayoutX(100);
        cardDetails.setLayoutY(200);
        cardDetails.setPrefWidth(600);

        Button placeOrder = new Button("Place Order");
        placeOrder.setLayoutX(150);
        placeOrder.setLayoutY(500);

        Label couponText = new Label();
        couponText.setText("Coupon Code if any:");
        couponText.setLayoutX(120);
        couponText.setLayoutY(400);

        TextField couponField = new TextField();
        couponField.setLayoutX(250);
        couponField.setLayoutY(400);
        couponField.setPrefWidth(60);

        Button applyCoupon = new Button("Apply");
        applyCoupon.setLayoutY(400);
        applyCoupon.setLayoutX(320);

        applyCoupon.setOnAction(e->{
//            TODO: check if coupon is valid and according apply.

        });

        placeOrder.setOnAction(e->{
//            TODO: clear the cart and add the order.
            Dialog<String> dialog = new Dialog<String>();

            dialog.setTitle("Order Placed.");
            dialog.setHeight(200);
            dialog.setWidth(400);
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("Congratulations, your order has been placed!");
            //Adding buttons to the dialog pane
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
            HomePage.show(stage);

        });

        main.getChildren().addAll(addresses, cardDetails, placeOrder, couponText, couponField, applyCoupon, back);

        Scene orderConfirmScene = new Scene(main, 800, 600);
        stage.setScene(orderConfirmScene);

    }

    public static ArrayList<String> getCardsInfo(int custID) throws SQLException {
        String query = "select * from paymentinfo where paymentinfo.customerID = "+custID;
        ArrayList<String> toRet = new ArrayList<>();

        ResultSet rs = HelloApplication.retrieveData(query, 0);
        while(rs.next()){
            toRet.add(String.valueOf(rs.getString("cardNo")));
        }
        return toRet;
    }

    public static ArrayList<String> getAddressLines(ArrayList<address> a){
        ArrayList<String> toRet = new ArrayList<>();
        for(int i = 0; i<a.size(); i++){
            toRet.add(a.get(i).getAddressLine());
        }
        return toRet;
    }
}
