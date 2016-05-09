/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.ui.exception;

import pl.expert.core.exception.ExpertSystemException;


/**
 *
 * @author rafal16x
 */
public class UIException extends ExpertSystemException{
    
    public UIException(String messagString) {
        super(messagString);
    }
    
    public UIException(String message, Throwable cause){
        super(message, cause);
    }
 
}
