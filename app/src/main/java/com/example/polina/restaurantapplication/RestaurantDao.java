package com.example.polina.restaurantapplication;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by polina on 07.02.16.
 */
public class RestaurantDao extends BaseDaoImpl<Restaurant, String> {
    protected RestaurantDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Restaurant.class);
    }
}
