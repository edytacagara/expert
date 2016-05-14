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
    public void openFileAction(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            loadFile(selectedFile);
        }
    }

    @FXML
    public void exitAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void editRulesAction(ActionEvent actionEvent) {
        //uncomment to autoload knowledge base
        loadFileForTestPurposes();
        
        if (!checkIfKnowledgeBaseLoaded()) {
            return;
        }

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
        if (!checkIfKnowledgeBaseLoaded()) {
            return;
        }

        try {
            this.createInferenceForwardDialog();
        } catch (UIException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, "Błąd podczas tworzenia dialogu wnioskowania",
                ex);
            MessageDialogs.showErrorAlert(ex.getMessage(), ex);
        }
    }

    private void loadFile(File file) {
        try {
            Knowledge knowledge = new KnowledgeReader().loadKnowledge(file);
            MainFrame.setKnowledge(knowledge);
            LOGGER.log(Level.INFO, "wczytano {0}", file.getName());
            MessageDialogs.showSuccessAlert("Baza wiedzy została poprawnie wczytana");
        } catch (KnowledgeReaderException exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage());
            MessageDialogs.showErrorAlert("Wystąpił błąd podczas wczytywania bazy wiedzy", exception);
        }
    }

    private void createInferenceForwardDialog() throws UIException {
        InferenceDialog dialog = new InferenceDialog(MainFrame.getStage());
        dialog.showDialog();
    }

    private boolean checkIfKnowledgeBaseLoaded() {
        if (MainFrame.getKnowledge() == null) {
            MessageDialogs.showSimpleErrorAlert("Nie wczytano jeszcze bazy wiedzy");
            return false;
        }
        return true;
    }
    
    private void loadFileForTestPurposes() {
        String kzagrabaKnowledgeBaseFilePath = "/home/kzagraba/workspace/expert/src/main/resources/knowledge.xml";
        File file = new File(kzagrabaKnowledgeBaseFilePath);
        loadFile(file);
    }
}
