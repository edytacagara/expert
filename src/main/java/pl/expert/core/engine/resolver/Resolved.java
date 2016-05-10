/*
 * The GNU GPL License 
 * Copyright (c) 2015-2016 IT Students of 5th year 
 * at the University of Maria Curie-Sklodowska in Lublin 
 */
package pl.expert.core.engine.resolver;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Mariusz Batyra
 */
public class Resolved {

    private static final Map<String, Boolean> resolve = new LinkedHashMap<>();
    private static boolean changed = false;

    public static Boolean get(final String condition) {
        return resolve.get(condition);
    }

    public static void put(final String condition, final boolean value) {
        resolve.put(condition, value);
        changed = true;
    }

    public static boolean wasChanged() {
        return changed;
    }

    public static void clearChanged() {
        changed = false;
    }

}
