/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.engine.resolver;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mariusz Batyra
 */
public class Resolved {

    private static final Map<String, List<Boolean>> resolveDuplicates = new HashMap<>();
    private static final Map<String, Boolean> resolve = new LinkedHashMap<>();
    private static boolean changed = false;

    public static Boolean get(final String condition) {
        return resolve.get(condition);
    }

    public static void put(final String condition, final boolean value) {
        Boolean oldValue = resolve.get(condition);
        if(oldValue == null || oldValue != value) {
            changed = true;
        }
        resolve.put(condition, value);
    }

    public static boolean wasChanged() {
        return changed;
    }

    public static void clearChanged() {
        changed = false;
    }

    public static void putWithDuplicates(final String condition, final boolean value) {

        checkDuplicates(resolveDuplicates, condition, value);

    }

    public static void checkDuplicates(Map<String, List<Boolean>> Mapa, final String condition, final boolean value) {
        List<Boolean> listBooleanResults = resolveDuplicates.get(condition);
        if (listBooleanResults == null) {
        } else if (!listBooleanResults.isEmpty()) {
            listBooleanResults.add(value);
            resolveDuplicates.put(condition, listBooleanResults);
            changed = true;
        } else {
            putNonDuplicates(condition, value);
            changed = true;
        }
    }

    public static void putNonDuplicates(final String condition, final boolean value) {

        List<Boolean> list = null;
        list.add(value);
        resolveDuplicates.put(condition, list);

    }
}
