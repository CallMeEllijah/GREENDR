package com.example.greendr.MatchViews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greendr.ChatViews.chatActivity;
import com.example.greendr.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class matchHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView matchname, matchid;
    public ImageView matchimg;

    public String userGender, matchGender;

    public matchHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        matchimg = (ImageView) itemView.findViewById(R.id.img);
        matchname = (TextView) itemView.findViewById(R.id.name);
        matchid = (TextView) itemView.findViewById(R.id.uid);

    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(view.getContext(), chatActivity.class);
        Bundle b = new Bundle();
        b.putString("matchId", matchid.getText().toString());
        b.putString("userSex", userGender);
        b.putString("matchSex", matchGender);
        intent.putExtras(b);
        view.getContext().startActivity(intent);

    }
}
