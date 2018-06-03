package com.example.vicky.matchfixtures;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity  {

    private Button upload;

    private ListView list;

    private static  final String TAG ="MainActivity";

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public Bitmap BM = null;

    public Bitmap bmlogo1;

    public Bitmap bmlogo2;


    public File file;

    private int pos = 500;

    public int count = 0;



    public ArrayList<Subject> content;

    public Details adapter;

    public String datestring = null;

    public DataBaseHelper myDB;

    public int element_id = 0;





    public TimePickerDialog.OnTimeSetListener mTimeSetListener;




    private static final int REQUEST_CAMERA =1;
    private static final int SELECT_FILE = 0;



    public Bitmap BITMAP_RESIZER(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, 0, 0);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;

    }








    @Override
    protected void onCreate(Bundle savedInstanceState) {







        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("idstorage", MODE_PRIVATE);
        element_id = prefs.getInt("idName" , 0);








        list = (ListView) findViewById(R.id.list);

         content = new ArrayList<Subject>();
         adapter = new Details(MainActivity.this , content);


        myDB = new DataBaseHelper(this);


        retriveAll();









        upload = (Button)findViewById(R.id.upload);













        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bmlogo1 = null;
                bmlogo2 = null;

                AlertDialog.Builder infobuilder = new AlertDialog.Builder(MainActivity.this);
                View infoview = getLayoutInflater().inflate(R.layout.dialog_box , null);

                final EditText team1 = (EditText) infoview.findViewById(R.id.team1);
                final EditText team2 = (EditText)infoview.findViewById(R.id.team2);
                final EditText date = (EditText)infoview.findViewById(R.id.date);
                final EditText time = (EditText)infoview.findViewById(R.id.time);
                final EditText venue = (EditText)infoview.findViewById(R.id.venue);


                Button submit = (Button)infoview.findViewById(R.id.submit);
                final Button img1 = (Button)infoview.findViewById(R.id.img1);
                Button img2 = (Button)infoview.findViewById(R.id.img2);

                infobuilder.setView(infoview);
                final AlertDialog dialog = infobuilder.create();
                dialog.show();


                img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {




                        BM = null;
                        count = 1;
                        Selectimage();




                    }
                });

                img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        BM = null;
                        count = 0;
                        Selectimage();






                    }
                });


                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,
                                android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                                mDateSetListener,
                                year,month,day



                                );

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();





                    }
                });








                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month +1;
                        Log.d("VALUES",String.valueOf(year)+String.valueOf(month)+String.valueOf(dayOfMonth));


                        datestring =String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
                        date.setText(datestring);

                    }
                };


                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Calendar cal = Calendar.getInstance();
                        int hour = cal.get(Calendar.HOUR_OF_DAY);
                        int minute = cal.get(Calendar.MINUTE);

                        TimePickerDialog mtimepicker;
                        mtimepicker = new TimePickerDialog(MainActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                        String timestring = String.valueOf(hourOfDay)+":"+String.format("%02d",minute);
                                        time.setText(timestring);

                                    }
                                } , hour , minute , true


                        );


                        mtimepicker.setTitle("Select Time");
                        mtimepicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mtimepicker.show();




                    }



                });










                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {





                        adapter.add(new Subject(
                                String.valueOf(element_id),
                                team1.getText().toString(),
                                team2.getText().toString() ,
                                date.getText().toString() ,
                                time.getText().toString(),
                                venue.getText().toString(),
                                bmlogo1,
                                bmlogo2

                                )

                        );

                        list.setAdapter(adapter);





                        if((bmlogo1!=null)&&(bmlogo2!=null)) {





                                    Log.d("value" ,"1");

                            boolean isInserted = myDB.InsertData(

                                    String.valueOf(element_id),
                                    team1.getText().toString(),
                                    team2.getText().toString(),
                                    date.getText().toString(),
                                    time.getText().toString(),
                                    venue.getText().toString(),
                                    getBytes(bmlogo1),
                                    getBytes(bmlogo2)


                            );
                            if (isInserted == true)
                                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();



                        }
                        else if((bmlogo1==null)&&(bmlogo2==null))
                        {
                                     Log.d("value" ,"1");
                            boolean isInserted = myDB.InsertData(

                                    String.valueOf(element_id),
                                    team1.getText().toString(),
                                    team2.getText().toString(),
                                    date.getText().toString(),
                                    time.getText().toString(),
                                    venue.getText().toString(),
                                    null,
                                    null


                            );

                            if (isInserted == true)
                                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();








                        }


                        else if((bmlogo1==null)&&(bmlogo2!=null)){

                               Log.d("value" ,"1");
                            boolean isInserted = myDB.InsertData(

                                    String.valueOf(element_id),
                                    team1.getText().toString(),
                                    team2.getText().toString(),
                                    date.getText().toString(),
                                    time.getText().toString(),
                                    venue.getText().toString(),
                                    null,
                                    getBytes(bmlogo2)


                            );

                            if (isInserted == true)
                                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();




                        }

                        else
                        {
                            boolean isInserted = myDB.InsertData(

                                    String.valueOf(element_id),
                                    team1.getText().toString(),
                                    team2.getText().toString(),
                                    date.getText().toString(),
                                    time.getText().toString(),
                                    venue.getText().toString(),
                                    getBytes(bmlogo1),
                                    null


                            );

                            if (isInserted == true)
                                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();






                        }













                        element_id++;



                        SharedPreferences.Editor editor = getSharedPreferences("idstorage", MODE_PRIVATE).edit();

                        editor.putInt("idName", element_id);
                        editor.apply();

                        dialog.dismiss();


                    }
                });



            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {





                pos = position;
                bmlogo1 = null;
                bmlogo2 = null;

                AlertDialog.Builder infobuilder = new AlertDialog.Builder(MainActivity.this);
                View infoview = getLayoutInflater().inflate(R.layout.dialog_box , null);

                final EditText team1 = (EditText) infoview.findViewById(R.id.team1);
                final EditText team2 = (EditText)infoview.findViewById(R.id.team2);
                final EditText date = (EditText)infoview.findViewById(R.id.date);
                final EditText time = (EditText)infoview.findViewById(R.id.time);
                final EditText venue = (EditText)infoview.findViewById(R.id.venue);



                String test = null;

                Button submit = (Button)infoview.findViewById(R.id.submit);
                Button img1 = (Button)infoview.findViewById(R.id.img1);
                Button img2 = (Button)infoview.findViewById(R.id.img2);


                Subject sub = content.get(pos);

                if(sub.name1!=null)
                {
                    test = sub.name1;



                    team1.setText(test);



                }


                if(sub.name2!=null)
                {

                    test = sub.name2;
                    team2.setText(test);
                }


                if(sub.date!=null);
                {

                    test = sub.date;
                    date.setText(test);


                }


                if(sub.venue!=null){

                    test = sub.venue;
                    venue.setText(test);



                }





                if(sub.venue!=null)
                {
                    test = sub.time;
                    time.setText(test);


                }


                if(sub.bmp1!=null){

                    bmlogo1 = sub.bmp1;


                }


                if(sub.bmp2!=null){

                    bmlogo2 = sub.bmp2;


                }




                infobuilder.setView(infoview);
                final AlertDialog dialog = infobuilder.create();
                dialog.show();




                img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BM = null;

                        count=1;

                        Selectimage();




                    }
                });

                img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BM = null;

                        count=0;

                        Selectimage();






                    }
                });


                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,
                                android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                                mDateSetListener,
                                year,month,day



                        );

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();





                    }
                });



                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month +1;
                        Log.d("VALUES",String.valueOf(year)+String.valueOf(month)+String.valueOf(dayOfMonth));


                        datestring =String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
                        date.setText(datestring);

                    }
                };




                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Calendar cal = Calendar.getInstance();
                        int hour = cal.get(Calendar.HOUR_OF_DAY);
                        int minute = cal.get(Calendar.MINUTE);

                        TimePickerDialog mtimepicker;
                        mtimepicker = new TimePickerDialog(MainActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                        String timestring = String.valueOf(hourOfDay)+":"+String.format("%02d",minute);
                                        time.setText(timestring);

                                    }
                                } , hour , minute , true


                        );


                        mtimepicker.setTitle("Select Time");
                        mtimepicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mtimepicker.show();




                    }



                });








                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {






                        if(content.size()!=0) {







                            String flag = content.get(pos).id;
                            content.remove(pos);
                            content.add(pos, new Subject(
                                    flag,
                                    team1.getText().toString(),
                                    team2.getText().toString(),
                                    date.getText().toString(),
                                    time.getText().toString(),
                                    venue.getText().toString(),
                                    bmlogo1,
                                    bmlogo2
                            ));






                          myDB.deleteData(flag);



                            if((content.get(pos).bmp1!=null)&&(content.get(pos).bmp2!=null)) {




                                Log.d("both" ,"1");

                                boolean isInserted = myDB.InsertData(

                                        content.get(pos).id,
                                        content.get(pos).name1,
                                        content.get(pos).name2,
                                        content.get(pos).date,
                                        content.get(pos).time,
                                        content.get(pos).venue,
                                        getBytes(content.get(pos).bmp1),
                                        getBytes(content.get(pos).bmp2)


                                );
                                if (isInserted == true)
                                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();



                            }
                            else if((content.get(pos).bmp1==null)&&(content.get(pos).bmp2==null))
                            {
                                Log.d("value" ,"1");
                                boolean isInserted = myDB.InsertData(

                                        content.get(pos).id,
                                        content.get(pos).name1,
                                        content.get(pos).name2,
                                        content.get(pos).date,
                                        content.get(pos).time,
                                        content.get(pos).venue,
                                        null,
                                        null


                                );




                                if (isInserted == true)
                                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();








                            }


                            else if((content.get(pos).bmp1==null)&&(content.get(pos).bmp2!=null)){

                                Log.d("value" ,"1");
                                boolean isInserted = myDB.InsertData(

                                        content.get(pos).id,
                                        content.get(pos).name1,
                                        content.get(pos).name2,
                                        content.get(pos).date,
                                        content.get(pos).time,
                                        content.get(pos).venue,
                                        null,
                                        getBytes(content.get(pos).bmp2)


                                );

                                if (isInserted == true)
                                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();




                            }

                            else
                            {
                                boolean isInserted = myDB.InsertData(

                                        content.get(pos).id,
                                        content.get(pos).name1,
                                        content.get(pos).name2,
                                        content.get(pos).date,
                                        content.get(pos).time,
                                        content.get(pos).venue,
                                        getBytes(content.get(pos).bmp1),
                                        null


                                );

                                if (isInserted == true)
                                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();






                            }









                            list.setAdapter(adapter);
                            dialog.dismiss();

                        }





                        else
                            dialog.dismiss();


                    }
                });



            }
        });


         list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                 final String[] choose = new String[]{

                         "Yes",
                         "No"


                 };

                 AlertDialog.Builder delbuilder = new AlertDialog.Builder(MainActivity.this);
                 delbuilder.setTitle("Delete entry");

                 delbuilder.setItems(choose, new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {

                         if(choose[which].equals("Yes")){



                             myDB.deleteData(content.get(position).id);
                             content.remove(position);
                             list.setAdapter(adapter);



                         }

                         else
                             dialog.dismiss();









                     }
                 });



                 delbuilder.show();

                 return true;
             }
         });









    }





    private void Selectimage(){

         final String[] values = new String[]{"Camera","Gallery","Cancel"   };
        AlertDialog.Builder imgbuilder = new AlertDialog.Builder(MainActivity.this);
        imgbuilder.setTitle("Add Image");
        imgbuilder.setItems(values, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {



                if(values[which].equals("Camera")){




                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, REQUEST_CAMERA);














                }

                else if(values[which].equals("Gallery"))
                {


                    try {
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_FILE);
                        } else {
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            galleryIntent.setType("image/*");
                            startActivityForResult(galleryIntent, SELECT_FILE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }






                }

                else{

                    dialog.dismiss();


                }





            }
        });

        imgbuilder.show();




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if(resultCode== Activity.RESULT_OK){



             if(requestCode==REQUEST_CAMERA){

              Bundle bundle = data.getExtras();
              BM = (Bitmap)bundle.get("data");
              Log.d("BM",String.valueOf(BM));


             }
             else if(requestCode==SELECT_FILE){

                 Uri imageUri = data.getData();

                 try {
                     BM = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                 Log.d("BM",String.valueOf(BM));

             }

             BM = BITMAP_RESIZER(BM , 100 ,100);





             if(count == 1){

                 bmlogo1 = BM;


             }
             else {
                 bmlogo2 = BM;

             }







         }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {







                       switch (requestCode) {


                           case SELECT_FILE:
                               // If request is cancelled, the result arrays are empty.
                               if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                   Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                   startActivityForResult(galleryIntent, SELECT_FILE);
                               } else {
                                   Toast.makeText(getApplicationContext(), "Gallery permission denied",
                                           Toast.LENGTH_LONG).show();
                               }
                               break;
                       }










    }

    public void retriveAll(){


        Cursor res = myDB.getAllData();

        Log.d("count" , String.valueOf(res.getCount()));
        if(res.getCount() == 0) {


            return;
        }

            String item0 ,item1 ,item2 , item3 ,item4 ,item5 = null;
            byte[] b1,b2 = null;
            Bitmap bm1,bm2 = null;


            while (res.moveToNext()){


                item0 = res.getString(0);
                item1 = res.getString(1);
                item2 = res.getString(2);
                item3 = res.getString(3);
                item4 = res.getString(4);
                item5 = res.getString(5);


                b1=res.getBlob(6);
                b2=res.getBlob(7);

                if((b1!=null)&&(b2!=null))
                {

                    bm1=getImage(b1);
                    bm2=getImage(b2);

                }
                else if((b1==null)&&(b2==null)){

                    bm1=null;
                    bm2=null;
                }

                else if((b1!=null)&&(b2==null))
                {

                    bm1 = getImage(b1);
                    bm2 = null;



                }

                else
                {
                    bm1=null;
                    bm2=getImage(b2);

                }


                adapter.add(new Subject(item0 ,item1 , item2 ,item3 ,item4 ,item5 ,bm1,bm2));
                list.setAdapter(adapter);






            }


        }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }








    }









