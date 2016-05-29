/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.ui;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainFrame extends Application {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private static Stage primaryStage;

    // Creating a static root to pass to the controller
    private static BorderPane root = new BorderPane();

    /**
     * Just a root getter for the controller to use
     */
    public static BorderPane getRoot() {
        return root;
    }
    
    public static Stage getStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {       
        URL menuBarUrl = getClass().getResource("/fxml/Menu.fxml");
        MenuBar bar = FXMLLoader.load(menuBarUrl);

        URL homeScreenUrl = getClass().getResource("/fxml/HomeScreen.fxml");
        AnchorPane homeScreen = FXMLLoader.load(homeScreenUrl);

        root.setTop(bar);
        root.setCenter(homeScreen);

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
