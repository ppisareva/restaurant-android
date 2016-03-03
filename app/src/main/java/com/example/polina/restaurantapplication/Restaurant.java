package com.example.polina.restaurantapplication;


import android.location.Location;
import com.example.polina.restaurantapplication.dto.FoursquareDto;
import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by polina on 28.01.16.
 */

@DatabaseTable
public class Restaurant implements Serializable {


    @DatabaseField(id = true)
    private String id;
    @DatabaseField (index = true)
    private String name;
    @DatabaseField
    private double lat;
    @DatabaseField
    private double lng;
    @DatabaseField
    private int distance;
    @DatabaseField
    private String formattedPhone;
    @DatabaseField
    private String address;
    @DatabaseField
    private String tier;
    @DatabaseField
    private double rating;
    @DatabaseField
    private String photo;

    public Restaurant() {

    }

    public Restaurant(FoursquareDto.Result result, Location location) {

        id=result.venue.getId();
        name=result.venue.getName();
        lat=result.venue.getLocation().getLat();
        lng=result.venue.getLocation().getLng();
        rating = result.venue.getRating();

        if( result.venue.getLocation().getDistance()!=0){
            distance=result.venue.getLocation().getDistance();
        } else {
           distance = (int) location.distanceTo(new Location(lat+","+lng));
        }
        formattedPhone=result.venue.getContact().getFormattedPhone();
        address = result.venue.getLocation().getAddress();
        if(result.photo!=null) {
            photo = result.photo.getPrefix() + "70x70" + result.photo.getSuffix();
        }

        if(result.venue.getPrice()!=null) {
            tier = "$$$$".substring(0, result.venue.getPrice().getTier());
        }
    }

    public Double getRating() {

        return rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }


    public String getAddress() {
        return address;
    }



    public int getDistance() {
        return distance;
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


    public double getLng() {
        return lng;
    }

    public String getFormattedPhone() {
        return formattedPhone;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

}
