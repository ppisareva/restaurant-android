package com.example.polina.restaurantapplication;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.polina.restaurantapplication.dto.FoursquareDto;

import java.io.Serializable;

/**
 * Created by polina on 28.01.16.
 */
public class Restaurant implements Serializable {
    private String name;
    private double lat;
    private double lng;
    private int distance;
    private String formattedPhone;
    private String address;
    private String tier = "";
    private String photo;


    public Restaurant(FoursquareDto.Result result) {
        name=result.venue.getName();
        lat=result.venue.getLocation().getLat();
        lng=result.venue.getLocation().getLng();

        distance=result.venue.getLocation().getDistance();
        System.out.println(distance);
        formattedPhone=result.venue.getContact().getFormattedPhone();
        address = result.venue.getLocation().getAddress();
        if(result.photo!=null) {
            photo = result.photo.getPrefix() + "70x70" + result.photo.getSuffix();
        }

        if(result.venue.getPrice()!=null) {
            tier = "$$$$".substring (0, result.venue.getPrice().getTier());
        }
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

}
