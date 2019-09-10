package com.example.projectgcdp.UseFuntionCheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectgcdp.BaseActivity;
import com.example.projectgcdp.Model.User;
import com.example.projectgcdp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.xml.datatype.Duration;

public class Checking extends BaseActivity {

    Button btChecking;
    ProgressBar progressBar;
    public Date currentTime;
    ImageView hindStar1, hindStar2, hindStar3, hindStar4, hindStar5,
              fullstar1, fullstar2, fullstar3, fullstar4, fullstar5;
    ViewGroup layoutresult;

    public int result;

    public DatabaseReference databaseReference;
    public FirebaseDatabase mFirebaseDatabase;
    public FirebaseAuth firebaseAuth;
    ViewGroup layoutLogo;
    MediaPlayer sd, best, medium, bad, wait;
    public TextView textinfo;
    public TextView texthead;


    private TextToSpeech mTTs;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking);


        Initial();


        sd = MediaPlayer.create(getApplicationContext(), R.raw.bteffext);
        best = MediaPlayer.create(getApplicationContext(), R.raw.best);
        medium = MediaPlayer.create(getApplicationContext(), R.raw.medium);
        bad = MediaPlayer.create(getApplicationContext(), R.raw.bad);
        wait = MediaPlayer.create(getApplicationContext(), R.raw.wait);


        findViewById(R.id.layoutresult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowContain(result);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("");



       btChecking.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               sd.start();
               Processing();
           }
       });

         currentTime = Calendar.getInstance().getTime();

    }



    public void Initial(){
        btChecking = findViewById(R.id.Checking);
        progressBar = findViewById(R.id.progressBar);
        layoutLogo = findViewById(R.id.layout);
        layoutresult = findViewById(R.id.layoutresult);

        hindStar1 = findViewById(R.id.star1);
        hindStar2 = findViewById(R.id.star2);
        hindStar3 = findViewById(R.id.star3);
        hindStar4 = findViewById(R.id.star4);
        hindStar5 = findViewById(R.id.star5);

        fullstar1 = findViewById(R.id.fullstar1);
        fullstar2 = findViewById(R.id.fullstar2);
        fullstar3 = findViewById(R.id.fullstar3);
        fullstar4 = findViewById(R.id.fullstar4);
        fullstar5 = findViewById(R.id.fullstar5);

        mTTs = new TextToSpeech(this, new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = mTTs.setLanguage(new Locale("en"));

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){

                    }else{
//                        speakbt.setEnabled(true);

                    }
                }else{

                }
            }
        });

    }

    public void Processing(){

        btChecking.setVisibility(View.GONE);


        findViewById(R.id.question).setVisibility(View.GONE);
        WaitShow();





//        layoutLogo.setVisibility(View.VISIBLE);
//        ShowStar5();

        Toast.makeText(this, "Processing...", Toast.LENGTH_SHORT).show();

//        final String userId = getUid();
//        mDatabase.child("user-usage").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

         firebaseAuth = FirebaseAuth.getInstance();
         mFirebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = mFirebaseDatabase.getReference();

        final FirebaseUser user = firebaseAuth.getCurrentUser();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               WaitDestroy();

                User member =  new User();

                Map map = (Map) dataSnapshot.child("Accout").child(user.getUid()).getValue();

                member.setResult(String.valueOf(map.get("resent_poopdata")));

                result = Integer.valueOf(member.getResult());

                if (result == 0 ){
                    WaitShow();
                }else{
                   ShowStarandResult(result);
                }




//                Name.setText(User.getProfileName());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void WaitDestroy() {
        progressBar.setVisibility(View.GONE);
        wait.stop();
    }

    private void WaitShow() {
        progressBar.setVisibility(View.VISIBLE);
        wait.start();
    }

    public void ShowContain(int getinfo){



        ////////////////////////////////////////////////////////////////////////////


        final Dialog dialog = new Dialog(Checking.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.showdetail_dialog);
        dialog.setCancelable(true);

         textinfo = (TextView) dialog.findViewById(R.id.infodialog);

         textinfo.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 speakStart();


             }
         });
//        btOndevice.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "On Device", Toast.LENGTH_SHORT).show();
//            }
//        });




        ImageView imageView = (ImageView) dialog.findViewById(R.id.imshowdetail);

        texthead = (TextView) dialog.findViewById(R.id.textdetail);

