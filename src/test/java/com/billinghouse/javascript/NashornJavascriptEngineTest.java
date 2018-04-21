package com.billinghouse.javascript;

import com.billinghouse.javascript.model.options.TrGetUserLanguageOptions;
import com.essent.testing.util.ResourceUtils;
import org.junit.Before;
import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class NashornJavascriptEngineTest {

    private FileReader JS_CLASS;
    public TrGetUserLanguageOptions options = new TrGetUserLanguageOptions();

    @Before
    public void setUp() throws Exception{
        try {
            JS_CLASS = new FileReader(ResourceUtils.toPath("/js/nashorn/JGetUserLanguage.js"));
            options.setLanguageKey("NG_TRANSLATE_LANG_KEY");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void invokeJavascript() throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(JS_CLASS);
        Map result = (Map) ((Invocable) engine).invokeFunction("JGetUserLanguage", this);
        assertNotNull(result);
    }
}
