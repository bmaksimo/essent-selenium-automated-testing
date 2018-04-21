package com.billinghouse.cucumber.runtime.parameter;

import com.billinghouse.cucumber.runtime.annotations.InputParameter;
import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;


@Test
public class ParametersUtilTest {
    private final static Logger log = Logger.getLogger(ParametersUtilTest.class);
    @OutputParameter(name = "developer")
    public String customerName;

    @OutputParameter(name = "cto")
    public static final String MANAGER = "Jim van Dam";

    @InputParameter(name = "cto")
    public String developer;

    @InputParameter(name = "cto")
    public String cto;

    @Test
    public void testOutputParameter() throws Throwable {
        customerName = "Sjaak van Vliet";

        ParametersUtil.visitOutputParameters(this, (BiConsumer<String, Object>) (n, v) -> {

        });
        ParametersUtil.assignOutToEachInputParam(
            (Function<String, Object>)(name) -> {
                return MANAGER;
            },
            this);
        assertThat("Input Parameter is assigned a value", developer.equals(MANAGER));
        assertThat("Input Parameter is assigned a value", cto.equals(MANAGER));
    }

}
