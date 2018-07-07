package com.example.vicky.gotexplore.main_Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vicky.gotexplore.R;
import com.squareup.picasso.Picasso;

public class Display_Activity extends AppCompatActivity {

    public TextView name;
    public TextView dob;
    public TextView dod;
    public TextView spouse;
    public TextView house;
    public TextView culture;
    public TextView gender;
    public ImageView imageView;
    public TextView status;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_);

        Intent intent = getIntent();
        String character_name = intent.getStringExtra("name");
        String Spouse_name = intent.getStringExtra("spouse");
        String House_name = intent.getStringExtra("house");
        String Culture_name = intent.getStringExtra("culture");
        String url = intent.getStringExtra("URL");
        String dateofbirth = intent.getStringExtra("dob");
        String dateofdeath = intent.getStringExtra("dod");
        String Male_status = intent.getStringExtra("male");

        name = (TextView)findViewById(R.id.name);
        dob = (TextView)findViewById(R.id.date_of_birth);
        dod = (TextView)findViewById(R.id.date_of_death);
        spouse = (TextView)findViewById(R.id.spouse);
        house = (TextView)findViewById(R.id.house);
        culture = (TextView)findViewById(R.id.culture);
        gender = (TextView)findViewById(R.id.gender);
        imageView = (ImageView)findViewById(R.id.imageView);
        status = (TextView)findViewById(R.id.status);


        if(url.equals("null")){

            imageView.setImageResource(R.drawable.imgunavailable);
        }

        else {

            Picasso.with(this)
                    .load("https://api.got.show" + url)
                    .resize(625, 590)
                    .into(imageView);
        }





        Log.d("image link" , url);




        name.setText(character_name);


        Log.e("death" , dateofdeath);



        if((dateofbirth.equals("null")) || (dateofdeath.equals("null")) ){

            Log.d("any one zero" , "check");

            if((dateofbirth.equals("null"))) {

                dob.setText("Birth : Unknown");
                dod.setText("Died : " + dateofdeath + "  AC");
                status.setText("Status : Deceased");
            }

            else{


                dob.setText("Birth : " + dateofbirth + "  AC");
                dod.setVisibility(View.GONE);
                status.setText("Status : Alive");



            }

        }

        else if((dateofbirth.equals("null")) && (dateofdeath.equals("null"))){

            Log.d("both zero" , "check");
            dob.setText("Birth : Unknown");
            dod.setText("Died : Unknown");
            status.setText("Status : Unknown");



        }

        else{

            Log.d("both non zero" , "check");
            dob.setText("Birth : " + dateofbirth + "  AC");
            dod.setText("Died : " + dateofdeath + "  AC");
            status.setText("Status : Deceased");

        }





        spouse.setText("Spouse : " +Spouse_name );
        house.setText("House : " + House_name);
        culture.setText("Culture : " + Culture_name);
        gender.setText("Gender : " + Male_status);


    }



}
