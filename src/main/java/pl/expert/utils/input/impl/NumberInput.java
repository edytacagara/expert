/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.utils.input.impl;

import pl.expert.utils.input.Input;
import com.sun.javafx.beans.IDProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import pl.expert.utils.MessageDialogs;

/**
 *
 * @author rafal16x
 */
@IDProperty("number")
public class NumberInput extends TextField implements Input<Double>{

    private static final String EMPTY = "";
    private static final String COMMA = ".,";

    public NumberInput() {
        this.addEventFilter(KeyEvent.KEY_TYPED, this.getKeyEvent());
    }

    public void clean() {
        this.setText(EMPTY);
    }

    private EventHandler<KeyEvent> getKeyEvent() {
        return (KeyEvent t) -> {
            char ar[] = t.getCharacter().toCharArray();
            int dividerNumber = this.charCount(COMMA, ar);
            char ch = ar[t.getCharacter().toCharArray().length - 1];
            if (!(ch >= '0' && ch <= '9') || ((ch == '.' || ch == ',') && dividerNumber > 1)) {
                MessageDialogs.showSimpleErrorAlert("Wprowadzono niepoprawny znak");
                t.consume();
            }
        };
    }

    private int charCount(String expr, char[] chars) {
        int count = 0;
        for (char h : chars) {
            if (expr.contains(String.valueOf(h))) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Double getValue() {
        return  Double.valueOf(this.getText().replace(",", "."));
    }
}
