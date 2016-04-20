/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.ui;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.expert.core.database.knowledge.Knowledge;
import pl.expert.core.database.reader.KnowledgeReader;
import pl.expert.core.exception.KnowledgeReaderException;

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

    private Menu createFileMenu() {
        Menu menuFile = new Menu("File");
        MenuItem openFile = new MenuItem("Load knowledge base");
        openFile.setOnAction(event -> handleOpenFileAction(event));

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(event -> handleExitAction(event));

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

    private void handleOpenFileAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Knowledge knowledge = new KnowledgeReader().loadKnowledge(selectedFile);
                LOGGER.log(Level.INFO, "{0} loaded", selectedFile.getName());
                showSuccessAlert();
            } catch (KnowledgeReaderException exception) {
                LOGGER.log(Level.SEVERE, exception.getMessage());
                showErrorAlert(exception);
            }
        }
    }

    private void handleExitAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    private void showErrorAlert(Exception exception) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Knowledge reading error");
        alert.setHeaderText("Error occurred during loading knowledge base");
        alert.setContentText(exception.getMessage());
        alert.setResizable(true);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().getChildren()
            .stream()
            .filter(node -> node instanceof Label)
            .forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Knowledge base has been successfully loaded");

        alert.showAndWait();
    }
}