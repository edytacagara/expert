/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.ui;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainFrame extends Application {

    private final static Logger LOGGER = Logger.getLogger(MainFrame.class.getName());
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("Expert");
        primaryStage.setScene(scene);

        createMenuBar(root);
        createHomeScreen(root);

        primaryStage.show();
    }

    private void createMenuBar(BorderPane root) {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = createFileMenu();
        Menu menuInference = createInferenceMenu();
        Menu menuHelp = createHelpMenu();

        menuBar.getMenus().addAll(menuFile, menuInference, menuHelp);
        root.setTop(menuBar);
    }

    private void loadFile() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            LOGGER.log(Level.INFO, "{0} loaded", selectedFile.getName());
        }
    }

    private Menu createFileMenu() {
        Menu menuFile = new Menu("File");
        MenuItem openFile = new MenuItem("Load knowledge base");
        openFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                loadFile();
            }
        });

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });

        menuFile.getItems().addAll(openFile, exit);
        return menuFile;
    }

    private Menu createInferenceMenu() {
        Menu menuInference = new Menu("Inference");
        MenuItem forward = new MenuItem("Forward");
        MenuItem backward = new MenuItem("Backward");

        menuInference.getItems().addAll(forward, backward);
        return menuInference;
    }

    private Menu createHelpMenu() {
        Menu menuHelp = new Menu("Help");
        MenuItem about = new MenuItem("About");
        
        menuHelp.getItems().addAll(about);
        return menuHelp;
    }

    private void createHomeScreen(BorderPane root) {
        Image image = new Image("images/syringe-icon-big.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);

        HBox pictureRegion = new HBox();
        pictureRegion.getChildren().add(imageView);

        root.setCenter(pictureRegion);
    }
}
