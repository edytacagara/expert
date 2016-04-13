/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.database.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pl.expert.utils.XMLTagConst;
import pl.expert.core.database.knowledge.Rule;
import pl.expert.utils.SpecialCharacterConst;

/**
 *
 * @author Mariusz Batyra
 */
public class RuleReader {

    public static List<Rule> readRuleList(Document document) {
        final List<Rule> rules = new ArrayList<>();
        Node rulesListNode = document.getElementsByTagName(XMLTagConst.RULES_SET_TAG).item(0);
        Element rulesElement = (Element) rulesListNode;
        NodeList conditionList = rulesElement.getElementsByTagName(XMLTagConst.CONDITIONS_TAG);
        NodeList roleList = rulesElement.getElementsByTagName(XMLTagConst.RESULT_TAG);
        for (int i = 0; i < conditionList.getLength(); i++) {
            Element conditionElem = (Element) conditionList.item(i);
            String conditions = conditionElem.getChildNodes().item(0).getNodeValue();

            Element resultElem = (Element) roleList.item(i);
            String result = resultElem.getChildNodes().item(0).getNodeValue();

            rules.add(new Rule(conditionsAsList(conditions), result));
        }
        return rules;
    }

    private static List<String> conditionsAsList(String conditions) {
        conditions = conditions.replaceAll(SpecialCharacterConst.SPACE_CHARACTER,
                SpecialCharacterConst.EMPTY_CHARACTER);
        String[] split = conditions.split(SpecialCharacterConst.COMMA_CHARACTER);
        return Arrays.asList(split);
    }

}
