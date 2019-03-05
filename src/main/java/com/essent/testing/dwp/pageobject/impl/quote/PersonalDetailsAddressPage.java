package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Autocrat;
import com.essent.automation.autocrat.Model;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.tables.CustomerAddress;

import java.util.Random;

import static com.essent.automation.autocrat.Action.*;
import static com.essent.testing.dwp.autocrat.element.quote.B2CQuoteElements.*;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.INPUT;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.TOGGLE_CHECKBOX;
import static org.junit.Assert.fail;

public class PersonalDetailsAddressPage extends QuoteCreationGuidedStep {

    private CustomerAddress address;

    public void setCustometAddress(CustomerAddress customerAddress) {
        address = customerAddress;
    }

    private class HideAddressSuggestion implements Model.Callback {
        @Override
        public void onAccess(Autocrat.ExecutionContext context, Model.Step step, WebElement value) {
            JavascriptExecutor jsExec = (JavascriptExecutor) context.driver;
            String setProperty = "style = 'display:none'";
            logger().info("Executing javascript " + setProperty + " on target element");
            jsExec.executeScript("arguments[0]." + setProperty, value);
        }
    }

    @Override
    public boolean fillInFormData() {
        return fillInCustomerAddress();
    }

    public boolean fillInCustomerAddress() {

        String street = address.getStreet();
        if(street.equals("Random")) { street =  randomStreet(); }
        String houseNr = Integer.toString(address.getHouseNr());
        String houseNrAdd = address.getHouseNrAdd();
        String bus = address.getBus();
        String postcode = address.getPostalCode();
        String city = address.getCity();
        String country = address.getCountry();

        Model.Execution initializeAddress = createExecution();
        initializeAddress.
            element(DELIVERY_ADDR_STREET.element()).
            element(DELIVERY_ADDR_STREET_SUGGESTION.element()).
            element(DELIVERY_ADDR_HOUSE_NR.element()).
            element(DELIVERY_ADDR_HOUSE_ADD.element()).
            element(DELIVERY_ADDR_BUS.element()).
            element(DELIVERY_ADDR_ZIPCODE.element()).
            element(DELIVERY_ADDR_CITY.element()).
            element(DELIVERY_ADDR_COUNTRY.element()).
            element(COPY_ADDRESS_CONNECTION_TO_BILLING.element()).
            step(createStep(ACCESS).element(COPY_ADDRESS_CONNECTION_TO_BILLING.name()).requireDisplayed(false).callback(hideIconOverlays())).
            step(createStep(CLICK).element(COPY_ADDRESS_CONNECTION_TO_BILLING.name()).requireDisplayed(false), INPUT.getSleepInMillis());

        initializeAddress.
            step(createStep(TYPING).element(DELIVERY_ADDR_STREET.name()).value(street), INPUT.getSleepInMillis()).
            step(createStep(ACCESS).element(DELIVERY_ADDR_STREET_SUGGESTION.name()).requireDisplayed(false).callback(new HideAddressSuggestion())).
            step(createStep(TYPING).timeoutInSeconds(3).element(DELIVERY_ADDR_HOUSE_NR.name()).value(houseNr), INPUT.getSleepInMillis()).
            step(createStep(TYPING).element(DELIVERY_ADDR_HOUSE_ADD.name()).value(houseNrAdd), INPUT.getSleepInMillis());
        if (StringUtils.isNotEmpty(bus)) {
            initializeAddress.step(createStep(TYPING).element(DELIVERY_ADDR_BUS.name()).value(bus), INPUT.getSleepInMillis());
        }
        initializeAddress.step(createStep(TYPING).element(DELIVERY_ADDR_ZIPCODE.name()).value(postcode), INPUT.getSleepInMillis()).
            step(createStep(TYPING).element(DELIVERY_ADDR_CITY.name()).value(city), INPUT.getSleepInMillis());
        if (StringUtils.isNotEmpty(country)) {
            initializeAddress.step(createStep(Action.SELECT).element(DELIVERY_ADDR_COUNTRY.name()).value(country));
        }
        if(!execute(initializeAddress)) {
            fail("Customer Address fields were not initialized");
        }
        Model.Execution copyAddress = createExecution()
            .element(COPY_ADDRESS_CONNECTION_TO_BILLING.element())
            .step(createStep(SLEEP).sleepInMillis(3000))
            .step(createStep(CLICK).element(COPY_ADDRESS_CONNECTION_TO_BILLING.name()).requireDisplayed(false), TOGGLE_CHECKBOX.getSleepInMillis());
        return execute(copyAddress);
    }

