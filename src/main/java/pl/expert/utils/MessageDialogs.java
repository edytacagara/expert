/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import pl.expert.core.exception.ExpertSystemException;

/**
 *
 * @author rafal16x
 */
public class MessageDialogs {

    public static void showErrorAlert(String message, ExpertSystemException exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Wystąpił nieoczekiwany błąd");
        alert.setHeaderText(message);
        alert.setContentText(exception.getMessage());
        alert.setResizable(true);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Szczegóły:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().getChildren()
            .stream()
            .filter(node -> node instanceof Label)
            .forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    public static void showSimpleErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static void showSimpleErrorAlert(String message) {
        showSimpleErrorAlert("Wystąpił błąd", message);
    }

    public static void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukces");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

}
