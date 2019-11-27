package com.example.greendr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText name, bio;
    private ImageView dpImg;

    private FirebaseAuth auth;
    private DatabaseReference userDB;

    private String userID, localname, localbio, imgurl;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //getting of mobile layout id
        name = (EditText) findViewById(R.id.name);
        bio = (EditText) findViewById(R.id.bio);
        dpImg = (ImageView) findViewById(R.id.dpImg);

        //setting of local variables
        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();
        String userSex = getIntent().getExtras().getString("sex");
        userDB = FirebaseDatabase.getInstance().getReference().child("Users").child(userSex).child(userID);

        //getInfo will get everything else na data and set them into the mobile app
        getInfo();getInfo();

        dpImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
    }

    //onClicks
    public void back(View view){
        finish();
        return;
    }
    public void save(View view){
        saveInfo();
    }

    //gets the URI if upload image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imgUri = data.getData();
            uri = imgUri;
            dpImg.setImageURI(uri);
        }
    }

    //updates the view
    public void getInfo(){
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("name") != null){
                        localname = map.get("name").toString();
                        name.setText(map.get("name").toString());
                    }

                    if(map.get("bio") != null){
                        localbio = map.get("bio").toString();
                        bio.setText(map.get("bio").toString());
                    }

                    if(map.get("imgUrl") != null){
                        imgurl = map.get("imgUrl").toString();
                        Glide.with(getApplication()).load(imgurl).into(dpImg);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    //sae the info to the view
    public void saveInfo(){
        localname = name.getText().toString();
        localbio = bio.getText().toString();

        Map info = new HashMap();
        info.put("name", localname);
        info.put("bio", localbio);
        userDB.updateChildren(info);

        final StorageReference path = FirebaseStorage.getInstance().getReference().child("dpimg").child(userID);
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, temp);
        byte[] data = temp.toByteArray();
        UploadTask uploadTask = path.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e){
                finish();
            }
        });

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Map inf = new HashMap();
                        inf.put("imgUrl", uri.toString());
                        userDB.updateChildren(inf);
                        finish();
                        return;
                    }
                });
            }
        });
    }
}
