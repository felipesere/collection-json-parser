package de.fesere.hypermedia.cj.transformer.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FieldExtractor {

    public List<Field> annotatedFieldsFromType(Class type, Class annotation) {
        if(isObjectType(type)) {
            return Collections.emptyList();
        }
        Field [] fields =  type.getDeclaredFields();
        List<Field> result = filterFieldsWithAnnotation(fields, annotation);

        result.addAll(annotatedFieldsFromType(type.getSuperclass(), annotation));

        return result;
    }

    private List<Field> filterFieldsWithAnnotation(Field[] fields, Class annotation) {
        List<Field> result = new LinkedList<>(); for(Field field : fields) {
            Object obj = field.getAnnotation(annotation);
            if (obj != null) {
                result.add(field);
            }
        }
        return result;
    }

    private boolean isObjectType(Class type) {
        return type.getName().equalsIgnoreCase(Object.class.getName());
    }

    public <T> Constructor<T> findAnnotatedConstructor(Class<T> type, Class annotation) {
        Constructor[] constructorCandidates = type.getConstructors();

        if(constructorCandidates == null) {
            constructorCandidates = type.getSuperclass().getConstructors();
        }

        return filterByAnnotation(constructorCandidates, annotation);
    }

    private Constructor filterByAnnotation(Constructor[] constructorCandidates, Class annotation) {
        for(Constructor constructor : constructorCandidates) {

            if(hasAnnotatedParameters(constructor, annotation)) {
                return constructor;
            }
        }

        return null;
    }

    private boolean hasAnnotatedParameters(Constructor constructor, Class annotation) {
        for(Annotation[] parameterAnnoatations : constructor.getParameterAnnotations()) {

           for(Annotation anno : parameterAnnoatations) {
               if(anno.annotationType().equals(annotation)) {
                   return true;
               }
           }
        }

        return false;
    }

    public <T> T createInstance(Class<T> type, Map<String, Object> values) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<T> constructor = findAnnotatedConstructor(type, Data.class);

        List<String> parameterNames = getOrderedListOfAnnotatedParameters(constructor, Data.class);

        Object [] parameterValues = getValuesInOrder(parameterNames, values);


        return constructor.newInstance(parameterValues);
    }

    private Object[] getValuesInOrder(List<String> parameterNames, Map<String, Object> values) {
        Object [] result = new Object[parameterNames.size()];
        for(int i = 0; i < parameterNames.size(); i++) {
            result[i] = values.get(parameterNames.get(i));
        }

        return result;
    }

    private <T> List<String> getOrderedListOfAnnotatedParameters(Constructor<T> constructor, Class<Data> dataClass) {
        List<String> result = new LinkedList<>();
        for(Annotation[] parameterAnnotations : constructor.getParameterAnnotations() ) {
            Data data = null;
            for(Annotation annotation : parameterAnnotations) {
                if(annotation.annotationType().equals(dataClass)) {
                    data = (Data) annotation;
                    break;
                }
            }

            if(data != null) {
                result.add(data.name());
            }
        }

        return result;
    }
}
