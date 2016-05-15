/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.ui;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import pl.expert.core.database.knowledge.Knowledge;
import pl.expert.core.database.knowledge.Rule;

public class EditController implements Initializable {

    private Knowledge knowledge;
    private Rule selectedRule;
    private int selectedRuleIndex;

    @FXML
    private Pane testPane;

    @FXML
    private TextField conditionsInput;

    @FXML
    private TextField resultInput;

    @FXML
    private Button addEditButton;

    @FXML
    private ListView<Rule> listView;
    ObservableList<Rule> items = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        testPane.setVisible(false);
        knowledge = MainFrame.getKnowledge();
        knowledge.getRules().forEach(rule -> items.add(rule));
        listView.setItems(items);
        listView.setOnMouseClicked(event -> listElementSelected(event));
    }

    public void initData() {
        System.out.println("init ");
    }

    public void addButtonAction() {
        testPane.setVisible(false);
    }

    public void listElementSelected(MouseEvent event) {
        addEditButton.setText("Zapisz");
        selectedRule = listView.getSelectionModel().getSelectedItem();
        selectedRuleIndex = items.indexOf(selectedRule);
        conditionsInput.setText(selectedRule.getConditionsToInput());
        resultInput.setText(selectedRule.getResult());
    }

    @FXML
    public void saveAction(MouseEvent event) {
        List<String> conditions = Arrays.asList(conditionsInput.getText().replace(" ", "").split(","));
        String result = resultInput.getText();

        if (selectedRule == null) {
            Rule newRule = new Rule(conditions, result);
            items.add(newRule);
        } else {
            selectedRule.setConditions(conditions);
            selectedRule.setResult(result);
            listView.getSelectionModel().getSelectedItem().updateRule(selectedRule);
            items.remove(selectedRuleIndex);
            items.add(selectedRuleIndex, selectedRule);
            listView.getSelectionModel().clearSelection();
        }
        cleraInputs();
        addEditButton.setText("Dodaj");
    }

    @FXML
    public void cancelAction(MouseEvent event) {
        listView.getSelectionModel().clearSelection();
        selectedRule = null;
        cleraInputs();
        addEditButton.setText("Dodaj");
    }

    private void cleraInputs() {
        conditionsInput.clear();
        resultInput.clear();
    }
}
