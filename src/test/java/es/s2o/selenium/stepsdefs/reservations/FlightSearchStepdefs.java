package es.s2o.selenium.stepsdefs.reservations;

import es.s2o.selenium.domain.FlightSearchDTO;
import es.s2o.selenium.pages.FlightSearcherPage;
import es.s2o.selenium.pages.ReservationPage;
import es.s2o.selenium.services.ReservationService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Steps;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sacrists on 26.02.17.
 */
public class FlightSearchStepdefs {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String WEB_ROOT = "WEB_ROOT";
    @Managed(driver = "firefox")
    private WebDriver driver;


    @Steps
    private FlightSearcherPage flightSearcherPage;

    @Before
    public void beforeScenario() {
        LOGGER.debug("beforeScenario starts");
    }

    @After
    public void afterScenario() {
        LOGGER.debug("afterScenario starts");
    }

    @Given("^I'm in the search page$")
    public void iMInTheSearchPage() throws Throwable {
        LOGGER.debug("iMInTheSearchPage starts");
        String baseUrl = SystemEnvironmentVariables.createEnvironmentVariables().getProperty(WEB_ROOT);
        flightSearcherPage.openAt(baseUrl);

    }

    @When("^I check the following flight:$")
    public void iCheckTheFollowingFlight(List<FlightSearchDTO> flightSearchDTOList) throws Throwable {
        FlightSearchDTO flights = flightSearchDTOList.get(0);
        flightSearcherPage.enterOrigin(flights.getOrigin());
        flightSearcherPage.enterDestiny(flights.getDestiny());
        flightSearcherPage.enterDate(flights.getTypeOfFlight(), flights.getDate(), flights.getReturnDate());
        flightSearcherPage.enterPassengers(flights.getAdults(), flights.getChildren(), flights.getBabies());

    }

    @Then("^I verify that the flight exists$")
    public void iVerifyThatTheFlightExists() throws Throwable {
        flightSearcherPage.clickSubmitButton();
        LOGGER.debug("iVerifyThatTheFlightExists starts");
        boolean flightsFound = flightSearcherPage.isThereAFlight();
        Assert.assertTrue("No flights found after search", flightsFound);
    }
}
