package com.example.projectgcdp.GuideBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projectgcdp.R;

public class Shades extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shades);

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(), info.class);
        switch (v.getId()){
            case R.id.cardCol1 : i.putExtra("piccode", 1);
                break;
            case R.id.cardCol2 : i.putExtra("piccode", 2);
                break;
            case R.id.cardCol3 : i.putExtra("piccode", 3);
                break;
            case R.id.cardCol4 : i.putExtra("piccode", 4);
                break;
            case R.id.cardCol5 : i.putExtra("piccode", 5);
                break;
            case R.id.cardCol6 : i.putExtra("piccode", 6);
                break;
        }
        startActivity(i);
    }
}
