/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.utils;

import pl.expert.utils.input.impl.TextInput;
import pl.expert.utils.input.impl.NumberInput;
import pl.expert.utils.input.Input;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
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
 * @param <T>
 */
public class InputDialog<T> extends AbstractAction {

	private final Dialog<Input<T>> dialog;
	private String title;
	private String question;
	private final Class<T> type;

	private Input<BigDecimal> numberField;
	private Input<String> textField;
	private Input<Boolean> booleanInput;
	private ButtonType acceptButton;

	private InputDialog(Class<T> type) {
		this.dialog = new Dialog<>();
		this.inityFields();
		this.type = type;
	}

	/**
	 *
	 * @param type
	 * @return
	 */
	public static InputDialog create(final Class<?> type) {
		return new InputDialog(type);
	}

	private void inityFields() {
		this.textField = new TextInput();
		this.numberField = new NumberInput();
		this.booleanInput = new BooleanInput();
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	private GridPane getContent() {
		this.dialog.setTitle(title);
		this.dialog.setHeaderText(this.question);
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));
		Input<?> in = this.getInputField();
		grid.add((Node) in, 1, 0);
		grid.setUserData(in);
		this.acceptButton = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(acceptButton);
		Node acceptButtoNode = this.dialog.getDialogPane().lookupButton(this.acceptButton);
		if (!this.isBooleanType()) {
			grid.add(new Label("Wprowadź warrtość: "), 0, 0);
			acceptButtoNode.setDisable(true);
			((TextField) this.getInputField()).textProperty().addListener((observable, oldValue, newValue) -> {
				acceptButtoNode.setDisable(newValue.trim().isEmpty() || !this.getInputField().validate());
			});
			Platform.runLater(() -> ((TextField) this.getInputField()).requestFocus());
		}
		return grid;
	}

	private Input<?> getInputField() {
		if (this.type.isAssignableFrom(Boolean.class)) {
			return this.booleanInput;
		} else if (this.type.isAssignableFrom(BigDecimal.class)) {
			return this.numberField;
		} else if (this.type.isAssignableFrom(String.class)) {
			return this.textField;
		}
		return this.textField;
	}

	public Optional<Input<T>> showAndWait() {
		this.dialog.getDialogPane().setContent(this.getContent());
		this.dialog.setResultConverter((value) -> {
			return (Input<T>) this.getInputField();
		});
		return this.dialog.showAndWait();
	}

	private boolean isBooleanType() {
		return this.type.isAssignableFrom(Boolean.class);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		((Dialog) e.getSource()).hide();
	}
}
