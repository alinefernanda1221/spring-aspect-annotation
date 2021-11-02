package com.aspect.annotation;

import com.aspect.exception.MethodNoArgumentsException;
import com.aspect.exception.NoPropertyFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FindValueByPropertyNameAspect {


    @After("@annotation(FindValueByPropertyName)")
    public void doSearch(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        var anotation = signature.getMethod().getAnnotation(FindValueByPropertyName.class);

        String propertyName = anotation.propertyName();
        String[] methodArgsNames = signature.getParameterNames();
        Object[] methodArgsValues = joinPoint.getArgs();

        validateSearchExecution(propertyName, methodArgsValues);

        Object fetchedItem = searchPropertyValue(propertyName, methodArgsNames, methodArgsValues);

        if (fetchedItem == null){
            throw new NoPropertyFoundException("Property name request for search was not found or your value is null.");
        }

        System.out.println("Property name request for search value: " + fetchedItem);
    }

    private Object searchPropertyValue(String propertyName, String[] methodArgsNames, Object[] methodArgsValues) {
        boolean isPropertyNameSearch = !propertyName.contains(".");
        String approachSearchDescription = isPropertyNameSearch ? "property name" : "hierarchy object";

        System.out.println("Searching property value by approach -> " + approachSearchDescription);

        Object fetchedItem = isPropertyNameSearch ? findByPropertyName(propertyName, methodArgsNames, methodArgsValues)
                                                  : findByObjectHierarchy(propertyName, methodArgsNames, methodArgsValues);
        return fetchedItem;
    }

    private Object findByPropertyName(String propertyName, String[] methodArgsNames, Object[] methodArgsValues) {
        Object findItem = null;

        for (int i = 0; i < methodArgsNames.length; i++) {
            if (findItem != null) break;

            findItem = (methodArgsNames[i].equals(propertyName) && methodArgsValues[i] != null) ? methodArgsValues[i] : null;
        }
        return findItem;
    }

    private Object findByObjectHierarchy(String propertyName, String[] methodArgsNames, Object[] methodArgsValues) {
        String searchParam = propertyName.split("\\.")[0];
        String propertyHierarchy = propertyName.replace(searchParam + ".", "");

        Object objectParam = findByPropertyName(searchParam, methodArgsNames, methodArgsValues);

        if (objectParam != null) {
            ExpressionParser parser = new SpelExpressionParser();
            return parser.parseExpression(propertyHierarchy).getValue(new StandardEvaluationContext(objectParam));
        }

        return null;
    }


    private void validateSearchExecution(String propertyName, Object[] methodArgsValues) {
        if (propertyName.isEmpty()) {
            throw new IllegalArgumentException("No property name was entered for search.");
        }

        if (methodArgsValues.length == 0) {
            throw new MethodNoArgumentsException("Method annotated has no arguments for query.");
        }
    }
}