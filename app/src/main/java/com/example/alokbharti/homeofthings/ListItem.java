package com.example.alokbharti.homeofthings;

/**
 * Created by Alok Bharti on 4/8/2018.
 */

public class ListItem {

    private String heading;
    private String details;
    private String ImageUrl;

    public ListItem(String heading, String details, String ImageUrl) {
        this.heading = heading;
        this.details = details;
        this.ImageUrl= ImageUrl;
    }

    public String getHeading() {
        return heading;
    }

    public String getDetails() {
        return details;
    }

    public String getImageUrl() {
        return ImageUrl;
    }
}
