/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.database.knowledge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mariusz Batyra
 */
public class Knowledge implements Serializable {

    private List<Rule> rules;
    private List<Model> models;
    private List<String> constraints;

    public Knowledge() {
        rules = new ArrayList<>();
        models = new ArrayList<>();
        constraints = new ArrayList<>();
    }

    public Knowledge(List<Rule> rules, List<Model> models, List<String> constraints) {
        this.rules = rules;
        this.models = models;
        this.constraints = constraints;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public List<String> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<String> constraints) {
        this.constraints = constraints;
    }

}
