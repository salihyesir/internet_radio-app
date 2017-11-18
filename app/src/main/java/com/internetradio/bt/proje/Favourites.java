package com.internetradio.bt.proje;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Favourites extends AppCompatActivity {

    public Button button_pop, button_kategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        //Kategoriler button
        button_kategori=(Button)findViewById(R.id.Favkategorilerbutton);

        button_kategori.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent kategoriIntent=new Intent(Favourites.this,Category.class);
                startActivity(kategoriIntent);
            }
        });


        //Populer buton
        button_pop=(Button)findViewById(R.id.Favpopulerbutton);

        button_pop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent popIntent=new Intent(Favourites.this,MainActivity.class);
                startActivity(popIntent);
            }
        });

    }
}
