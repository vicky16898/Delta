package com.example.vicky.imagehide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomArrayAdapter  extends ArrayAdapter<User_Stored_details> {

    private Context context = null;
    private ArrayList<User_Stored_details> value = null;
    private User_Stored_details subject;
    private SparseBooleanArray mSelectedItemsIds;


    public CustomArrayAdapter(Context context , ArrayList<User_Stored_details> value){

        super(context,-1,value);
        this.context = context;
        mSelectedItemsIds = new SparseBooleanArray();
        this.value = value;



    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        subject = value.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_layout , parent , false);

        TextView user_title = (TextView)view.findViewById(R.id.user_title);
        ImageView hidden_image = (ImageView)view.findViewById(R.id.hidden_image);
        TextView date = (TextView)view.findViewById(R.id.date);



        user_title.setText(subject.title);
        date.setText(subject.stored_info);












        return view;
    }
    @Override
    public void remove(User_Stored_details object) {
        value.remove(object);
        notifyDataSetChanged();
    }


    public ArrayList<User_Stored_details> getWorldPopulation() {
        return value;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));


    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}
