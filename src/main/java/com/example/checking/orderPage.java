package com.example.checking;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class orderPage {

    public static void show(int orderID, Stage stage){
        Pane main = new Pane();
        Scene orderScene = new Scene(main, 800, 600);
        stage.setScene(orderScene);


    }
}

