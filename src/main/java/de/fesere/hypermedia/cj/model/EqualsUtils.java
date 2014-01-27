package de.fesere.hypermedia.cj.model;

public class EqualsUtils {

    public static boolean isSameRef(Object a, Object b) {
        return a == b;
    }

    public static boolean isSameTypeNotNull(Object a, Object b) {
        if(a == null || b == null) {
            return false;
        }


        return b.getClass().isInstance(a);
    }
}
