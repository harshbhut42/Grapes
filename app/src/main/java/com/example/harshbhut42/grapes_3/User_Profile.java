package com.example.harshbhut42.grapes_3;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class User_Profile extends AppCompatActivity {

    private ImageView mImage;
    private TextView mUserName;
    private Button mAddFriend;
    String Uid ;    // store id of user
    private DatabaseReference mDatabase;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);

        mImage = (ImageView) findViewById(R.id.image_profile);
        mUserName = (TextView) findViewById(R.id.user_profil_name);
        mAddFriend = (Button) findViewById(R.id.add_as_friend);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Chats");


        final Bundle extra = getIntent().getExtras();


        if(extra.getString("image_url") != null && extra.getString("user_name") != null && extra.getString("uid") != null)
        {
            String UserName = extra.getString("user_name");
            String ProfilePic = extra.getString("image_url");
            Uid = extra.getString("uid");

            Picasso.get().load(ProfilePic).into(mImage);
            mUserName.setText(UserName);
            if(mCurrentUser.getUid().equals(Uid))
            {
                mAddFriend.setEnabled(false);
            }
        }


        mDatabase.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(Uid))
                {
                    mAddFriend.setText("Alrady Friends");
                    mAddFriend.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child(mCurrentUser.getUid()).child(Uid).child("message").setValue("");
                mDatabase.child(Uid).child(mCurrentUser.getUid()).child("message").setValue("");

                mAddFriend.setEnabled(false);
            }
        });




    }
}
