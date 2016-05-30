/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.ui.inference;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.expert.Context;
import pl.expert.core.database.knowledge.Knowledge;
import pl.expert.core.database.knowledge.KnowledgeElement;
import pl.expert.core.database.knowledge.Model;
import pl.expert.core.database.knowledge.Rule;
import pl.expert.core.engine.Engine;
import pl.expert.ui.exception.UIException;

/**
 *
 * @author rafal16x
 */
public class InferenceDialog extends Stage implements InferenceInterface {

	private Knowledge knowledge;
	
    public InferenceDialog(Stage rootStage) throws UIException {
        super();
        this.initLayout();
        this.knowledge = Context.getInstance().getKnowledge();
        this.setResizable(true);

    }

    private void closeDialog() {
        super.close();

    }

    public void showDialog() {
        super.show();
    }

    @Override
    public void onClose() {
        this.closeDialog();
    }

    @Override
    public void startAll() {
        System.out.println("Start all");
//        Thread thread = new Thread(() -> {
            Engine engine = new Engine();
            engine.doMagic(this.knowledge);
//        });
        System.out.println("Do magic starting");
//        thread.start();
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

	@Override
	public List<Rule> getRules() {
		return this.knowledge.getRules();
	}

	@Override
	public List<Model> getModels() {
		return this.knowledge.getModels();
	}
	
	
	
	
}
