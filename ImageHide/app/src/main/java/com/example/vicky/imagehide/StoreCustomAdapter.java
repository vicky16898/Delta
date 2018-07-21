package com.example.vicky.imagehide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StoreCustomAdapter extends ArrayAdapter<User_Images> {

    public Context context;
    public ArrayList<User_Images> fireList;
    public TextView img_title;
    public TextView img_date;
    public User_Images sub;

    public StoreCustomAdapter(Context context , ArrayList<User_Images> value){

        super(context,-1,value);
        this.context = context;
        this.fireList = value;



    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        sub = fireList.get(position);


        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view_ = inflater.inflate(R.layout.rt_list , parent , false);



        img_title = (TextView)view_.findViewById(R.id.img_title);
        img_date = (TextView)view_.findViewById(R.id.img_date);

        img_title.setText(sub.image_title);
        img_date.setText(sub.image_date);

        return view_;

    }
}
