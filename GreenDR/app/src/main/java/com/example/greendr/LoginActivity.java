package com.example.greendr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private EditText mail, pass;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        auth = FirebaseAuth.getInstance();

        login = (Button) findViewById(R.id.login);
        mail = (EditText) findViewById(R.id.tmail);
        pass = (EditText) findViewById(R.id.tpass);

        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                final String email = mail.getText().toString();
                final String password = pass.getText().toString();

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                });

            }

        });

    }

    public void back(View view){
        Intent intent = new Intent(LoginActivity.this, startupScreen.class);
        startActivity(intent);
        finish();
        return;
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.removeAuthStateListener(authListener);
    }
}
