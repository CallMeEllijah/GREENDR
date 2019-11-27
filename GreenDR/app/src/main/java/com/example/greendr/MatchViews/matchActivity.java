package com.example.greendr.MatchViews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.greendr.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class matchActivity extends AppCompatActivity {

    private RecyclerView recycleView;
    private RecyclerView.Adapter matchesAdapter;
    private RecyclerView.LayoutManager lm;

    private String Uid;
    private String userSex, matchSex;
    private ArrayList<matchObject> resultMatches = new ArrayList<matchObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        //gender
        userSex = getIntent().getStringExtra("userSex");
        matchSex = getIntent().getStringExtra("otherSex");

        //uid
        Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //recyclerview related
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        recycleView.setNestedScrollingEnabled(false);
        recycleView.setHasFixedSize(true);

        lm = new LinearLayoutManager(matchActivity.this);

        recycleView.setLayoutManager(lm);

        matchesAdapter = new matchAdapter(getDataSet(), matchActivity.this);

        recycleView.setAdapter(matchesAdapter);

        getUserID(userSex);

    }

    private List<matchObject> getDataSet(){
        return resultMatches;
    }

    private void getUserID(String userSex){
        DatabaseReference matchDB = FirebaseDatabase.getInstance().getReference().child("Users").child(userSex).child(Uid).child("connections").child("matches");
        matchDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot match : dataSnapshot.getChildren()){
                        getMatchInfo(match.getKey());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void getMatchInfo(String key) {
        DatabaseReference matchDB = FirebaseDatabase.getInstance().getReference().child("Users").child(matchSex).child(key);
        matchDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String userid = dataSnapshot.getKey();
                    String name = dataSnapshot.child("name").getValue().toString();
                    String url = dataSnapshot.child("imgUrl").getValue().toString();

                    matchObject tempObject = new matchObject(userid, name, url, userSex, matchSex);
                    resultMatches.add(tempObject);
                    matchesAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void back(View view){
        finish();
        return;
    }

}
