package edu.uga.cs.ridesharefirebase;

import com.google.firebase.auth.FirebaseUser;

public class User {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private int points;

    public User() {
    }

    /**
     * Creates a User POJO based on a FirebaseUser.
     * @param firebaseUser the FirebaseUser from which to create an User POJO.
     */
    public User(FirebaseUser firebaseUser) {
        this.id = firebaseUser.getUid();
        this.name = firebaseUser.getDisplayName();
        this.email = firebaseUser.getEmail();
        this.phoneNumber = firebaseUser.getPhoneNumber();
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", points=" + points +
                '}';
    } // toString()

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Changes the user's points by the specified value, and returns the new total.
     * Use positive ints to increment points, use negative ints to decrement.
     * @param change the amount to change the point total by.
     * @return the new point total;
     */
    public int changePoints(int change) {
        this.points =this.points + change;
        return points;
    }
}
