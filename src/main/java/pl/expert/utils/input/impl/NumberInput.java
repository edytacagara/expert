/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.utils.input.impl;

import pl.expert.utils.input.Input;
import com.sun.javafx.beans.IDProperty;
import java.math.BigDecimal;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import pl.expert.utils.MessageDialogs;

/**
 *
 * @author rafal16x
 */
@IDProperty("number")
public class NumberInput extends TextField implements Input<BigDecimal>{

    private static final String EMPTY = "";
    private static final char COMMA = ',';
    private static final char DOT = '.';

    public NumberInput() {
        //this.addEventFilter(KeyEvent.KEY_TYPED, this.getKeyEvent());
    }

    public void clean() {
        this.setText(EMPTY);
    }

/*    private EventHandler<KeyEvent> getKeyEvent() {
        return (KeyEvent t) -> {
            char ar[] = t.getCharacter().toCharArray();
            char ch = ar[t.getCharacter().toCharArray().length - 1];
            boolean isSeparator = ch == COMMA || ch == DOT;
            boolean justHaveSep = t.toString().contains(String.valueOf(COMMA)) || t.toString().contains(String.valueOf(DOT));
            if (!(ch >= '0' && ch <= '9') ||  (isSeparator && justHaveSep)) {
                MessageDialogs.showSimpleErrorAlert("Wprowadzono niedozwolony znak");
                t.consume();
            }
        };
    }*/
    @Override
    public BigDecimal getValue() {
        return  new BigDecimal(this.getText().replace(",", "."));
    }
    
    private boolean checkInput(){
    	int dots = 0;
    	int nonDigits = 0;
    	String inputText = this.getText().replace(",", ".");
    	char[] chars = inputText.toCharArray();
    	for(char h : chars){
    		if(h != '.' && !(h > '0' && h < '9') ){
    			nonDigits++;
    		}
    		if(h == '.'){
    			dots++;
    		}
    	}
    	return dots < 2 && !(nonDigits > 0);
    }

	@Override
	public boolean validate() {
		return this.checkInput();
	}
}
