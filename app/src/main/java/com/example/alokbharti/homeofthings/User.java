package com.example.alokbharti.homeofthings;

/**
 * Created by Alok Bharti on 4/7/2018.
 */

public class User {

    public String name;
    public String address;
    public String pincode;
    public String details;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPincode() {
        return pincode;
    }

    public String getDetails() {
        return details;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public boolean claimed;
    public User() {
    }

    public User(String name, String address, String pincode, String details, boolean claimed) {
        this.name = name;
        this.address = address;
        this.pincode = pincode;
        this.details = details;
        this.claimed = claimed;
    }
}
