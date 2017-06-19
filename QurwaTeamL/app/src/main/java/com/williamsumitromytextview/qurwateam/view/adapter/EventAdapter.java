package com.williamsumitromytextview.qurwateam.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.model.entity.EventClass;

import java.util.List;

/**
 * Created by william on 25/05/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {
    private List<EventClass> listevent;
    private LayoutInflater inflater;
    Context context;
    private itemClickCallBack itemclickcallback;

    public EventAdapter(List<EventClass> listevent, Context c){
        this.inflater = LayoutInflater.from(c);
        this.listevent = listevent;
    }
    public void setitemclickcallback(final itemClickCallBack itemclickcallback) {
        this.itemclickcallback = itemclickcallback;
    }
    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_events, parent, false);
        context = parent.getContext();

        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        if(position%4==0){
            holder.itemView.setBackgroundResource(R.color.light_red);
        }
        else if(position%4==1){
            holder.itemView.setBackgroundResource(R.color.light_cyan);
        }
        else if(position%4==2){
            holder.itemView.setBackgroundResource(R.color.light_blue);
        }
        else {
            holder.itemView.setBackgroundResource(R.color.light_purple);
        }
        EventClass event = listevent.get(position);
        holder.judulevent.setText(event.getJudulEvent());

        Picasso.with(context).load("file:///android_asset/"+listevent.get(position).getGambarEvent()).into(holder.gambarevent);
        holder.alamatevent.setText(event.getALAMATEVENT());
        holder.tanggalevent.setText(event.getWAKTUEVENT());
    }

    @Override
    public int getItemCount() {
        return listevent.size();
    }
    public interface itemClickCallBack {
        void onItemClick(int p);
    }

    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView judulevent, alamatevent, tanggalevent;
        private ImageView gambarevent;
        private View container;

        public EventHolder(View itemView) {
            super(itemView);

            judulevent = (TextView) itemView.findViewById(R.id.Event_Judul);
            alamatevent = (TextView) itemView.findViewById(R.id.Event_Alamat);
            tanggalevent = (TextView) itemView.findViewById(R.id.Event_Jadwal);
            gambarevent = (ImageView) itemView.findViewById(R.id.Event_Gambar);
            container = itemView.findViewById(R.id.cv_items);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.cv_items){
                itemclickcallback.onItemClick(getAdapterPosition());
            } else {

            }
        }
    }
}
