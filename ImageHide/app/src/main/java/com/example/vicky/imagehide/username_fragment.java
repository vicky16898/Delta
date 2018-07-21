package com.example.vicky.imagehide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vicky.imagehide.DatabasePackage.DatabaseHolder;
import com.example.vicky.imagehide.R;

import java.util.ArrayList;


public class username_fragment extends Fragment {

    public TextInputLayout textInputLayout;
    public TextInputEditText username_field;
    public Button next;
    public TextView newsignup;
    public DatabaseHolder MyNewDb;
    public ArrayList<User_Details> enteredName;
    public int size_of_db = 0;
    public Button loginButton;
    public boolean connection_check = false;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        enteredName = new ArrayList<User_Details>();

    }







    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_username_fragment , container , false);

        connection_check = haveNetworkConnection();

        textInputLayout = (TextInputLayout)myView.findViewById(R.id.textInput);
        username_field = (TextInputEditText)myView.findViewById(R.id.user_name);
        next = (Button)myView.findViewById(R.id.next);
        newsignup = (TextView)myView.findViewById(R.id.newsignup);
        loginButton = (Button)myView.findViewById(R.id.loginButton);
        newsignup.setText("New to this app?");


        //data fetch;
        retriveData();









       loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               if(connection_check == true) {

                   String typedUser = username_field.getText().toString().trim();
                   textInputLayout.setError("");


                   if (typedUser.equals("")) {

                       textInputLayout.setError("Enter an username");

                   } else {


                       int fire_base_check = 1;
                       password_fragment frag = new password_fragment();
                       Bundle args = new Bundle();

                       args.putString("username", typedUser);
                       args.putInt("check", fire_base_check);
                       frag.setArguments(args);
                       FragmentManager fragmentManager = getFragmentManager();
                       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                       fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);

                       fragmentTransaction.replace(R.id.frameMain, frag);
                       fragmentTransaction.addToBackStack(null);
                       fragmentTransaction.commit();


                   }

               }

               else{

                   Toast.makeText(getActivity(), "Internet connection not available", Toast.LENGTH_LONG).show();

               }

           }
       });





        newsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newsignup.setPaintFlags(newsignup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,R.anim.enter_from_right  , R.anim.exit_to_left);

                fragmentTransaction.replace(R.id.frameMain, new registration_fragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });















        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String typedUser = username_field.getText().toString().trim();
                textInputLayout.setError("");




                if(typedUser.equals("")){

                    textInputLayout.setError("Enter an username");

                }

                else {



                    boolean flag  = false;
                    int count_ = 0;


                    if(size_of_db != 0 ){




                    for(int i = 0 ; i < size_of_db ; i++) {


                        if (typedUser.equals((enteredName.get(i).name_of_user))) {


                            Log.e("equality check" , "check");
                            flag = true;
                            count_ = i;

                        }

                    }

                    Log.e("flag" , String.valueOf(flag));

                    if(flag == true){

                        Log.v("flag loop run" , "check");


                        password_fragment frag = new password_fragment();
                        Bundle args = new Bundle();
                        args.putString("pass", (enteredName.get(count_).pass_of_user));
                        args.putString("username" , enteredName.get(count_).name_of_user);
                        frag.setArguments(args);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);

                        fragmentTransaction.replace(R.id.frameMain, frag);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();



                    }

                    else{

                        textInputLayout.setError("Wanna login , create an account");

                    }



                    }

                    else {

                        textInputLayout.setError("Wanna login , create an account");
                        Log.v("size check" , "check");

                    }











                }

            }
        });


        return myView;
    }

    public  void retriveData(){

        MyNewDb = new DatabaseHolder(getActivity());
        Cursor res = MyNewDb.getAll_users();
        enteredName.clear();

        size_of_db = res.getCount();

        while (res.moveToNext()){

            enteredName.add(new User_Details(


                    res.getString(1) ,

                    res.getString(0) ,

                    res.getString(2)

                    )


            );


        }








    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}
