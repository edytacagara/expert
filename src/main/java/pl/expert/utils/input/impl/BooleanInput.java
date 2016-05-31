/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.utils.input.impl;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import pl.expert.utils.input.Input;

/**
 *
 * @author rafal16x
 */
public class BooleanInput extends HBox implements Input<Boolean> {

    private final ToggleGroup group;
    private final RadioButton yes;
    private final RadioButton no;

    public BooleanInput() {
        group = new ToggleGroup();
        this.yes = new RadioButton("Tak");
        this.no = new RadioButton("Nie");
        this.yes.setSelected(true);
        this.setGroup(yes, no);
        this.getChildren().add(this.yes);
        this.getChildren().add(this.no);
    }

    @Override
    public Boolean getValue() {
        return this.yes.isSelected();
    }

    private void setGroup(RadioButton... radioButtons) {
        for (RadioButton rb : radioButtons) {
            rb.setToggleGroup(this.group);
        }
    }

    @Override
    public boolean validate() {
        return true;
    }

}
