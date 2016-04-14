/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.ui;

import java.io.File;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainFrame extends Application {

    private final static Logger LOGGER = Logger.getLogger(MainFrame.class.getName()); 
    
    @Override
    public void start(Stage primaryStage) {       
        BorderPane root = new BorderPane();
        
        Scene scene = new Scene(root, 600, 480);
        
        primaryStage.setTitle("Expert main frame");
        primaryStage.setScene(scene);
        
        createMenuBar(root);
        
        primaryStage.show();
    }
    
    private void createMenuBar(BorderPane root) {
        MenuBar menuBar = new MenuBar();
        
        Menu menuFile = new Menu("File");
        MenuItem add = new MenuItem("Load knowledge base");
        add.setOnAction(new EventHandler<ActionEvent>() {
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
 
        menuFile.getItems().addAll(add, exit);
 
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
 
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        root.setTop(menuBar);
    }
    
    private void loadFile() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        LOGGER.info(selectedFile.getName() + " loaded");
    }
    
}
