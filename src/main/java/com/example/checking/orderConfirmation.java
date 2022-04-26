package com.example.checking;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class orderConfirmation {
    public static void show(Stage stage){
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

        addresses.getItems().addAll("Address1", "Address 2");
        addresses.setLayoutX(100);
        addresses.setLayoutY(100);
        addresses.setPrefWidth(600);


        ComboBox<String> cardDetails = new ComboBox();

        cardDetails.getItems().addAll("card1", "card2");
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
}
