/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.database.reader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pl.expert.utils.XMLTagConst;
import pl.expert.core.database.knowledge.Model;
import pl.expert.utils.SpecialCharacterConst;

/**
 *
 * @author Mariusz Batyra
 */
public class ModelReader {

    public static List<Model> readModelList(Document document) {
        final List<Model> models = new ArrayList<>();
        Node modelsListNode = document.getElementsByTagName(XMLTagConst.MODELS_TAG).item(0);
        Element modelsElement = (Element) modelsListNode;
        NodeList argumentList = modelsElement.getElementsByTagName(XMLTagConst.ARGUMENT_TAG);
        NodeList operatorList = modelsElement.getElementsByTagName(XMLTagConst.OPERATOR_TAG);
        NodeList valueList = modelsElement.getElementsByTagName(XMLTagConst.VALUE_TAG);
        NodeList resultList = modelsElement.getElementsByTagName(XMLTagConst.RESULT_TAG);
        for (int i = 0; i < argumentList.getLength(); i++) {
            Element argumentElem = (Element) argumentList.item(i);
            String argument = argumentElem.getChildNodes().item(0).getNodeValue();

            Element operatorElem = (Element) operatorList.item(i);
            String operators = operatorElem.getChildNodes().item(0).getNodeValue();

            Element valueElem = (Element) valueList.item(i);
            String values = valueElem.getChildNodes().item(0).getNodeValue();

            Element resultElem = (Element) resultList.item(i);
            String result = resultElem.getChildNodes().item(0).getNodeValue();

            models.add(new Model(argument, operatorsAsList(operators), valuesAsList(values), result));
        }
        return models;
    }

    private static List<String> operatorsAsList(String operators) {
        operators = operators.replaceAll(SpecialCharacterConst.SPACE_CHARACTER,
                SpecialCharacterConst.EMPTY_CHARACTER);
        String[] split = operators.split(SpecialCharacterConst.COMMA_CHARACTER);
        return Arrays.asList(split);
    }

    private static List<BigDecimal> valuesAsList(String values) {
        values = values.replaceAll(SpecialCharacterConst.SPACE_CHARACTER,
                SpecialCharacterConst.EMPTY_CHARACTER);
        String[] split = values.split(SpecialCharacterConst.COMMA_CHARACTER);
        List<BigDecimal> result = new ArrayList<>();
        for (String i : split) {
            result.add(new BigDecimal(i));
        }
        return result;
    }

}
