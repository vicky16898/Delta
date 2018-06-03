package com.example.vicky.matchfixtures;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Details extends ArrayAdapter<Subject>
{
    private  Context context = null;
    private ArrayList<Subject> teams = null;
    private Subject subject;


    public Details(Context context , ArrayList<Subject> teams){

        super(context,-1,teams);
        this.context = context;
        this.teams = teams;



    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        subject =teams.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.match_content , parent , false);

        TextView date = (TextView)view.findViewById(R.id.date);
        TextView time = (TextView)view.findViewById(R.id.time);
        TextView teamname1 = (TextView)view.findViewById(R.id.teamname1);
        TextView teamname2 = (TextView)view.findViewById(R.id.teamname2);

        TextView venue = (TextView)view.findViewById(R.id.venue);
        Button logo1 = (Button) view.findViewById(R.id.logo1);
        Button logo2 = (Button) view.findViewById(R.id.logo2);

        BitmapDrawable bdrawable1 = new BitmapDrawable(context.getResources(),subject.bmp1);
        BitmapDrawable bdrawable2 = new BitmapDrawable(context.getResources(),subject.bmp2);

        logo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(context, Main2Activity.class);
                i.putExtra("idKey", teams.get(position).id);
                context.startActivity(i);


            }
        });

        logo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent i = new Intent(context, Main2Activity.class);
                i.putExtra("idKey", teams.get(position).id);
                    context.startActivity(i);


            }
        });

        teamname1.setText(subject.name1);
        teamname2.setText(subject.name2);

        date.setText(subject.date);
        time.setText(subject.time);
        venue.setText(subject.venue);
        logo1.setBackground(bdrawable1);
        logo2.setBackground(bdrawable2);







        return view;

    }
}
