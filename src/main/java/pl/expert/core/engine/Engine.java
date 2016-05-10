/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.expert.core.database.knowledge.Knowledge;
import pl.expert.core.database.knowledge.Model;
import pl.expert.core.database.knowledge.Rule;

/**
 *
 * @author Mariusz Batyra
 */
public class Engine {

    private static final Logger LOG = Logger.getLogger(Engine.class.getName());
    private final Map<String, Boolean> resolved = new LinkedHashMap<>();

    public void doMagic(final Knowledge knowledge) {
        for (Rule rule : knowledge.getRules()) {
            for (String condition : rule.getConditions()) {
                LOG.log(Level.INFO, "Processing condition: {0}", condition);
                Boolean conditionResolved = resolved.get(condition);
                if (conditionResolved == null) {
                    resolveModel(knowledge.getModels(), condition);
                } else if (!conditionResolved) {
                    LOG.log(Level.INFO, "Condition: {0} is false", condition);
                    break;
                }
            }
        }
    }

    private void resolveModel(final List<Model> models, final String condition) {
        if (resolved.get(condition) != null) {
            LOG.log(Level.INFO, "Condition: {0} is already resolved", condition);
            return;
        }
        for (Model model : models) {
            if (condition.equalsIgnoreCase(model.getResult())) {
                // TODO zapamietac argumenty aby nie pytac np 2 razy o temp
                BigDecimal userValue = read(model.getArgument());
                boolean resolve = ModelResolver.resolve(userValue, model);
                model.setResolved(resolve);
                resolved.put(condition, resolve);
                LOG.log(Level.INFO, "Model Condition: {0} was resolved: {1}", new Object[]{condition, resolve});
                return;
            }
        }
    }

    private BigDecimal read(String question) {
        System.out.println(question);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String str = bufferedReader.readLine();
            return new BigDecimal(str);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error while input reading! {0}", e.getMessage());
            return null;
        }

    }

}
