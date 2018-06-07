package com.example.vicky.matchfixtures;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {


    private DataBaseHelper dataBaseHelper;

    private ArrayList<String> id;
    private ArrayList<String> name1;
    private ArrayList<String> name2;
    private ArrayList<String> date;
    private ArrayList<String> time;
    private ArrayList<String> venue;
    private ArrayList<Bitmap> image1;
    private ArrayList<Bitmap> image2;
    private byte[] b1, b2 = null;
    private String idData;
    public CustomArrayAdapter customAdapter;
    public ArrayList<SubSpecific> infos;
    public ListView list2;
    public int flag;
    private String ButtonId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Intent intent = getIntent();
        idData = intent.getExtras().getString("idKey");
        ButtonId = intent.getExtras().getString("buttonid");



        infos = new ArrayList<SubSpecific>();
        list2 = (ListView) findViewById(R.id.list2);


        customAdapter = new CustomArrayAdapter(Main2Activity.this, infos);


        id = new ArrayList<String>();
        name1 = new ArrayList<String>();
        name2 = new ArrayList<String>();
        date = new ArrayList<String>();
        time = new ArrayList<String>();
        venue = new ArrayList<String>();
        image1 = new ArrayList<Bitmap>();
        image2 = new ArrayList<Bitmap>();


        dataBaseHelper = new DataBaseHelper(this);

        Cursor res = dataBaseHelper.getAllData();


        while (res.moveToNext()) {


            id.add(res.getString(0));
            name1.add(res.getString(1));
            name2.add(res.getString(2));
            date.add(res.getString(3));
            time.add(res.getString(4));
            venue.add(res.getString(5));


            b1 = res.getBlob(6);
            b2 = res.getBlob(7);

            if ((b1 != null) && (b2 != null)) {

                image1.add(getImage(b1));
                image2.add(getImage(b2));

            } else if ((b1 == null) && (b2 == null)) {

                image1.add(null);
                image2.add(null);
            } else if ((b1 != null) && (b2 == null)) {

                image1.add(getImage(b1));
                image2.add(null);


            } else {
                image1.add(null);
                image2.add(getImage(b2));
            }


        }

        Log.d("no of elements", String.valueOf(res.getCount()));


        int i = 0;
        while (i < res.getCount()) {


            Log.d("while check", "whileloop");
            Log.d("iddata", idData);
            Log.d("idget", String.valueOf(id.get(i)));
            if (id.get(i).equals(idData))

            {

                flag = i;
                Log.d("check", "ifloop");


                customAdapter.add(new SubSpecific(
                        id.get(i),
                        name1.get(i),
                        name2.get(i),
                        date.get(i),
                        time.get(i),
                        venue.get(i),
                        image1.get(i),
                        image2.get(i)


                ));

                list2.setAdapter(customAdapter);


            }


            i++;


        }


        if(ButtonId.equals("1")) {


            if (flag != 0) {


                for (int j = 0; j < flag; j++) {


                    if ((name1.get(j).equals(name1.get(flag))) || (name2.get(j).equals(name1.get(flag)))) {


                        customAdapter.add(new SubSpecific(
                                id.get(j),
                                name1.get(j),
                                name2.get(j),
                                date.get(j),
                                time.get(j),
                                venue.get(j),
                                image1.get(j),
                                image2.get(j)


                        ));

                        list2.setAdapter(customAdapter);


                    }


                }
                for (int k = (flag + 1); k < res.getCount(); k++) {


                    if ((name1.get(k).equals(name1.get(flag))) || (name2.get(k).equals(name1.get(flag)))) {


                        customAdapter.add(new SubSpecific(
                                id.get(k),
                                name1.get(k),
                                name2.get(k),
                                date.get(k),
                                time.get(k),
                                venue.get(k),
                                image1.get(k),
                                image2.get(k)


                        ));

                        list2.setAdapter(customAdapter);


                    }


                }


            } else {


                for (int wolf = 1; wolf < res.getCount(); wolf++) {


                    if ((name1.get(wolf).equals(name1.get(flag))) || (name2.get(wolf).equals(name1.get(flag)))) {


                        customAdapter.add(new SubSpecific(
                                id.get(wolf),
                                name1.get(wolf),
                                name2.get(wolf),
                                date.get(wolf),
                                time.get(wolf),
                                venue.get(wolf),
                                image1.get(wolf),
                                image2.get(wolf)


                        ));

                        list2.setAdapter(customAdapter);


                    }


                }


            }


        }



        else if(ButtonId.equals("2")){



            if (flag != 0) {


                for (int j = 0; j < flag; j++) {


                    if ((name1.get(j).equals(name2.get(flag))) || (name2.get(j).equals(name2.get(flag)))) {


                        customAdapter.add(new SubSpecific(
                                id.get(j),
                                name1.get(j),
                                name2.get(j),
                                date.get(j),
                                time.get(j),
                                venue.get(j),
                                image1.get(j),
                                image2.get(j)


                        ));

                        list2.setAdapter(customAdapter);


                    }


                }
                for (int k = (flag + 1); k < res.getCount(); k++) {


                    if ((name1.get(k).equals(name2.get(flag))) || (name2.get(k).equals(name2.get(flag)))) {


                        customAdapter.add(new SubSpecific(
                                id.get(k),
                                name1.get(k),
                                name2.get(k),
                                date.get(k),
                                time.get(k),
                                venue.get(k),
                                image1.get(k),
                                image2.get(k)


                        ));

                        list2.setAdapter(customAdapter);


                    }


                }


            } else {


                for (int wolf = 1; wolf < res.getCount(); wolf++) {


                    if ((name1.get(wolf).equals(name2.get(flag))) || (name2.get(wolf).equals(name2.get(flag)))) {


                        customAdapter.add(new SubSpecific(
                                id.get(wolf),
                                name1.get(wolf),
                                name2.get(wolf),
                                date.get(wolf),
                                time.get(wolf),
                                venue.get(wolf),
                                image1.get(wolf),
                                image2.get(wolf)


                        ));

                        list2.setAdapter(customAdapter);


                    }


                }


            }














        }


    }

        public static Bitmap getImage ( byte[] image){
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }

    }

