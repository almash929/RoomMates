package com.example.alma.roommates.entities;

/**
 * Created by alma on 16/04/2016.
 */
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName("ShoppingList")
public class shoppingList extends ParseObject {

    public shoppingList(){}

    public String getItemName() {
        return getString("itemName");
    }

    public void setItemName(String itemName) {
        put("itemName", itemName);
    }

    public String getUserID() {
        return getString("userID");
    }

    public void setUserID(String userID) {
        put("userID", userID);
    }

    public String getApartmentId() {
        return getString("apartmentId");
    }

    public void setApartmentId(String apartmentId) {
        put("apartmentId", apartmentId);
    }

    public boolean isCrossed() {
        return getBoolean("isCrossed");
    }

    public void setIsCrossed(boolean isCrossed) {
        put("isCrossed", isCrossed);
    }

}
