package com.williamsumitromytextview.qurwateam.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.model.entity.ChatMessage;

import java.util.List;

/**
 * Created by USER on 10/06/2017.
 */

public class ChatArrayAdapter extends RecyclerView.Adapter<ChatArrayAdapter.EventHolder> {
    List<ChatMessage> messagelist;
    private LayoutInflater inflater;
    Context context;
    public interface itemClickCallBack{
        void onItemClick(int p);
    }

    private ChatArrayAdapter.itemClickCallBack itemClickCallBack;

    public ChatArrayAdapter(List<ChatMessage> messagelist, Context c){
        this.messagelist = messagelist;
        this.inflater = LayoutInflater.from(c);
    }
    public void setitemclickcallback(final ChatArrayAdapter.itemClickCallBack itemclickcallback) {
        this.itemClickCallBack = itemclickcallback;
    }

    @Override
    public ChatArrayAdapter.EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.right, parent, false);
        context = parent.getContext();
        return new ChatArrayAdapter.EventHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatArrayAdapter.EventHolder holder, int position) {
        ChatMessage chat = messagelist.get(position);
        holder.user_name.setText(chat.getEmail());
        holder.msgr.setText(chat.getMessage());
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView user_name, msgr;
        public EventHolder(View itemView) {
            super(itemView);
            user_name = (TextView) itemView.findViewById(R.id.user_name_chat);
            msgr = (TextView) itemView.findViewById(R.id.msgr_chat);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.cv_bookmark) {
                itemClickCallBack.onItemClick(getAdapterPosition());
            } else {

            }
        }
    }
}
