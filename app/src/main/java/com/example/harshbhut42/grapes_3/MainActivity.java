package com.example.harshbhut42.grapes_3;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private android.support.v7.widget.Toolbar mToolbar;  // toolbar in activity_main

    // for tabs
    private ViewPager mViewPager;
    private ViewPagerAddapter mViewPagerAddapter;

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.main_toolbar);

        setSupportActionBar(mToolbar);  // set toolbar as action bar
        getSupportActionBar().setTitle("Grapes");   // set title of action bar(toolbar)


        mViewPager = (ViewPager) findViewById(R.id.main_tab_pager);

        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);

        mViewPagerAddapter = new ViewPagerAddapter(getSupportFragmentManager());
        // set adapter
        mViewPager.setAdapter(mViewPagerAddapter);
        mTabLayout.setupWithViewPager(mViewPager);  // set tabes according to number of fragments in View Pager

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {
            updateUI();
        }
    }


    private void updateUI() {


        Intent intent = new Intent(MainActivity.this,Login.class);
        startActivity(intent);

        finish();  //    user can not come back if he is not login using press back
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu,menu);   // set menu like XML file in onCreate method

        return true;           // always return true
    }


    // caled when item of menu bar seleced
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);


        if(item.getItemId() == R.id.main_menu_logout)
        {
            FirebaseAuth.getInstance().signOut();  // user will log out but we need to change UI
            updateUI();
        }

        return true;
    }



}
