/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.ui.inference;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import pl.expert.core.database.knowledge.KnowledgeElement;
import pl.expert.core.database.knowledge.Model;
import pl.expert.core.database.knowledge.Rule;

/**
 * FXML Controller class
 *
 * @author rafal16x
 */
public class DialogController implements Initializable {

	private InferenceInterface inferenceInterface;

	@FXML
	public Button inferenceStart;

	@FXML
	public Button showResult;

	@FXML
	public ListView<KnowledgeElement> ruleView;
	
	@FXML
	private ListView<KnowledgeElement> modelView;

	/**
	 * Initializes the controller class.
	 *
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	@FXML
	private void close() {
		this.inferenceInterface.onClose();
	}

	@FXML
	private void doMagic() {
		this.inferenceInterface.startAll();
	}

	@FXML
	private void showRules() {
		this.ruleView.setItems(FXCollections.observableArrayList(this.prepareKnowledgeElements(this.inferenceInterface.getRules())));
		this.modelView.setItems(FXCollections.observableArrayList(this.prepareKnowledgeElements(this.inferenceInterface.getModels())));
		
	}
	
	private List<KnowledgeElement>  prepareKnowledgeElements(List<? extends KnowledgeElement> elements){
		List<KnowledgeElement> rules = new ArrayList<>();
		elements.stream().filter((element) -> (element != null && (element).isResolved()))
				.forEach(element -> {
					if (element != null && element.isResolved()) {
						rules.add(element);
					}
				});
		return rules;
	}

	/**
	 *
	 * @param inferenceInterface1
	 */
	public void initControiller(InferenceInterface inferenceInterface1) {
		this.inferenceInterface = inferenceInterface1;
	}

}
