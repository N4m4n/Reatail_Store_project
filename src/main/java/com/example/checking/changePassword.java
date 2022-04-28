package com.example.checking;

import javafx.beans.property.ReadOnlySetProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class changePassword {
    public static void show(Stage stage){
        Pane main = new Pane();
        Button back = new Button("Back");
        back.setLayoutY(20);
        back.setLayoutX(20);
        back.setOnAction(e->{
            try {
                profilePage.show(stage);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });


        Label old = new Label("Enter old password");
        old.setLayoutX(300);
        old.setLayoutY(300);

        Label newPass = new Label("Enter new password");
        newPass.setLayoutX(300);
        newPass.setLayoutY(340);

        Label reEnterNewPass = new Label("Re-enter password");
        reEnterNewPass.setLayoutX(300);
        reEnterNewPass.setLayoutY(380);

        PasswordField OldPass = new PasswordField();
        OldPass.setLayoutX(500);
        OldPass.setLayoutY(300);

        PasswordField NewPass = new PasswordField();
        NewPass.setLayoutX(500);
        NewPass.setLayoutY(340);

        PasswordField ReenterNewPass = new PasswordField();
        ReenterNewPass.setLayoutX(500);
        ReenterNewPass.setLayoutY(380);

        Button submit = new Button("Submit");
        submit.setLayoutX(600);
        submit.setLayoutY(550);
        submit.setOnAction(e->{
            String old_Pass = OldPass.getText();
            String new_Pass = NewPass.getText();
            String re_new_pass = ReenterNewPass.getText();
            if(new_Pass.equals(re_new_pass)){
                String query = "select accountPassword from customers where customerID = " + HelloApplication.customerID;
                ResultSet rs = HelloApplication.retrieveData(query, 0);
                try {
                    if(rs.next()){
                        String curr = rs.getString("accountPassword");
                        if(curr.equals(old_Pass)){
                            query = "update customers set accountPassword = \'"+new_Pass+"\' where customerID = "+HelloApplication.customerID;
                            HelloApplication.sendData(query, 1);
                            HelloApplication.showError("Password Changed", "Password was succesfully updated.");
                            profilePage.show(stage);
                        }else{
                            HelloApplication.showError("Incorrect Password", "Incorrect password entered!");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }else{
                HelloApplication.showError("Mismatch", "The passwords you entered do not match!");
            }
        });



        main.getChildren().addAll(old, newPass, reEnterNewPass, OldPass, NewPass, ReenterNewPass, submit);

        Scene changePasswordScene = new Scene(main, 800, 600);
        stage.setScene(changePasswordScene);

    }
}
