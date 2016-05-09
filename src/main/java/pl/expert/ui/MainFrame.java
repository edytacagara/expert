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
import pl.expert.core.database.knowledge.Knowledge;
import pl.expert.core.database.reader.KnowledgeReader;
import pl.expert.core.exception.KnowledgeReaderException;
import pl.expert.ui.inference.InferenceDialog;
import pl.expert.ui.exception.UIException;
import pl.expert.utils.MessageDialogs;

public class MainFrame extends Application {

    private final static Logger LOGGER = Logger.getLogger(MainFrame.class.getName());
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
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
        Menu menuEdit = createEditMenu();

        menuBar.getMenus().addAll(menuFile, menuEdit, menuInference, menuHelp);
        root.setTop(menuBar);
    }

    private Menu createFileMenu() {
        Menu menuFile = new Menu("Plik");
        MenuItem openFile = new MenuItem("Ładowanie bazy wiedzy");
        openFile.setOnAction(event -> handleOpenFileAction(event));

        MenuItem exit = new MenuItem("Wyjście");
        exit.setOnAction(event -> handleExitAction(event));

        menuFile.getItems().addAll(openFile, exit);
        return menuFile;
    }

    private Menu createEditMenu() {
        Menu menuEdit = new Menu("Edycja");

        MenuItem editKnowledge = new MenuItem("Baza wiedzy");
        MenuItem editModel = new MenuItem("Model");
        MenuItem editConstraints = new MenuItem("Ograniczenia");

        menuEdit.getItems().addAll(editKnowledge, editModel, editConstraints);
        return menuEdit;
    }

    private Menu createInferenceMenu() {
        Menu menuInference = new Menu("Wnioskowanie");
        MenuItem forward = new MenuItem("W przód");
        forward.setId("forward");
        MenuItem backward = new MenuItem("W tył");
        backward.setDisable(true);

        forward.setOnAction((value) -> {
            try {
                this.createInferenceForwardDialog();
            } catch (UIException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, "Błąd podczas tworzenia dialogu wnioskowania", ex);
                MessageDialogs.showErrorAlert(ex.getMessage(), ex);
            }
        });

        menuInference.getItems().addAll(forward, backward);
        return menuInference;
    }

    private Menu createHelpMenu() {
        Menu menuHelp = new Menu("Pomoc");
        MenuItem about = new MenuItem("O programie");

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
                LOGGER.log(Level.INFO, "wczytano {0}", selectedFile.getName());
                MessageDialogs.showSuccessAlert("Baza wiedzy została poprawnie wczytana");
            } catch (KnowledgeReaderException exception) {
                LOGGER.log(Level.SEVERE, exception.getMessage());
                MessageDialogs.showErrorAlert("Wystąpił błąd podczas wczytywania bazy wiedzy", exception);
            }
        }
    }

    private void createInferenceForwardDialog() throws UIException {
        InferenceDialog dialog = new InferenceDialog(primaryStage);
        dialog.showDialog();
    }

    private void handleExitAction(ActionEvent actionEvent) {
        System.exit(0);
    }

}
