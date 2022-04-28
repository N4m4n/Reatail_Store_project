package com.example.checking;

import eu.hansolo.tilesfx.tools.MovingAverage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.lang.module.ResolvedModule;
import java.sql.*;
import java.util.ArrayList;

public class orderConfirmation {
    static float discountOnOrder = 0;
    public static void show(Stage stage) throws SQLException {
        discountOnOrder = 0;
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

        Label addressPrompt = new Label("Choose your address");
        addressPrompt.setLayoutX(100);
        addressPrompt.setLayoutY(80);
        main.getChildren().add(addressPrompt);

        Button addAddr = new Button("Add Address");
        addAddr.setLayoutX(710);
        addAddr.setLayoutY(140);
        addAddr.setOnAction(e->{
            newAddress.show(stage);
        });
        main.getChildren().addAll(addAddr);

        Button removeSelectedAdd = new Button("Remove address");
        removeSelectedAdd.setLayoutY(170);
        removeSelectedAdd.setLayoutX(700);
        removeSelectedAdd.setOnAction(e->{
            int indAdd = addresses.getSelectionModel().getSelectedIndex();
            if(indAdd != -1){
                int addID = completeAdd.get(indAdd).getAddressID();
                String query = "delete from address where addressID = "+ addID;
                HelloApplication.sendData(query, 1);
                try {
                    orderConfirmation.show(stage);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }

        });
        main.getChildren().addAll(removeSelectedAdd);

        Button addCard = new Button("Add Card");
        addCard.setLayoutX(710);
        addCard.setLayoutY(290);
        addCard.setOnAction(e->{
            newCard.show(stage);
        });
        main.getChildren().addAll(addCard);

        Button removeSelectedCard = new Button("Remove Card");
        removeSelectedCard.setLayoutX(700);
        removeSelectedCard.setLayoutY(320);
        main.getChildren().addAll(removeSelectedCard);


        Label cardPrompt = new Label("Choose mode of payment:");
        cardPrompt.setLayoutY(200);
        cardPrompt.setLayoutX(100);
        main.getChildren().add(cardPrompt);

        ComboBox<String> cardDetails = new ComboBox();
        cardDetails.getItems().addAll("COD");
        cardDetails.getItems().addAll(getCardsInfo(HelloApplication.customerID));
        cardDetails.setLayoutX(100);
        cardDetails.setLayoutY(220);
        cardDetails.setPrefWidth(600);

        removeSelectedCard.setOnAction(e->{
            String selected = cardDetails.getSelectionModel().getSelectedItem();
            if(!selected.equals("COD")){
                String query = "delete from paymentInfo where cardNo = "+selected;
                HelloApplication.sendData(query, 1);
                try {
                    orderConfirmation.show(stage);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        });


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
            String appliedCoupon = couponField.getText();
            try {
                String query = "{call apply_Coupon(?, ?, ?, ?, ?)}";

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailstore", "root", "ComeCode1608");

                    CallableStatement cstmt = conn.prepareCall(query);
                    cstmt.setInt(1, Integer.valueOf(appliedCoupon));
                    cstmt.setFloat(2, Float.valueOf(profilePage.getCartTotal(HelloApplication.customerID)));
                    cstmt.setDate(3, Date.valueOf(java.time.LocalDate.now()));

                    cstmt.registerOutParameter(4, java.sql.Types.INTEGER);

                    cstmt.registerOutParameter(5, java.sql.Types.FLOAT);
                    cstmt.executeUpdate();

                    int isValid = cstmt.getInt(4);

                    float discount = cstmt.getFloat(5);
                    if(isValid == 1){
                        discountOnOrder = discount;
                        HelloApplication.showError("Applied", "Hurray! you saved "+discountOnOrder);
                    }else{
                        HelloApplication.showError("Cannot use coupon", "This coupon is not valid");
                    }


                } catch (ClassNotFoundException ex) {

                    ex.printStackTrace();
                } catch (SQLException ex) {

                    ex.printStackTrace();
                }

            } catch (Exception ex) {
                System.out.println("www");
                ex.printStackTrace();
            }



        });

        placeOrder.setOnAction(e->{

            int indAdd = addresses.getSelectionModel().getSelectedIndex();
            int indCard = cardDetails.getSelectionModel().getSelectedIndex();
            if (indAdd == -1 || indCard == -1){
                HelloApplication.showError("Missing details", "Please enter all the details");
            }else{


                String mop = "";
                if (indCard == 0){
                    mop = "COD";
                }else{
                    mop = "Online";
                }
                int addID = completeAdd.get(indAdd).getAddressID();
                try {
                    float finalAmt = Float.valueOf(profilePage.getCartTotal(HelloApplication.customerID)) - discountOnOrder;
                    String query = "insert into orders (addressID, amount, modeOfPayment) values ("+ addID+", "+finalAmt+", \'"+mop+"\')";
                    System.out.println(query);
                    HelloApplication.sendData(query, 1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

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

            }



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
