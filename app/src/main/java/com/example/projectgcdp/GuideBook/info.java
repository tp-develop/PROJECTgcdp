package com.example.projectgcdp.GuideBook;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectgcdp.R;

import java.util.Locale;

public class info extends AppCompatActivity {

    ImageView imageView;
     TextView text;
    private TextToSpeech mTTs;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        imageView = findViewById(R.id.impoop);
        text = findViewById(R.id.info);

        findViewById(R.id.btSpeak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

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



        Bundle b = getIntent().getExtras();
        int index = b.getInt("piccode");

        switch (index){
            case 1 :
                imageView.setImageResource(R.drawable.brown);
                     text.setText(R.string.brown);
                break;
            case 2 : imageView.setImageResource(R.drawable.green);
                     text.setText(R.string.green);
                break;
            case 3 : imageView.setImageResource(R.drawable.yellow);
                     text.setText(R.string.yellow);
                break;
            case 4 : imageView.setImageResource(R.drawable.black);
                     text.setText(R.string.black);
                break;
            case 5 : imageView.setImageResource(R.drawable.gray);
                     text.setText(R.string.gray);
                break;
            case 6 : imageView.setImageResource(R.drawable.red);
                     text.setText(R.string.red);
                break;

                /////// shape
            case 11 : imageView.setImageResource(R.drawable.shape7);
                      text.setText(R.string.shape1);
                break;
            case 12 : imageView.setImageResource(R.drawable.shape3);
                text.setText(R.string.shape2);
                break;
            case 13: imageView.setImageResource(R.drawable.shape1);
                text.setText(R.string.shape3);
                break;
            case 14 : imageView.setImageResource(R.drawable.shape8);
                text.setText(R.string.shape4);
                break;
            case 15 : imageView.setImageResource(R.drawable.shape4);
                text.setText(R.string.shape5);
                break;
            case 16: imageView.setImageResource(R.drawable.shape2);
                text.setText(R.string.shape6);
                break;
            case 17 : imageView.setImageResource(R.drawable.shape5);
                text.setText(R.string.shape7);
                break;
            case 18: imageView.setImageResource(R.drawable.shape6);
                text.setText(R.string.shape8);
                break;
        }

text.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        speak();
        Toast.makeText(getApplicationContext(), "Speak", Toast.LENGTH_SHORT).show();
    }
});



    }

    public void speak(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTTs.speak(text.getText(), TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

}
