/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.database.knowledge;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mariusz Batyra
 */
public class Constraint implements KnowledgeElement {

    private List<String> constraint;

    public Constraint() {
        constraint = new ArrayList<>();
    }

    public Constraint(List<String> constraint) {
        this.constraint = constraint;
    }

    public List<String> getConstraint() {
        return constraint;
    }

    public void setConstraint(List<String> constraint) {
        this.constraint = constraint;
    }

    @Override
    public String toString() {
        return Joiner.on(", ").join(constraint);
    }
}
