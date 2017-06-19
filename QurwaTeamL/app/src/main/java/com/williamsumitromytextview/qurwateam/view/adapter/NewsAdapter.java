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
import com.williamsumitromytextview.qurwateam.model.entity.Berita;

import java.util.List;

/**
 * Created by william on 23/05/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<Berita> listnews;
    private LayoutInflater inflater;
    Context context;
    public interface itemClickCallBack{
        void onItemClick(int p);
    }
    private itemClickCallBack itemClickCallBack;

    public NewsAdapter(List<Berita> listnews, Context c){
        this.listnews = listnews;
        this.inflater = LayoutInflater.from(c);
    }
    public void setitemclickcallback(final itemClickCallBack itemClickCallBack){
        this.itemClickCallBack = itemClickCallBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_news, parent, false);
        context = parent.getContext();
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Berita news = listnews.get(position);
        holder.date.setText(news.getWaktu_berita());
        holder.title.setText(news.getJudul_berita());

        Picasso.with(context).load("file:///android_asset/"+listnews.get(position).getGambar_berita()).into(holder.pic);

    }

    @Override
    public int getItemCount() {
        return listnews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView date, title;
        private ImageView pic;
        private View container;
        public ViewHolder(View itemView, Context context) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.newsDate);
            title = (TextView) itemView.findViewById(R.id.newsTitle);
            pic = (ImageView) itemView.findViewById(R.id.newsPicture);
            container = itemView.findViewById(R.id.cv_news);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.cv_news){
                itemClickCallBack.onItemClick(getAdapterPosition());
            }
            else{

            }
        }
    }
}
