package com.example.vicky.infinitygems;

import android.support.v7.app.AppCompatActivity;



        import android.content.SharedPreferences;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final ArrayList<String> stone = new ArrayList<String>(6);
    private int index = 0;
    private static final Random random = new Random();
    int power=0;
    int space=0;
    int time=0;
    int reality=0;
    int soul=0;
    int mind=0;
    int flag=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stone.add("Power Stone");
        stone.add("Space Stone");
        stone.add("Time Stone");
        stone.add("Reality Stone");
        stone.add("Soul Stone");
        stone.add("Mind Stone");
        Button choose = (Button) findViewById(R.id.choose);
        final TextView stonelist=(TextView)findViewById(R.id.stonelist);
        final TextView name = (TextView) findViewById(R.id.name);
        final TextView color = (TextView) findViewById(R.id.color);
        Button reset = (Button) findViewById(R.id.reset);


        SharedPreferences pref=getSharedPreferences("details",MODE_PRIVATE);

        power=pref.getInt("power",0);
        space=pref.getInt("space",0);
        time=pref.getInt("time",0);
        reality=pref.getInt("reality",0);
        soul=pref.getInt("soul",0);
        mind=pref.getInt("mind",0);
        flag=pref.getInt("flagvalue",0);
        String stones=pref.getString("stonelist","");

        stonelist.setText(stones);

        if(flag==6)
            name.setText("You've got all the stones!!");
        else
            name.setText("GET THE STONES");





        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                index=random.nextInt(6);
                name.setText("You've got "+stone.get(index));



                if(flag<6) {

                    if (stone.get(index) == "Power Stone") {
                        color.setBackgroundColor(getResources().getColor(R.color.purple));
                        power++;
                    } else if (stone.get(index) == "Space Stone") {
                        color.setBackgroundColor(getResources().getColor(R.color.blue));
                        space++;
                    } else if (stone.get(index) == "Time Stone") {
                        color.setBackgroundColor(getResources().getColor(R.color.green));
                        time++;
                    } else if (stone.get(index) == "Reality Stone") {
                        color.setBackgroundColor(getResources().getColor(R.color.red));
                        reality++;
                    } else if (stone.get(index) == "Soul Stone") {
                        color.setBackgroundColor(getResources().getColor(R.color.Orange));
                        soul++;
                    } else if (stone.get(index) == "Mind Stone") {
                        color.setBackgroundColor(getResources().getColor(R.color.yellow));
                        mind++;
                    }


                    if (power == 1) {

                        stonelist.append("   Obtained Power Stone!\n");
                        power++;
                        flag++;
                    } else if (space == 1) {
                        stonelist.append("   Obtained Space Stone!\n");
                        space++;
                        flag++;
                    } else if (time == 1) {
                        stonelist.append( " Obtained Time Stone!\n");
                        time++;
                        flag++;

                    } else if (reality == 1) {

                        stonelist.append("    Obtained Reality Stone!\n");
                        reality++;
                        flag++;
                    } else if (soul == 1) {

                        stonelist.append("Obtained Soul Stone!\n");
                        soul++;
                        flag++;
                    } else if (mind == 1) {
                        stonelist.append(" Obtained Mind Stone!\n");
                        mind++;
                        flag++;

                    }

                    if (flag == 6) {

                        color.setText("You've got all  the stones!!");

                    }

                }

                else
                {
                    name.setText("Just Snap");
                    color.setText("You've got all the stones!!");
                    stonelist.setText("");
                    color.setBackgroundColor(getResources().getColor(R.color.white));



                }

                SharedPreferences pref=getSharedPreferences("details",MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();

                editor.putInt("power",power);
                editor.putInt("space",space);
                editor.putInt("time",time);
                editor.putInt("reality",reality);
                editor.putInt("soul",soul);
                editor.putInt("mind",mind);
                editor.putInt("flagvalue",flag);
                editor.putString("stonelist",stonelist.getText().toString());
                editor.commit();






            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flag=0;
                power=0;
                space=0;
                time=0;
                reality=0;
                soul=0;
                mind=0;
                flag=0;

                name.setText("GET THE STONES");
                color.setBackgroundColor(getResources().getColor(R.color.white));
                color.setText("");
                stonelist.setText("");
                SharedPreferences pref=getSharedPreferences("details",MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();
                editor.putInt("power",power);
                editor.putInt("space",space);
                editor.putInt("time",time);
                editor.putInt("reality",reality);
                editor.putInt("soul",soul);
                editor.putInt("mind",mind);
                editor.putInt("flagvalue",flag);
                editor.putString("stonelist",stonelist.getText().toString());
                editor.commit();

            }
        });







    }

}