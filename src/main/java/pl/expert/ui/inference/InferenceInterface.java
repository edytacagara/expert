/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.ui.inference;

import java.io.Serializable;
import java.util.List;

import pl.expert.core.database.knowledge.KnowledgeElement;
import pl.expert.core.database.knowledge.Model;
import pl.expert.core.database.knowledge.Rule;

/**
 *
 * @author rafal16x
 */
public interface InferenceInterface {

    void startAll();
    void onClose();
    List<Rule> getRules();
    List<Model> getModels();
    
    
}
