package com.example.vicky.onsite1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    public FrameLayout top;

    public FrameLayout bottom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        top = (FrameLayout)findViewById(R.id.top);
        bottom = (FrameLayout)findViewById(R.id.bottom);




        getSupportFragmentManager().beginTransaction().add(R.id.top , new TopFragment() , "top fragment").commit();
        getSupportFragmentManager().beginTransaction().add(R.id.bottom , new BottomFragment() , "bottom fragment").commit();

    }
}
