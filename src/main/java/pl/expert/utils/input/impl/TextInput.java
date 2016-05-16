/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.utils.input.impl;

import pl.expert.utils.input.Input;
import com.sun.javafx.beans.IDProperty;
import javafx.scene.control.TextField;

/**
 *
 * @author rafal16x
 */
@IDProperty("text")
public class TextInput extends TextField implements Input<String> {

    @Override
    public String getValue() {
        return this.getText();
    }

}
