package com.example.checking;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class employeeHomePage {
    public static void show(Stage stage){
        Pane main = new Pane();





        Scene employeeHome = new Scene(main, 800, 600);
        stage.setScene(employeeHome);

    }
}
