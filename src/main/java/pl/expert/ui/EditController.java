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
    private static final String ADD_BUTTON_LABEL = "Dodaj";

    private Knowledge knowledge;
    private Rule selectedRule;
    private Model selectedModel;
    private Constraint selectedConstraint;
    private Integer selectedElementIndex;

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
    private TextField argumentInput;

    @FXML
    private TextField operatorsInput;

    @FXML
    private TextField valuesInput;

    @FXML
    private TextField modelResultInput;

    @FXML
    private TextField constraintsInput;

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
        //TODO save

        if (!checkIfIsRuleValid()) {
            MessageDialogs.showSimpleErrorAlert("Zapisywana reguła jest niepoprawna");
            return;
        }

        List<String> conditions = Arrays.asList(conditionsInput.getText().replace(" ", "").split(","));
        String result = ruleResultInput.getText();

        if (selectedRule == null) {
            Rule newRule = new Rule(conditions, result);
//            ruleItems.add(newRule);
        } else {
            selectedRule.setConditions(conditions);
            selectedRule.setResult(result);
            ((Rule) listView.getSelectionModel().getSelectedItem()).updateRule(selectedRule);
//            ruleItems.remove(selectedElementIndex);
//            ruleItems.add(selectedElementIndex, selectedRule);
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
        selectedModel = null;
        selectedConstraint = null;
        selectedElementIndex = null;

        clearInputs();

        addEditRuleButton.setText(ADD_BUTTON_LABEL);
        removeRuleButton.setVisible(false);
        addEditModelButton.setText(ADD_BUTTON_LABEL);
        removeModelButton.setVisible(false);
        addEditConstraintButton.setText(ADD_BUTTON_LABEL);
        removeConstraintButton.setVisible(false);
    }

    @FXML
    public void removeAction(MouseEvent event) {
        items.remove(selectedElementIndex.intValue());
        cancelAction(event);
    }

    private void clearInputs() {
        conditionsInput.clear();
        ruleResultInput.clear();

        argumentInput.clear();
        operatorsInput.clear();
        valuesInput.clear();
        modelResultInput.clear();

        constraintsInput.clear();
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
        findSelectedValue(editView);
        if (selectedElementIndex.intValue() != -1) {
            fillFormFieldsByValuesFromSelectedElement(editView);
            showNeededButtonsAndAdjustTheirLabels(editView);
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

    private void findSelectedValue(EditView editView) {
        switch (editView) {
            case RULE:
                selectedRule = (Rule) listView.getSelectionModel().getSelectedItem();
                selectedElementIndex = items.indexOf(selectedRule);
                break;
            case MODEL:
                selectedModel = (Model) listView.getSelectionModel().getSelectedItem();
                selectedElementIndex = items.indexOf(selectedModel);
                break;
            case CONSTRAINT:
                selectedConstraint = (Constraint) listView.getSelectionModel().getSelectedItem();
                selectedElementIndex = items.indexOf(selectedConstraint);
                break;
            default:
        }
    }

    private void fillFormFieldsByValuesFromSelectedElement(EditView editView) {
        switch (editView) {
            case RULE:
                conditionsInput.setText(selectedRule.getConditionsToInput());
                ruleResultInput.setText(selectedRule.getResult());
                break;
            case MODEL:
                argumentInput.setText(selectedModel.getArgument());
                operatorsInput.setText(selectedModel.getOperatorsToInput());
                valuesInput.setText(selectedModel.getValuesToInput());
                modelResultInput.setText(selectedModel.getResult());
                break;
            case CONSTRAINT:
                constraintsInput.setText(selectedConstraint.toString());
                break;
            default:
        }
    }
}
