package com.example.polina.restaurantapplication;


import com.example.polina.restaurantapplication.dto.FoursquareDto;

/**
 * Created by polina on 28.01.16.
 */
public class Restaurant {
    private String name;
    private double lat;
    private double lng;
    private int distance;
    private String formattedPhone;
    private int tier = 0;

    public Restaurant(FoursquareDto.Result result) {
        name=result.venue.getName();
        lat=result.venue.getLocation().getLat();
        lng=result.venue.getLocation().getLng();
        distance=result.venue.getLocation().getDistance();
        formattedPhone=result.venue.getContact().getFormattedPhone();
        if(result.venue.getPrice()!=null)
            tier=result.venue.getPrice().getTier();
    }

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

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }
}
