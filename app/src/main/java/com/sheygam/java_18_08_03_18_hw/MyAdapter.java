package com.sheygam.java_18_08_03_18_hw;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregorysheygam on 08/03/2018.
 */

public class MyAdapter extends BaseAdapter {
    private List<Contact> contacts;

    public MyAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_row,parent,false);
        }
        Contact current = contacts.get(position);
        TextView nameTxt = convertView.findViewById(R.id.name_txt);
        TextView emailTxt = convertView.findViewById(R.id.email_txt);
        nameTxt.setText(current.getName());
        emailTxt.setText(current.getEmail());
        return convertView;
    }
}