    protected String randomStreet() {
        String[] streets = {
            "Abelenlaan", "Abraham Hanslaan", "Albertlei", "Albrecht Rodenbachlaan", "Alfsberg", "Altenastraat", "Antwerpsesteenweg", "Asterlaan", "Astweg", "Azalealaan", "Baanvelden", "Babbelkroonstraat", "Bautersemstraat", "Beeklaan", "Beekse Velden", "Beemdenlaan", "Bergstraat", "Beukendreef", "Biesaard", "Binnenbeemd", "Blauwesteenstraat", "Bochtstraat", "Boniverlei", "Boomgaard", "Boskapelweg", "Bosveldlaan", "Broekbosstraat", "Brouwersstraat", "Brugmanstraat", "Cornelis Marckxlaan", "Cornelis Verhulstlaan", "De Villermontstraat", "Deken Jozef Van Herckstraat", "Dennenlaan", "Doelveld", "Doopput", "Doornstraat", "Dorre Eikstraat", "Drabstraat", "Dries", "Duffelsesteenweg", "Duffelshoek", "Duivenstraat", "Edegemsesteenweg", "Edgard Tinellaan", "Eekhoven", "Eertbrugge", "Eikenstraat", "Elisabethstraat", "Elsbos", "Elshagelaan", "Gallo-Romeinenlaan", "Ganzenbollaan", "Gemeenteplein", "Graaf de Ribeaucourtplein", "Groene Wandeling", "Groene Weg", "Groeningenlei", "Groot Veld", "Haakstuk", "Heiveldekens", "Helenaveldstraat", "Hoeve-ter-Bekelaan", "Hof van Spruytlaan", "Hofstraat", "Hoge Akker", "Holle Eikaard", "Holle Weg", "Hondstraat", "Hoogbunderlaan", "Hoogmolenlaan", "Ijzermaalberg", "Infanterielaan", "Irislaan", "Jan-Baptist Reykerslaan", "Jeroen en Peter Conventlaan", "Jordaensstraat", "Joris Olyslaegerslaan", "Josephine Charlottestraat", "Josse Clymansstraat", "Kapelstraat", "Kartuizersweg", "Kattenbroek", "Kauwlei", "Kazernelaan", "Keizershoek", "Keltenveld", "Kerkeland", "Kleine Meylstraat", "Klokkestraat", "Kongostraat", "Konijnenveld", "Koningin Astridlaan", "Koningin Fabiolalaan", "Kontichhof", "Kosterijstraat", "Kruisbeemd", "Kruisschanslei", "Kruisstraat", "Langbosweg", "Leopoldstraat", "Liersebaan", "Lijsterbolstraat", "Lints Veld", "Lintsesteenweg", "Magdalenastraat", "Mechelsesteenweg", "Metsersaard", "Meylweg", "Mina Telghuislaan", "Molenstraat", "Montfortstraat", "Moorstraat", "Mortelstuk", "Nachtegaalstraat", "Nakkersgoed", "Neerveld", "Nerenaard", "Nieuwstraat", "Noordstraat", "Oever", "Ooststatiestraat", "Oude Lei", "Pastoor De Laetstraat", "Pauwhoevestraat", "Peter Benoitlaan", "Philip Romboutslaan", "Pierstraat", "Pieter Potlaan", "Pluyseghemstraat", "Prins Boudewijnlaan", "Prins Filiplaan", "Pronkenbergstraat", "Rauwaard", "Reepkenslei", "Reetsestraat", "Reipelveld", "Rijkerooistraat", "Rompelei", "Roosken", "Rozengaard", "Rubensstraat", "Satenrozen", "Scheihagenstraat", "Schoolstraat", "Schuttershofstraat", "Schuurveld", "Singel", "Sint-Jansplein", "Sint-Martinusplein", "Sint-Martinusstraat", "Sleutelstraat", "Spoorwegstraat", "Staf Van Elzenlaan", "Stationsplein", "Steenakker", "Steentjeslaan", "Strepestraat", "Tanghoflaan", "Ter Sneeuw", "Transvaalstraat", "Tulpenlaan", "Twee Bunder", "Valveken", "Van Dyckstraat", "Varkensmarkt", "Vekenveld", "Veldkant", "Verbrande Hoevestraat", "Vijverlaan", "Vitsenveld", "Vlierenpaal", "Volderij", "Vredestraat", "Wierookstuk", "Wild Veld", "Wilgstuk", "Wipstraat", "Wisselbeemd", "Witte-Stedeweg", "Witvrouwenveldstraat", "Zilverbergstraat",
        };

        Random rnd = new Random();

        int index = (int) (rnd.nextFloat() * streets.length);

        return streets[index];
    }
}
