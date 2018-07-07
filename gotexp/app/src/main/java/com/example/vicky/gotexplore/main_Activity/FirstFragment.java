package com.example.vicky.gotexplore.main_Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vicky.gotexplore.DatabaseHelper.Data_fetchclass;
import com.example.vicky.gotexplore.DatabaseHelper.DatabaseClass;
import com.example.vicky.gotexplore.R;
import com.example.vicky.gotexplore.Retrofit.ApiClient;
import com.example.vicky.gotexplore.Retrofit.ApiInterface;
import com.example.vicky.gotexplore.model.Model;
import com.example.vicky.gotexplore.model.ModelResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FirstFragment extends Fragment {


    public AutoCompleteTextView ac;
    public Button search;
    public Model model;
    public ProgressBar progressBar;
    public MyTask background_task;
    public ArrayAdapter<String> myAdapter;
    public ArrayList<String> name_list;
    public boolean network_check = false;
    public int counter = 0;
    public DatabaseClass db;
    public int dbCounter = 0;
    public int _id = 0;
    public boolean present_datacheck = false;
    public int db_recover = 0;




    public static FirstFragment newInstance(int page, String title) {
        FirstFragment fragmentFirst = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        db = new DatabaseClass(getContext());
        name_list = new ArrayList<String>();
        retrive_all();
        myAdapter = new ArrayAdapter<String>(getContext() , R.layout.suggestion_list , name_list);

        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        _id = pref.getInt("id_value" , 0);

        Log.v("db current id value" , String.valueOf(_id));

        Log.e("on create check" , "check");



    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        ac = (AutoCompleteTextView) view.findViewById(R.id.ac);
        search = (Button) view.findViewById(R.id.search);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        ac.setAdapter(myAdapter);
        ac.setThreshold(1);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                present_datacheck = false;

                final String check_content = ac.getText().toString();

                String temp = capitalize(check_content);


                for (int store = 0; store < name_list.size(); store++) {


                    if (temp.equals(name_list.get(store))) {


                        db_recover = store;

                        Log.e("db recover" , String.valueOf(db_recover));

                        present_datacheck = true;



                    }


                }


                if (present_datacheck == true) {




                    Cursor fetch = db.getPerson(db_recover);

                    while (fetch.moveToNext()){


                        Intent i = new Intent(getContext(), Display_Activity.class);
                        i.putExtra("name", fetch.getString(1));
                        i.putExtra("spouse", fetch.getString(3));
                        i.putExtra("house", fetch.getString(5));
                        i.putExtra("culture", fetch.getString(4));
                        i.putExtra("dob", fetch.getString(6));
                        i.putExtra("dod", fetch.getString(7));
                        i.putExtra("male", fetch.getString(2));
                        i.putExtra("URL", fetch.getString(8));
                        progressBar.setVisibility(View.GONE);
                        startActivity(i);



                    }





                } else
                {





                    counter = 0;
                    dbCounter = 0;


                network_check = haveNetworkConnection();
                Log.d("network check", String.valueOf(network_check));


                if (network_check == false) {

                    Toast.makeText(getContext(), "No Internet Connection!",
                            Toast.LENGTH_SHORT).show();


                } else {

                    if (check_content.equals("")) {

                        Toast.makeText(getContext(), "You did not type any name!",
                                Toast.LENGTH_SHORT).show();


                    } else {


                        progressBar.setVisibility(View.VISIBLE);


                        ApiInterface apiService =
                                ApiClient.getClient().create(ApiInterface.class);


                        Call<ModelResponse> call = apiService.getCharacterDetails(ac.getText().toString());


                        call.enqueue(new Callback<ModelResponse>() {
                            @Override
                            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {

                                model = new Model();
                                Log.e("response", String.valueOf(response.body()));

                                if (response.body() != null) {

                                    model = response.body().getData();

                                    String sex;
                                    if (model.getMale() == false) {

                                        sex = "Female";
                                        Log.d("sex", sex);
                                    } else {

                                        sex = "Male";
                                        Log.d("sex", sex);
                                    }

                                    String name, spouse, house, culture, image_link, birth, death = null;


                                    //data in model object;
                                    name = String.valueOf(model.getName());
                                    spouse = String.valueOf(model.getSpouse());
                                    house = String.valueOf(model.getHouse());
                                    culture = String.valueOf(model.getCulture());
                                    image_link = String.valueOf(model.getImageLink());
                                    Log.e("img", image_link);
                                    birth = String.valueOf(model.getDateOfBirth());
                                    death = String.valueOf(model.getDateOfDeath());
                                    Log.d("death", death);


                                    String check_type = capitalize(check_content);

                                    Log.e("entered string", check_type);

                                    Log.d("length", String.valueOf(name_list.size()));
                                    Log.e("length of String", String.valueOf(check_type.length()));
                                    Log.e("length of String", String.valueOf(name.length()));


                                    for (int i = 0; i < name_list.size(); i++) {

                                        String s = name_list.get(i);
                                        Log.e("arraylist value", s);

                                        if (s.equals(check_type)) {

                                            counter = 1;
                                            Log.d("loop run", name_list.get(i));

                                        }

                                    }

                                    Log.e("length", String.valueOf(name_list.size()));


                                    Log.e("counter value", String.valueOf(counter));


                                    if (counter == 0) {


                                        Log.e("if check", "check");


                                        Log.d("length", String.valueOf(name_list.size()));

                                        name_list.add(name);
                                        myAdapter.add(name);

                                        Log.e("length", String.valueOf(name_list.size()));
                                        for (int j = 0; j < name_list.size(); j++) {

                                            Log.e("loop check for", "check");

                                            for (int k = (j + 1); k < name_list.size(); k++) {

                                                Log.e("loop check for", "check");
                                                String name1 = name_list.get(j);
                                                String name2 = name_list.get(k);

                                                if (name1.equals(name2)) {

                                                    Log.e("second if check", "check");
                                                    name_list.remove(k);
                                                    myAdapter.clear();
                                                    myAdapter.addAll(name_list);
                                                    dbCounter = 1;

                                                }


                                            }
                                        }

                                        Log.e("length", String.valueOf(name_list.size()));


                                    } else {

                                        dbCounter = 1;
                                    }

                                    Log.e("db counter", String.valueOf(dbCounter));

                                    if (dbCounter == 0) {

                                        Log.e("name list size" , String.valueOf(name_list.size()));
                                        Log.v("id value of db", String.valueOf(_id));
                                        background_task = new MyTask(getContext());
                                        background_task.execute(String.valueOf(_id), name, sex, spouse, culture, house, birth, death , image_link);
                                        _id++;
                                        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putInt("id_value", _id);
                                        editor.commit();


                                    }


                                    Intent i = new Intent(getContext(), Display_Activity.class);
                                    i.putExtra("name", name);
                                    i.putExtra("spouse", spouse);
                                    i.putExtra("house", house);
                                    i.putExtra("culture", culture);
                                    i.putExtra("dob", birth);
                                    i.putExtra("dod", death);
                                    i.putExtra("male", sex);
                                    i.putExtra("URL", image_link);
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(i);


                                    Log.d("call check", "Success");

                                } else {

                                    progressBar.setVisibility(View.GONE);

                                    Toast.makeText(getContext(), "No results found!",
                                            Toast.LENGTH_SHORT).show();


                                }

                            }

                            @Override
                            public void onFailure(Call<ModelResponse> call, Throwable t) {

                                Log.e("call check", t.toString());
                            }
                        });


                    }


                }


            }

                //end of button activity
            }
        });






        return view;
    }


    public class MyTask extends AsyncTask<String , Void ,Void>{

        public DatabaseClass myDb;
        public Context ctx;

        public MyTask(Context ctx){
            this.ctx = ctx;

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            myDb = new DatabaseClass(getContext());
        }

        @Override
        protected Void doInBackground(String... strings) {
            boolean insert_data = myDb.insertData(strings[0],
                    strings[1],
                    strings[2],
                    strings[3],
                    strings[4],
                    strings[5],
                    strings[6] ,
                    strings[7] ,
                    strings[8]

            );

            Log.e("store check" , String.valueOf(insert_data));

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


    public static String capitalize(@NonNull String input) {

        String[] words = input.toLowerCase().split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            if (i > 0 && word.length() > 0) {
                builder.append(" ");
            }

            String cap = word.substring(0, 1).toUpperCase() + word.substring(1);
            builder.append(cap);
        }
        return builder.toString();
    }

    public void retrive_all(){

        Cursor res = db.getAllData();

        if(res.getCount() == 0) {


            return;
        }



        while(res.moveToNext()){


               name_list.add(res.getString(1));


        }




    }
}



