package com.example.vicky.imagehide;


import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.vicky.imagehide.R;


public class MainActivity extends AppCompatActivity {

 public FrameLayout frameMain;
 public String name_of_the_user = null;
 public String pass_of_the_user = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       

        frameMain = (FrameLayout)findViewById(R.id.frameMain);
        getSupportFragmentManager().beginTransaction().add(R.id.frameMain , new username_fragment() , "first fragment").commit();











    }


}
