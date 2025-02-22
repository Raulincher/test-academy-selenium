package es.s2o.selenium.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

/**
 * Created by sacrists on 26.02.17.
 */
public class FlightSearchDTO {

    private String name;
    private String phone;
    private String email;
    private String date;
    private String number;
    private String time;
    private String origin;
    private String destiny;
    private String returnDate;
    private String passengers;
    private int babies;
    private int adults;
    private int children;
    private String color;
    private String type;
    private String typeOfFlight;

    public FlightSearchDTO() {

    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public String getTypeOfFlight() {
        return typeOfFlight;
    }

    public void setTypeOfFlight(String typeOfFlight) {
        this.typeOfFlight = typeOfFlight;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setPassengers(String passengers) {
        this.passengers = passengers;
    }

    public String getPassengers() {return passengers;}

    public int getBabies() {
        return babies;
    }

    public void setBabies(int babies) {
        this.babies = babies;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", date='" + date + '\'' +
                ", number='" + number + '\'' +
                ", time='" + time + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
