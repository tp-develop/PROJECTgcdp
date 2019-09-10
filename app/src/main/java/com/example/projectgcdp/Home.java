package com.example.projectgcdp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.projectgcdp.UseFuntionCheck.Checking;
import com.example.projectgcdp.UseageHistory.Usage;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projectgcdp.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView Name;
    ImageView imageViewUser;
    private DrawerLayout drawer;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    TabLayout tabLayout;
    private String m_Text = "";
    ViewPager viewPager;
    TabItem tabPersonal ;
    TabItem tabGuide ;
    FirebaseDatabase mFirebaseDatabase;
    ProgressBar progressBar;
    CardView UseProcess;
    public MediaPlayer sd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressBar = findViewById(R.id.progressBar);
        UseProcess = findViewById(R.id.cardCol1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageViewUser = findViewById(R.id.imProfile);
        Name = findViewById(R.id.txName);
        sd = MediaPlayer.create(getApplicationContext(), R.raw.bteffext);


        UseProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sd.start();
                startActivity(new Intent(getApplicationContext(), Checking.class));
            }
        });

        imageViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sd.start();
                recreate();
            }
        });


//        viewPager = findViewById(R.id.viewPager);
//        tabPersonal= findViewById(R.id.personal);
//        tabGuide =findViewById(R.id.Guide);
//        tabLayout = findViewById(R.id.tabLayout);
//        final PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(pageAdapter);
//        viewPager.setPageTransformer(true, new AccordionTransformer());
//
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        drawer = findViewById(R.id.drawLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        loadUserInformation();

//        Bundle bundle = getIntent().getExtras();
//        int Numuser = bundle.getInt("Num");
//        String NameUser = bundle.getString("NameUser");

//

//        switch (Numuser) {
//            case 1:
//                imageViewUser.setImageResource(R.drawable.user1);
//                Name.setText(NameUser);
//                break;
//            case 2:
//                imageViewUser.setImageResource(R.drawable.user2);
//                Name.setText(NameUser);
//                break;
//            case 3:
//                imageViewUser.setImageResource(R.drawable.user3);
//                Name.setText(NameUser);
//                break;
//            case 4:
//                imageViewUser.setImageResource(R.drawable.user4);
//                Name.setText(NameUser);
//                break;
//        }

//        FirebaseUser user = firebaseAuth.getCurrentUser();
//
//        if (user != null && profileImageUri != null) {
//            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
//                    .setDisplayName(profileName)
//                    .setPhotoUri(Uri.parse(profileImageUri))
//                    .build();
//
//            user.updateProfile(profileChangeRequest)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()){
//                                Toast.makeText(getApplicationContext(), "Wellcome to Application", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });

        }


//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//                viewPager.setCurrentItem(tab.getPosition());
//
//            }

//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });






//    public void CheckNull(DispPersonal dispPersonal, String names) {
//        if (dispPersonal != null) {
//            String message = dispPersonal.setText(Name);
//            Log.i("Check", message);
//        }
//        if (dispPersonal != null) {
//            String message = dispPersonal.setText(names);
//            Log.i("Check", message);
//        }
//    }

//        public Fragment getActiveFragment(ViewPager container, int position) {
//        String name = "android:switcher:" + container.getId() + ":" + position;
//        return getSupportFragmentManager().findFragmentByTag(name);
//    }



    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        switch (menuItem.getItemId()) {
            case R.id.Signout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.menageprofile:
                Intent i = new Intent(getApplicationContext(), Feed.class);
                startActivity(i);
//                finish();
                break;
            case R.id.Usagehistory: startActivity(new Intent(getApplicationContext(), Usage.class));
                break;
            case R.id.Feed:
                startActivity(new Intent(this, Information.class));

                break;
        }
        return true;
    }

    private void loadUserInformation() {


        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = mFirebaseDatabase.getReference();

        final FirebaseUser user = firebaseAuth.getCurrentUser();



        StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                .child("profilePic/" + user.getUid() + ".jpg");




        storageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                progressBar.setVisibility(View.GONE);
                Bitmap imageView = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Glide.with(getApplicationContext()).load(imageView).into(imageViewUser);
                User image = new User();
                image.setProfile(imageViewUser);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                progressBar.setVisibility(View.GONE);
                imageViewUser.setImageResource(R.drawable.error);
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User User =  new User();

                Map map = (Map) dataSnapshot.child("Accout").child(user.getUid()).getValue();

                User.setProfileName(String.valueOf(map.get("profileName")));

                Name.setText(User.getProfileName());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        if (user.getPhotoUrl() != null) {
//
//            Glide.with(this)
//                    .load(user.getPhotoUrl())
//                    .into(imageViewUser);
//        }
//
//        if (user.getDisplayName() != null) {
//
//            Name.setText(user.getDisplayName());
//        }
    }
    }
