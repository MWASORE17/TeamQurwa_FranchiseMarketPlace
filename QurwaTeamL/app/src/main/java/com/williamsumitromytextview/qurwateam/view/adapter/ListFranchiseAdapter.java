package com.williamsumitromytextview.qurwateam.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.model.entity.DaftarFranchise;
import com.williamsumitromytextview.qurwateam.view.activity.DetailFranchise;

import java.util.ArrayList;

/**
 * Created by william on 28/05/2017.
 */

public class ListFranchiseAdapter extends RecyclerView.Adapter<ListFranchiseAdapter.EventHolder> {
    private static final String EXTRA_ID = "EXTRA_ID";
    private static final String EXTRA_NAMA = "EXTRA_NAMA";
    private static final String EXTRA_KETERANGAN = "EXTRA_KETERANGAN";
    private static final String EXTRA_JENIS = "EXTRA_JENIS";
    private static final String EXTRA_KATEGORI = "EXTRA_KATEGORI";
    private static final String EXTRA_BERDIRI = "EXTRA_BERDIRI";
    private static final String EXTRA_INVESTASI = "EXTRA_INVESTASI";
    private static final String EXTRA_WEBSITE= "EXTRA_WEBSITE";
    private static final String EXTRA_ALAMAT = "EXTRA_ALAMAT";
    private static final String EXTRA_LOKASI = "EXTRA_LOKASI";
    private static final String EXTRA_TELEPON = "EXTRA_TELEPON";
    private static final String EXTRA_EMAIL = "EXTRA_EMAIL";
    private static final String EXTRA_GAMBAR = "EXTRA_GAMBAR";

    private ArrayList<DaftarFranchise> franchisorsList;
    private LayoutInflater inflater;
    Context context;

    public interface itemisclick{
        void onitemclicked(int p);
    }
    private itemisclick itemisclick;
    public void setItemisclick(final itemisclick itemisclick){
        this.itemisclick = itemisclick;
    }
    public ListFranchiseAdapter(ArrayList<DaftarFranchise> franchisorsList, Context c){
        this.franchisorsList = franchisorsList;
        this.inflater = LayoutInflater.from(c);
    }
    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_listfranchise, parent,false);
        context = parent.getContext();
        return new ListFranchiseAdapter.EventHolder(view);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        final DaftarFranchise franchisor = franchisorsList.get(position);
        holder.nama.setText(franchisor.getNama_franchise());
        holder.kategori.setText(franchisor.getKategori());
        Picasso.with(context).load("file:///android_asset/"+franchisorsList.get(position).getGambar_franchise()).into(holder.gambar);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailFranchise.class);
                intent.putExtra(EXTRA_NAMA,franchisor.getNama_franchise());
                intent.putExtra(EXTRA_KETERANGAN,franchisor.getKeterangan());
                intent.putExtra(EXTRA_JENIS,franchisor.getJenis());
                intent.putExtra(EXTRA_KATEGORI,franchisor.getKategori());
                intent.putExtra(EXTRA_BERDIRI,franchisor.getBerdiri_sejak());
                intent.putExtra(EXTRA_INVESTASI,franchisor.getInvestasi());
                intent.putExtra(EXTRA_WEBSITE,franchisor.getWebsite());
                intent.putExtra(EXTRA_ALAMAT,franchisor.getAlamat());
                intent.putExtra(EXTRA_LOKASI,franchisor.getLokasi());
                intent.putExtra(EXTRA_TELEPON,franchisor.getTelepon());
                intent.putExtra(EXTRA_EMAIL,franchisor.getEmail());
                intent.putExtra(EXTRA_GAMBAR,franchisor.getGambar_franchise());
                intent.putExtra(EXTRA_ID,franchisor.getId());
                context.startActivity(intent);
//
            }
        });
    }

    @Override
    public int getItemCount() {
        return franchisorsList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nama, kategori;
        private ImageView gambar;
        private View container;
        public EventHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.itemlist_name);
            kategori = (TextView) itemView.findViewById(R.id.itemlist_category);
            gambar = (ImageView) itemView.findViewById(R.id.itemlist_image);
            container = itemView.findViewById(R.id.container_itemlistfranchise);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.container_itemlistfranchise){
                itemisclick.onitemclicked((getAdapterPosition()));
            }
        }


    }
    //Filter
    public void setFilter(ArrayList<DaftarFranchise> newdaftarFranchise){

        this.franchisorsList = new ArrayList<>();
        this.franchisorsList.addAll(newdaftarFranchise);
        notifyDataSetChanged();
    }

}
