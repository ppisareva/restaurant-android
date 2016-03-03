package com.example.polina.restaurantapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


/**
 * Created by polina on 27.01.16.
 */
public class MapFragment extends SupportMapFragment implements ViewUpdater {

    private App application;
    private LatLng location;
    private GoogleMap map;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    public MapFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        application = (App) getActivity().getApplication();
        map = getMap();
        map.setMyLocationEnabled(true);
        if(application.getLocation()!=null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(application.getLocation().getLatitude(), application.getLocation().getLongitude()), 15));
        }
        updateMarkers();
        map.animateCamera(CameraUpdateFactory.zoomTo(15), 500, null);
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(getActivity(), RestaurantDetailsActivity.class);
                int position = Integer.parseInt(marker.getId().substring(1));
                intent.putExtra(Utils.ID, position - application.getDownloadedMarkers());
                getActivity().startActivity(intent);
                return false;
            }
        });
        return rootView;
    }

    @Override
    public void updateView() {
        updateMarkers();
    }

    public void clearMap() {
        map.clear();
    }


    private void updateMarkers() {
        List<Restaurant> restaurantList = application.getRestaurantList();
        if (!restaurantList.isEmpty()) {
            location = new LatLng(restaurantList.get(0).getLat(), restaurantList.get(0).getLng());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        }
        for (Restaurant restaurant : restaurantList) {
            LatLng latLng = new LatLng(restaurant.getLat(), restaurant.getLng());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(restaurant.getName())
                    .snippet(restaurant.getAddress());
            if (application.getMaxRat() == restaurant.getRating()) {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            }
            map.addMarker(markerOptions);
        }
    }
}
