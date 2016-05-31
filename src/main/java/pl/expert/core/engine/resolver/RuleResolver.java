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
        knowledge.getRules().stream().forEach((rule) -> {
            rule.setResolved(resolveRule(knowledge, rule));
//            Resolved.put(rule.getResult(), ruleResult);
//            Resolved.putWithDuplicates(rule.getResult(), ruleResult);
        });
    }

    private static boolean resolveRule(final Knowledge knowledge, Rule rule) {
        for (String condition : rule.getConditions()) {
            LOG.log(Level.INFO, "Processing condition: {0}", condition);
            Boolean conditionResolved = Resolved.get(condition);
            if (conditionResolved == null) {
                Rule conditionAsRule = conditionAsRule(knowledge, condition);
                if(conditionAsRule != null) {
                    resolveRule(knowledge, conditionAsRule);
                }
                ModelResolver.resolve(knowledge.getModels(), condition);
                conditionResolved = Resolved.get(condition);
                if (conditionResolved == null) {
                    conditionResolved = UserAnswerReader.getAnswear(condition, UserAnswerReader.BOOLEAN_QUESTION, Boolean.class);
                    Resolved.put(condition, conditionResolved);
                }
                if(!conditionResolved) {
                    return conditionResolved;
                }
                ConstraintResolver.resolveConstraint(knowledge.getConstraints(), condition);
            } else if (!conditionResolved) {
                LOG.log(Level.INFO, "Condition: {0} is false", condition);
                return false;
            }
        }
        return true;
    }

    private static Rule conditionAsRule(final Knowledge knowledge, String condition) {
        for(Rule rule : knowledge.getRules()) {
            if(rule.getResult().equalsIgnoreCase(condition)) {
                return rule;
            }
        }
        return null;
    }
    
}
