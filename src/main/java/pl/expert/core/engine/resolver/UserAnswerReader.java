/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.engine.resolver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mariusz Batyra
 */
public class UserAnswerReader {

    private static final Logger LOG = Logger.getLogger(UserAnswerReader.class.getName());

    public static BigDecimal readBigDecimal(String question) {
        System.out.println("Question (numeric value): " + question);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String str = bufferedReader.readLine();
            return new BigDecimal(str);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error while input reading! {0}", e.getMessage());
            return null;
        }
    }

    public static boolean readBoolean(String question) {
        System.out.println("Question (logical value y/n): " + question + " ?");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String str = bufferedReader.readLine();
            return "y".equalsIgnoreCase(str);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error while input reading! {0}", e.getMessage());
            return false;
        }
    }

}
