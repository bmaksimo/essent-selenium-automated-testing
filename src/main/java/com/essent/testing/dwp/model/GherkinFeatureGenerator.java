package com.essent.testing.dwp.model;

import org.junit.Test;

import java.util.stream.Stream;

public class GherkinFeatureGenerator {

    private static final FilterElementConverter generator = new FilterElementConverter();

    @Test
    public void generate() throws Throwable {
        Stream.of("admin", "billing", "contracting_switching", "credit_management",
        "ESS", "service", "tasks", "test", "sales_marketing").forEach(where ->
            generator.generateGherkinPhrase(where));
    }
}
