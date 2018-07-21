package com.example.vicky.imagehide;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class realtime_list extends AppCompatActivity {

    public ListView stored_list;
    public DatabaseReference db_reg;
    private FirebaseAuth fbAuth;
    public ArrayList<User_Images> list_project;
    public StoreCustomAdapter Fire_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_list);
        stored_list = (ListView)findViewById(R.id.stored_list);




        fbAuth = FirebaseAuth.getInstance();

        FirebaseUser user = fbAuth.getCurrentUser();
        Intent i = getIntent();
        String name_of = i.getStringExtra("email");
        db_reg = FirebaseDatabase.getInstance().getReference("users").child(name_of);


        list_project = new ArrayList<User_Images>();
        Fire_adapter = new StoreCustomAdapter(realtime_list.this , list_project);
        stored_list.setAdapter(Fire_adapter);


        db_reg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list_project.clear();

                Log.v("dataSnapshot" , String.valueOf(dataSnapshot));
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {


                   User_Images img = new User_Images(postSnapshot.child("userId").getValue().toString() ,
                                                     postSnapshot.child("image_title").getValue().toString(),
                                                     postSnapshot.child("image_date").getValue().toString()

                           );
                  Fire_adapter.add(img);

                }

                Log.v("size of array list" , String.valueOf(list_project.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
