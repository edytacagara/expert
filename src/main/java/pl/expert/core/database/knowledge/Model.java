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
import java.util.stream.Collectors;
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

    public String getOperatorsToInput() {
        return Joiner.on(", ").join(operators.stream()
            .map(operator -> OperatorEnum.createByName(operator).getOperator())
            .collect(Collectors.toList()));
    }

    public String getValuesToInput() {
        return Joiner.on(", ").join(values);
    }

    public void updateModel(Model newModel) {
        this.argument = newModel.getArgument();
        this.operators = newModel.getOperators();
        this.values = newModel.getValues();
        this.result = newModel.getResult();
    }

    private String getCondition() {
        if (operators.size() == 2 && values.size() == 2) {
            return OperatorEnum.createByName(operators.get(0)).getOperator() + " " + values.get(0)
                + " i " + OperatorEnum.createByName(operators.get(1)).getOperator() + " " + values.get(1);
        } else {
            return OperatorEnum.createByName(operators.get(0)).getOperator() + " " + values.get(0);
        }
    }
}
