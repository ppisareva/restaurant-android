package com.example.polina.restaurantapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by polina on 07.02.16.
 */
public class RestaurantOpenHelper extends OrmLiteSqliteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Restaurant.db";


    public RestaurantOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        System.err.println("============================= CREATE!!! ==================");
        try {
            TableUtils.createTable(connectionSource, Restaurant.class);
            System.err.println("============================== DONE ====================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Restaurant.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        onCreate(database, connectionSource);
    }




}
