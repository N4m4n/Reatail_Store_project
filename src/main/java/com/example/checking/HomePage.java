package com.example.checking;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomePage {
    public static void show(Stage stage){
        Pane main = new Pane();

        TextField searchBar = new TextField();
        searchBar.setPrefWidth(500);
        searchBar.setLayoutX(20);
        searchBar.setLayoutY(50);

        Image search = new Image("file:images/search_icon.png");
        ImageView searchIcon = new ImageView(search);
        searchIcon.setLayoutY(50);
        searchIcon.setLayoutX(525);
        searchIcon.setFitHeight(20);
        searchIcon.setFitWidth(20);

        Image profile = new Image("file:images/profile.jpg");
        ImageView profileImg = new ImageView(profile);
        profileImg.setLayoutY(50);
        profileImg.setLayoutX(650);
        profileImg.setFitHeight(50);
        profileImg.setFitWidth(50);


        Image eatableImg = new Image("file:images/eatables.jpg");
        Image apparelsImg = new Image("file:images/apparels.jpg");
        Image electronicsImg = new Image("file:images/electronics.jpg");
        Image furnitureImg = new Image("file:images/furniture.jpg");

        ImageView Eatables = new ImageView(eatableImg);
        Eatables.setLayoutX(100);
        Eatables.setLayoutY(100);
        Eatables.setFitHeight(200);
        Eatables.setFitWidth(200);
        main.getChildren().add(Eatables);

        Label eat = new Label("Eatables");
        eat.setLayoutX(150);
        eat.setLayoutY(320);


        ImageView Apparels = new ImageView(apparelsImg);
        Apparels.setLayoutX(400);
        Apparels.setLayoutY(100);
        Apparels.setFitHeight(200);
        Apparels.setFitWidth(200);
        main.getChildren().add(Apparels);
        Label appr = new Label("Apparels");
        appr.setLayoutX(450);
        appr.setLayoutY(320);

        ImageView Electronics = new ImageView(electronicsImg);
        Electronics.setLayoutX(100);
        Electronics.setLayoutY(350);
        Electronics.setFitHeight(200);
        Electronics.setFitWidth(200);
        main.getChildren().add(Electronics);
        Label elect = new Label("Electronics");
        elect.setLayoutX(140);
        elect.setLayoutY(555);

        ImageView Furniture = new ImageView(furnitureImg);
        Furniture.setLayoutX(400);
        Furniture.setLayoutY(350);
        Furniture.setFitHeight(200);
        Furniture.setFitWidth(240);
        main.getChildren().add(Furniture);
        Label furni = new Label("Furniture");
        furni.setLayoutX(440);
        furni.setLayoutY(555);
        main.getChildren().addAll(furni, elect, appr, eat, searchBar, searchIcon, profileImg);
        Scene homeScene = new Scene(main, 800, 600);
        stage.setScene(homeScene);

        profileImg.setOnMouseClicked(e->{
            showProfile(stage);
        });
    }

    public static void showProfile(Stage stage){
        profile.show(stage);
    }

}
