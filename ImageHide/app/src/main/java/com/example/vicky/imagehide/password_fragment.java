package com.example.vicky.imagehide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.app.Activity.RESULT_OK;


public class password_fragment extends Fragment {


    public TextInputLayout passInput;
    public TextInputEditText passwordText;
    public Button next2;
    public FirebaseAuth fb;
    public ProgressBar progressBar2;







    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_password_fragment , container , false);
        passInput = (TextInputLayout)myView.findViewById(R.id.passInput);
        passwordText = (TextInputEditText)myView.findViewById(R.id.passwordText);
        next2 = (Button)myView.findViewById(R.id.next2);
        progressBar2 = (ProgressBar)myView.findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.GONE);

        fb = FirebaseAuth.getInstance();

        final String userPass = getArguments().getString("pass");
        final String userName = getArguments().getString("username");
        final int fire_base = getArguments().getInt("check" , 0 );
        final String name = getArguments().getString("username");








        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                    String typedPass = passwordText.getText().toString();
                    passInput.setError("");

                    if (typedPass.equals("")) {


                        passInput.setError("Enter a password");

                    } else {

                        if (fire_base != 0) {

                            progressBar2.setVisibility(View.VISIBLE);


                            fb.signInWithEmailAndPassword(name , typedPass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {


                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        Toast.makeText(getActivity(),"Log in success",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), Main2Activity.class);
                                        intent.putExtra("user_name", name);

                                        progressBar2.setVisibility(View.GONE);
                                        startActivity(intent);
                                        getActivity().finish();



                                    } else {
                                        // If sign in fails, display a message to the user.
                                        passInput.setError("Password entered is wrong");
                                        Toast.makeText(getActivity(),"User name or password is incorrect",Toast.LENGTH_LONG).show();
                                        progressBar2.setVisibility(View.GONE);
                                    }




                                }
                            });


                        }




                else{

                        if (userPass.equals(typedPass)) {

                            Intent intent = new Intent(getActivity(), Main2Activity.class);
                            intent.putExtra("user_name", userName);
                            startActivity(intent);
                            getActivity().finish();


                        } else {

                            passInput.setError("Password entered is incorrect");

                        }
                    }


                    }

                }


        });

        return myView;
    }




}
