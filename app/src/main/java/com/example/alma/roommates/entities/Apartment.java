package com.example.alma.roommates.entities;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName("Apartment")
public class Apartment extends ParseObject{

    public Apartment (){}

    public void setName(String name) {
        put("appName", name);
    }

    public String getName() { return getString("appName"); }

    public void setNumOfRoomates(int numOfRoomates) {
        put("numOfRoomates", numOfRoomates);
    }

    public int getNumOfRoomates() {
        return getInt("numOfRoomates");
    }

    public static String getApartmentId(ParseUser currentUser){
        Apartment app = (Apartment)currentUser.getParseObject("Apartment");
        String appId = app.getObjectId();
        return appId;
    }

    public static String getApartmentName(ParseUser currentUser){
        Apartment app = (Apartment)currentUser.getParseObject("Apartment");
        String appName = app.getName();
        return appName;
    }

}
