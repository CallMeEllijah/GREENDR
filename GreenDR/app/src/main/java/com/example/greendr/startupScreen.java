package com.example.greendr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class startupScreen extends AppCompatActivity {

    private Button bLogin, bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_screen);

        //finding the buttons
        bLogin = (Button) findViewById(R.id.login);
        bRegister = (Button) findViewById(R.id.register);

        //on click listener to go to login view
        bLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(startupScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }

        });

        //login listener to go to register view
        bRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(startupScreen.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                return;
            }

        });

    }
}
