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
import com.williamsumitromytextview.qurwateam.model.database.DatabaseHelper;
import com.williamsumitromytextview.qurwateam.model.entity.DaftarFranchise;
import com.williamsumitromytextview.qurwateam.view.activity.DetailFranchise;

import java.util.List;

/**
 * Created by william on 27/05/2017.
 */

public class HomeFranchiseListAdapter extends RecyclerView.Adapter<HomeFranchiseListAdapter.EventHolder>{
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

    private List<DaftarFranchise> franchiseList;

    private LayoutInflater inflater;
    private Context context;
    private DatabaseHelper db;

    public interface itemClickCallBack{
        void onItemClick(int p);
    }
    private itemClickCallBack itemClickCallBack;

    public HomeFranchiseListAdapter(List<DaftarFranchise> franchiseList, Context c){
        this.franchiseList = franchiseList;
        this.inflater = LayoutInflater.from(c);
    }
    public void setitemclickcallback(final itemClickCallBack itemclickcallback) {
        this.itemClickCallBack = itemclickcallback;
    }


    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_franchise, parent, false);
        context = parent.getContext();
        db = new DatabaseHelper(context);
        return new EventHolder(view,context);

    }




    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        final DaftarFranchise franchisor = franchiseList.get(position);
        final long hit = db.getSameDFinOutlet(String.valueOf(franchisor.getId()));

        holder.namafranchise.setText(franchisor.getNama_franchise());
        holder.namaptfranchise.setText(franchisor.getNama_pt_franchisor());
        holder.nfranchisee.setText(String.valueOf(hit));
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailFranchise.class);
                intent.putExtra(EXTRA_ID,franchisor.getId());
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
                context.startActivity(intent);
            }
        });
        Picasso.with(context).load("file:///android_asset/"+franchiseList.get(position).getGambar_franchise()).into(holder.gambarbanner);
    }

    @Override
    public int getItemCount() {
        return franchiseList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView namafranchise, namaptfranchise, nfranchisee;
        private ImageView gambarbanner;
        private View container;
        public EventHolder(View itemView,Context context) {
            super(itemView);
            namafranchise = (TextView) itemView.findViewById(R.id.homefranchiselistNamaFranchise);
            namaptfranchise = (TextView) itemView.findViewById(R.id.homefranchiselistNamaPerusahaanFranchise);
            nfranchisee = (TextView) itemView.findViewById(R.id.homefranchiselistjumlahFranchisee);
            gambarbanner = (ImageView) itemView.findViewById(R.id.homefranchiselistGambar);
            container = itemView.findViewById(R.id.cont_item_root);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.cont_item_root) {
                itemClickCallBack.onItemClick(getAdapterPosition());
            }
        }
    }
}
