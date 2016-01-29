package com.example.polina.restaurantapplication.dto;

/**
 * Created by polina on 28.01.16.
 */
public class Price {
    private String tier;
    private String message;
    private String currency;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
