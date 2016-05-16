/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.utils;

import pl.expert.utils.input.InputType;
import pl.expert.utils.input.impl.TextInput;
import pl.expert.utils.input.impl.NumberInput;
import pl.expert.utils.input.Input;
import java.awt.event.ActionEvent;
import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javax.swing.AbstractAction;
import pl.expert.utils.input.impl.BooleanInput;

/**
 *
 * @author rafal16x
 */
public class InputDialog extends AbstractAction {

    private final Dialog<Input> dialog;
    private String title;
    private String question;

    private Input<Double> numberField;
    private Input<String> textField;
    private Input<Boolean> booleanInput;
    private InputType type = InputType.TEXT;

    private InputDialog() {
        this.dialog = new Dialog<>();
        this.inityFields();
    }

    public static InputDialog create() {
        return new InputDialog();
    }

    private void inityFields() {
        this.textField = new TextInput();
        this.numberField = new NumberInput();
        this.booleanInput = new BooleanInput();
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    private GridPane getContent() {
        this.dialog.setTitle(title);
        this.dialog.setHeaderText(this.question);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        grid.add((Node) this.getInputField(), 1, 0);
        ButtonType acceptButton = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(acceptButton, ButtonType.CANCEL);
        Node acceptButtoNode = this.dialog.getDialogPane().lookupButton(acceptButton);
        if (!this.type.equals(InputType.BOOLEAN)) {
            grid.add(new Label("Wprowadź warrtość: "), 0, 0);
            acceptButtoNode.setDisable(true);
            ((TextField) this.getInputField()).textProperty().addListener((observable, oldValue, newValue) -> {
                acceptButtoNode.setDisable(newValue.trim().isEmpty());
            });
            Platform.runLater(() -> ((TextField) this.getInputField()).requestFocus());
        }
        return grid;
    }

    public void setInputType(InputType type) {
        this.type = type;
    }

    private Input<?> getInputField() {
        switch (this.type) {
            case DOUBLE:
                return this.numberField;
            case TEXT:
                return this.textField;
            case BOOLEAN:
                return this.booleanInput;
            default:
                return this.textField;
        }
    }

    public Optional<Input> showAndWait() {
        this.dialog.getDialogPane().setContent(this.getContent());
        return this.dialog.showAndWait();
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((Dialog) e.getSource()).hide();
    }
}
