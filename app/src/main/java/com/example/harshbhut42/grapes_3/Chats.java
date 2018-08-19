package com.example.harshbhut42.grapes_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Chats extends AppCompatActivity {

    private android.support.v7.widget.Toolbar mToolbar;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mRef1;   //referance for current user in chats
    private DatabaseReference mRef2;   //referance for second user in chats
    private DatabaseReference mDatabase;
    private DatabaseReference mTemp;

    private final List<Message> mList = new ArrayList<>();

    RecyclerView mRecyclerView;
    Chat_RecyclerViewAdapter mAdapter;

    private String mFriend_id;
    private EditText mMessage;
    private ImageButton mSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        Bundle extra = getIntent().getExtras();
        mFriend_id = extra.getString("friend_id");


        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.activity_chat_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(extra.getString("friend_name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mRecyclerView = (RecyclerView) findViewById(R.id.chats_recyclerView);
        mAdapter = new Chat_RecyclerViewAdapter(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
          //get a data


        mMessage = (EditText) findViewById(R.id.chat_message);
        mSend = (ImageButton) findViewById(R.id.chat_send_btn);


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRef1 = mDatabase.child("Chats").child(mCurrentUser.getUid()).child(mFriend_id);
        //  mRef1.setValue(ServerValue.TIMESTAMP);
        mRef2 = mDatabase.child("Chats").child(mFriend_id).child(mCurrentUser.getUid());
        //  mRef2.setValue(ServerValue.TIMESTAMP);

        initImageBitmaps();


        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = mMessage.getText().toString();

                if (!message.equals("")) {
                    Map<String, String> time = ServerValue.TIMESTAMP;
                    DatabaseReference mNewMessage = mRef1.push();   // create new child id for store message

                    // HashMap<String,String> messageMap = new HashMap<>();

                    //   messageMap.put("message",message);
                    // messageMap.put("time",String.valueOf(ServerValue.TIMESTAMP));

                    String tempId = mNewMessage.getKey();

                    Toast.makeText(Chats.this,message,Toast.LENGTH_LONG).show();

                    mTemp = mRef1.child(tempId).child("message");
                    mTemp.setValue(message);
                    mTemp = mRef1.child(tempId).child("time");
                    mTemp.setValue(time);
                    mTemp = mRef1.child(tempId).child("send_by");
                    mTemp.setValue("0");


                    mTemp = mRef2.child(tempId).child("message");
                    mTemp.setValue(message);
                    mTemp = mRef2.child(tempId).child("time");
                    mTemp.setValue(time);
                    mTemp = mRef2.child(tempId).child("send_by");
                    mTemp.setValue("1");

                   // mMessage.setText("");


                }

            }
        });


    }

    private void initImageBitmaps() {

        mRef1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);

                mList.add(message);

                mAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}