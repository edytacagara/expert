/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.ui.inference;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.expert.core.engine.Engine;
import pl.expert.ui.MainFrame;
import pl.expert.ui.exception.UIException;

/**
 *
 * @author rafal16x
 */
public class InferenceDialog extends Stage implements InferenceInterface {

    public InferenceDialog(Stage rootStage) throws UIException {
        super();
        this.initLayout();
        this.setResizable(false);

    }

    private void closeDialog() {
        System.out.println("On close super");
        super.close();

    }

    public void showDialog() {
        super.show();
    }

    @Override
    public void onClose() {
        System.out.println("On close ");
        this.closeDialog();
    }

    @Override
    public void onNextRule() {
        System.out.println("Next rule");
    }

    @Override
    public void startAll() {
        System.out.println("Start all");
//        Thread thread = new Thread(() -> {
            Engine engine = new Engine();
            engine.doMagic(MainFrame.getKnowledge());
//        });
        System.out.println("Do magic starting");
//        thread.start();
    }

    @Override
    public void onStop() {
        System.out.println("on Stop");
    }

    @Override
    public void showSteps() {
        System.out.println("Show steps");
    }

    private void initLayout() throws UIException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/inferenceDialog.fxml"));
            VBox vBox = fxmlLoader.<VBox>load();
            DialogController controller = fxmlLoader.<DialogController>getController();
            controller.initControiller(this);
            setScene(new Scene(vBox));
        } catch (Exception ex) {
            Logger.getLogger(InferenceDialog.class.getName()).log(Level.SEVERE, null, ex);
            throw new UIException("Blad qpodczas tworzenia layoutu", ex.getCause());
        }
    }
}
