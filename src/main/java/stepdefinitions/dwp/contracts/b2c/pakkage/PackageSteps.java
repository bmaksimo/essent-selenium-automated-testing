package stepdefinitions.dwp.contracts.b2c.pakkage;

import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.vocabulary.DwpEntity;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;

import java.util.Optional;

import static com.billinghouse.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PackageSteps extends DwpScenario {

    @Before("@DWP, @B2C, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }


    @And("^Package name is extracted from \"([^\"]*)\"$")
    public void extractPackageName(String packageAndProduct) throws Throwable {
        Optional<String> value = parameterProvider.getParameterAsString(packageAndProduct);
        assertThat(String.format("Parameter \"%s\" is unknown", packageAndProduct), value.isPresent(), is(true));
        String[] words=value.get().split("\\s");
        parameterProvider.put(DwpEntity.PackageName.name(), words[0]);

    }


    @Override
    @After("@DWP, @B2C, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
