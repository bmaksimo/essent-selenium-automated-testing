package com.billinghouse.cucumber.runtime.parameter;

import com.billinghouse.cucumber.runtime.annotations.InputParameter;
import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import cucumber.runtime.CucumberException;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * http://toolsqa.com/cucumber/cucumber-hooks/
 */
public class ParametersUtil {

    private static final Logger logger = Logger.getLogger(ParametersUtil.class);

    private static <T extends Annotation> String extractParameterName(T t) {
        try {
            Method method = t.getClass().getMethod("name");
            logger.info("STEP:");
            logger.info(" - ACTION: EXTRACT_PARAM_NAME");
            String name = (String) method.invoke(t);
            logger.info(" - VALUE: " + name);
            return name;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error(" - ERROR:  Failure when accessing the parameter");
            throw new CucumberException("Failure when accessing the parameter", e);
        }
    }

    public static void visitOutputParameters(Object visitableTest, BiConsumer<String, Object> parameterVisitor) {
        Class<?> objectClass = requireNonNull(visitableTest).getClass();
        Set<String> uniqueNames = new HashSet<>();
        for (Field field : objectClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(OutputParameter.class)) {
                OutputParameter annotation = field.getAnnotation(OutputParameter.class);
                try {
                    String name = extractParameterName(annotation);
                    Object value = field.get(visitableTest);
                    logger.info("STEP:");
                    if(uniqueNames.contains(name)) {
                        logger.info(" - ACTION: OUT_PARAM_OVERRIDE: Overriding output Parameter" + " '" + name + "': " + (value == null ? "null" : value.toString()));

                    } else {
                        logger.info(" - ACTION: OUT_PARAM_NEW: Output Parameter" + " '" + name + "': " + (value == null ? "null" : value.toString()));
                        uniqueNames.add(name);
                    }
                    parameterVisitor.accept(name, value);

                } catch (IllegalAccessException e) {
                    logger.error(" - ERROR:  Failure when visiting Output Parameter");
                    throw new CucumberException("Failure when accessing the parameter", e);
                }
            }
        }
    }

    public static <T extends Annotation> void assignOutToEachInputParam(Function<String, Object> outParamProvider, Object visitableTest) {
        Class<?> objectClass = requireNonNull(visitableTest).getClass();
        List<Field> fields = Arrays.asList(objectClass.getDeclaredFields());
        fields.stream().filter(p -> {return p.isAnnotationPresent(InputParameter.class);}).
            forEach(field -> {
                InputParameter annotation = field.getAnnotation(InputParameter.class);
                Object outputParameter = outParamProvider.apply(annotation.name());
                logger.info("STEP:");
                try {
                    field.setAccessible(true);
                    logger.info(" - ACTION: IN_PARAM_ASSIGN_VALUE: Assigning " + (outputParameter == null? "null": outputParameter.toString()) + " to " + field.getDeclaringClass().getName() +"."+field.getName());
                    field.set(visitableTest, outputParameter);
                } catch (IllegalAccessException e) {
                    logger.error(" - FAILURE:  Failure accessing the InputParameter '" + annotation.name() + "'");
                    throw new CucumberException("Error accessing the InputParameter '" + annotation.name() + "'");
                }
            });
    }

    private static Object requireNonNull(Object object) {
        if (object == null) throw new IllegalStateException("Object may not be null");
        return object;
    }
}
