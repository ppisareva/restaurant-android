package com.example.polina.restaurantapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.example.polina.restaurantapplication.dto.FoursquareDto;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by polina on 27.01.16.
 */
public class App extends Application {

    private static final String RESTORAN_URL = "https://api.foursquare.com";
    Location location;
    String address = "";
    double maxRat;
    int downloadedMarkers = 0;
    Comparator<Restaurant> cmp;
    ConnectionSource connectionSource;
    Dao<Restaurant,String> restaurantDao;



    public double getMaxRat() {
        return maxRat;
    }

    public int getDownloadedMarkers() {
        return downloadedMarkers;
    }

    public void setDownloadedMarkers(int downloadedMarkers) {
        this.downloadedMarkers = downloadedMarkers;
    }

    public ArrayList<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void clearList(){
        restaurantList.clear();
    }

    final ArrayList<Restaurant> restaurantList = new ArrayList<>();

    FoursquareApi service;
    @Override
    public void onCreate() {
        Fresco.initialize(this);
        super.onCreate();
        cmp = new Comparator<Restaurant>() {

            @Override
            public int compare(Restaurant lhs, Restaurant rhs) {
               return Double.valueOf(lhs.getRating()).compareTo(rhs.getRating());
            }
        };
        Retrofit client = new Retrofit.Builder()
                .baseUrl(RESTORAN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = client.create(FoursquareApi.class);


       connectionSource = new AndroidConnectionSource(new RestaurantOpenHelper(this));
        try {
            restaurantDao = new RestaurantDao(connectionSource);
            restaurantList.addAll(restaurantDao.queryForAll());
            if (!restaurantList.isEmpty())

                maxRat = Collections.max(App.this.restaurantList, cmp).getRating();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRestaurant(Restaurant restaurant) {
        try {
            restaurantDao.update(restaurant);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    interface FoursquareApi {
        @GET("/v2/search/recommendations?v=20160127&categoryId=4d4b7105d754a06374d81259&sortByDistance=1&radius=2000&limit=20")
        public Call<FoursquareDto> getRestorans(@QueryMap Map<String,String> params,
                                                @Query("client_id") String clientId,
                                                @Query("client_secret") String clientSecret,
                                                @Query("offset") int offset );
    }


    public void getRestorans(final int offset) {
        Map<String, String> paramsMap = new HashMap<>();

        System.out.println(" location " + location + " address" + address);
                 if(!address.isEmpty()){
                paramsMap.put(Utils.NEAR, address);
            } else {
                paramsMap.put(Utils.LL, location.getLatitude() + "," + location.getLongitude());

        }
        Call<FoursquareDto> call = service.getRestorans(paramsMap, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, offset);

        System.err.println("========================");

        call.enqueue(new Callback<FoursquareDto>() {
            @Override
            public void onResponse(retrofit2.Response<FoursquareDto> response) {
                FoursquareDto dto = response.body();
                List<Restaurant> restaurantList = new ArrayList<Restaurant>();
                if (dto != null && dto.response.group.results != null) {

                    List<FoursquareDto.Result> results = dto.response.group.results;
                    System.out.println("size" + results.size());
                    if (offset == 0) {
                        try {
                            TableUtils.clearTable(connectionSource, Restaurant.class);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int i = 0; i < results.size(); i++) {

                        Restaurant restaurant = new Restaurant(results.get(i), getLocation());
                        restaurantList.add(restaurant);
                        System.out.println(restaurant.getRating());
                        try {

                            if (restaurantDao.create(restaurant) != 1) {
                                throw new Exception("Failure adding account");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    if (offset == 0) App.this.restaurantList.clear();
                    App.this.restaurantList.addAll(restaurantList);
                    if (!App.this.restaurantList.isEmpty())
                        maxRat = Collections.max(App.this.restaurantList, cmp).getRating();
                    Intent intent = new Intent(Utils.BROADCAST_INTENT);
                    intent.putExtra(Utils.INTENT_MESSAGE, "update adapter");
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                    ListFragment.flag = true;
                    try {
                        connectionSource.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(" faild");

            }
        });

        System.err.println("========================" + restaurantList);

    }







    public void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        final LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                System.err.println("LOCATION: " + location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                System.err.println("onStatusChanged: " + provider + " " + extras);
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };



        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, mLocationListener);

        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 5000, 1, mLocationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null)
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null)
            location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        setLocation(location);
    }



}
