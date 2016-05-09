/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.exception;


/**
 *
 * @author rafal16x
 */
public class ExpertSystemException extends Exception {

    public ExpertSystemException() {
        super();
    }

    public ExpertSystemException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ExpertSystemException(String message) {

        super(message);
    }
}
