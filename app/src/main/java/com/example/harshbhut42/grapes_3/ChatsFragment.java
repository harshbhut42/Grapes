package com.example.harshbhut42.grapes_3;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {


    private View mView1;
    private RecyclerView mRecyclerView1;



    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase_user;           // for get the data of friends from all users
    private FirebaseUser mCurrentUser;


    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView1 = inflater.inflate(R.layout.fragment_chats, container, false);


        mRecyclerView1 = mView1.findViewById(R.id.feagment_friend_list);
        //  mRecyclerView.setHasFixedSize(true);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Chats").child(mCurrentUser.getUid());
        mDatabase_user = FirebaseDatabase.getInstance().getReference().child("users");


        return mView1;
    }

    @Override
    public void onStart() {
        super.onStart();



        FirebaseRecyclerAdapter<AllFriends,FriendViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<AllFriends,FriendViewHolder>(
                AllFriends.class,R.layout.singal_user_layout,FriendViewHolder.class,mDatabase

        ) {
            @Override
            protected void populateViewHolder(final FriendViewHolder friendsViewHolder, AllFriends friends, int i) {


                final String list_user_id = getRef(i).getKey();

                mDatabase_user.child(list_user_id).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final String userName = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                        String userThumb = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();


                        friendsViewHolder.setUserName(userName);
                        friendsViewHolder.setProfilePic(userThumb);

                        friendsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                
                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        };



        mRecyclerView1.setAdapter(friendsRecyclerViewAdapter);

    }




    public static class FriendViewHolder extends RecyclerView.ViewHolder{


        TextView userName;
        CircleImageView Image;
        View view2;

        public FriendViewHolder(View itemView) {
            super(itemView);

            view2 = itemView;
            userName = (TextView) view2.findViewById(R.id.user_name);
            Image = (CircleImageView) view2.findViewById(R.id.user_profil_pic);

        }

        public void setUserName(String name) {

            userName.setText(name);
        }


        public void setProfilePic(String profilePic) {

            Picasso.get().load(profilePic).into(Image);
        }
    }


}
