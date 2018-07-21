package com.example.vicky.imagehide;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vicky.imagehide.DatabasePackage.DatabaseHolder;
import com.github.cirorizzo.JealousSky;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class User_item_act extends AppCompatActivity {

    public ImageView encryptImageView;
    public Button decrypt;
    public DatabaseHolder myNewDb;
    public int Id_of_User;
    public String encrypted_image_path;
    public Bitmap img;
    public String time;
    public String title_of_;
    public TextView title_of_image;
    public TextView time_of_image;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_item_act);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        encryptImageView = (ImageView)findViewById(R.id.encryptImageView);
        decrypt = (Button)findViewById(R.id.decrypt);
        title_of_image = (TextView)findViewById(R.id.title_of_image);
        time_of_image = (TextView)findViewById(R.id.time_of_image);

        Intent intent = getIntent();
        Id_of_User = intent.getIntExtra("id_of_user" , 0);
        Log.d("value" , String.valueOf(Id_of_User));

        getUserImage();

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                File Encrypt_file =  new File(encrypted_image_path);



                if(Encrypt_file.exists()){


                    Log.d("Encrypt File" , String.valueOf(Encrypt_file));
                }

                JealousSky jealousSky = JealousSky.getInstance();

                try {
                    jealousSky.initialize(
                            "longestPasswordEverCreatedInAllTheUniverseOrMore",
                            "FFD7BADF2FBB1999");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                try {
                    FileInputStream encryptedInput = new FileInputStream(Encrypt_file);
                    img = jealousSky.decryptToBitmap(encryptedInput);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }

                encryptImageView.setImageBitmap(img);
                time_of_image.setText(time);
                title_of_image.setText(title_of_);


            }
        });








    }


    public void getUserImage(){

        myNewDb = new DatabaseHolder(User_item_act.this);
        Cursor ind = myNewDb.getIndividual(Id_of_User);
        title_of_ = null;
        time = null;
        while (ind.moveToNext()){

            title_of_ = ind.getString(2);
            time = ind.getString(4);
            encrypted_image_path = ind.getString(3);


        }




    }





}
