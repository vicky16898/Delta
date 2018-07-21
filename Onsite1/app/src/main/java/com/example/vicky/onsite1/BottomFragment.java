package com.example.vicky.onsite1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

public class BottomFragment extends Fragment {

    public EditText editText2;
    public Button button2;
    public TextView sender1;
    public TextView receiver1;
    public String sentText;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_fragment , container , false);

        editText2 = (EditText)v.findViewById(R.id.editText2);
        button2 = (Button)v.findViewById(R.id.button2);
        sender1 = (TextView)v.findViewById(R.id.sender1);
        receiver1 = (TextView)v.findViewById(R.id.recevier1);
        sender1.setMovementMethod(new ScrollingMovementMethod());
        receiver1.setMovementMethod(new ScrollingMovementMethod());



            SharedPreferences prefs = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
            receiver1.setText(prefs.getString("key2", null));
            sender1.setText(prefs.getString("key3" , null));







        Bundle bundle=getArguments();

        if(bundle != null) {


            String txt2 = String.valueOf(bundle.getString("typed text"));
            String sendtxt = String.valueOf(bundle.getString("sendtxt"));




            receiver1.append(txt2 + "\n");
            SharedPreferences pref1 = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref1.edit();
            editor.putString("key2" , receiver1.getText().toString());
            editor.commit();


        }




        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText2.getText().toString().trim();
                sender1.append(editText2.getText().toString().trim() + "\n");
                SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("key3" , sender1.getText().toString());
                editor.commit();
                TopFragment top_frag = new TopFragment();
                Bundle bundle=new Bundle();
                bundle.putString("sent txt" , sentText);
                bundle.putString("receiver_text",text);
                top_frag.setArguments(bundle);
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.top, top_frag);
                transaction.commit();
            }
        });




        return v;
    }
}
