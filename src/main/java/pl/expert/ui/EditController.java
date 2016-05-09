/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.ui;

import com.google.common.collect.Lists;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import pl.expert.core.database.knowledge.Knowledge;
import pl.expert.core.database.knowledge.Rule;

public class EditController implements Initializable {
    
    private Knowledge knowledge;

    @FXML
    private ListView<Rule> list;
    ObservableList<Rule> items = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        knowledge = MainFrame.getKnowledge();
        knowledge.getRules().forEach(rule -> items.add(rule));
        list.setItems(items);
    }

    public void initData() {
        System.out.println("init ");
    }

    public void addButtonAction() { 
        Rule rule = new Rule(Lists.newArrayList("testWarunek1", "testWarunek2"), "testWniosek");
        knowledge.getRules().add(rule);
        items.add(rule);
        list.setItems(items);
    }
}
