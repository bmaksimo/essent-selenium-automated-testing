package com.billinghouse.cucumber.runtime.parameter;

import com.billinghouse.cucumber.runtime.annotations.InputParameter;
import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import com.essent.testing.scenario.RegisteredScenario;
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
 * Support of input- and output- parameters (fields
 * annotated with @OutputParameter and @InputParameter defined in subclasses of RegisteredScenario )
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

    public static void collectScenarioOutputParameters(RegisteredScenario registeredScenario, BiConsumer<String, Object> putToParameterProvider) {
        Class<?> objectClass = registeredScenario.getClass();
        Set<String> parameterNames = new HashSet<>();
        for (Field declaredField : objectClass.getDeclaredFields()) {
            declaredField.setAccessible(true);
            if (declaredField.isAnnotationPresent(OutputParameter.class)) {
                OutputParameter outParamAnnotation = declaredField.getAnnotation(OutputParameter.class);
                try {
                    String parameterName = extractParameterName(outParamAnnotation);
                    Object parameterValue = declaredField.get(registeredScenario);
                    logger.info("STEP:");
                    if(parameterNames.contains(parameterName)) {
                        logger.info(" - ACTION: OUT_PARAM_OVERRIDE: Overriding output Parameter" + " '" + parameterName + "': " + (parameterValue == null ? "null" : parameterValue.toString()));

                    } else {
                        logger.info(" - ACTION: OUT_PARAM_NEW: Output Parameter" + " '" + parameterName + "': " + (parameterValue == null ? "null" : parameterValue.toString()));
                        parameterNames.add(parameterName);
                    }
                    putToParameterProvider.accept(parameterName, parameterValue);

                } catch (IllegalAccessException e) {
                    logger.error(" - ERROR:  Failure when visiting Output Parameter");
                    throw new CucumberException("Failure when accessing the parameter", e);
                }
            }
        }
    }

    public static <T extends Annotation> void assignOutValuesToInputParameters(Function<String, Object> outParamProvider, RegisteredScenario registeredScenario) {
        Class<?> objectClass = registeredScenario.getClass();
        List<Field> fields = Arrays.asList(objectClass.getDeclaredFields());
        fields.stream().filter(p -> {return p.isAnnotationPresent(InputParameter.class);}).
            forEach(field -> {
                InputParameter annotation = field.getAnnotation(InputParameter.class);
                Object outputParameter = outParamProvider.apply(annotation.name());
                logger.info("STEP:");
                try {
                    field.setAccessible(true);
                    logger.info(" - ACTION: IN_PARAM_ASSIGN_VALUE: Assigning " + (outputParameter == null? "null": outputParameter.toString()) + " to " + field.getDeclaringClass().getName() +"."+field.getName());
                    field.set(registeredScenario, outputParameter);
                } catch (IllegalAccessException e) {
                    logger.error(" - FAILURE:  Failure accessing the InputParameter '" + annotation.name() + "'");
                    throw new CucumberException("Error accessing the InputParameter '" + annotation.name() + "'");
                }
            });
    }
}
