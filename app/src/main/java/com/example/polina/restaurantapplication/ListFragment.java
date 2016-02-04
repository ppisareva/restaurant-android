package com.example.polina.restaurantapplication;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by polina on 27.01.16.
 */
public class ListFragment extends Fragment implements ViewUpdater {


    BroadcastReceiver receiver;
    public static  boolean flag = true;
    private RecyclerView.Adapter adapter;
    RecyclerViewPositionHelper mRecyclerViewHelper;
    App application;
    int offset = 10;
    private  final int STEP = 10;

        public static ListFragment newInstance() {
            ListFragment fragment = new ListFragment();
            return fragment;
        }

        public ListFragment() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_list, container, false);
            application = ((App)getActivity().getApplication());
            RecyclerView restaurantList = (RecyclerView) v.findViewById(R.id.my_recycler_view);
            adapter = new RestaurantAdapter(getActivity(),((App)getActivity().getApplication()).restaurantList);
            restaurantList.setAdapter(adapter);
            final RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getActivity());
            restaurantList.setLayoutManager(mLayoutManager);
            restaurantList.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
                    int visibleItemCount = recyclerView.getChildCount();
                    int totalItemCount = mRecyclerViewHelper.getItemCount();
                    int firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
                    System.err.println("first visible id" + firstVisibleItem + "visibleItemCount " + visibleItemCount + "totalItemCount" + totalItemCount);
                    if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 1) {
                        if (flag) {
                            flag = false;
                            System.out.println(flag);
                          application.getRestorans(offset);
                            offset += STEP;

                        }


                    }
                }
            });
            return v;
        }


    public void updateView() {
        adapter.notifyDataSetChanged();
    }



    }



