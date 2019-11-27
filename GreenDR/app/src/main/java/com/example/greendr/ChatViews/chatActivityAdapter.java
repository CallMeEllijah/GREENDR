package com.example.greendr.ChatViews;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.greendr.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class chatActivityAdapter extends RecyclerView.Adapter<chatActivityHolder> {

    private List<chatActivityObject> msgList;
    private Context context;

    public chatActivityAdapter(List<chatActivityObject> msgList, Context context){
        this.msgList = msgList;
        this.context = context;
    }


    @NonNull
    @Override
    public chatActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        chatActivityHolder yeet = new chatActivityHolder((layoutView));

        return yeet;
    }

    @Override
    public void onBindViewHolder(@NonNull chatActivityHolder holder, int position) {
        holder.nMessage.setText(msgList.get(position).getmMessage());
        if(msgList.get(position).getCurrentUser()){
            holder.nMessage.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.nMessage.setTextColor(Color.parseColor("#000000"));
            holder.container.setBackgroundColor(Color.parseColor("#90EE90"));
        } else{
            holder.nMessage.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.nMessage.setTextColor(Color.parseColor("#000000"));
            holder.container.setBackgroundColor(Color.parseColor("#E8E4C9"));
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
