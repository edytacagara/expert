/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.database.knowledge;

import com.google.common.base.Joiner;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mariusz Batyra
 */
public class Rule implements Serializable {

    private List<String> conditions;
    private String result;
    private Boolean resolved;

    public Rule() {
        conditions = new ArrayList<>();
    }

    public Rule(List<String> conditions, String result) {
        this.conditions = conditions;
        this.result = result;
    }

    public List<String> getConditions() {
        return conditions;
    }
    
    public String getConditionsToInput() {
        return Joiner.on(", ").join(conditions);
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
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
        return Joiner.on(" i ").join(conditions) + " -> " + result;
    }
    
    public void updateRule(Rule newRule) {
        this.conditions = newRule.getConditions();
        this.result = newRule.getResult();
    }
}
