/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.engine.resolver;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.expert.core.database.knowledge.Model;
import pl.expert.core.engine.expression.Comparator;
import pl.expert.core.engine.expression.OperatorEnum;

/**
 *
 * @author Mariusz Batyra
 */
public class ModelResolver {

    private static final Logger LOG = Logger.getLogger(ModelResolver.class.getName());

    public static void resolve(final List<Model> models, final String condition) {
        if (Resolved.get(condition) != null) {
            LOG.log(Level.INFO, "Condition: {0} is already resolved", condition);
            return;
        }
        for (Model model : models) {
            if (condition.equalsIgnoreCase(model.getResult())) {
                BigDecimal userValue = UserAnswerReader.readBigDecimal(model.getArgument());
                boolean resolve = resolveModel(userValue, model);
                model.setResolved(resolve);
                Resolved.put(condition, resolve);
                resolveWithRequestedArgument(models, model.getArgument(), userValue);
                LOG.log(Level.INFO, "Model Condition: {0} was resolved: {1}", new Object[]{condition, resolve});
                return;
            }
        }
    }

    private static boolean resolveModel(final BigDecimal userValue, final Model model) {
        if (model.getResolved() != null) {
            return model.getResolved();
        }

        boolean result = false;
        if (model.getOperators().size() == 1) {
            result = resovleSingle(userValue, model.getValues().get(0), model.getOperators().get(0));
        } else if (model.getOperators().size() == 2) {
            result = resolveRange(userValue, model.getValues().get(0), model.getValues().get(1),
                    model.getOperators().get(0), model.getOperators().get(0));
        } else {
            throw new UnsupportedOperationException("Unsupported number of operations: " + model.getOperators().size());
        }

        return result;
    }

    private static boolean resovleSingle(
            final BigDecimal userValue,
            final BigDecimal value,
            final String operator) {

        OperatorEnum operatorEnum = OperatorEnum.createByName(operator);
        return Comparator.compare(userValue, value, operatorEnum);
    }

    private static boolean resolveRange(final BigDecimal userValue,
            final BigDecimal value1, final BigDecimal value2,
            final String operator1, final String operator2) {
        OperatorEnum operatorEnum1 = OperatorEnum.createByName(operator1);
        OperatorEnum operatorEnum2 = OperatorEnum.createByName(operator2);

        return Comparator.compare(value1, userValue, operatorEnum1)
                && Comparator.compare(userValue, value2, operatorEnum2);
    }

    private static void resolveWithRequestedArgument(final List<Model> models, final String argument, final BigDecimal userValue) {
        for (Model i : models) {
            if (i.getArgument().equalsIgnoreCase(argument)) {
                boolean resolve = resolveModel(userValue, i);
                i.setResolved(resolve);
                Resolved.put(i.getResult(), resolve);
            }
        }
    }

}