//        layoutDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//          speak();
//            }
//        });
        textinfo.setMovementMethod(new ScrollingMovementMethod());



        switch (getinfo){
            case 1 :
                imageView.setImageResource(R.drawable.brown);
                texthead.setText("brown poop");
                textinfo.setText(R.string.brown);
                break;
            case 2 : imageView.setImageResource(R.drawable.green);
                texthead.setText("green poop");
                textinfo.setText(R.string.green);
                break;
            case 3 : imageView.setImageResource(R.drawable.yellow);
                texthead.setText("yellow poop");
                textinfo.setText(R.string.yellow);
                break;
            case 4 : imageView.setImageResource(R.drawable.black);
                texthead.setText("black brown poop");
                textinfo.setText(R.string.black);
                break;
            case 5 : imageView.setImageResource(R.drawable.gray);
                texthead.setText("gray poop");
                textinfo.setText(R.string.gray);
                break;
            case 6 : imageView.setImageResource(R.drawable.red);
                texthead.setText("red poop");
                textinfo.setText(R.string.red);
                break;

            /////// shape
            case 11 : imageView.setImageResource(R.drawable.shape7);
                texthead.setText("Separate hard lumps, like nuts");
                textinfo.setText(R.string.shape1);
                break;
            case 12 : imageView.setImageResource(R.drawable.shape3);
                texthead.setText("Sausage-shaped, smooth and soft");
                textinfo.setText(R.string.shape2);
                break;
            case 13: imageView.setImageResource(R.drawable.shape1);
                texthead.setText("Watery, no solid pieces, all liquid");
                textinfo.setText(R.string.shape3);
                break;
            case 14 : imageView.setImageResource(R.drawable.shape8);
                texthead.setText("Sausage-shaped but lumpy ");
                textinfo.setText(R.string.shape4);
                break;
            case 15 : imageView.setImageResource(R.drawable.shape4);
                texthead.setText("Soft blobs with clear-cut edges ");
                textinfo.setText(R.string.shape5);
                break;
            case 16: imageView.setImageResource(R.drawable.shape2);
                texthead.setText("Sausage-shaped but with cracks on surface");
                textinfo.setText(R.string.shape6);
                break;
            case 17 : imageView.setImageResource(R.drawable.shape5);
                texthead.setText("Fluffy pieces with ragged edges, a mushy stool");
                textinfo.setText(R.string.shape7);
                break;
            case 18: imageView.setImageResource(R.drawable.shape6);
                texthead.setText("Soft and sticks to the side of the toilet bowl");
                textinfo.setText(R.string.shape8);
                break;
        }

        dialog.show();



    }

    public void ShowStarandResult(int num){

        switch (num){
            case 1 : ShowStar5();

                break;
            case 2 : ShowStar3();

                break;
            case 3 : ShowStar4();


                break;
            case 4 : ShowStar1();

                break;
            case 5 : ShowStar2();

                break;
            case 6 : ShowstarBad();
            break;

            case 11 : ShowStar3();
                break;

            case 12 : ShowStar5();
                break;

            case 13 : ShowStar1();
                break;

            case 14 : ShowStar5();
                break;

            case 15 : ShowStar3();
                break;

            case 16 : ShowStar2();
                break;

            case 17 : ShowstarBad();
                break;

            case 18 : ShowStar1();
                break;

        }






        ///fullstar1
//        ObjectAnimator star1 = ObjectAnimator.ofFloat(fullstar1, View.ALPHA, 1f).setDuration();
//        star1.setDuration(1000);
//        star1.start();
//
//        ///fullstar2
//        ObjectAnimator star2 = ObjectAnimator.ofFloat(fullstar2, View.ALPHA, 1f);
//        star2.setDuration(1000);
//        star2.start();
//
//        ///fullstar3
//        ObjectAnimator star3 = ObjectAnimator.ofFloat(fullstar3, View.ALPHA, 1f);
//        star3.setDuration(1000);
//        star3.start();
//
//        ///fullstar4
//        ObjectAnimator star4 = ObjectAnimator.ofFloat(fullstar4, View.ALPHA, 1f);
//        star4.setDuration(1000);
//        star4.start();
//
//        ///fullstar5
//        ObjectAnimator star5 = ObjectAnimator.ofFloat(fullstar5, View.ALPHA, 1f);
//        star5.setDuration(1000);
//        star5.start();

        ImageView poop = findViewById(R.id.poopView);
        TextView poopDis = findViewById(R.id.poopDis);

        ImageView texture = findViewById(R.id.TextureView);
        TextView textureDis = findViewById(R.id.textureDis);


        switch (result){
            case 1 : poop.setImageResource(R.drawable.brown);
                poopDis.setText("brown poop");
                break;
            case 2 : poop.setImageResource(R.drawable.green);
                poopDis.setText("green poop");
                break;
            case 3 : poop.setImageResource(R.drawable.yellow);
                poopDis.setText("yellow poop");
                break;
            case 4: poop.setImageResource(R.drawable.black);
                poopDis.setText("black brown poop");
                break;
            case 5 : poop.setImageResource(R.drawable.gray);
                poopDis.setText("gray poop");
                break;
            case 6 : poop.setImageResource(R.drawable.red);
                poopDis.setText("red poop");
                break;
        }

        switch (result){
            case 11 : texture.setImageResource(R.drawable.shape7);
                textureDis.setText("Seperate hard lumps, like nut");
                break;
            case 12 : texture.setImageResource(R.drawable.shape3);
                textureDis.setText("Sausage-shaped, smooth and soft ");
                break;
            case 13 : texture.setImageResource(R.drawable.shape1);
                textureDis.setText("Watery, no solid pieces, all liquid");
                break;
            case 14: texture.setImageResource(R.drawable.shape8);
                textureDis.setText("Sausage-shaped but lumpy ");
                break;
            case 15: texture.setImageResource(R.drawable.shape4);
                textureDis.setText("Soft blobs with clear-cut edges ");
                break;
            case 16 : texture.setImageResource(R.drawable.shape2);
                textureDis.setText("Sausage-shaped but with cracks on surface ");
                break;
            case 17 : texture.setImageResource(R.drawable.shape5);
                textureDis.setText("Fluffy pieces with ragged edges, a mushy stool ");
                break;
            case 18 : texture.setImageResource(R.drawable.shape6);
                textureDis.setText("Soft and sticks to the side of the toilet bowl ");
                break;
        }


    }

    private void speakStart(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            mTTs.speak(texthead.getText(), TextToSpeech.QUEUE_FLUSH, null, null);
            mTTs.speak(textinfo.getText(), TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private void speakStop(){

    }

    private void ShowStar1(){
        hindStar1.setVisibility(View.GONE);
        fullstar1.setVisibility(View.VISIBLE);
        bad.start();
    }

    private void ShowStar2(){
        hindStar1.setVisibility(View.GONE);
        hindStar2.setVisibility(View.GONE);

        fullstar1.setVisibility(View.VISIBLE);
        fullstar2.setVisibility(View.VISIBLE);
        bad.start();
    }

    private void ShowStar3(){
        hindStar1.setVisibility(View.GONE);
        hindStar2.setVisibility(View.GONE);
        hindStar3.setVisibility(View.GONE);

        fullstar1.setVisibility(View.VISIBLE);
        fullstar2.setVisibility(View.VISIBLE);
        fullstar3.setVisibility(View.VISIBLE);
        medium.start();
    }

    private void ShowStar4(){
        hindStar1.setVisibility(View.GONE);
        hindStar2.setVisibility(View.GONE);
        hindStar3.setVisibility(View.GONE);
        hindStar4.setVisibility(View.GONE);

        fullstar1.setVisibility(View.VISIBLE);
        fullstar2.setVisibility(View.VISIBLE);
        fullstar3.setVisibility(View.VISIBLE);
        fullstar4.setVisibility(View.VISIBLE);
        best.start();
    }

    private void ShowStar5(){
        hindStar1.setVisibility(View.GONE);
        hindStar2.setVisibility(View.GONE);
        hindStar3.setVisibility(View.GONE);
        hindStar4.setVisibility(View.GONE);
        hindStar5.setVisibility(View.GONE);

        fullstar1.setVisibility(View.VISIBLE);
        fullstar2.setVisibility(View.VISIBLE);
        fullstar3.setVisibility(View.VISIBLE);
        fullstar4.setVisibility(View.VISIBLE);
        fullstar5.setVisibility(View.VISIBLE);
        best.start();
    }

    private void ShowstarBad(){
        bad.start();

        ObjectAnimator star1 = ObjectAnimator.ofFloat(hindStar1, View.ALPHA, 0f);
        ObjectAnimator star2 = ObjectAnimator.ofFloat(hindStar2, View.ALPHA, 0f);
        ObjectAnimator star3 = ObjectAnimator.ofFloat(hindStar3, View.ALPHA, 0f);
        ObjectAnimator star4 = ObjectAnimator.ofFloat(hindStar4, View.ALPHA, 0f);
        ObjectAnimator star5 = ObjectAnimator.ofFloat(hindStar5, View.ALPHA, 0f);

        star1.setDuration(1000);
        star2.setDuration(1000);
        star3.setDuration(1000);
        star4.setDuration(1000);
        star5.setDuration(1000);

        star1.start();
        star2.start();
        star3.start();
        star4.start();
        star5.start();



    }

}
