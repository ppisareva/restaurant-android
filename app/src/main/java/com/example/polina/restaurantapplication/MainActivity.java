package com.example.polina.restaurantapplication;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Location location;
    public static final int FRAGMENT_MAP = 0;
    public static final int FRAGMENT_LIST = 1;
    private final int OFFSET = 0;
    App application;
    List<Restaurant> restaurantLis = new ArrayList<>();

    ListFragment listFragment;
    MapFragment mapFragment;

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(Utils.BROADCAST_INTENT));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        application = ((App) getApplication());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(FRAGMENT_MAP).setIcon(R.drawable.ic_map_white_24dp);
        tabLayout.getTabAt(FRAGMENT_LIST).setIcon(R.drawable.ic_list_white_24dp);
        try {
            restaurantLis = application.restaurantDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(restaurantLis!=null){
            application.restaurantList.addAll(restaurantLis);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Utils.BROADCAST_INTENT));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    System.out.println("query" + query);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    System.out.println("newText" + newText);
                    return true;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.location) {
            System.out.println(" LOCATION selected ");
            Location location = getLocation();
            if (location == null) {
                System.err.println("Empty location: ");
                return true;
            }

            try {
                TableUtils.clearTable(application.getConnectionSource(), Restaurant.class);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            application.setLocation(location.getLatitude() + "," + location.getLongitude());
            System.out.println("location " + application.getLocation());


            application.getRestorans(OFFSET);

           LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Utils.BROADCAST_INTENT));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Location getLocation() {
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

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, mLocationListener);

        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 5000, 1, mLocationListener);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null)
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null)
            location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        return location;

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            System.out.println(position);
            if (position == FRAGMENT_MAP) {
                return MapFragment.newInstance(position + 1);
            } else {
                if (listFragment == null) {
                    listFragment = ListFragment.newInstance();
                }
                return listFragment;
            }

        }

        @Override
        public int getCount() {
            return 2;
        }
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            listFragment.updateView();

        }
    };

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }




}
