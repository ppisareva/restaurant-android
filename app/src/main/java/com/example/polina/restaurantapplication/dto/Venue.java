package com.example.polina.restaurantapplication.dto;

import java.io.Serializable;

/**
 * Created by nicolas on 12/22/13.
 */
public class Venue implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	private double rating;
	private Location location;
	private Contact contact;

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	private Price price;
	// for this test we don't need additional attributes like categories and stats.

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}


}
