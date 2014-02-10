package de.fesere.hypermedia.cj.annotations;

public class TypeUtils {

    public static boolean isBoolean(Class type) {
        return Boolean.class.isAssignableFrom(type) || Boolean.TYPE.isAssignableFrom(type);
    }

    public static boolean isNumber(Class type) {
        return Integer.class.isAssignableFrom(type) ||
                Integer.TYPE.isAssignableFrom(type)  ||
                Long.class.isAssignableFrom(type)   ||
                Long.TYPE.isAssignableFrom(type)    ||
                Float.class.isAssignableFrom(type)  ||
                Float.TYPE.isAssignableFrom(type)   ||
                Double.class.isAssignableFrom(type) ||
                Double.TYPE.isAssignableFrom(type);


    }

    public static boolean isString(Class type) {
        return String.class.isAssignableFrom(type);
    }

}
