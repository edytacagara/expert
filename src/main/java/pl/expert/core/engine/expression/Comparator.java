/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.engine.expression;

import java.math.BigDecimal;

/**
 *
 * @author Mariusz Batyra
 */
public class Comparator {
    
    public static boolean compare(final BigDecimal userValue, final BigDecimal value, final OperatorEnum operator) {
        if(OperatorEnum.EQ.equals(operator)) {
            return userValue.compareTo(value) == 0;
        }
        if(OperatorEnum.NE.equals(operator)) {
            return userValue.compareTo(value) != 0;
        }
        if(OperatorEnum.LT.equals(operator)) {
            return userValue.compareTo(value) < 0;
        }
        if(OperatorEnum.LE.equals(operator)) {
            return userValue.compareTo(value) <= 0;
        }
        if(OperatorEnum.GE.equals(operator)) {
            return userValue.compareTo(value) >= 0;
        }
        if(OperatorEnum.GT.equals(operator)) {
            return userValue.compareTo(value) > 0;
        }
        
        throw new UnsupportedOperationException("Unsupported operator " + operator.name());
    }
    
}
