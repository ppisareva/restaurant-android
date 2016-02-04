package com.example.polina.restaurantapplication.dto;


import java.util.List;

/**
 * FoursquareDto
 * Created by nicolas on 12/22/13.
 */
public class FoursquareDto {

	public Response response;
	// for this test we don't need additional attributes like confident or geocode.
	public static class Response {
		public Group group;
	}

	public static class Group {
		public List<Result> results;
	}

	public static class Result {
		public Venue venue;
		public Photo photo;
	}
}
