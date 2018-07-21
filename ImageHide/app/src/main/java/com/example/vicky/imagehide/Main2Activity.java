package com.example.vicky.imagehide;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vicky.imagehide.DatabasePackage.DatabaseHolder;
import com.github.cirorizzo.JealousSky;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Main2Activity extends AppCompatActivity {


    public FloatingActionButton upload;
    public ListView img_list;
    private static final int REQUEST_CAMERA =1;
    private static final int SELECT_FILE = 0;
    public Bitmap image_stored = null;
    public String user_account_name = null;
    public ArrayList<User_Stored_details> user_content;
    public CustomArrayAdapter myAdapter;
    public DatabaseHolder Db;
    public int users_id = 0;
    public int fileCounter = 0;
    public String file_path = null;
    public DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    public String email;




    //// create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logut, menu);
        return super.onCreateOptionsMenu(menu);

    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.log_out) {




            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Confirm");
            builder.setMessage("Are you sure want to sign out?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    Main2Activity.this.finish();




                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();






        }

        else if (id == R.id.new_list){


            Intent intent = new Intent(Main2Activity.this , realtime_list.class);
            Log.v("email check" , email);
            intent.putExtra("email" , email);
            startActivity(intent);





        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        users_id = pref.getInt("no of users" , 0);
        SharedPreferences file_pref = getApplicationContext().getSharedPreferences("MyPref2", MODE_PRIVATE);
        fileCounter = file_pref.getInt("no of images" , 0);

        Db = new DatabaseHolder(Main2Activity.this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        email = String.valueOf(currentUser.getEmail());
        email = email.replace("@" , "je");
        email = email.replace("." , "je");
        Log.v("email replaced" , email);




        upload = (FloatingActionButton)findViewById(R.id.upload);
        img_list = (ListView)findViewById(R.id.img_list);
        user_account_name = getIntent().getStringExtra("user_name");
        Log.d("user_name" , user_account_name);
        user_content = new ArrayList<User_Stored_details>();
        getUser_data();
        myAdapter = new CustomArrayAdapter(Main2Activity.this , user_content);
        img_list.setAdapter(myAdapter);
        img_list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        img_list.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {





            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {



                // Capture total checked items
                final int checkedCount = img_list.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount +" Items Selected");



                // Calls toggleSelection method from ListViewAdapter Class
                myAdapter.toggleSelection(position);

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {

                mode.getMenuInflater().inflate(R.menu.multiple_delete, menu);
                return true;

            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);

                        builder.setTitle("Delete!!");
                        builder.setMessage("Are you sure want to delete the selected items?");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {



                                // Calls getSelectedIds method from ListViewAdapter Class
                                SparseBooleanArray selected = myAdapter.getSelectedIds();
                                Log.d("Selectedids" , String.valueOf(selected));

                                // Captures all selected ids with a loop
                                for (int i = (selected.size() - 1); i >= 0; i--) {
                                    if (selected.valueAt(i)) {
                                        User_Stored_details selected_item = myAdapter
                                                .getItem(selected.keyAt(i));
                                        // Remove selected items following the ids

                                        myAdapter.remove(selected_item);
                                        Db.deleteData(selected_item.id);

                                    }
                                }
                                // Close CAB
                                mode.finish();
                                selected.clear();
                                user_content = myAdapter.getWorldPopulation();



                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();

                        return true;



                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {



            }
        });

        img_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(Main2Activity.this, User_item_act.class);
                myIntent.putExtra("id_of_user", user_content.get(position).id);
                Log.e("ID" , String.valueOf(user_content.get(position).id));
                Main2Activity.this.startActivity(myIntent);
            }
        });






        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(Main2Activity.this);
                final View uploadDialogView = factory.inflate(R.layout.my_dialog, null);
                final AlertDialog uploadDialog = new AlertDialog.Builder(Main2Activity.this).create();
                uploadDialog.setView(uploadDialogView);


                Button okButton = (Button)uploadDialogView.findViewById(R.id.okButton);
                final TextInputEditText title = (TextInputEditText)uploadDialogView.findViewById(R.id.title);
                final TextInputLayout titleLayout = (TextInputLayout)uploadDialogView.findViewById(R.id.titleLayout);
                Button choose = (Button)uploadDialogView.findViewById(R.id.choose);

                choose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Select_image();


                    }
                });




















                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String titleText = title.getText().toString().trim();

                        if(titleText.equals("")){

                            titleLayout.setError("Enter any title");


                        }

                        else {


                            if(image_stored != null) {




                                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                                String date = df.format(Calendar.getInstance().getTime());
                                myAdapter.add(new User_Stored_details(users_id , titleText, user_account_name , date));

                                Log.e("user account name" , user_account_name);

                                boolean insert = Db.insertUserContent(String.valueOf(users_id) ,
                                                                      user_account_name ,
                                                                      titleText,
                                                                      file_path,
                                                                      date
                                        );
                                users_id++;

                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putInt("no of users", users_id);
                                editor.commit();

                                String fireBase_id = databaseReference.push().getKey();
                                User_Images user_images = new User_Images(fireBase_id ,titleText , date);
                                databaseReference.child(email).child(fireBase_id).setValue(user_images);
                                Toast.makeText(getApplicationContext(), "item added",
                                        Toast.LENGTH_SHORT).show();



                                Log.v("insert check" , String.valueOf(insert));





                                image_stored = null;
                                uploadDialog.dismiss();

                            }

                            else{

                                Toast.makeText(getApplicationContext(), "Choose an image to encrypt",
                                        Toast.LENGTH_SHORT).show();

                            }




                        }
                    }
                });




                uploadDialog.show();




            }
        });
    }


    private void Select_image(){

        final String[] values = new String[]{"Camera","Gallery","Cancel"   };
        AlertDialog.Builder imgbuilder = new AlertDialog.Builder(Main2Activity.this);
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
                        if (ActivityCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_FILE);
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

        image_stored = null;

        if(requestCode==REQUEST_CAMERA){

                if(data != null) {
                    Bundle bundle = data.getExtras();
                    image_stored = (Bitmap) bundle.get("data");
                }




        }

        else if(requestCode==SELECT_FILE){


                if(data != null) {
                    Uri imageUri = data.getData();


                    try {
                        image_stored = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }






        }


        if(image_stored != null) {


            JealousSky jealousSky = JealousSky.getInstance();

            try {
                jealousSky.initialize(
                        "longestPasswordEverCreatedInAllTheUniverseOrMore",
                        "FFD7BADF2FBB1999");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            String path = getApplicationContext().getFilesDir().getAbsolutePath();
            Log.d("path" , path);
            OutputStream fOut = null;
            File file = new File(path, "ImageStored.jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
            try {
                fOut = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Bitmap pictureBitmap = image_stored; // obtaining the Bitmap



            pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            try {
                fOut.flush(); // Not really required
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("file" , String.valueOf(file));
            Log.d("file check" , String.valueOf(file.exists()));

            try {
                String directory_path = "Encrypted" + String.valueOf(fileCounter) + ".jpg";
                FileInputStream inputStream = new FileInputStream(file);
                File encryptedFile = new File(path , directory_path);
                fileCounter++;

                SharedPreferences file_pref = getApplicationContext().getSharedPreferences("MyPref2", MODE_PRIVATE);
                SharedPreferences.Editor editor = file_pref.edit();
                editor.putInt("no of images", fileCounter);
                editor.commit();
                Log.d("encrypted file" , String.valueOf(encryptedFile));
                Log.v("new file check" , String.valueOf(encryptedFile.exists()));
                encryptedFile = jealousSky.encryptToFile(inputStream , String.valueOf(encryptedFile));

                file_path = String.valueOf(encryptedFile);



                //encrypted file contains encrypted image;





                if(file.exists()) {

                    file.delete();
                }




                Log.v("file check" , String.valueOf(file.exists()));
                Log.v("encrypted file check" , String.valueOf(encryptedFile.exists()));
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


    public void getUser_data(){

        DatabaseHolder db = new DatabaseHolder(Main2Activity.this);
        Cursor res = db.getUser_data();
        user_content.clear();

        Log.v("size" , String.valueOf(res.getCount()));

        while (res.moveToNext()){

            Log.v("user name" , res.getString(1));
            Log.v("title" , res.getString(2));

            if(user_account_name.equals(res.getString(1))){

                user_content.add(new User_Stored_details( Integer.valueOf(res.getString(0)),
                                                          res.getString(2) ,
                                                          res.getString(1) ,
                                                          res.getString(4)


                        ));




            }









        }





    }

    public static byte[] convertFileToByteArray(File f)
    {
        byte[] byteArray = null;
        try
        {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024*8];
            int bytesRead =0;

            while ((bytesRead = inputStream.read(b)) != -1)
            {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return byteArray;
    }





}
