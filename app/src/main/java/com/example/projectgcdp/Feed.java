package com.example.projectgcdp;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.projectgcdp.Model.User;
import com.example.projectgcdp.fragment.MyPostsFragment;
import com.example.projectgcdp.fragment.MyTopPostsFragment;
import com.example.projectgcdp.fragment.RecentPostsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Feed extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    DatabaseReference databaseReference;
    String tranText;
    MediaPlayer sd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        sd = MediaPlayer.create(getApplicationContext(), R.raw.bteffext);
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new RecentPostsFragment(),
                    new MyPostsFragment(),
                    new MyTopPostsFragment(),
            };
            private final String[] mFragmentNames = new String[] {
                    getString(R.string.heading_recent),
                    getString(R.string.heading_my_posts),
                    getString(R.string.heading_my_top_posts)
            };
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = mFirebaseDatabase.getReference();

        final FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User User =  new User();

                Map map = (Map) dataSnapshot.child("Accout").child(user.getUid()).getValue();

                User.setProfileName(String.valueOf(map.get("profileName")));

                 tranText = User.getProfileName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        findViewById(R.id.fabNewPost).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sd.start();
                recreate();
                return true;
            }
        });

        // Button launches NewPostActivity
        findViewById(R.id.fabNewPost).setOnClickListener(new View.OnClickListener() {






            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), NewPostActivity.class));
                Intent i = new Intent(getApplicationContext(), NewPostActivity.class);
                i.putExtra("namecurrent", tranText);
                startActivity(i);
            }
        });
    }

    }

