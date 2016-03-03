package com.example.polina.restaurantapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


public class RestaurantDetailsActivity extends AppCompatActivity {

    RatingBar ratingBar;
    EditText nick;
    App application;
    Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        application = (App) getApplication();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.restaunt_details);
        restaurant =  application.getRestaurantList().get(getIntent().getIntExtra(Utils.ID,0));
        ((TextView) findViewById(R.id.dialog_name)).setText(restaurant.getName());
        ((TextView) findViewById(R.id.dialog_adress)).setText(restaurant.getAddress());
        ((TextView) findViewById(R.id.dialog_number)).setText(restaurant.getFormattedPhone());
        ((TextView) findViewById(R.id.dialog_raiting)).setText(String.valueOf(restaurant.getTier()));
        nick = (EditText) findViewById(R.id.dialog_nickname);
        ratingBar = (RatingBar) findViewById(R.id.dialog_ratingBar);
    }

    public void onClick(View v) {
        float rating = ratingBar.getRating();
        String tier = "$$$$".substring(0, (int) rating);
        restaurant.setTier(tier);
        application.updateRestaurant(restaurant);
        Toast.makeText(this, "rating " + rating + " nickname " + (nick.getText()), Toast.LENGTH_LONG).show();
        ((TextView) findViewById(R.id.dialog_raiting)).setText(String.valueOf(restaurant.getTier()));
        setResult(RESULT_OK);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
