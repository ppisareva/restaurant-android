package com.example.polina.restaurantapplication;

/**
 * Created by polina on 28.01.16.
 */
public class Restaurant {
    private String name;
    private double lat;
    private double lng;
    private int distance;
    private String formattedPhone;
    private String tier;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getFormattedPhone() {
        return formattedPhone;
    }

    public void setFormattedPhone(String formattedPhone) {
        this.formattedPhone = formattedPhone;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }



}
