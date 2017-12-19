package com.internetradio.bt.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.internetradio.bt.proje.R;

/**
 * Created by Salih on 15.12.2017.
 */

public class RegisterFragment extends Fragment {

    private EditText registerUserName;
    private EditText registerPassword;
    private Button buttonRegister;
    private FirebaseAuth mAuth;
    private String userName;
    private String userPassword;

    private static View rootView;



    public RegisterFragment() {
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
        rootView = inflater.inflate(R.layout.activity_register, container, false);



        registerUserName = (EditText)rootView.findViewById(R.id.registerUserName);
        registerPassword = (EditText)rootView.findViewById(R.id.registerPassword);
        buttonRegister = (Button) rootView.findViewById(R.id.buttonRegister);

        mAuth = FirebaseAuth.getInstance();

        // register buton tiklaninca
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = registerUserName.getText().toString();
                userPassword = registerPassword.getText().toString();
                if(userName.isEmpty() || userPassword.isEmpty()){

                    Toast.makeText(getContext().getApplicationContext(),"Lütfen gerekli alanları doldurunuz!",Toast.LENGTH_SHORT).show();

                }else{

                    register();
                }

            }
        });

        return rootView;
    }
    private void register() {

        mAuth.createUserWithEmailAndPassword(userName,userPassword)
                .addOnCompleteListener((Activity) getActivity(), new OnCompleteListener<AuthResult>() {

            //Giriş var ise homefragmenta yönlensin
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            HomeFragment homeFragment = new HomeFragment();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.activity_register, homeFragment);
                            transaction.addToBackStack(null);
                            // işlerimizi bitirelim
                            transaction.commit();

                        }
                        else{
                            Toast.makeText(getContext().getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }


                });

    }

}