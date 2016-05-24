/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.database.knowledge;

import com.google.common.base.Joiner;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import pl.expert.core.engine.expression.OperatorEnum;

/**
 *
 * @author Mariusz Batyra
 */
public class Model implements Serializable, KnowledgeElement {

    private String argument;
    private List<String> operators;
    private List<BigDecimal> values;
    private String result;
    private Boolean resolved;

    public Model() {
        operators = new ArrayList<>();
        values = new ArrayList<>();
    }

    public Model(String argument, List<String> operators, List<BigDecimal> values, String result) {
        this.argument = argument;
        this.operators = operators;
        this.values = values;
        this.result = result;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public List<String> getOperators() {
        return operators;
    }

    public void setOperators(List<String> operators) {
        this.operators = operators;
    }

    public List<BigDecimal> getValues() {
        return values;
    }

    public void setValues(List<BigDecimal> values) {
        this.values = values;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Boolean getResolved() {
        return resolved;
    }

    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
    }
    
    @Override
    public String toString() {
        return argument + " " + getCondition() + " -> " + result;
    }
    
    private String getCondition() {
        String condition = "";
        for (int i=0; i< operators.size(); i++) {
            condition += OperatorEnum.createByName(operators.get(i)).getOperator()+ " " + values.get(i);
        }
//        return Joiner.on(" ").join(operators, values);
        return condition;
    }
}
