package edu.uga.cs.ridesharefirebase;

/**
 * This class represents a single ride, either an offer or a request.
 */
public class Ride {

    private String rideId;
    private String driver;
    private String rider;;
    private String sourceCity;
    private String sourceState;
    private String sourceZip;
    private String destinationCity;
    private String destinationState;
    private String destinationZip;
    private String date;
    private String car;

    public Ride()
    {
        this.rideId = null;
        this.driver = null;
        this.rider = null;
        this.sourceCity = null;
        this.sourceState = null;
        this.sourceZip = null;
        this.destinationCity = null;
        this.destinationState = null;
        this.destinationZip = null;
        this.date = null;
        this.car = null;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "rideId='" + rideId + '\'' +
                ", driver='" + driver + '\'' +
                ", rider='" + rider + '\'' +
                ", sourceCity='" + sourceCity + '\'' +
                ", sourceState='" + sourceState + '\'' +
                ", sourceZip='" + sourceZip + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", destinationState='" + destinationState + '\'' +
                ", destinationZip='" + destinationZip + '\'' +
                ", date='" + date + '\'' +
                ", car='" + car + '\'' +
                '}';
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;

    }

    public String getRideId() {
        return rideId;
    }
    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getRider() {
        return rider;
    }

    public void setRider(String driver) {
        this.rider = driver;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getSourceState() {
        return sourceState;
    }

    public void setSourceState(String sourceState) {
        this.sourceState = sourceState;
    }

    public String getSourceZip() {
        return sourceZip;
    }

    public void setSourceZip(String sourceZip) {
        this.sourceZip = sourceZip;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDestinationState() {
        return destinationState;
    }

    public void setDestinationState(String destinationState) {
        this.destinationState = destinationState;
    }

    public String getDestinationZip() {
        return destinationZip;
    }

    public void setDestinationZip(String destinationZip) {
        this.destinationZip = destinationZip;
    }


    public void setDate(String date) {
        this.date = date;
    }
    public String getDate(){
        return date;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }
} // Ride class
