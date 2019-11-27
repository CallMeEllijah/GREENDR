package com.example.greendr.ChatViews;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.greendr.R;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class chatActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView nMessage;
    public LinearLayout container;

    public chatActivityHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        nMessage = (TextView) itemView.findViewById(R.id.sentMessage);
        container = (LinearLayout) itemView.findViewById(R.id.messageContainer);

    }

    @Override
    public void onClick(View view) { }
}
