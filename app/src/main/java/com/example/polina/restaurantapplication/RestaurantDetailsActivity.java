package com.example.polina.restaurantapplication;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polina.restaurantapplication.dto.Venue;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RestaurantDetailsActivity extends AppCompatActivity {

    RatingBar ratingBar;
    EditText nick;
    App application;
    Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        application =(App) getApplication();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.restaunt_details);
        restaurant = (Restaurant) getIntent().getSerializableExtra(Utils.RESTAURANT);

        ((TextView) findViewById(R.id.dialog_name)).setText(restaurant.getName());
        ((TextView) findViewById(R.id.dialog_adress)).setText(restaurant.getAddress());
        ((TextView) findViewById(R.id.dialog_number)).setText(restaurant.getFormattedPhone());
        ((TextView) findViewById(R.id.dialog_raiting)).setText(String.valueOf(restaurant.getTier()));
        nick = (EditText) findViewById(R.id.dialog_nickname);
        ratingBar = (RatingBar) findViewById(R.id.dialog_ratingBar);
    }

    public void onClick(View v){
    float rating =  ratingBar.getRating();

        String nickname = String.valueOf(nick.getText());
        Toast.makeText(this, "rating "+ rating + " nickname " + nickname, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
