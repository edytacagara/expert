/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.engine.resolver;

import java.util.logging.Level;
import java.util.logging.Logger;
import pl.expert.core.database.knowledge.Knowledge;
import pl.expert.core.database.knowledge.Rule;

/**
 *
 * @author Mariusz Batyra
 */
public class RuleResolver {

    private static final Logger LOG = Logger.getLogger(RuleResolver.class.getName());

    public static void resolve(final Knowledge knowledge) {
        Resolved.clearChanged();
        for (Rule rule : knowledge.getRules()) {
            boolean ruleResult = true;
            for (String condition : rule.getConditions()) {
                LOG.log(Level.INFO, "Processing condition: {0}", condition);
                Boolean conditionResolved = Resolved.get(condition);
                if (conditionResolved == null) {
                    ModelResolver.resolve(knowledge.getModels(), condition);
                    conditionResolved = Resolved.get(condition);
                    if (conditionResolved == null) {
                        conditionResolved = UserAnswerReader.readBoolean(condition);
                        Resolved.put(condition, conditionResolved);
                    }
                    ConstraintResolver.resolveConstraint(knowledge.getConstraints(), condition);
                } else if (!conditionResolved) {
                    LOG.log(Level.INFO, "Condition: {0} is false", condition);
                    ruleResult = false;
                    break;
                }
            }
            rule.setResolved(ruleResult);
            Resolved.put(rule.getResult(), ruleResult);
            //Resolved.putWithDuplicates(rule.getResult(), ruleResult);
            
        }
    }

}
