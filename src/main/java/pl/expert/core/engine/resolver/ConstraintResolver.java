/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.engine.resolver;

import java.util.List;
import pl.expert.core.database.knowledge.Constraint;

/**
 *
 * @author Mariusz Batyra
 */
public class ConstraintResolver {

    public static void resolveConstraint(final List<Constraint> constraints, final String condition) {
        boolean conditionResolved = Resolved.get(condition);
        for (Constraint i : constraints) {
            if (i.getConstraint().get(0).equalsIgnoreCase(condition)) {
                Resolved.put(i.getConstraint().get(1), !conditionResolved);
            } else if (i.getConstraint().get(1).equalsIgnoreCase(condition)) {
                Resolved.put(i.getConstraint().get(0), !conditionResolved);
            }
        }
    }

}
