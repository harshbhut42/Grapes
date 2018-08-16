package com.example.harshbhut42.grapes_3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {


    private View mView;
    private RecyclerView mRecyclerView;
    private ArrayList<String> mUsersName = new ArrayList<>();
    private ArrayList<String> mProfile = new ArrayList<>();


    private DatabaseReference mDatabase;


    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_users, container, false);


        mRecyclerView = (RecyclerView) mView.findViewById(R.id.feagment_user_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        initImageBitmaps();
        
        return mView;
    }

    private void initImageBitmaps() {



        initRecycleView();
    }

    private void initRecycleView() {

        RecyclerView recyclerView = mView.findViewById(R.id.feagment_user_list);

        Users_RecyclerViewAdapter adapter = new Users_RecyclerViewAdapter(getContext(),mUsersName,mProfile);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


    }

}
