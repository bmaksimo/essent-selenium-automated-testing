package com.essent.testing.dwp.scenario;

import com.billinghouse.random.RandomUser;
import com.billinghouse.test_automation.util.dsl.DateExpressionsUtil;
import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model.Execution;
import com.essent.automation.autocrat.Model.Step;
import com.essent.automation.util.Sleeper;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.datagenerator.vat.VatNumberGenerator;
import com.essent.testing.scenario.RegisteredScenario;
import com.essent.testing.selenium.DWPSeleniumDriver;
import com.essent.testing.selenium.helper.autocrat.AutocratExecutionAdapter;
import com.google.gson.Gson;
import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.StringUtils;
import org.iban4j.CountryCode;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkAndConvertToDwpDate;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jim on 27-12-2017.
 *
 */
public abstract class DwpScenario extends RegisteredScenario {

    @Resource(name="dwpSeleniumDriver")
    protected DWPSeleniumDriver seleniumDriver;

    protected void isDwpRunning() throws Exception {
        String dwpUrl = ConfigProvider.getProperty(ConfigKey.DWP_BASE_URL);
        seleniumDriver.setBaseUrl(dwpUrl);
        seleniumDriver.goToHomePage();
        String currentUrl = seleniumDriver.getDriver().getCurrentUrl();
        if (null != currentUrl && !currentUrl.equals(dwpUrl)) {
            seleniumDriver.setBaseUrl(currentUrl);
            seleniumDriver.goToHomePage();
        }
        Sleeper.sleepTightInSeconds(3);
        logger().info("Current URL: " + currentUrl);
        assertTrue(currentUrl.startsWith(seleniumDriver.getBaseUrl()));
    }

    protected String generateVat(String generatorParam) {
        String countryCode = generatorParam.replace("generator:vat:", "");
        if(countryCode.length() > 3 || CountryCode.getByCode(countryCode) == null) {
            throw new CucumberException("Wrong country code: " + countryCode);
        }
        return new VatNumberGenerator().getVatNum(CountryCode.getByCode(countryCode));
    }

    protected String generateCompanyName() {
        Map<String, String> options = new HashMap<>();
        Map reply = executeJavascriptMethod("TrGetRandomUser", options);
        String status = ((String) reply.get("status"));
        boolean success = StringUtils.equals("PASSED", status);
        if (success) {
            Map userData = (Map) reply.get("user");
            RandomUser randomUser = randomUser(userData);
            String first = randomUser.getName().getFirst();
            String last = randomUser.getName().getLast();
            return first + " & " + last + " Startup";
        }
        else throw new CucumberException("ramdomuser.me API failure");
    }

    protected RandomUser randomUser(Map reply) {
        Gson gson = new Gson();
        String randomUserJs = gson.toJson(reply);
        return gson.fromJson(randomUserJs, RandomUser.class);
    }

