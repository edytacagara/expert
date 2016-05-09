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
import pl.expert.utils.SpecialCharacterConst;

/**
 *
 * @author Mariusz Batyra
 */
public class ConstraintReader {

    public static List<String> readConstraintList(Document document) {
        final List<String> constraints = new ArrayList<>();
        Node constraintsListNode = document.getElementsByTagName(XMLTagConst.CONSTRAINTS_TAG).item(0);
        Element constraintsElement = (Element) constraintsListNode;
        NodeList constraintsList = constraintsElement.getElementsByTagName(XMLTagConst.CONSTRAINT_TAG);
        for (int i = 0; i < constraintsList.getLength(); i++) {
            Element constraintElem = (Element) constraintsList.item(i);
            String constraintStr = constraintElem.getChildNodes().item(0).getNodeValue();

            constraints.addAll(constraintsAsList(constraintStr));
        }
        return constraints;
    }

    private static List<String> constraintsAsList(String constraints) {
        constraints = constraints.replaceAll(SpecialCharacterConst.SPACE_CHARACTER,
                SpecialCharacterConst.EMPTY_CHARACTER);
        String[] split = constraints.split(SpecialCharacterConst.COMMA_CHARACTER);
        return Arrays.asList(split);
    }

}
