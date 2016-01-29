package com.example.polina.restaurantapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by polina on 27.01.16.
 */
public class ListFragment extends Fragment {




    private List<Restaurant> restaurantList = new ArrayList<>();
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static ListFragment newInstance(int sectionNumber) {
            ListFragment fragment = new ListFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public ListFragment() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list, container, false);
            RecyclerView mRecyclerView; mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
           final RecyclerView.LayoutManager mLayoutManager; mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            Restaurant restaurant = new Restaurant();
            restaurant.setDistance(345);
            restaurant.setName("kjnkjnjknk");
            restaurantList.add(restaurant);
            RecyclerView.Adapter mAdapter; mAdapter = new RestaurantAdapter(getActivity(), restaurantList);
            mRecyclerView.setAdapter(mAdapter);
            return rootView;
        }
    }



