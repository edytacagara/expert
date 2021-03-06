package pl.expert.ui;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
import pl.expert.core.engine.expression.OperatorEnum;
import pl.expert.ui.dictionary.AddEditErrorCode;
import pl.expert.ui.dictionary.EditView;
import pl.expert.ui.exception.ValidationException;
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
        List<AddEditErrorCode> errors = checkIfIsRuleValid();
        if (errors.size() > 0) {
            MessageDialogs.showSimpleErrorAlert("Błąd walidacji", Joiner.on("\n").join(getValidationErrorCodes(errors)));
            return;
        }

        try {
            save(editView);
            cancelAction(event);
        } catch (ValidationException validationException) {
            MessageDialogs.showSimpleErrorAlert("Błąd walidacji", validationException.getErrorCode()
                .getValidationErrorCode());
            return;
        }

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
        updateKnowledgeBase(editView);
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

    private List<String> getValidationErrorCodes(List<AddEditErrorCode> errors) {
        return errors.stream()
            .map(error -> error.getValidationErrorCode())
            .collect(Collectors.toList());
    }

    private List<AddEditErrorCode> checkIfIsRuleValid() {
        List<AddEditErrorCode> errors = new ArrayList<>();

        switch (editView) {
            case RULE:
                if (Strings.isNullOrEmpty(conditionsInput.getText())) {
                    errors.add(AddEditErrorCode.BRAK_WARUNKOW_REGULY);
                }

                if (Strings.isNullOrEmpty(ruleResultInput.getText())) {
                    errors.add(AddEditErrorCode.BRAK_WNIOSKU_REGULY);
                }
                break;
            case MODEL:
                if (Strings.isNullOrEmpty(argumentInput.getText())) {
                    errors.add(AddEditErrorCode.BRAK_ARGUMENTU_MODELU);
                }

                if (Strings.isNullOrEmpty(operatorsInput.getText())) {
                    errors.add(AddEditErrorCode.BRAK_OPERATOROW_MODELU);
                }

                if (Strings.isNullOrEmpty(valuesInput.getText())) {
                    errors.add(AddEditErrorCode.BRAK_WARTOSCI_MODELU);
                }

                if (Strings.isNullOrEmpty(modelResultInput.getText())) {
                    errors.add(AddEditErrorCode.BRAK_WNIOSKU_MODELU);
                }
                break;
            case CONSTRAINT:
                if (Strings.isNullOrEmpty(constraintsInput.getText())) {
                    errors.add(AddEditErrorCode.BRAK_OGRANICZEN);
                }
                break;
        }
        return errors;
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

    private void save(EditView editView) throws ValidationException {
        switch (editView) {
            case RULE:
                List<String> conditionsPlain = Arrays.asList(conditionsInput.getText().trim().split(","));
                List<String> conditions = new ArrayList<>();
                for (String condition : conditionsPlain) {
                    conditions.add(condition.trim());
                }

                String ruleResult = ruleResultInput.getText();

                if (selectedRule == null) {
                    Rule newRule = new Rule(conditions, ruleResult);
                    items.add(newRule);
                } else {
                    selectedRule.setConditions(conditions);
                    selectedRule.setResult(ruleResult);
                    ((Rule) listView.getSelectionModel().getSelectedItem()).updateRule(selectedRule);
                    items.remove(selectedElementIndex.intValue());
                    items.add(selectedElementIndex, selectedRule);
                    listView.getSelectionModel().clearSelection();
                }
                break;
            case MODEL:
                String argument = argumentInput.getText();

                List<String> operatorsPlain = Arrays.asList(operatorsInput.getText().trim().split(","));
                List<String> operators = new ArrayList<>();
                for (String operator : operatorsPlain) {
                    try {
                        operator = OperatorEnum.createByOperator(operator.trim()).getName();
                    } catch (NullPointerException e) {
                        throw new ValidationException(AddEditErrorCode.ZLE_OPERATORY_MODELU);
                    }
                    operators.add(operator);
                }

                if (operators.isEmpty()) {
                    throw new ValidationException(AddEditErrorCode.BRAK_OPERATOROW_MODELU);
                }

                if (operators.size() == 2 && (operators.get(0).equals(operators.get(1)))) {
                    throw new ValidationException(AddEditErrorCode.TAKIE_SAME_OPERATORY_MODELU);
                }

                if (operators.size() > 2) {
                    throw new ValidationException(AddEditErrorCode.ZA_DUZO_OPERATOROW_MODELU);
                }

                List<String> valuesString = Arrays.asList(valuesInput.getText().trim().split(","));
                List<BigDecimal> values = new ArrayList<>();
                for (String value : valuesString) {
                    value = value.trim();
                    try {
                        values.add(new BigDecimal(value.trim()));
                    } catch (NumberFormatException e) {
                        throw new ValidationException(AddEditErrorCode.ZLE_WARTOSCI_MODELU);
                    }
                }

                if (values.isEmpty()) {
                    throw new ValidationException(AddEditErrorCode.BRAK_WARTOSCI_MODELU);
                }

                if (values.size() == 2 && (values.get(0).equals(values.get(1)))) {
                    throw new ValidationException(AddEditErrorCode.TAKIE_SAME_WARTOSCI_MODELU);
                }

                if (values.size() > 2) {
                    throw new ValidationException(AddEditErrorCode.ZA_DUZO_WARTOSCI_MODELU);
                }

                if (operators.size() != values.size()) {
                    throw new ValidationException(AddEditErrorCode.NIEZGODNA_LICZBA_OPERATOROW_I_WARTOSCI);
                }

                String modelResult = modelResultInput.getText();

                if (selectedModel == null) {
                    Model newModel = new Model(argument, operators, values, modelResult);
                    items.add(newModel);
                } else {
                    selectedModel.setArgument(argument);
                    selectedModel.setOperators(operators);
                    selectedModel.setValues(values);
                    selectedModel.setResult(modelResult);
                    ((Model) listView.getSelectionModel().getSelectedItem()).updateModel(selectedModel);
                    items.remove(selectedElementIndex.intValue());
                    items.add(selectedElementIndex, selectedModel);
                    listView.getSelectionModel().clearSelection();
                }
                break;
            case CONSTRAINT:
                List<String> constraintsPlain = Arrays.asList(constraintsInput.getText().trim().split(","));
                List<String> constraints = new ArrayList<>();
                for (String constraint : constraintsPlain) {
                    constraints.add(constraint.trim());
                }

                if (constraints.isEmpty()) {
                    throw new ValidationException(AddEditErrorCode.BRAK_OGRANICZEN);
                }

                if (constraints.size() == 1) {
                    throw new ValidationException(AddEditErrorCode.ZA_MALO_OGRANICZEN);
                }

                if (selectedConstraint == null) {
                    Constraint newConstraint = new Constraint(constraints);
                    items.add(newConstraint);
                } else {
                    selectedConstraint.setConstraint(constraints);
                    ((Constraint) listView.getSelectionModel().getSelectedItem()).updateConstraint(selectedConstraint);
                    items.remove(selectedElementIndex.intValue());
                    items.add(selectedElementIndex, selectedConstraint);
                    listView.getSelectionModel().clearSelection();
                }
                break;
            default:
        }
        updateKnowledgeBase(editView);
    }

    private void updateKnowledgeBase(EditView editView) {
        switch (editView) {
            case RULE:
                List<Rule> newRulesList = new ArrayList<Rule>();
                items.forEach(rule -> newRulesList.add((Rule) rule));
                Context.getInstance().getKnowledge().setRules(newRulesList);
                knowledge = Context.getInstance().getKnowledge();
                break;
            case MODEL:
                List<Model> newModelElementsList = new ArrayList<Model>();
                items.forEach(model -> newModelElementsList.add((Model) model));
                Context.getInstance().getKnowledge().setModels(newModelElementsList);
                knowledge = Context.getInstance().getKnowledge();
                break;
            case CONSTRAINT:
                List<Constraint> newConstraintsList = new ArrayList<Constraint>();
                items.forEach(constraint -> newConstraintsList.add((Constraint) constraint));
                Context.getInstance().getKnowledge().setConstraints(newConstraintsList);
                knowledge = Context.getInstance().getKnowledge();
                break;
            default:
        }
    }
}
