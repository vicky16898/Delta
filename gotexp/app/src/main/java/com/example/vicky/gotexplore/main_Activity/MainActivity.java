package com.example.vicky.gotexplore.main_Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.vicky.gotexplore.R;
import com.example.vicky.gotexplore.Retrofit.ApiClient;
import com.example.vicky.gotexplore.Retrofit.ApiInterface;
import com.example.vicky.gotexplore.model.Model;
import com.example.vicky.gotexplore.model.ModelResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    ViewPager vppager;
    FragmentPagerAdapter adapterViewPager;
    public String Character_Name = "Catelyn Stark";
    public List<Model> models;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("TAG" , TAG);



        adapterViewPager = new Mypager(getSupportFragmentManager());
        vppager = (ViewPager)findViewById(R.id.vpPager);
        vppager.setAdapter(adapterViewPager);







    }


    public class Mypager extends FragmentPagerAdapter {


        private  int NUM_ITEMS = 1;

        public Mypager(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FirstFragment.newInstance(0, "Page # 1");


                default:
                    return null;
            }
        }


        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + (position+1);
        }













    }
}
