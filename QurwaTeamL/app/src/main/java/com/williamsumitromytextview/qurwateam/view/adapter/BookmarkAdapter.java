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

import java.util.List;

/**
 * Created by Adrian on 6/10/2017.
 */

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.EventHolder> {
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
    Context context;

    public interface itemClickCallBack{
        void onItemClick(int p);
    }
    private BookmarkAdapter.itemClickCallBack itemClickCallBack;

    public BookmarkAdapter(List<DaftarFranchise> franchiseList, Context c){
        this.franchiseList = franchiseList;
        this.inflater = LayoutInflater.from(c);
    }
    public void setitemclickcallback(final BookmarkAdapter.itemClickCallBack itemclickcallback) {
        this.itemClickCallBack = itemclickcallback;
    }


    @Override
    public BookmarkAdapter.EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_bookmark, parent, false);
        context = parent.getContext();
        return new BookmarkAdapter.EventHolder(view);
    }

    @Override
    public void onBindViewHolder(BookmarkAdapter.EventHolder holder, int position) {
        final DaftarFranchise franchisor = franchiseList.get(position);
        holder.nama_franchise.setText(franchisor.getNama_franchise());
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
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
                    }
                }
        );
        Picasso.with(context).load("file:///android_asset/"+franchiseList.get(position).getGambar_franchise()).into(holder.gambarbanner);


    }

    @Override
    public int getItemCount() {
        return franchiseList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nama_franchise;
        private ImageView gambarbanner;
        private View container;
        public EventHolder(View itemView) {
            super(itemView);
            nama_franchise = (TextView) itemView.findViewById(R.id.nama_franchise);
            gambarbanner = (ImageView) itemView.findViewById(R.id.bookmark_Picture);
            container = itemView.findViewById(R.id.cv_bookmark);
            container.setOnClickListener(this);
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
