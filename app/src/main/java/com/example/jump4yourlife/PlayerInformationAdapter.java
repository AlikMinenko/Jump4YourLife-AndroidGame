package com.example.jump4yourlife;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerInformationAdapter extends RecyclerView.Adapter<PlayerInformationAdapter.InformationViewHolder>
{

    private Context context; // the context of the activity
    private ArrayList<PlayerInformation> objects; // list of informations of players

    public PlayerInformationAdapter(Context context, ArrayList<PlayerInformation> objects)
    {
        this.context = context;
        this.objects = objects;
    }

    @Override
    public InformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_row, null);
        return new InformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InformationViewHolder holder, int position)
    {
            PlayerInformation playerinfo = objects.get(position);
            holder.playerplace.setText(String.valueOf(playerinfo.getPlace()));
            holder.playerbitmap.setImageBitmap(playerinfo.getBitmap());
            holder.playername.setText(playerinfo.getNickName());
            holder.playerheight.setText(String.valueOf(playerinfo.getHeight()));
    }

    @Override
    public int getItemCount()
    {
        return objects.size();
    }

    public class InformationViewHolder extends RecyclerView.ViewHolder
    {
        TextView playerplace, playername, playerheight;
        ImageView playerbitmap;

        public InformationViewHolder(View itemView)
        {
            super(itemView);
            playerplace =itemView.findViewById(R.id.placetblrow);
            playerbitmap = itemView.findViewById(R.id.ivtblrow);
            playername = itemView.findViewById(R.id.nametblrow);
            playerheight = itemView.findViewById(R.id.heighttblrow);
        }
    }
}
