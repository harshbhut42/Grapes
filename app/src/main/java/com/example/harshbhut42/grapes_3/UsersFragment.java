package com.example.harshbhut42.grapes_3;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {


    private View mView;
    private RecyclerView mRecyclerView;
    private ArrayList<String> mUsersName = new ArrayList<>();
    private ArrayList<String> mProfile = new ArrayList<>();
    private String mUsers;

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
      //  mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<AllUsers,ViewHolder> Users_RecyclerViewAdapter = new FirebaseRecyclerAdapter<AllUsers, ViewHolder>(AllUsers.class,R.layout.singal_user_layout,ViewHolder.class,mDatabase)
        {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, final AllUsers model, int position) {

                ViewHolder.setUserName(model.getName());
                ViewHolder.setProfilPic(model.getImage());

                final String Uid = getRef(position).getKey();

                ViewHolder.mView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getContext(), User_Profile.class);
                        intent.putExtra("image_url", model.getImage());
                        intent.putExtra("user_name", model.getName());
                        intent.putExtra("uid",Uid);
                        getContext().startActivity(intent);

                    }
                });

            }
        };
        mRecyclerView.setAdapter(Users_RecyclerViewAdapter);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


       private static CircleImageView Image;
       private static TextView userName;
        protected static View mView2;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mView2 = itemView;

            Image = (CircleImageView) itemView.findViewById(R.id.user_profil_pic);
            userName = (TextView) itemView.findViewById(R.id.user_name);

            parentLayout = itemView.findViewById(R.id.singal_user_layout);
        }

        public static void setUserName(String name) {
            userName.setText(name);
        }

        public static void setProfilPic(String image) {
            Picasso.get().load(image).into(Image);
        }
    }


}
