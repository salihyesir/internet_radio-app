package com.internetradio.bt.proje;

/**
 * Created by Salih on 13.12.2017.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by alper on 16/07/2017.
 */

public class CustomAdapterMessage extends ArrayAdapter<Message> {

    private FirebaseUser firebaseUser;

    public CustomAdapterMessage(@NonNull  Context context, ArrayList<Message> chatList, FirebaseUser firebaseUser) {
        super(context, 0, chatList);
        this.firebaseUser = firebaseUser;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message message = getItem(position);
        if (firebaseUser.getEmail().equalsIgnoreCase(message.getGonderici())){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.right_item_layout, parent, false);

            TextView txtUser = (TextView) convertView.findViewById(R.id.txtUserRight);
            TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessageRight);
            TextView txtTime = (TextView) convertView.findViewById(R.id.txtTimeRight);

            txtUser.setText(message.getGonderici());
            txtMessage.setText(message.getMesajText());
            txtTime.setText(message.getZaman());

        }else{

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.left_item_layout, parent, false);

            TextView txtUser = (TextView) convertView.findViewById(R.id.txtUserLeft);
            TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessageLeft);
            TextView txtTime = (TextView) convertView.findViewById(R.id.txtTimeLeft);

            txtUser.setText(message.getGonderici());
            txtMessage.setText(message.getMesajText());
            txtTime.setText(message.getZaman());

        }

        return convertView;
    }
}