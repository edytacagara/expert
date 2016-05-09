/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.ui.inference;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author rafal16x
 */
public class DialogController implements Initializable {

    private InferenceInterface inferenceInterface;

    @FXML
    private Button inferenceStart;
    @FXML
    private Button nextStep;
    @FXML
    private Button showSteps;
    @FXML
    private Label processingRule;
    @FXML
    private Label ruleDesc;
    @FXML
    private CheckBox stepsMode;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void close() {
        this.inferenceInterface.onClose();
    }

    /**
     *
     * @param inferenceInterface1
     */
    public void initControiller(InferenceInterface inferenceInterface1) {
        this.inferenceInterface = inferenceInterface1;
    }

}
