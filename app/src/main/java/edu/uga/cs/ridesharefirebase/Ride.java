package edu.uga.cs.ridesharefirebase;

/**
 * This class represents a single ride, either an offer or a request.
 */
public class Ride {

    /* driver and rider store the id of that respective user.

    They are also used to store whether driver and rider have accepted the ride.

    If driver is not null, then the driver has accepted the ride.
    Similarly, if rider is not null, then the rider has accepted the ride

    When a Ride is created, either driver or rider will be initialized with an id.
    If a driver posts a ride offer, then the driver id will be set.
    Conversely, if the user posts a rider request, the rider id will be set.

    The other id will be set when that respective user accepts the ride in either
    the browse offers screen or the browse requests screen.

    */
    private String driver;
    private String rider;

    private String key; /* A unique key representing the ride form the database */
    private String sourceCity;
    private String sourceState;
    private String sourceZip;
    private String destinationCity;
    private String destinationState;
    private String destinationZip;
    private String date;
    private String car;

    /*
    Confirmation can only happen after both the driver and rider have accepted.
    When both users have accepted, the ride will show up in both their "My Rides" screen.

    From their, both users can choose to confirm the ride. After both users have confirmed,
    points will be exchanged.
     */
    private boolean driverConfirmed = false;
    private boolean riderConfirmed = false;

    public Ride()
    {
        this.key= null;
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
                "key='" + key + "\'" +
                ",\n driver='" + driver + '\'' +
                ",\n rider='" + rider + '\'' +
                ",\n driverConfirmed='" + driverConfirmed + '\'' +
                ",\n riderConfirmed='" + riderConfirmed + '\'' +
                ",\n sourceCity='" + sourceCity + '\'' +
                ",\n sourceState='" + sourceState + '\'' +
                ",\n sourceZip='" + sourceZip + '\'' +
                ",\n destinationCity='" + destinationCity + '\'' +
                ",\n destinationState='" + destinationState + '\'' +
                ",\n destinationZip='" + destinationZip + '\'' +
                ",\n car='" + car + '\'' +
                ",\n date='" + date + '\'' +

                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public boolean isDriverConfirmed() {
        return driverConfirmed;
    }

    public void setDriverConfirmed(boolean driverConfirmed) {
        this.driverConfirmed = driverConfirmed;
    }

    public boolean isRiderConfirmed() {
        return riderConfirmed;
    }

    public void setRiderConfirmed(boolean riderConfirmed) {
        this.riderConfirmed = riderConfirmed;
    }
} // Ride class
