package com.example.polina.restaurantapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by polina on 28.01.16.
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    Context context;
   RelativeLayout layout;
    List<Restaurant> restaurants = new ArrayList<>();
    String defaultUri = "https://www.google.com.ua/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwig0uSB597KAhUi_nIKHauwC8gQjRwIBw&url=http%3A%2F%2Fwww.papktop.com%2Ftag%2Ffoursquare-apk&psig=AFQjCNFozIFVJ6vp44E2qaPUTpt_t10mjg&ust=1454699209779427";


    public RestaurantAdapter(Context context, List<Restaurant> restaurants) {
        this.context=context;
        this.restaurants =restaurants;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rest_list, parent, false);
        layout = (RelativeLayout)v.findViewById(R.id.rest_list_layout);
        ViewHolder vh = new ViewHolder(v);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHolder holder = (ViewHolder) v.getTag();
                Restaurant restaurant = holder.getRestaurant();
                Intent intent = new Intent(context, RestaurantDetailsActivity.class);
                intent.putExtra(Utils.RESTAURANT, restaurant);
                context.startActivity(intent);
            }
        });
        return vh;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        holder.mName.setText(restaurant.getName());
        holder.mDistance.setText(restaurant.getDistance() + "m");
        Uri uri;
        if(restaurant.getPhoto()!=null) {
            uri = Uri.parse(restaurant.getPhoto());
        } else {
            uri = Uri.parse(defaultUri);
        }


        holder.draweeView.setImageURI(uri);
        holder.setRestaurant(restaurants.get(position));
        layout.setTag(holder);



    }

    @Override
    public int getItemCount() {
        return (null!=restaurants? restaurants.size(): 0);
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mDistance;
        SimpleDraweeView draweeView;
        public Restaurant restaurant;

        public void setRestaurant(Restaurant restaurant) {
            this.restaurant = restaurant;
        }

        public Restaurant getRestaurant() {
            return restaurant;
        }

        public ViewHolder(View v ) {
            super(v);
            mName = (TextView) v.findViewById(R.id.rest_list_name);
            mDistance = (TextView) v.findViewById(R.id.rest_list_distance);
            draweeView = (SimpleDraweeView) v.findViewById(R.id.my_image_view);

        }
    }



}
