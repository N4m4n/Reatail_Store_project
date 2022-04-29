package com.example.checking;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class supplierLogin {
    public static int supplierID = -1;
    public static String supplierEmail = "";
    public static String passWord = "";
    public static void show(Stage stage){


        Pane main = new Pane();

        Label emailPh = new Label("Supplier Email id / Phone no.");
        emailPh.setLayoutY(200);
        emailPh.setLayoutX(100);

        Label password = new Label("Password");
        password.setLayoutY(250);
        password.setLayoutX(100);

        TextField EmailPh = new TextField();
        EmailPh.setLayoutX(300);
        EmailPh.setLayoutY(200);

        PasswordField Pass = new PasswordField();
        Pass.setLayoutX(300);
        Pass.setLayoutY(250);

        Button login = new Button("Login");
        login.setLayoutY(450);
        login.setLayoutX(700);

        login.setOnAction(e->{

        String phoneoremail = EmailPh.getText();
        String pass = Pass.getText();
        String query = "select * from supplierInfo where (emailID = \'"+phoneoremail+"\') and accountPassword = \'"+pass+"\'";
        ResultSet rs = HelloApplication.retrieveData(query, 2);

            try {

                if(rs.next()){
                    supplierID = rs.getInt("supplierID");
                    supplierEmail = EmailPh.getText();
                    passWord = Pass.getText();
                    System.out.println("ID: "+supplierID);
                    System.out.println("Logged in");
                    supplierHomePage.show(stage);

                }else{
                    HelloApplication.showError("Invalid Credentials", "No such user found");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }


        });

        main.getChildren().addAll(login, password, emailPh, Pass, EmailPh);
        Scene suppLoginScren = new Scene(main, 800, 600);
        stage.setScene(suppLoginScren);


    }





}
