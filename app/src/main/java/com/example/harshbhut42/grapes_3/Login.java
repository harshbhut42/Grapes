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

public class Login extends AppCompatActivity {

    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mRegistor;
    private Button mLogin;

    private ProgressDialog mLoginProgress;

    private android.support.v7.widget.Toolbar mToolbar;

    //Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (TextInputLayout) findViewById(R.id.activity_login_email);
        mPassword = (TextInputLayout) findViewById(R.id.activity_login_password);

        mLogin = (Button) findViewById(R.id.activity_login_login_btn);
        mRegistor = (Button) findViewById(R.id.activity_login_register_btn);

        mLoginProgress = new ProgressDialog(this);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login");

        //Firebase
        mAuth = FirebaseAuth.getInstance();


        mRegistor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registor_intent = new Intent(Login.this,RegisterActivity.class);
                startActivity(registor_intent);
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = mEmail.getEditText().getText().toString();
                String Password = mPassword.getEditText().getText().toString();

                if(Email.equals("") || Password.equals(""))
                {
                    Toast.makeText(Login.this,"Enter email and password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mLoginProgress.setTitle("Logging...");
                    mLoginProgress.setMessage("Please wait");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();

                    login(Email,Password);
                }
            }
        });
    }

    private void login(String email, String password) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    mLoginProgress.dismiss();

                    Intent main_intent = new Intent(Login.this,MainActivity.class);
                    startActivity(main_intent);
                    finish();
                }
                else
                {
                    mLoginProgress.hide();
                    Toast.makeText(Login.this,"Enter correct email and password",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
