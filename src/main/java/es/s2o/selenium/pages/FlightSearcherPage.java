package es.s2o.selenium.pages;

import es.s2o.selenium.domain.FlightSearchDTO;
import org.openqa.selenium.By;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Created by sacrists on 26.02.17.
 */

public class FlightSearcherPage extends PageObjectBase {

    public void enterOrigin(String origin) {
        WebElementFacade acceptCookiesButton = $(By.id("onetrust-accept-btn-handler"));
        if (acceptCookiesButton.isCurrentlyVisible()) {
            acceptCookiesButton.click();
        }
        WebElementFacade originField = $(By.id("originInput"));
        originField.waitUntilVisible().waitUntilClickable().click();
        originField.type(origin);


        WebElementFacade popupList = $(By.id("popup-list"));
        popupList.waitUntilVisible();

        WebElementFacade firstButton = popupList.thenFindAll(By.cssSelector("li.vy-list-dropdown_item button.vy-list-dropdown_item_button")).get(0);
        firstButton.waitUntilClickable().click();

    }

    public void enterDestiny(String destiny) {
        WebElementFacade destinyField = $(By.id("destinationInput"));
        destinyField.waitUntilVisible().waitUntilClickable().click();
        destinyField.type(destiny);

        WebElementFacade popupList = $(By.id("popup-list"));
        popupList.waitUntilVisible();

        WebElementFacade firstButton = popupList.thenFindAll(By.cssSelector("li.vy-list-dropdown_item button.vy-list-dropdown_item_button")).get(0);
        firstButton.waitUntilClickable().click();
    }

    public void enterDate(String typeOfFlight, String departureDate, String returnDate) {
        WebElementFacade datePickerButton = $(By.id("outboundDateIdLabel"));
        datePickerButton.waitUntilVisible().waitUntilClickable().click();

        WebElementFacade calendarPopup = $(By.id("popupCalendarSelector"));
        calendarPopup.waitUntilVisible();

        WebElementFacade flightTypeButton = $(By.cssSelector(".vy-switch_button"));

        boolean isRoundTrip = Boolean.parseBoolean(flightTypeButton.getAttribute("aria-pressed"));

        if (typeOfFlight.equalsIgnoreCase("Ida y vuelta") && !isRoundTrip) {
            flightTypeButton.waitUntilClickable().click();
        } else if (typeOfFlight.equalsIgnoreCase("Solo ida") && isRoundTrip) {
            flightTypeButton.waitUntilClickable().click();
        }

        selectDate(departureDate);

        if (typeOfFlight.equalsIgnoreCase("Ida y vuelta") && returnDate != null) {
            selectDate(returnDate);
        }

    }

    private void selectDate(String date) {
        String[] dateParts = date.split("/");
        String day = removeLeadingZero(dateParts[0]);
        String month = removeLeadingZero(dateParts[1]);
        String year = dateParts[2];

        String monthName = getMonthName(Integer.parseInt(month));


        int monthIndex = Integer.parseInt(month) - 1;
        String calendarId = "calendarDaysTable" + year + monthIndex + day;

        WebElementFacade nextMonthButton = $(By.id("nextButtonCalendar"));

        while (!isMonthVisible(monthName)) {
            nextMonthButton.waitUntilClickable().click();
        }


        WebElementFacade calendarTable = $(By.id(calendarId));
        calendarTable.waitUntilVisible();

        List<WebElementFacade> dayElements = findAll(By.cssSelector("#" + calendarId + " a"));
        for (WebElementFacade dayElement : dayElements) {
            if (dayElement.getText().equals(day)) {
                dayElement.waitUntilClickable().click();
                return;
            }
        }

        throw new RuntimeException("No se pudo encontrar el día " + day + " en el calendario.");
    }


    private boolean isMonthVisible(String expectedMonth) {
        List<WebElementFacade> visibleMonths = findAll(By.cssSelector("#id-grid-label .ui-datepicker-month"));

        for (WebElementFacade monthElement : visibleMonths) {
            if (monthElement.getText().equalsIgnoreCase(expectedMonth)) {
                return true;
            }
        }
        return false;
    }

    private String removeLeadingZero(String value) {
        if (value != null && value.startsWith("0")) {
            return value.substring(1);
        }
        return value;
    }

    private String getMonthName(int monthNumber) {
        String[] months = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        return months[monthNumber - 1];
    }

    public void enterPassengers(int adults, int children, int babies) {

        WebElementFacade passengersPopup = $(By.cssSelector(".passengers-popup_wrapper .passengers-popup"));
        passengersPopup.waitUntilVisible();

        setPassengerCount("adults", adults);
        setPassengerCount("children", children);
        setPassengerCount("babies", babies);

    }


    private void setPassengerCount(String type, int desiredCount) {
        String buttonIncreaseId = "";
        String buttonDecreaseId = "";
        String numberSelector = "";

        switch (type) {
            case "adults":
                buttonIncreaseId = "adultsIncrease";
                buttonDecreaseId = "adultsDecrease";
                numberSelector = ".passengers-popup_main_counter[aria-label*='Adultos'] div.number";
                break;
            case "children":
                buttonIncreaseId = "childrenIncrease";
                buttonDecreaseId = "childrenDecrease";
                numberSelector = ".passengers-popup_main_counter[aria-label*='Ni'] div.number";
                break;
            case "babies":
                buttonIncreaseId = "infantsIncrease";
                buttonDecreaseId = "infantsDecrease";
                numberSelector = ".passengers-popup_main_counter[aria-label*='Beb'] div.number";
                break;
        }

        WebElementFacade currentCountElement = $(By.cssSelector(numberSelector));
        currentCountElement.waitUntilVisible();

        int currentCount = Integer.parseInt(currentCountElement.getText());

        while (currentCount < desiredCount) {
            WebElementFacade buttonIncrease = $(By.id(buttonIncreaseId));
            buttonIncrease.waitUntilClickable().click();
            currentCount++;
        }

        while (currentCount > desiredCount) {
            WebElementFacade buttonDecrease = $(By.id(buttonDecreaseId));
            buttonDecrease.waitUntilClickable().click();
            currentCount--;
        }
    }

    public void clickSubmitButton() {
        WebElementFacade submitButton = $(By.id("btnSubmitHomeSearcher"));
        submitButton.waitUntilVisible().waitUntilClickable().click();
    }

    public boolean isThereAFlight() {
        try {
            boolean tabFound = false;

            for (String tab : getDriver().getWindowHandles()) {
                getDriver().switchTo().window(tab);
                String currentUrl = getDriver().getCurrentUrl();

                if (currentUrl.startsWith("https://tickets.vueling.com")) {
                    tabFound = true;
                    break;
                }
            }

            if (!tabFound) {
                return false;
            }

            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
            WebElement flightCardContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flightCardContent")));
            return flightCardContent != null;
        } catch (Exception e) {
            return false;
        }
    }



    private FlightSearchDTO mapReservation(Map<Object, String> rowMap) {
        FlightSearchDTO reservation = new FlightSearchDTO();
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
