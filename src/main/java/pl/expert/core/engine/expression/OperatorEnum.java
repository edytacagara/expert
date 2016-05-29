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

    EQ("==", "eq"),
    NE("!=", "ne"),
    LT("<", "lt"),
    LE("<=", "le"),
    GE(">=", "ge"),
    GT(">", "gt");

    private final String operator;
    private final String name;

    private OperatorEnum(final String operator, final String name) {
        this.operator = operator;
        this.name = name;
    }

    public String getOperator() {
        return operator;
    }

    public String getName() {
        return name;
    }

    public static OperatorEnum createByName(final String name) {
        for (OperatorEnum i : OperatorEnum.values()) {
            if (i.name().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return null;
    }

    public static OperatorEnum createByOperator(final String operator) {
        for (OperatorEnum i : OperatorEnum.values()) {
            if (i.getOperator().equalsIgnoreCase(operator)) {
                return i;
            }
        }
        return null;
    }
}
