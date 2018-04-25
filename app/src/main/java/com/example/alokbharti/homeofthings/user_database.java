package com.example.alokbharti.homeofthings;

/**
 * Created by Alok Bharti on 4/12/2018.
 */

public class user_database {
    public String email;
    public String time;
    public String claimedKey;

    public user_database(String email, String time, String claimedKey) {
        this.email = email;
        this.time = time;
        this.claimedKey = claimedKey;
    }

    public String getEmail() {
        return email;
    }

    public String getTime() {
        return time;
    }

    public String getClaimedKey() {
        return claimedKey;
    }
}
