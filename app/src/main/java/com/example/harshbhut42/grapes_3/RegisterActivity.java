package com.example.harshbhut42.grapes_3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout mUserName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    Button mCreatAcount;

    private ProgressDialog mRegisterProgress;  // progress dilog

    private android.support.v7.widget.Toolbar mToolbar;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private DatabaseReference mChatRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserName = (TextInputLayout) findViewById(R.id.activity_ragister_userName);
        mEmail = (TextInputLayout) findViewById(R.id.activity_ragister_email);
        mPassword = (TextInputLayout) findViewById(R.id.activity_ragister_password);

        mCreatAcount = (Button) findViewById(R.id.activity_ragister_creatAcount_btn);



        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.regester_toolbar);
        setSupportActionBar(mToolbar);                     // set toolbar as action bar
        getSupportActionBar().setTitle("Create Acount");       // set title of action bar(toolbar)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // wii show arrow(<-) in action bar when we press it will take as on parent activity

        mRegisterProgress = new ProgressDialog(this);


        //Firebase
         mAuth = FirebaseAuth.getInstance();




        mCreatAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String User_name = mUserName.getEditText().getText().toString();
                String Email = mEmail.getEditText().getText().toString();
                String Password = mPassword.getEditText().getText().toString();

                if(User_name.equals("") || Email.equals("") || Password.equals("")){
                    Toast.makeText(RegisterActivity.this,"Enter all information",Toast.LENGTH_SHORT).show();
                }
                else {
                    mRegisterProgress.setTitle("Registering...");
                    mRegisterProgress.setMessage("Please wait...");
                    mRegisterProgress.setCanceledOnTouchOutside(false);
                    mRegisterProgress.show();

                    register_User(User_name,Email,Password);
                }


            }
        });
    }

    private void register_User(final String user_name, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                    String uid = currentUser.getUid();

                    mRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                    mChatRef = FirebaseDatabase.getInstance().getReference().child("Chats");
                    mChatRef.child(uid);

                    HashMap<String,String> userMap = new HashMap<>();

                    userMap.put("name",user_name);
                    userMap.put("image","http://d28hp0i0mf9k3x.cloudfront.net/assets/default_person-3886b66ad5ca85d57ed2a0d12fd2b2e4.png");

                    mRef.setValue(userMap);


                    mRegisterProgress.dismiss();
                    Intent main_intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(main_intent);
                    finish();
                }
                else
                {
                    mRegisterProgress.hide();
                    Toast.makeText(RegisterActivity.this,"Error Occured",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }



}
