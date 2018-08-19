package com.example.harshbhut42.grapes_3;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class Chat_RecyclerViewAdapter extends RecyclerView.Adapter<Chat_RecyclerViewAdapter.ViewHolder> {


    private List<Message> messages ;

    public Chat_RecyclerViewAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public Chat_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singal_chat,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull Chat_RecyclerViewAdapter.ViewHolder holder, int position) {



       holder.chat.setText(messages.get(position).getMessage());

        if(messages.get(position).getSend_by().equals("0"))
        {
            holder.chat.setBackgroundColor(Color.MAGENTA);
            holder.chat.setTextColor(Color.WHITE);

        }
        else if(messages.get(position).getSend_by().equals("-1"))
        {
            holder.chat.setBackgroundColor(Color.WHITE);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder{


       public TextView chat;

        public ViewHolder(View itemView) {
            super(itemView);

            chat = (TextView) itemView.findViewById(R.id.singal_chat_message);

        }
    }
}
