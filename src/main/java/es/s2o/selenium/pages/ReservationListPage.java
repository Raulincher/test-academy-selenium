package es.s2o.selenium.pages;

import es.s2o.selenium.domain.ReservationDTO;
import org.openqa.selenium.By;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static net.thucydides.core.pages.components.HtmlTable.inTable;

/**
 * Created by sacrists on 26.02.17.
 */

public class ReservationListPage extends PageObjectBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private ReservationPage reservationPage;

    private WebElementFacade tblList;
    private WebElementFacade btnAdd;

    public void addReservations(ReservationDTO reservation) {
        btnAdd.click();
        reservationPage.registerReservation(reservation);
    }

    public void enterOrigin(String origin) {
        WebElementFacade acceptCookiesButton = $(By.id("onetrust-accept-btn-handler"));
        acceptCookiesButton.click();

        $(By.id("flight_origin1")).waitUntilVisible().waitUntilClickable().type(origin);
    }
    public void enterDestiny(String destiny) {
        $(By.id("flight_destiny1")).type(destiny);
    }
    public void enterDate(String date) {
        $(By.id("flight_round_date1")).type(date);
    }
    public void enterReturnDate(String returnDate) {
        $(By.id("flight_return_date1")).type(returnDate);
    }
    public void enterPassengers(int adults, int children, int babies) {

        WebElementFacade passengersButton = $(By.id("flight_passengers1"));
        passengersButton.click();
        $(By.id("people-counter-1")).waitUntilVisible();
        setPassengerCount("adult", adults);
        setPassengerCount("infants", children);
        setPassengerCount("babys", babies);
        passengersButton.click();

    }
    public void enterTypeOfFlight(String typeOfFlight) {
        WebElementFacade typeOfFlightMenuButton = $(By.id("ticketops-seeker-button"));
        typeOfFlightMenuButton.waitUntilVisible().waitUntilClickable().click();

        String xpath = "//li[.//span[text()='" + typeOfFlight + "']]";

        WebElementFacade flightOption = $(By.xpath(xpath));
        flightOption.waitUntilVisible().waitUntilClickable().click();
    }


    private void setPassengerCount(String passengerType, int count) {
        String counterId = passengerType + "1"; // Id de los contadores como "adult1", "children1", etc.
        WebElementFacade counter = $(By.id(counterId));

        // Encuentra los botones "+" y "-" para incrementar y decrementar la cantidad
        WebElementFacade incrementButton = counter.find(By.xpath(".//following-sibling::div//button[@data-people-counter-button='more']"));
        WebElementFacade decrementButton = counter.find(By.xpath(".//following-sibling::div//button[@data-people-counter-button='less']"));

        int currentCount = Integer.parseInt(counter.getText());

        // Incrementar hasta llegar al número deseado
        while (currentCount < count) {
            incrementButton.click();
            currentCount++;
        }

        // Decrementar si es necesario (en caso de que se haya pasado del número deseado)
        while (currentCount > count) {
            decrementButton.click();
            currentCount--;
        }
    }

    public void clickSubmitButton() {
        WebElementFacade submitButton = $(By.id("buttonSubmit1"));
        submitButton.waitUntilVisible().waitUntilClickable().click();
    }

    public boolean isThereAFlight() {
        LOGGER.debug("isThereAFlight starts");

        try {
            WebElementFacade resultsContainer = $(By.id("ib-fc-calendar-slides-loading"));

            resultsContainer.waitUntilVisible();

            List<WebElementFacade> flightElements = resultsContainer.thenFindAll(By.tagName("div"));

            boolean hasFlights = flightElements.size() > 2;

            LOGGER.debug("Flights found: " + flightElements.size() + " - Exists: " + hasFlights);
            return hasFlights;

        } catch (Exception e) {
            LOGGER.warn("Flight results container not found or not visible. Returning false.");
            return false;
        }
    }


    private ReservationDTO mapReservation(Map<Object, String> rowMap) {
        ReservationDTO reservation = new ReservationDTO();
        reservation.setName(rowMap.get("Name"));
        reservation.setPhone(rowMap.get("Phone"));
        reservation.setEmail(rowMap.get("Email"));
        reservation.setDate(rowMap.get("Date"));
        reservation.setNumber(rowMap.get("Number"));
        reservation.setTime(rowMap.get("Time"));
        reservation.setColor(rowMap.get("Table"));
        reservation.setOrigin(rowMap.get("Origin"));
        reservation.setDestiny(rowMap.get("Destiny"));
        reservation.setReturnDate(rowMap.get("ReturnDate"));
        reservation.setPassengers(rowMap.get("Passengers"));
        reservation.setTypeOfFlight(rowMap.get("TypeOfFlight"));
        return reservation;
    }
}
