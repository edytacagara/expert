/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.utils.input.impl;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import pl.expert.utils.input.Input;

/**
 *
 * @author rafal16x
 */
public class BooleanInput extends ToggleGroup implements Input<Boolean> {

    private RadioButton yes;
    private RadioButton no;

    public BooleanInput() {
        this.yes = new RadioButton("Tak");
        this.no = new RadioButton("No");
        this.yes.setSelected(true);
        this.setGroup(yes,no);
    }

    @Override
    public Boolean getValue() {
        return this.yes.isSelected();
    }

    private void setGroup(RadioButton... radioButtons) {
        for (RadioButton rb : radioButtons) {
            rb.setToggleGroup(this);
        }
    }
}
