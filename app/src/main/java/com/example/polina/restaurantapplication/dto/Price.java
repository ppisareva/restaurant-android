package com.example.polina.restaurantapplication.dto;

import java.io.Serializable;

/**
 * Created by polina on 28.01.16.
 */
public class Price implements Serializable {
    private int tier;
    private String message;
    private String currency;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
