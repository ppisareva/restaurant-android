package com.example.polina.restaurantapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by polina on 27.01.16.
 */
public class ListFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

    BroadcastReceiver receiver;

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
            View v = inflater.inflate(R.layout.fragment_list, container, false);
            RecyclerView restaurantList = (RecyclerView) v.findViewById(R.id.my_recycler_view);

            final RecyclerView.Adapter adapter = new RestaurantAdapter(getActivity(),((App)getActivity().getApplication()).restaurantList);
            restaurantList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            restaurantList.setAdapter(adapter);


            final RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getActivity());
            restaurantList.setLayoutManager(mLayoutManager);
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String message = intent.getStringExtra(App.INTENT_MESSAGE);
                    System.out.println(message);
                    adapter.notifyDataSetChanged();


                }
            };
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter(App.BROADCAST_INTENT));



            return v;
        }

    @Override
    public void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onDestroy();
    }
    }



