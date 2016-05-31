/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.engine;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.expert.core.database.knowledge.Knowledge;
import pl.expert.core.engine.resolver.Resolved;
import pl.expert.core.engine.resolver.RuleResolver;

/**
 *
 * @author Mariusz Batyra
 */
public class Engine {

    private static final Logger LOG = Logger.getLogger(Engine.class.getName());

    public Knowledge doMagic(final Knowledge knowledge) {
        LOG.log(Level.INFO, "Start algorithm");
        do {
            LOG.log(Level.INFO, "Next iteration");
            RuleResolver.resolve(knowledge);
        } while (Resolved.wasChanged());
        LOG.log(Level.INFO, "End algorithm");
        printResult(knowledge);
        return knowledge;
    }

    private Map<String, Boolean> packResolvedToMap(final Knowledge knowledge) {
        Map<String, Boolean> result = new LinkedHashMap<>();
        knowledge.getRules().stream().filter((rule) -> (rule.getResolved() != null)).forEach((rule) -> {
            result.put(rule.getResult(), rule.getResolved());
        });

        return result;
    }

    private void printResult(final Knowledge knowledge) {
        System.out.println("-------------- result ----------");

        Map<String, Boolean> resolvedMap = packResolvedToMap(knowledge);
        resolvedMap.keySet().stream().forEach((key) -> {
            System.out.println("Rule: " + key + " -> " + resolvedMap.get(key));
        });
    }

}
