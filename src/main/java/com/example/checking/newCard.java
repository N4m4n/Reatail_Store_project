package com.example.checking;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class newCard {

    public static void show(Stage stage){
        Pane main = new Pane();
        Scene newAddrScene = new Scene(main, 800, 600);
        TextField CardNo = new TextField();
        CardNo.setLayoutX(200);
        CardNo.setLayoutY(200);

        TextField NameOnCard = new TextField();
        NameOnCard.setLayoutX(200);
        NameOnCard.setLayoutY(250);

        TextField CardType = new TextField();
        CardType.setLayoutX(200);
        CardType.setLayoutY(300);

        ComboBox<String> year = new ComboBox<>();
        year.getItems().addAll("2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033");
        year.setLayoutX(200);
        year.setLayoutY(350);

        ComboBox<String> month = new ComboBox<>();
        month.getItems().addAll("01","02","03","04","05","06","07","08","09","10","11","12");
        month.setLayoutX(280);
        month.setLayoutY(350);

        ComboBox<String> day = new ComboBox<>();
        day.getItems().addAll("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31");
        day.setLayoutX(360);
        day.setLayoutY(350);

        Label cardNo = new Label("Card no");
        cardNo.setLayoutY(200);
        cardNo.setLayoutX(120);

        Label nameOnCard = new Label("Name on Card");
        nameOnCard.setLayoutY(250);
        nameOnCard.setLayoutX(120);

        Label cardType = new Label("Card Type");
        cardType.setLayoutY(300);
        cardType.setLayoutX(120);

        Label dateOfExp = new Label("Date of Exp");
        dateOfExp.setLayoutY(350);
        dateOfExp.setLayoutX(120);

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
            String card_no = CardNo.getText();
            String name_on_card = NameOnCard.getText();
            String card_type = CardType.getText();
            String year_of_exp = year.getSelectionModel().getSelectedItem();
            String month_of_exp = month.getSelectionModel().getSelectedItem();
            String day_of_exp = day.getSelectionModel().getSelectedItem();
            String date = year_of_exp+"-"+month_of_exp+"-"+day_of_exp;
            String query = "insert into paymentInfo (customerID, cardNo, cardType, nameOnCard, expiryDate) values ("+ HelloApplication.customerID +", "+ card_no +", \'" + card_type  +"\', \'"+name_on_card+"\', \'"+date+"\')";
            HelloApplication.sendData(query, 1);
            try {
                orderConfirmation.show(stage);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }


        });
        main.getChildren().addAll(CardNo, cardNo, nameOnCard, NameOnCard, cardType, CardType, month, day, year, dateOfExp, submit, back);



        stage.setScene(newAddrScene);


    }

}
