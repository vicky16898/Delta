package com.example.vicky.canvascrop;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public CanvasView cv;
    public int counter = 0;
    private static final int SELECT_FILE = 0;
    public Bitmap BM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cv = new CanvasView(this);
        setContentView(cv );







    }


    private void Select_image(){

        final String[] values = new String[]{"Gallery","Cancel"   };
        AlertDialog.Builder imgbuilder = new AlertDialog.Builder(MainActivity.this);
        imgbuilder.setTitle("Add Image");
        imgbuilder.setItems(values, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {




               if(values[which].equals("Gallery"))
                {



                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, SELECT_FILE);





                }

                else{

                    dialog.dismiss();


                }





            }
        });

        imgbuilder.show();




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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_OK){







            if(requestCode==SELECT_FILE) {

                if (data != null) {

                    Uri imageUri = data.getData();


                    try {
                        BM = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("BM", String.valueOf(BM));

                    CanvasView v = new CanvasView(this);
                    v.invalidate();


                }
            }



        }

    }




    public class CanvasView extends View{

        public int x1,y1,x2,y2;




        public CanvasView(Context context) {
            super(context);

        }

        @Override
        protected void onDraw(Canvas canvas) {


            super.onDraw(canvas);
            Log.d("on draw enter" , "enter");
            Log.d("counter value" , String.valueOf(counter));

            if(counter == 1) {
                if(BM != null) {
                    BM = BITMAP_RESIZER(BM, 1200, 1680);
                    canvas.drawBitmap(BM, 0, 0, null);
                }

            }

            else if (counter == 2){
                Log.d("loop check" , "check");

                if(BM != null){


                    canvas.drawBitmap(BM,new Rect(x1,y1,x2,y2),new Rect(x1,y1,x2,y2),null);


                }



                else{


                }




            }


        }


        @Override
        public boolean onTouchEvent(MotionEvent event) {

            int action = event.getAction();
            int x = (int)event.getX();
            int y = (int)event.getY();


            switch(action){






               case MotionEvent.ACTION_DOWN:


                   if(counter == 0){

                       Select_image();


                   }

                   else {

                       x1 = x;
                       y1 = y;
                       Log.d("x1 co" , String.valueOf(x));
                       Log.d("y1 co" , String.valueOf(y));

                   }

                   break;

                case MotionEvent.ACTION_MOVE:

                    break;

                case MotionEvent.ACTION_UP:
                    if(counter == 0)

                    {

                        counter++;
                        Log.d("counter value" , String.valueOf(counter));


                    }

                    else

                    {

                        x2 = x;
                        y2 = y;
                        Log.d("x2 co" , String.valueOf(x));
                        Log.d("y2 co" , String.valueOf(y));
                        counter++;
                        if(counter == 2)
                        invalidate();

                    }






                    break;






            }
















            return true;
        }












    }

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

}
