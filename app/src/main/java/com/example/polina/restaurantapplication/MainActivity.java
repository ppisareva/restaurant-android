package com.example.polina.restaurantapplication;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public static final int FRAGMENT_MAP = 0;
    public static final int FRAGMENT_LIST = 1;
    private final int OFFSET = 0;
    private final int MAX_WIDT = 3000;
    LocationManager locationManager;

    App application;

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
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        setContentView(R.layout.activity_main);
        application = ((App) getApplication());
        application.getCurrentLocation();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(FRAGMENT_MAP).setIcon(R.drawable.ic_map_white_24dp);
        tabLayout.getTabAt(FRAGMENT_LIST).setIcon(R.drawable.ic_list_white_24dp);

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

            searchView.setMaxWidth(MAX_WIDT);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Utils.LOAD_FROM_CACHE = true;
                    System.out.println("query" + query);
                    application.getCurrentLocation();
                    application.setDownloadedMarkers(application.getDownloadedMarkers() + application.restaurantList.size());
                    application.setAddress(query);
                    if (mapFragment != null) {
                        mapFragment.clearMap();
                    }
                    mapFragment.clearMap();
                    application.getRestorans(OFFSET);
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
            Utils.LOAD_FROM_CACHE = true;
            application.setAddress("");
            application.setDownloadedMarkers(application.getDownloadedMarkers() + application.restaurantList.size());
            application.clearList();
            application.getCurrentLocation();
            if (application.getLocation() == null) {
                System.err.println("Empty location: ");
                return true;
            }
            try {
                TableUtils.clearTable(application.getConnectionSource(), Restaurant.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            application.getRestorans(OFFSET);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Utils.EDIT_CODE && resultCode == RESULT_OK) {
            listFragment.updateView();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == FRAGMENT_MAP && mapFragment == null) {
                return mapFragment = new MapFragment().newInstance();
            }
            if (position == FRAGMENT_LIST && listFragment == null) {
                return listFragment = new ListFragment().newInstance();
            }
            return listFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mapFragment != null) mapFragment.updateView();
            if (listFragment != null) listFragment.updateView();
        }
    };

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }


}
