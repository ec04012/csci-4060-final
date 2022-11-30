package edu.uga.cs.ridesharefirebase;

/**
 * This class represents a single ride, either an offer or a request.
 */
public class Ride {

    private String driver;;
    private String sourceCity;
    private String sourceState;
    private String sourceZip;
    private String destinationCity;
    private String destinationState;
    private String destinationZip;
    private String car;

    public Ride()
    {
        this.driver = null;
        this.sourceCity = null;
        this.sourceState = null;
        this.sourceZip = null;
        this.destinationCity = null;
        this.destinationState = null;
        this.destinationZip = null;
        this.car = null;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "driver='" + driver + '\'' +
                ", sourceCity='" + sourceCity + '\'' +
                ", sourceState='" + sourceState + '\'' +
                ", sourceZip='" + sourceZip + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", destinationState='" + destinationState + '\'' +
                ", destinationZip='" + destinationZip + '\'' +
                ", car='" + car + '\'' +
                '}';
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
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

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }
} // Ride class