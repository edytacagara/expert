/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.engine.expression;

/**
 *
 * @author Mariusz Batyra
 */
public enum OperatorEnum {

    EQ("=="),
    NE("!="),
    LT("<"),
    LE("<="),
    GE(">="),
    GT(">");

    private final String operator;

    private OperatorEnum(final String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public static OperatorEnum createByName(final String name) {
        for (OperatorEnum i : OperatorEnum.values()) {
            if (i.name().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return null;
    }

}
