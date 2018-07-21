package com.example.vicky.imagehide;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vicky.imagehide.DatabasePackage.DatabaseHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class registration_fragment extends Fragment {

    public TextInputLayout signupUser;
    public TextInputEditText user_text;
    public TextInputLayout signupPass;
    public TextInputEditText pass_text;
    public TextInputLayout signupConfirmpass;
    public TextInputEditText confirmpass_text;
    public Button submit;
    public String name_of_the_user = null;
    public String password_of_the_user = null;
    public String confirmPassword_of_the_user = null;
    public int no_of_users = 0;
    public ArrayList<String> username_db;
    public int number = 0;
    private FirebaseAuth firebaseAuth;
    public ProgressBar progressBar;
    public boolean internet_check;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username_db = new ArrayList<String>();


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View regView = inflater.inflate(R.layout.fragment_registration_fragment , container , false);

        internet_check = haveNetworkConnection();

        if(internet_check == true) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        signupUser = (TextInputLayout)regView.findViewById(R.id.signupUser);
        signupPass = (TextInputLayout)regView.findViewById(R.id.signupPass);
        signupConfirmpass = (TextInputLayout)regView.findViewById(R.id.signupConfirmpass);
        progressBar = (ProgressBar)regView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        user_text = (TextInputEditText)regView.findViewById(R.id.user_text);
        pass_text = (TextInputEditText)regView.findViewById(R.id.pass_text);
        confirmpass_text = (TextInputEditText)regView.findViewById(R.id.confirmpass_text);




        //getting data;

        getAllData();
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        no_of_users = pref.getInt("no of users" , 0);




        submit = (Button)regView.findViewById(R.id.submit);





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.v("no of users" , String.valueOf(no_of_users));

                name_of_the_user = user_text.getText().toString().trim();
                password_of_the_user = pass_text.getText().toString().trim();
                confirmPassword_of_the_user = confirmpass_text.getText().toString().trim();

                signupUser.setError("");
                signupPass.setError("");
                signupConfirmpass.setError("");





                Log.e("name"  , name_of_the_user);
                Log.e("pass" , password_of_the_user);
                Log.e("confirm pass" , confirmPassword_of_the_user);

                if(name_of_the_user.equals("") && password_of_the_user.equals("") && confirmPassword_of_the_user.equals("")){

                    Log.v("if loop check" , "check");


                    signupUser.setError("username can't be empty");
                    signupPass.setError("Password can't be empty");
                    signupConfirmpass.setError("Confirm your password");







                }

                else if(name_of_the_user.equals("") || password_of_the_user.equals("") || confirmPassword_of_the_user.equals("")){

                    Log.v("if loop2 check" , "check");

                    if(name_of_the_user.equals("")){
                        signupUser.setError("username can't be empty");
                    }

                    if(password_of_the_user.equals("")){

                        signupPass.setError("Password can't be empty");


                    }

                    if(confirmPassword_of_the_user.equals("")){
                        signupConfirmpass.setError("Confirm your password");

                    }

                }

                else {

                    if(password_of_the_user.equals(confirmPassword_of_the_user)){



                        boolean flag = false;






                        for(int i =0 ; i < number ; i++){

                            Log.v("name " , name_of_the_user);
                            Log.v("user name from db" , username_db.get(i));

                            if((username_db.get(i)).equals (name_of_the_user))
                            {


                                flag = true;
                                Log.v("flag value" , String.valueOf(flag));

                            }


                        }

                        Log.v("flag value" , String.valueOf(flag));

                        if(flag == false) {


                            progressBar.setVisibility(View.VISIBLE);

                            if( internet_check == true) {


                                firebaseAuth.createUserWithEmailAndPassword(name_of_the_user, password_of_the_user).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {


                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {


                                                if (task.isSuccessful()) {


                                                    MyTask backTask = new MyTask(getActivity());
                                                    backTask.execute(String.valueOf(no_of_users), name_of_the_user, password_of_the_user);
                                                    no_of_users++;


                                                    Toast.makeText(getActivity(), "New account has been created", Toast.LENGTH_LONG).show();

                                                    SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = pref.edit();
                                                    editor.putInt("no of users", no_of_users);
                                                    editor.commit();


                                                    progressBar.setVisibility(View.GONE);


                                                    FragmentManager fm = getFragmentManager();
                                                    fm.popBackStack();

                                                } else {


                                                    progressBar.setVisibility(View.GONE);
                                                    Toast.makeText(getActivity(), "Registration error!", Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(getActivity(), "Password should be minimum of six characters", Toast.LENGTH_LONG).show();


                                                }
                                            }
                                        }


                                );

                            }

                            else {

                                Toast.makeText(getActivity(), "Internet connection not available", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);


                            }









                        }

                        else{

                            Log.v("error" , "user name already exisits");
                            signupUser.setError("username already exits");



                        }



                    }

                    else{


                        signupConfirmpass.setError("password didn't match");



                    }



                }







            }
        });




        return regView;
    }




    public class MyTask extends AsyncTask<String , Void , Void>{

        public Context context;
        public DatabaseHolder myDb;

        public MyTask(Context context){

            this.context = context;

        }

        @Override
        protected void onPreExecute() {

            myDb = new DatabaseHolder(context);

        }

        @Override
        protected Void doInBackground(String... strings) {

           boolean storeCheck =  myDb.insertUsers(strings[0] , strings[1] , strings[2]);
           Log.v("storeCheck" , String.valueOf(storeCheck));

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }



    public void getAllData(){

        DatabaseHolder db = new DatabaseHolder(getActivity());
        Cursor res = db.getAll_users();
        number = res.getCount();

        username_db.clear();


        while(res.moveToNext()){



            username_db.add(res.getString(1));




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
