/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.database.reader;

import java.io.File;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import pl.expert.core.database.knowledge.Constraint;
import pl.expert.core.database.knowledge.Knowledge;
import pl.expert.core.database.knowledge.Model;
import pl.expert.core.database.knowledge.Rule;
import pl.expert.core.exception.KnowledgeReaderException;

/**
 *
 * @author Mariusz Batyra
 */
public class KnowledgeReader {

    public Knowledge loadKnowledge(final File file) throws KnowledgeReaderException {
        Document document = readDocument(file);
        try {
            List<Rule> ruleList = RuleReader.readRuleList(document);
            List<Model> modelList = ModelReader.readModelList(document);
            List<Constraint> constraintList = ConstraintReader.readConstraintList(document);

            Knowledge knowledge = new Knowledge(ruleList, modelList, constraintList);
            return knowledge;
        } catch (Exception e) {
            e.printStackTrace();
            throw new KnowledgeReaderException("Blad parsowania xml: " + e.getMessage());
        }
    }

    private Document readDocument(final File file) throws KnowledgeReaderException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(file);
            document.getDocumentElement().normalize();
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            throw new KnowledgeReaderException("Blad odczytu pliku: " + e.getMessage());
        }
    }

}
