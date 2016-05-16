/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.ui;

import com.google.common.base.Strings;
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
import pl.expert.utils.MessageDialogs;

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
    private Button removeButton;

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
        removeButton.setVisible(true);
        selectedRule = listView.getSelectionModel().getSelectedItem();
        selectedRuleIndex = items.indexOf(selectedRule);
        conditionsInput.setText(selectedRule.getConditionsToInput());
        resultInput.setText(selectedRule.getResult());
    }

    @FXML
    public void saveAction(MouseEvent event) {
        
        if (!checkIfIsRuleValid()) {
            MessageDialogs.showSimpleErrorAlert("Zapisywana reguła jest niepoprawna");
            return;
        }
        
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
        clearInputs();
        addEditButton.setText("Dodaj");
        removeButton.setVisible(false);
    }

    @FXML
    public void cancelAction(MouseEvent event) {
        listView.getSelectionModel().clearSelection();
        selectedRule = null;
        clearInputs();
        addEditButton.setText("Dodaj");
        removeButton.setVisible(false);
    }
    
    @FXML
    public void removeAction(MouseEvent event) {
        items.remove(selectedRuleIndex);
        listView.getSelectionModel().clearSelection();
        selectedRule = null;
        clearInputs();
        addEditButton.setText("Dodaj");
        removeButton.setVisible(false);
    }

    private void clearInputs() {
        conditionsInput.clear();
        resultInput.clear();
    }
    
    private boolean checkIfIsRuleValid() {
        if (Strings.isNullOrEmpty(conditionsInput.getText())) {
            return false;
        }
        
        if (Strings.isNullOrEmpty(resultInput.getText())) {
            return false;
        }
        
        return true;
    }
}
