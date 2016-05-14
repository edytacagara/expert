/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import pl.expert.core.database.knowledge.Knowledge;
import pl.expert.core.database.knowledge.Rule;

public class EditController implements Initializable {
    
    private Knowledge knowledge;
    
    @FXML
    private Pane testPane;
    
//    @FXML
//    private Button addEditButton;

    @FXML
    private ListView<Rule> list;
    ObservableList<Rule> items = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        testPane.setVisible(false);
        knowledge = MainFrame.getKnowledge();
        knowledge.getRules().forEach(rule -> items.add(rule));
        list.setItems(items);
    }

    public void initData() {
        System.out.println("init ");
    }

    public void addButtonAction() { 
        testPane.setVisible(false);
//        Rule rule = new Rule(Lists.newArrayList("testWarunek1", "testWarunek2"), "testWniosek");
//        knowledge.getRules().add(rule);
//        items.add(rule);
//        list.setItems(items);
    }
}
