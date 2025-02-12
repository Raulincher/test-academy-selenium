package es.s2o.selenium.stepsdefs.reservations;

import es.s2o.selenium.domain.ReservationDTO;
import es.s2o.selenium.pages.ReservationListPage;
import es.s2o.selenium.pages.ReservationPage;
import es.s2o.selenium.services.ReservationService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.annotations.findby.By;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sacrists on 26.02.17.
 */
public class ReservationsStepdefs {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String WEB_ROOT = "WEB_ROOT";
    //private static final String HOME = "reservationList.html";
    @Managed(driver = "chrome")
    private WebDriver driver;


    @Steps
    private ReservationService reservationService;

    private ReservationListPage reservationListPage;
    private ReservationPage reservationPage;

    private List<ReservationDTO> reservations;

    @Before
    public void beforeScenario() {
        LOGGER.debug("beforeScenario starts");
        System.out.println("Driver initialized: " + driver);

    }

    @After
    public void afterScenario() {
        LOGGER.debug("afterScenario starts");
    }

    @Given("^I'm in the search page$")
    public void iMInTheSearchPage() throws Throwable {
        LOGGER.debug("iMInTheSearchPage starts");
        String baseUrl = SystemEnvironmentVariables.createEnvironmentVariables().getProperty(WEB_ROOT);
        reservationListPage.openAt(baseUrl);

    }

    @When("^I check the following flight:$")
    public void iCheckTheFollowingFlight(List<ReservationDTO> reservationDTOList) throws Throwable {
        //LOGGER.debug("iCheckTheFollowingFlight starts, list size:[{}]", reservationDTOList.size());
        ReservationDTO reservation = reservationDTOList.get(0);
        reservationListPage.enterOrigin(reservation.getOrigin());
        reservationListPage.enterTypeOfFlight(reservation.getTypeOfFlight());
        reservationListPage.enterDestiny(reservation.getDestiny());
        reservationListPage.enterDate(reservation.getDate());
        if(!reservation.getTypeOfFlight().equals("Solo ida")) {
            reservationListPage.enterReturnDate(reservation.getReturnDate());
        }
        reservationListPage.enterPassengers(reservation.getAdults(), reservation.getChildren(), reservation.getBabies());
        reservationListPage.clickSubmitButton();

    }

    @Then("^I verify that the flight exists$")
    public void iVerifyThatTheFlightExists() throws Throwable {
        LOGGER.debug("iVerifyThatTheFlightExists starts");
        assertThat(reservationListPage.isThereAFlight()).as("Flight existence").isTrue();
    }
}
