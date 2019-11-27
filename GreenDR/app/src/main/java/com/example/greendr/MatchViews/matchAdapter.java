package com.example.greendr.MatchViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.greendr.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class matchAdapter extends RecyclerView.Adapter<matchHolder> {

    private Context context;
    private List<matchObject> matchList;

    public matchAdapter(List<matchObject> matchList, Context context){
        this.context = context;
        this.matchList = matchList;
    }

    @NonNull
    @Override
    public matchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item, null,false);
        lView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        matchHolder yeet = new matchHolder(lView);

        return yeet;
    }

    @Override
    public void onBindViewHolder(@NonNull matchHolder holder, int position) {
        holder.userGender = matchList.get(position).getUserSex();
        holder.matchGender = matchList.get(position).getMatchSex();
        Glide.with(context).load(matchList.get(position).getUrl()).into(holder.matchimg);
        holder.matchname.setText(matchList.get(position).getName());
        holder.matchid.setText(matchList.get(position).getUserID());
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }
}
