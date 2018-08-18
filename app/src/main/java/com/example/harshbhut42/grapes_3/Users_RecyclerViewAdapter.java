package com.example.harshbhut42.grapes_3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Users_RecyclerViewAdapter extends RecyclerView.Adapter<Users_RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> mUserName = new ArrayList<>();
    private ArrayList<String>  mProfileImage = new ArrayList<>();
    private Context mContext;


    public Users_RecyclerViewAdapter(Context mContext, ArrayList<String> mUserName, ArrayList<String> mProfileImage) {
        this.mUserName = mUserName;
        this.mProfileImage = mProfileImage;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singal_user_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Picasso.get().load(mProfileImage.get(position)).into(holder.image);
       // Glide.with(mContext).asBitmap().load(mProfileImage.get(position)).into(holder.image);

        holder.userName.setText(mUserName.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,mUserName.get(position),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUserName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        CircleImageView image;
        TextView userName;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.user_profil_pic);
            userName = itemView.findViewById(R.id.user_name);
            parentLayout = itemView.findViewById(R.id.singal_user_layout);
        }
    }

}