    protected String randomStreet() {
        String[] streets = {
            "Abelenlaan", "Abraham Hanslaan", "Albertlei", "Albrecht Rodenbachlaan", "Alfsberg", "Altenastraat", "Antwerpsesteenweg", "Asterlaan", "Astweg", "Azalealaan", "Baanvelden", "Babbelkroonstraat", "Bautersemstraat", "Beeklaan", "Beekse Velden", "Beemdenlaan", "Bergstraat", "Beukendreef", "Biesaard", "Binnenbeemd", "Blauwesteenstraat", "Bochtstraat", "Boniverlei", "Boomgaard", "Boskapelweg", "Bosveldlaan", "Broekbosstraat", "Brouwersstraat", "Brugmanstraat", "Cornelis Marckxlaan", "Cornelis Verhulstlaan", "De Villermontstraat", "Deken Jozef Van Herckstraat", "Dennenlaan", "Doelveld", "Doopput", "Doornstraat", "Dorre Eikstraat", "Drabstraat", "Dries", "Duffelsesteenweg", "Duffelshoek", "Duivenstraat", "Edegemsesteenweg", "Edgard Tinellaan", "Eekhoven", "Eertbrugge", "Eikenstraat", "Elisabethstraat", "Elsbos", "Elshagelaan", "Gallo-Romeinenlaan", "Ganzenbollaan", "Gemeenteplein", "Graaf de Ribeaucourtplein", "Groene Wandeling", "Groene Weg", "Groeningenlei", "Groot Veld", "Haakstuk", "Heiveldekens", "Helenaveldstraat", "Hoeve-ter-Bekelaan", "Hof van Spruytlaan", "Hofstraat", "Hoge Akker", "Holle Eikaard", "Holle Weg", "Hondstraat", "Hoogbunderlaan", "Hoogmolenlaan", "Ijzermaalberg", "Infanterielaan", "Irislaan", "Jan-Baptist Reykerslaan", "Jeroen en Peter Conventlaan", "Jordaensstraat", "Joris Olyslaegerslaan", "Josephine Charlottestraat", "Josse Clymansstraat", "Kapelstraat", "Kartuizersweg", "Kattenbroek", "Kauwlei", "Kazernelaan", "Keizershoek", "Keltenveld", "Kerkeland", "Kleine Meylstraat", "Klokkestraat", "Kongostraat", "Konijnenveld", "Koningin Astridlaan", "Koningin Fabiolalaan", "Kontichhof", "Kosterijstraat", "Kruisbeemd", "Kruisschanslei", "Kruisstraat", "Langbosweg", "Leopoldstraat", "Liersebaan", "Lijsterbolstraat", "Lints Veld", "Lintsesteenweg", "Magdalenastraat", "Mechelsesteenweg", "Metsersaard", "Meylweg", "Mina Telghuislaan", "Molenstraat", "Montfortstraat", "Moorstraat", "Mortelstuk", "Nachtegaalstraat", "Nakkersgoed", "Neerveld", "Nerenaard", "Nieuwstraat", "Noordstraat", "Oever", "Ooststatiestraat", "Oude Lei", "Pastoor De Laetstraat", "Pauwhoevestraat", "Peter Benoitlaan", "Philip Romboutslaan", "Pierstraat", "Pieter Potlaan", "Pluyseghemstraat", "Prins Boudewijnlaan", "Prins Filiplaan", "Pronkenbergstraat", "Rauwaard", "Reepkenslei", "Reetsestraat", "Reipelveld", "Rijkerooistraat", "Rompelei", "Roosken", "Rozengaard", "Rubensstraat", "Satenrozen", "Scheihagenstraat", "Schoolstraat", "Schuttershofstraat", "Schuurveld", "Singel", "Sint-Jansplein", "Sint-Martinusplein", "Sint-Martinusstraat", "Sleutelstraat", "Spoorwegstraat", "Staf Van Elzenlaan", "Stationsplein", "Steenakker", "Steentjeslaan", "Strepestraat", "Tanghoflaan", "Ter Sneeuw", "Transvaalstraat", "Tulpenlaan", "Twee Bunder", "Valveken", "Van Dyckstraat", "Varkensmarkt", "Vekenveld", "Veldkant", "Verbrande Hoevestraat", "Vijverlaan", "Vitsenveld", "Vlierenpaal", "Volderij", "Vredestraat", "Wierookstuk", "Wild Veld", "Wilgstuk", "Wipstraat", "Wisselbeemd", "Witte-Stedeweg", "Witvrouwenveldstraat", "Zilverbergstraat",
        };

        Random rnd = new Random();

        int index = (int) (rnd.nextFloat() * streets.length);

        return streets[index];
    }

    protected Execution createExecution() {
        return AutocratExecutionAdapter.newExecution();
    }

    protected Step createStep(Action action) {
        return new Step().action(action);
    }

    protected boolean execute(final Execution execution) {
        seleniumDriver.waitForRequestsToFinish();
        return AutocratExecutionAdapter.execute(seleniumDriver.getDriver(), execution);
    }

    protected String toDwpDate(String parameter) {
        return checkAndConvertToDwpDate(parameter);
    }

    protected String toDwpEndDate(String parameter) {
        return DateExpressionsUtil.checkAndConvertToDwpContracEndDate(parameter);
    }

    protected void injectJavaScriptTestRunner() {
        seleniumDriver.injectJavaScriptTestRunner();
    }

    protected boolean executeJavascriptTest(String registeredJsClass, Object options) {
        return seleniumDriver.executeJavascriptTest(registeredJsClass, options);
    }

    public boolean executeJavascriptTest(String registeredJsClass, Object options, boolean withException) {
        return seleniumDriver.executeJavascriptTest(registeredJsClass, options, withException);
    }

    protected Map executeJavascriptMethod(String registeredJsClass, Object options) {
        return seleniumDriver.executeJavascriptMethod(registeredJsClass, options);
    }

    public void tearDown() {
        if (seleniumDriver != null) {
            tidyUp(seleniumDriver);
        }
    }

    protected void setUpWebDriver() throws Exception {
        setUpWebDriver(seleniumDriver);
        seleniumDriver.initNgWebDriver();
    }
}
