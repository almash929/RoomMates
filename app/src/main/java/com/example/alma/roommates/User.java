package com.example.alma.roommates;

import com.parse.ParseUser;

/**
 * Created by alma on 14/01/2016.
 */
public class User {

    String name;
    String mail;
    String apartmentName;
    String apartmentId;
    double age;

    //get user details from Parse
    protected void getUserDetails(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        this.name = currentUser.getUsername();
        this.mail = currentUser.getEmail();
        this.apartmentId = Apartment.getApartmentId(currentUser);
    }


}
