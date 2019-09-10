package com.example.projectgcdp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class First extends AppCompatActivity{
ViewGroup layout;
ImageView im;
TextView textView,textIntro;
MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer= MediaPlayer.create(getApplicationContext(), R.raw.music1);
//        mediaPlayer.start();

        im = findViewById(R.id.imageView);
        layout = findViewById(R.id.layout);
        textView = findViewById(R.id.textView);
        textIntro = findViewById(R.id.textIntro);


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator anim4 = ObjectAnimator.ofFloat(textIntro, View.ALPHA, 0f);
                anim4.setDuration(0);
                anim4.start();

                ObjectAnimator anim = ObjectAnimator.ofFloat(im, View.ALPHA, 0f);
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(textView, View.ALPHA, 0f);

                anim2.setDuration(1000);
                anim.setDuration(1000);

                anim.start();
                anim2.start();

                textIntro.setVisibility(View.VISIBLE);

                ObjectAnimator anim3 = ObjectAnimator.ofFloat(textIntro, View.ALPHA, 1f);
                anim3.setDuration(1000);
                anim3.start();

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(new Intent(First.this, MainActivity.class));
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                });
            }
        });

    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mediaPlayer.stop();
//        mediaPlayer.release();
//
//    }

}
