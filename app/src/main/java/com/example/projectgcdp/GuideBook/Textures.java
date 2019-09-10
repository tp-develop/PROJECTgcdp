package com.example.projectgcdp.GuideBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projectgcdp.R;

public class Textures extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textures);


    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getApplication(), info.class);
        switch (v.getId()){
            case R.id.cardCol1: i.putExtra("piccode", 11);
                break;
            case R.id.cardCol2 : i.putExtra("piccode", 12);
                break;
            case R.id.cardCol3: i.putExtra("piccode", 13);
                break;
            case R.id.cardCol4 : i.putExtra("piccode", 14);
                break;
            case R.id.cardCol5: i.putExtra("piccode", 15);
                break;
            case R.id.cardCol6 : i.putExtra("piccode", 16);
                break;
            case R.id.cardCol7: i.putExtra("piccode", 17);
                break;
            case R.id.cardCol8 : i.putExtra("piccode", 18);
                break;

        }
        startActivity(i);
    }
}
