package com.example.polina.restaurantapplication;

import android.app.Application;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.polina.restaurantapplication.dto.FoursquareDto;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
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
    public String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        restaurantList.clear();
    }

    final ArrayList<Restaurant> restaurantList = new ArrayList<>();

    FoursquareApi service;
    @Override
    public void onCreate() {
        Fresco.initialize(this);
        super.onCreate();
        Retrofit client = new Retrofit.Builder()
                .baseUrl(RESTORAN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = client.create(FoursquareApi.class);
    }

    interface FoursquareApi {
        @GET("/v2/search/recommendations?v=20160127&categoryId=4d4b7105d754a06374d81259&sortByDistance=1&radius=2000&limit=20")
        public Call<FoursquareDto> getRestorans(@Query("ll") String location,
                                                @Query("client_id") String clientId,
                                                @Query("client_secret") String clientSecret,
                                                @Query("offset") int offset );
    }

    public void getRestorans(int offset) {
        Call<FoursquareDto> call = service.getRestorans(location, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, offset);

        System.err.println("========================");

        call.enqueue(new Callback<FoursquareDto>() {
            @Override
            public void onResponse(retrofit2.Response<FoursquareDto> response) {
                FoursquareDto dto = response.body();
                List<Restaurant> restaurantList = new ArrayList<Restaurant>();
                if (dto.response.group.results != null) {

                List<FoursquareDto.Result> results = dto.response.group.results;

                    System.out.println("size" + results.size());
                    for (int i = 0; i < results.size(); i++) {
                        restaurantList.add(new Restaurant(results.get(i)));
                    }
                    App.this.restaurantList.addAll(restaurantList);
                    Intent intent = new Intent(Utils.BROADCAST_INTENT);
                    intent.putExtra(Utils.INTENT_MESSAGE, "update adapter");
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                    ListFragment.flag = true;
                }

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        System.err.println("========================" + restaurantList);

    }
}
