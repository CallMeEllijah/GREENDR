package com.example.greendr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.greendr.CardViews.cardAdapter;
import com.example.greendr.CardViews.cardObject;
import com.example.greendr.MatchViews.matchActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private cardObject carddb[];
    private cardAdapter cardAdapter;
    private FirebaseAuth auth;
    private String userID;

    private String userSex = "", otherSex = "";

    private DatabaseReference dbUser;

    List<cardObject> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbUser = FirebaseDatabase.getInstance().getReference().child("Users");

        auth = FirebaseAuth.getInstance();
        userID = auth.getUid();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference male = FirebaseDatabase.getInstance().getReference().child("Users").child("male");
        //slight same sa ibaba
        male.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey().equals(user.getUid())){
                    userSex = "male";
                    otherSex = "female";
                    DatabaseReference otha = FirebaseDatabase.getInstance().getReference().child("Users").child(otherSex);
                    otha.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            if(dataSnapshot.exists() && !dataSnapshot.child("connections").child("pass").hasChild(userID) && !dataSnapshot.child("connections").child("smash").hasChild(userID)){
                                cardObject item = new cardObject(dataSnapshot.getKey(), dataSnapshot.child("name").getValue().toString(), dataSnapshot.child("age").getValue().toString(), dataSnapshot.child("imgUrl").getValue().toString());
                                items.add(item);
                                cardAdapter.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        //slight same sa taas
        DatabaseReference female = FirebaseDatabase.getInstance().getReference().child("Users").child("female");
        female.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey().equals(user.getUid())){
                    userSex = "female";
                    otherSex = "male";
                    DatabaseReference otha = FirebaseDatabase.getInstance().getReference().child("Users").child(otherSex);
                    otha.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            if(dataSnapshot.exists() && !dataSnapshot.child("connections").child("pass").hasChild(userID) && !dataSnapshot.child("connections").child("smash").hasChild(userID)){
                                cardObject item = new cardObject(dataSnapshot.getKey(), dataSnapshot.child("name").getValue().toString(), dataSnapshot.child("age").getValue().toString(), dataSnapshot.child("imgUrl").getValue().toString());
                                items.add(item);
                                cardAdapter.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        items = new ArrayList<cardObject>();

        cardAdapter = new cardAdapter(this, R.layout.card_item, items);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(cardAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                items.remove(0);
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {

                cardObject itm = (cardObject) dataObject;

                dbUser.child(otherSex).child(itm.getUserID()).child("connections").child("pass").child(userID).setValue(true);
                Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                cardObject itm = (cardObject) dataObject;
                String id = itm.getUserID();

                dbUser.child(otherSex).child(itm.getUserID()).child("connections").child("smash").child(userID).setValue(true);
                Toast.makeText(MainActivity.this, "Right", Toast.LENGTH_SHORT).show();

                if(dbUser.child(otherSex).child(itm.getUserID()).child("connections").child("smash").child(userID).getKey() == userID){
                    connectMatch(id);
                }
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

                Toast.makeText(MainActivity.this, "Putapeks wala nang match", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onScroll(float scrollProgressPercent) { }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void connectMatch(String id) {
        //if bot wants to smash each other, they become connected and put under "matches" under connections

        DatabaseReference userRef = dbUser.child(userSex).child(userID).child("connections").child("smash").child(id);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    //this basically initializes the thread for the current user and match user and their convo
                    final String key = FirebaseDatabase.getInstance().getReference("matchActivity").push().getKey();
                    final String othermatch = dataSnapshot.getKey();

                    DatabaseReference checkMatch = dbUser.child(userSex).child(userID).child("connections").child("smash");

                    Log.i("keys", userID);
                    Log.i("keys", othermatch);

                    checkMatch.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(othermatch)){
                                dbUser.child(otherSex).child(othermatch).child("connections").child("matches").child(userID).child("chatID").setValue(key);
                                dbUser.child(userSex).child(userID).child("connections").child("matches").child(othermatch).child("chatID").setValue(key);
                                FirebaseDatabase.getInstance().getReference("matchActivity").child(key);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    //buttons
    public void toSettings(View view){
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.putExtra("userSex", userSex);
        startActivity(intent);
        return;
    }
    public void toChat(View view){
        Intent intent = new Intent(MainActivity.this, matchActivity.class);
        intent.putExtra("userSex", userSex);
        intent.putExtra("otherSex", otherSex);
        startActivity(intent);
        return;
    }
    public void logout(View view){
        auth.signOut();
        Intent intent = new Intent(MainActivity.this, startupScreen.class);
        startActivity(intent);
        finish();
        return;
    }

}