package com.internetradio.bt.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.internetradio.bt.proje.CustomAdapterMessage;
import com.internetradio.bt.proje.MainActivity;
import com.internetradio.bt.proje.Message;
import com.internetradio.bt.proje.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Salih on 15.12.2017.
 */

public class ChatFragment extends Fragment {




    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private FirebaseUser fUser;
    private ArrayList<Message> chatLists = new ArrayList<>();
    private CustomAdapterMessage customAdapterMessage;
    private String subject;
    private static String position;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private EditText inputChat;

    private static View rootView;


    public ChatFragment(){

    }

    @Override
    public void onResume() {
        super.onResume();

        if(getView()== null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().finish();
                    getActivity().startActivity(intent);
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_chat, container, false);

        listView = (ListView)rootView.findViewById(R.id.chatListView);
        inputChat = (EditText)rootView.findViewById(R.id.inputChat);
        floatingActionButton = (FloatingActionButton)rootView.findViewById(R.id.fab);

        db = FirebaseDatabase.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();


        customAdapterMessage = new CustomAdapterMessage(getActivity().getApplicationContext(),chatLists,fUser);
        listView.setAdapter(customAdapterMessage);

       if(position !=null) {
           subject = position;
           dbRef = db.getReference("ChatSubjects/" + subject + "/mesaj");
           getActivity().setTitle(subject);
       }


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatLists.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Message message = ds.getValue(Message.class);
                    chatLists.add(message);
                    //Log.d("VALUE",ds.getValue(Message.class).getMesajText());
                }
                customAdapterMessage.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//Mesajda 6 karakter sınırı
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inputChat.getText().length()>=6){

                    long msTime = System.currentTimeMillis();
                    Date curDateTime = new Date(msTime);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd'/'MM'/'y hh:mm");
                    String dateTime = formatter.format(curDateTime);
                    Message message = new Message(inputChat.getText().toString(),fUser.getEmail(),dateTime);
                    dbRef.push().setValue(message);
                    inputChat.setText("");

                }else{

                    Toast.makeText(getActivity().getApplicationContext(),"Gönderilecek mesaj uzunluğu en az 6 karakter olmalıdır!",Toast.LENGTH_SHORT).show();
                }


            }
        });
        return rootView;
    }
    public void setSubject(String pos){
        position = pos;
    }

}