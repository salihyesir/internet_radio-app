package com.internetradio.bt.proje;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Category extends AppCompatActivity {

    public Button button_pop, button_fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Populer button
        button_pop=(Button)findViewById(R.id.categorypopulerbutton);

        button_pop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent popIntent=new Intent(Category.this,MainActivity.class);
                startActivity(popIntent);
            }
        });

        // Favoriler button
        button_fav=(Button)findViewById(R.id.categoryfavorilerbutton);

        button_fav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent favIntent=new Intent(Category.this,Favourites.class);
                startActivity(favIntent);
            }
        });

    }
}
