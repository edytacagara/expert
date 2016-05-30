/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.engine;

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

    public void doMagic(final Knowledge knowledge) {
        LOG.log(Level.INFO, "Start algorithm");
        do {
            LOG.log(Level.INFO, "Next iteration");
            RuleResolver.resolve(knowledge);
        } while (Resolved.wasChanged());
        LOG.log(Level.INFO, "End algorithm");
        printResult(knowledge);
    }
    
    private void printResult(final Knowledge knowledge) {
        System.out.println("-------------- result ----------");
        knowledge.getRules().stream().filter((rule) -> (rule.getResolved() != null)).forEach((rule) -> {
            System.out.println("Rule: " + rule.getResult() + " -> " + rule.getResolved());
        });
    }

}
