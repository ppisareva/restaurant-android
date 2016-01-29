package com.example.polina.restaurantapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by polina on 28.01.16.
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    Context context;
    LinearLayout layout;
    List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantAdapter(Context context, List<Restaurant> restaurants) {
        this.context=context;
        this.restaurants =restaurants;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rest_list, parent, false);
        layout = (LinearLayout)v.findViewById(R.id.rest_list_layout);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mName.setText(restaurants.get(position).getName());
        holder.mDistance.setText(restaurants.get(position).getDistance()+ "m");
        holder.setRestaurant(restaurants.get(position));
        layout.setTag(holder);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             ViewHolder  holder = (ViewHolder)v.getTag();
                Restaurant restaurant = holder.getRestaurant();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null!=restaurants? restaurants.size(): 0);
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mDistance;
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

        }
    }


}
