package com.example.greendr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Button bRegister;
    private EditText eMail, ePass, eName, age;
    private RadioGroup rGroup;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        bRegister = (Button) findViewById(R.id.reg);
        eMail = (EditText) findViewById(R.id.tEmail);
        ePass = (EditText) findViewById(R.id.tPassword);
        eName = (EditText) findViewById(R.id.name);
        rGroup = (RadioGroup) findViewById(R.id.rgroup);
        age = (EditText) findViewById(R.id.tAge);

        bRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                int id = rGroup.getCheckedRadioButtonId();
                final RadioButton rd = (RadioButton) findViewById(id);
                final String email = eMail.getText().toString();
                final String password = ePass.getText().toString();
                final String userSex = rd.getText().toString();
                final String name = eName.getText().toString();
                final String dage = age.getText().toString();

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "success account creation", Toast.LENGTH_SHORT).show();
                            auth.signInWithEmailAndPassword(email,password);

                            //setting of the input values
                            String userId = auth.getCurrentUser().getUid();
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(userSex);
                            db.child(userId).child("name").setValue(name);
                            db.child(userId).child("age").setValue(dage);
                            //setting initial value of image
                            Map inf = new HashMap<>();
                            inf.put("imgUrl", "https://firebasestorage.googleapis.com/v0/b/greendr-e6125.appspot.com/o/avatar-default.png?alt=media&token=154281e6-556d-4112-b8a5-60e1e8cdb776");
                            FirebaseDatabase.getInstance().getReference().child("Users").child(userSex).child(userId).updateChildren(inf);

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
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
        Intent intent = new Intent(RegisterActivity.this, startupScreen.class);
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
