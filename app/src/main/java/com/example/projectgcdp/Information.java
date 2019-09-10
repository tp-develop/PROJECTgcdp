package com.example.projectgcdp;

import android.content.Intent;
import androidx.annotation.NonNull;

import com.example.projectgcdp.GuideBook.Shades;
import com.example.projectgcdp.GuideBook.Textures;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Information extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    CardView cardView1, cardView2;

    private DrawerLayout drawer;
    MediaPlayer sd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        sd = MediaPlayer.create(getApplicationContext(), R.raw.bteffext);
        cardView1 = findViewById(R.id.cardViewUseProcess1);
        cardView2 = findViewById(R.id.cardViewUseProcess2);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sd.start();
                startActivity(new Intent(getApplicationContext(), Shades.class));
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sd.start();
                startActivity(new Intent(getApplicationContext(), Textures.class));
            }
        });

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        initInstances();


    }
//    private void initInstances() {
//        tabLayout = findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("Excretory system"));
//        tabLayout.addTab(tabLayout.newTab().setText("Knowledge"));
//        tabLayout.addTab(tabLayout.newTab().setText("Foods"));
//
//
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.Signout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.menageprofile:
                startActivity(new Intent(this, Feed.class));
                finish();
                break;
            case R.id.Usagehistory:
                break;
            case R.id.Feed:
                startActivity(new Intent(this, Information.class));
                finish();

                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
        }


}
