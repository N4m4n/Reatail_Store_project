package com.example.checking;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class newAddress {
    public static void show(Stage stage){
        Pane main = new Pane();
        Scene newAddrScene = new Scene(main, 800, 600);
        TextField Addressline = new TextField();
        Addressline.setLayoutX(200);
        Addressline.setLayoutY(200);

        TextField Pincode = new TextField();
        Pincode.setLayoutX(200);
        Pincode.setLayoutY(250);
        
        Label addressLine = new Label("Address Line");
        addressLine.setLayoutY(200);
        addressLine.setLayoutX(120);

        Label pincode = new Label("Pincode");
        pincode.setLayoutY(250);
        pincode.setLayoutX(120);

        Button back = new Button();
        back.setText("Back");
        back.setLayoutX(20);
        back.setLayoutY(20);
        back.setOnAction(e->{
            try {
                orderConfirmation.show(stage);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Button submit = new Button();
        submit.setText("Add");
        submit.setLayoutY(400);
        submit.setLayoutX(500);
        submit.setOnAction(e->{
            String addrLin = Addressline.getText();
            String pin = Pincode.getText();
            String query = "insert into address (addressLine1, pincode, customerID) values (\'"+addrLin+"\', "+pin+", "+HelloApplication.customerID+")";
            HelloApplication.sendData(query, 1);
            try {
                orderConfirmation.show(stage);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        main.getChildren().addAll(Addressline, Pincode, addressLine, pincode, submit, back);



        stage.setScene(newAddrScene);
        

    }
}
