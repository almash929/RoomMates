package com.example.alma.roommates;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.example.alma.roommates.utils.CustomAdapter;

/**
 * Created by alma on 16/04/2016.
 */
public class ShoppingListFragment extends Fragment{


    private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    protected String[] mDataset;
    protected CustomAdapter mAdapter;

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset from remote server.
        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.shopping_list_fragment, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.shoplist_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mAdapter = new CustomAdapter(mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        int scrollPosition = 0;
        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);

        return rootView;
    }

    private void initDataset() {
        mDataset = new String[2];
        for (int i = 0; i < 2; i++) {
            mDataset[i] = "This is element #" + i;
        }
    }
}
