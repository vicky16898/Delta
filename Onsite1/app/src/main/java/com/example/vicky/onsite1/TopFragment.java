package com.example.vicky.onsite1;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


public class TopFragment extends Fragment {

    public EditText editText;
    public Button button;
    public TextView sender2;
    public TextView reciever2;
    public String send;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_fragment, container, false);

        editText = (EditText)view.findViewById(R.id.editText);
        button = (Button)view.findViewById(R.id.button);
        sender2 = (TextView)view.findViewById(R.id.sender2);
        reciever2 = (TextView)view.findViewById(R.id.receiver2);
        sender2.setMovementMethod(new ScrollingMovementMethod());
        reciever2.setMovementMethod(new ScrollingMovementMethod());



            SharedPreferences prefs = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
            reciever2.setText(prefs.getString("key1", null));
            sender2.setText(prefs.getString("key4" , null));







        Bundle bundle=getArguments();

        if(bundle != null) {


            String txt = String.valueOf(bundle.getString("receiver_text"));
            send = String.valueOf(bundle.getString("send_txt"));

            reciever2.append(txt + "\n");
            SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("key1" , reciever2.getText().toString());
            editor.commit();


        }





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







                String text = editText.getText().toString().trim();
                sender2.append(text + "\n");
                SharedPreferences pref2 = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref2.edit();
                editor.putString("key4" , sender2.getText().toString());
                editor.commit();



                BottomFragment bottom_frag = new BottomFragment();
                Bundle bundle=new Bundle();
                bundle.putString("typed text",text);
                bundle.putString("sendtxt" , send);
                bottom_frag.setArguments(bundle);
                FragmentTransaction transection=getFragmentManager().beginTransaction();
                transection.replace(R.id.bottom, bottom_frag);
                transection.commit();

            }
        });





        return view;
    }
}
