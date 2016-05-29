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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import pl.expert.Context;
import pl.expert.core.database.knowledge.Constraint;
import pl.expert.core.database.knowledge.Knowledge;
import pl.expert.core.database.knowledge.KnowledgeElement;
import pl.expert.core.database.knowledge.Model;
import pl.expert.core.database.knowledge.Rule;
import pl.expert.ui.dictionary.EditView;
import pl.expert.utils.MessageDialogs;

public class EditController implements Initializable {
    private static final String SAVE_BUTTON_LABEL = "Zapisz";

    private Knowledge knowledge;
    private Rule selectedRule;
    private int selectedRuleIndex;

    @FXML
    private Pane testPane;

    @FXML
    private GridPane rulePane;

    @FXML
    private GridPane modelPane;

    @FXML
    private GridPane constraintPane;

    @FXML
    private TextField conditionsInput;

    @FXML
    private TextField ruleResultInput;

    @FXML
    private Button addEditRuleButton;

    @FXML
    private Button removeRuleButton;

    @FXML
    private Button addEditModelButton;

    @FXML
    private Button removeModelButton;

    @FXML
    private Button addEditConstraintButton;

    @FXML
    private Button removeConstraintButton;

    @FXML
    private ListView<KnowledgeElement> listView;
    ObservableList<KnowledgeElement> items = FXCollections.observableArrayList();
    ObservableList<Rule> ruleItems = FXCollections.observableArrayList();
    ObservableList<Model> modelItems = FXCollections.observableArrayList();
    ObservableList<Constraint> constraintItems = FXCollections.observableArrayList();

    private EditView editView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editView = Context.getInstance().getCurrentEditView();

        hideProperPane(editView);
        initializeList(editView);
    }

    public void initData() {
        System.out.println("init ");
    }

    public void addButtonAction() {
        testPane.setVisible(false);
    }

    public void listElementSelected(MouseEvent event) {
        reactOnListElementSelected(editView);
    }

    @FXML
    public void saveAction(MouseEvent event) {

        if (!checkIfIsRuleValid()) {
            MessageDialogs.showSimpleErrorAlert("Zapisywana reguła jest niepoprawna");
            return;
        }

        List<String> conditions = Arrays.asList(conditionsInput.getText().replace(" ", "").split(","));
        String result = ruleResultInput.getText();

        if (selectedRule == null) {
            Rule newRule = new Rule(conditions, result);
            ruleItems.add(newRule);
        } else {
            selectedRule.setConditions(conditions);
            selectedRule.setResult(result);
            ((Rule) listView.getSelectionModel().getSelectedItem()).updateRule(selectedRule);
            ruleItems.remove(selectedRuleIndex);
            ruleItems.add(selectedRuleIndex, selectedRule);
            listView.getSelectionModel().clearSelection();
        }
        clearInputs();
        addEditRuleButton.setText("Dodaj");
        removeRuleButton.setVisible(false);
    }

    @FXML
    public void cancelAction(MouseEvent event) {
        listView.getSelectionModel().clearSelection();
        selectedRule = null;
        clearInputs();
        addEditRuleButton.setText("Dodaj");
        removeRuleButton.setVisible(false);
    }

    @FXML
    public void removeAction(MouseEvent event) {
        ruleItems.remove(selectedRuleIndex);
        listView.getSelectionModel().clearSelection();
        selectedRule = null;
        clearInputs();
        addEditRuleButton.setText("Dodaj");
        removeRuleButton.setVisible(false);
    }

    private void clearInputs() {
        conditionsInput.clear();
        ruleResultInput.clear();
    }

    private boolean checkIfIsRuleValid() {
        if (Strings.isNullOrEmpty(conditionsInput.getText())) {
            return false;
        }

        if (Strings.isNullOrEmpty(ruleResultInput.getText())) {
            return false;
        }

        return true;
    }

    private void hideProperPane(EditView editView) {
        switch (editView) {
            case RULE:
                rulePane.setVisible(true);
                modelPane.setVisible(false);
                constraintPane.setVisible(false);
                break;
            case MODEL:
                rulePane.setVisible(false);
                modelPane.setVisible(true);
                constraintPane.setVisible(false);
                break;
            case CONSTRAINT:
                rulePane.setVisible(false);
                modelPane.setVisible(false);
                constraintPane.setVisible(true);
                break;
            default:
        }

    }

    private void reactOnListElementSelected(EditView editView) {
        showNeededButtonsAndAdjustTheirLabels(editView);
        
        switch (editView) {
            case RULE:
                selectedRule = (Rule) listView.getSelectionModel().getSelectedItem();
                selectedRuleIndex = ruleItems.indexOf(selectedRule);
                conditionsInput.setText(selectedRule.getConditionsToInput());
                ruleResultInput.setText(selectedRule.getResult());
                break;
            case MODEL:
                break;
            case CONSTRAINT:
                break;
            default:
        }
    }

    private void initializeList(EditView editView) {
        testPane.setVisible(false);
        knowledge = Context.getInstance().getKnowledge();

        switch (editView) {
            case RULE:
                knowledge.getRules().forEach(rule -> items.add(rule));
                listView.setItems(items);
                listView.setOnMouseClicked(event -> listElementSelected(event));
                break;
            case MODEL:
                knowledge.getModels().forEach(model -> items.add(model));
                listView.setItems(items);
                listView.setOnMouseClicked(event -> listElementSelected(event));
                break;
            case CONSTRAINT:
                knowledge.getConstraints().forEach(constraint -> items.add(constraint));
                listView.setItems(items);
                listView.setOnMouseClicked(event -> listElementSelected(event));
                break;
            default:
        }

        ObservableList<ColumnConstraints> columnConstrains = rulePane.getColumnConstraints();
        columnConstrains.get(0).setPercentWidth(25.0);
        columnConstrains.get(1).setPercentWidth(75.0);
        conditionsInput.setPrefWidth(370);
    }
    
    private void showNeededButtonsAndAdjustTheirLabels(EditView editView) {
        switch (editView) {
            case RULE:
                addEditRuleButton.setText(SAVE_BUTTON_LABEL);
                removeRuleButton.setVisible(true);
                break;
            case MODEL:
                addEditModelButton.setText(SAVE_BUTTON_LABEL);
                removeModelButton.setVisible(true);
                break;
            case CONSTRAINT:
                addEditConstraintButton.setText(SAVE_BUTTON_LABEL);
                removeConstraintButton.setVisible(true);
                break;
            default:
        }
    }
}
