package com.essent.testing.dwp.model;

import com.essent.automation.autocrat.Model;
import com.essent.testing.util.ResourceUtils;
import cucumber.api.DataTable;
import io.restassured.path.json.JsonPath;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterElementConverter  {

    private static final FilterElementConverter instance = new FilterElementConverter();

    public static final FilterElementConverter get() {
        return instance;
    }

    protected final static String PATH = "/com/essent/testing/dwp/filter/model/";

    public List<Model.Element> getElements(DataTable labels) {
        JsonPath selectorJson = from(PATH + "selector.json.template");
        String xPath = selectorJson.get("selector.value");
        List<Model.Element> elements =
            labels.asList(String.class)
                .stream()
                .map(
                    lbl -> {
                             String query = xPath.replace("${label}", lbl);
                             return new Model.Element().search("XPATH").query(query).name(lbl);
                    })
                .collect(Collectors.toList());
        return elements;
    }

    public List<Model.Element> getElements(String... labels) {
        JsonPath selectorJson = from(PATH + "selector.json.template");
        String xPath = selectorJson.get("selector.value");
        List<Model.Element> elements =
            Stream.of(labels)
                .map(
                    lbl -> {
                        String query = xPath.replace("${label}", lbl);
                        return new Model.Element().search("XPATH").query(query).name(lbl);
                    })
                .collect(Collectors.toList());
        return elements;
    }



    private static String GHERKIN_PHRASE = "Then I check filter elements defined for '%s' Left Menu Item and '%s' Top Menu Item\n";

    public void generateGherkinPhrase(String where) {
        String dir = PATH + where;
        File file = new File(ResourceUtils.toPath(dir));
        File[] files = file.listFiles();
        StringBuilder builder = new StringBuilder();
        File output = new File("/Users/vagrant/Work/Billinghouse/Projects/essent-automated-testing/driver/src/test/resources/com/essent/testing/dwp/filter/model/features/" + where + ".feature");
        Arrays.stream(files).forEach(f-> {
            String then = String.format(GHERKIN_PHRASE, where.toUpperCase(), f.getName().toUpperCase());
            builder.append(then);
            JsonPath from = JsonPath.from(f);
            List<String> labels = new ArrayList<>();
            Arrays.stream(new String[]{"varchar", "enum", "selectWithSearch", "date", "bool"}).forEach(
                type -> {
                    List<String> result = from.get("test." + type + ".label");
                    if(result != null) {
                     labels.addAll(result);
                 }
                });

            for(String label: labels) {
                builder.append("|" + label + "|\n");
            }
            try {
                FileUtils.write(output, builder.toString(), Charset.forName("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    protected JsonPath from(String dataPath) {
        File file = new File(ResourceUtils.toPath(dataPath));
        return JsonPath.from(file);
    }
}
