package de.fesere.hypermedia.cj.annotations;

public final class TypeUtils {

    private TypeUtils() {} //Never called

    public static boolean isBoolean(Class type) {
        return Boolean.class.isAssignableFrom(type) || Boolean.TYPE.isAssignableFrom(type);
    }

    public static boolean isNumber(Class type) {
        return isInteger(type) ||
               isLong(type)    ||
               isFloat(type)   ||
               isDouble(type);
    }

    public static boolean isDouble(Class type) {
        return Double.class.isAssignableFrom(type) || Double.TYPE.isAssignableFrom(type);
    }

    public static boolean isFloat(Class type) {
        return Float.class.isAssignableFrom(type) || Float.TYPE.isAssignableFrom(type);
    }

    public static boolean isLong(Class type) {
        return Long.class.isAssignableFrom(type) || Long.TYPE.isAssignableFrom(type);
    }

    public static boolean isInteger(Class type) {
        return Integer.class.isAssignableFrom(type) || Integer.TYPE.isAssignableFrom(type);
    }

    public static boolean isString(Class type) {
        return String.class.isAssignableFrom(type);
    }

}
