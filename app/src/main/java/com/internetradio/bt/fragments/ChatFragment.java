package com.internetradio.bt.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.internetradio.bt.proje.HomeActivity;
import com.internetradio.bt.proje.R;
import com.internetradio.bt.proje.RegisterActivity;

/**
 * Created by Salih on 9.12.2017.
 */

public class ChatFragment extends Fragment {

    private EditText editTextUserName;
    private EditText editTextUserPassword;
    private Button buttonLogin;
    private TextView txtRegister;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private String userName;
    private String userPassword;

    private static View rootView;
    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_three, container, false);

        editTextUserName = (EditText) rootView.findViewById(R.id.editTextUserName);
        editTextUserPassword = (EditText) rootView.findViewById(R.id.editTextUserPassword);
        buttonLogin = (Button) rootView.findViewById(R.id.buttonLogin);
        txtRegister = (TextView) rootView.findViewById(R.id.txtRegister);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser(); // authenticate olan kullaniciyi aliyoruz eger var ise

        if (firebaseUser != null) {

            Intent i = new Intent(getActivity(), HomeActivity.class);
            startActivity(i);
            getActivity().finish();
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = editTextUserName.getText().toString();
                userPassword = editTextUserPassword.getText().toString();
                if (userName.isEmpty() || userPassword.isEmpty()) {

                    Toast.makeText(getContext().getApplicationContext(), "Lütfen gerekli alanları doldurunuz!", Toast.LENGTH_SHORT).show();

                } else {

                    login();
                }
            }
        });


        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        return  rootView;

    }
    private void login() {

        mAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(getActivity(),
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Intent i = new Intent(getActivity(),HomeActivity.class);
                            startActivity(i);
                            getActivity().finish();

                        }
                        else{
                            // hata
                            Toast.makeText(getContext().getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

}