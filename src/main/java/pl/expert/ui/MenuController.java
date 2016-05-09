/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import pl.expert.core.database.knowledge.Knowledge;
import pl.expert.core.database.reader.KnowledgeReader;
import pl.expert.core.exception.KnowledgeReaderException;
import pl.expert.ui.exception.UIException;
import pl.expert.ui.inference.InferenceDialog;
import pl.expert.utils.MessageDialogs;

public class MenuController {

    private final static Logger LOGGER = Logger.getLogger(MainFrame.class.getName());

    @FXML
    public void openFileAction(ActionEvent actionEvent) {
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

    @FXML
    public void exitAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void editRulesAction(ActionEvent actionEvent) {
        try {
            URL editUrl = getClass().getResource("/fxml/Edit.fxml");
            AnchorPane editView = FXMLLoader.load(editUrl);
            BorderPane border = MainFrame.getRoot();
            border.setCenter(editView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void forwardInferenceAction(ActionEvent event) {
        try {
            this.createInferenceForwardDialog();
        } catch (UIException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, "Błąd podczas tworzenia dialogu wnioskowania",
                ex);
            MessageDialogs.showErrorAlert(ex.getMessage(), ex);
        }
    }

    private void createInferenceForwardDialog() throws UIException {
        InferenceDialog dialog = new InferenceDialog(MainFrame.getStage());
        dialog.showDialog();
    }
}
