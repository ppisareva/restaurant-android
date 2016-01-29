package com.example.polina.restaurantapplication;

import android.app.Application;

import com.example.polina.restaurantapplication.dto.FoursquareDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by polina on 27.01.16.
 */
public class App extends Application {

    private static final String RESTORAN_URL = "https://api.foursquare.com";

    FoursquareApi service;
    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit client = new Retrofit.Builder()
                .baseUrl(RESTORAN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = client.create(FoursquareApi.class);
    }

    interface FoursquareApi {
        @GET("/v2/search/recommendations?v=20160127&radius=1000&categoryId=4d4b7105d754a06374d81259")
        public Call<FoursquareDto> getRestorans(@Query("ll") String location, @Query("client_id") String clientId, @Query("client_secret") String clientSecret);
    }

    public void getRestorants() {
        Call<FoursquareDto> call = service.getRestorans("50.519441,30.485003", BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET);

        System.err.println("========================");
        call.enqueue(new Callback<FoursquareDto>() {
            @Override
            public void onResponse(retrofit2.Response<FoursquareDto> response) {
                FoursquareDto dto = response.body();
                System.out.println();
                System.err.println("DTO: "+ dto.response.group.results);

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        System.err.println("========================");
    }
}
