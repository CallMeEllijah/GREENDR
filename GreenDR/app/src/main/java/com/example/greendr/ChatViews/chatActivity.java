package com.example.greendr.ChatViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.greendr.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class chatActivity extends AppCompatActivity {

    private RecyclerView chtrecycleView;
    private RecyclerView.Adapter chatActivityAdapter;
    private RecyclerView.LayoutManager chatLM;
    private EditText sendText;
    private Button sendButton;
    private TextView kausapMatch;

    private String Uid, matchId, chatId;
    private String userSex, matchSex;


    DatabaseReference userDB, chatDB;

    private ArrayList<chatActivityObject> resultChat = new ArrayList<chatActivityObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        matchId = getIntent().getExtras().getString("matchId");

        userSex = getIntent().getStringExtra("userSex");
        matchSex = getIntent().getStringExtra("matchSex");
        kausapMatch = (TextView) findViewById(R.id.kausapName);

        DatabaseReference kuhaName = FirebaseDatabase.getInstance().getReference().child("Users").child(matchSex).child(matchId).child("name");
        kuhaName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kausapMatch.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userDB = FirebaseDatabase.getInstance().getReference().child("Users").child(userSex).child(Uid).child("connections").child("matches").child(matchId).child("chatID");
        chatDB = FirebaseDatabase.getInstance().getReference().child("Chats");

        getChatId();

        //all for population
        chtrecycleView = (RecyclerView) findViewById(R.id.chtrecycleView);
        chtrecycleView.setNestedScrollingEnabled(false);
        chtrecycleView.setHasFixedSize(false);

        chatLM = new LinearLayoutManager(chatActivity.this);
        chtrecycleView.setLayoutManager(chatLM);

        chatActivityAdapter = new chatActivityAdapter(getDataSetChat(), chatActivity.this);

        chtrecycleView.setAdapter(chatActivityAdapter);

        sendText = (EditText) findViewById(R.id.sendText);
        sendButton = (Button) findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

    }

    private void sendMessage() {
        String msgText = sendText.getText().toString();

        if(!msgText.isEmpty()){
            DatabaseReference newMsgDB = chatDB.child(chatId).push();
            Map newmsg = new HashMap();

            newmsg.put("createdByUser", Uid);
            newmsg.put("text", msgText);

            newMsgDB.setValue(newmsg);
        }
        sendText.setText(null);
    }

    private void getChatId(){
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    chatId = dataSnapshot.getValue().toString();
                    chatDB.child(chatId);
                    getChatMessages();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getChatMessages() {

        chatDB.child(chatId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    String messagee = dataSnapshot.child("text").getValue().toString();
                    String createdBy = dataSnapshot.child("createdByUser").getValue().toString();

                    if(messagee!=null && createdBy!=null){
                        if(createdBy.equals(Uid)){
                            chatActivityObject kMessage = new chatActivityObject(messagee, true);
                            resultChat.add(kMessage);
                            chatActivityAdapter.notifyDataSetChanged();
                        } else{
                            chatActivityObject kMessage = new chatActivityObject(messagee, false);
                            resultChat.add(kMessage);
                            chatActivityAdapter.notifyDataSetChanged();
                        }
                    }

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

    private List<chatActivityObject> getDataSetChat(){
        return resultChat;
    }

    public void back(View view){
        finish();
        return;
    }

}
