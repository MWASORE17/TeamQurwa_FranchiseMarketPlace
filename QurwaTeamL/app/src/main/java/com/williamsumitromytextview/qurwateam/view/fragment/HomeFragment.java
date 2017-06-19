package com.williamsumitromytextview.qurwateam.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.model.database.DatabaseHelper;
import com.williamsumitromytextview.qurwateam.model.entity.DaftarFranchise;
import com.williamsumitromytextview.qurwateam.view.activity.DetailFranchise;
import com.williamsumitromytextview.qurwateam.view.adapter.HomeBestFranchiseAdapter;
import com.williamsumitromytextview.qurwateam.view.adapter.HomeFranchiseListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeFranchiseListAdapter.itemClickCallBack, HomeBestFranchiseAdapter.itemisclick {
    @BindView(R.id.rv_bestfranchise) RecyclerView rvbestfranchise;
    private HomeBestFranchiseAdapter homeBestFranchiseAdapter;
    private ArrayList listhomedata;
    private ArrayList listbestdata;
    private HomeFranchiseListAdapter homeFranchiseListAdapter;
    @BindView(R.id.rv_homefranchise) RecyclerView rvfranchiselist;
    private DatabaseHelper databaseHelper;
    private Context context;

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
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

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        context= this.getParentFragment().getContext();
        initObject();


        return view;
    }

    //HOME FRANCHISE
    @Override
    public void onItemClick(int p) {
        DaftarFranchise daftarfranchise = (DaftarFranchise) listhomedata.get(p);

        Intent intent = new Intent(getActivity(), DetailFranchise.class);

        Bundle extras = new Bundle();
        extras.putString(EXTRA_ID,String.valueOf(daftarfranchise.getId()));
        extras.putString(EXTRA_NAMA,daftarfranchise.getNama_franchise());
        extras.putString(EXTRA_KETERANGAN,daftarfranchise.getKeterangan());
        extras.putString(EXTRA_JENIS,daftarfranchise.getJenis());
        extras.putString(EXTRA_KATEGORI,daftarfranchise.getKategori());
        extras.putString(EXTRA_BERDIRI,daftarfranchise.getBerdiri_sejak());
        extras.putString(EXTRA_INVESTASI,daftarfranchise.getInvestasi()+"");
        extras.putString(EXTRA_WEBSITE,daftarfranchise.getWebsite());
        extras.putString(EXTRA_ALAMAT,daftarfranchise.getAlamat());
        extras.putString(EXTRA_LOKASI,daftarfranchise.getLokasi());
        extras.putString(EXTRA_TELEPON,daftarfranchise.getTelepon());
        extras.putString(EXTRA_EMAIL,daftarfranchise.getEmail());
        extras.putString(EXTRA_GAMBAR,daftarfranchise.getGambar_franchise());
        intent.putExtra(BUNDLE_EXTRAS,extras);
        startActivity(intent);
    }


    //BEST FRANCHISE
    @Override
    public void onitemclicked(int p) {
        DaftarFranchise daftarfranchise = (DaftarFranchise) listbestdata.get(p);
        Intent intent = new Intent(getActivity(), DetailFranchise.class);

        Bundle extras = new Bundle();
        extras.putString(EXTRA_ID,String.valueOf(daftarfranchise.getId()));
        extras.putString(EXTRA_NAMA,daftarfranchise.getNama_franchise());
        extras.putString(EXTRA_KETERANGAN,daftarfranchise.getKeterangan());
        extras.putString(EXTRA_JENIS,daftarfranchise.getJenis());
        extras.putString(EXTRA_KATEGORI,daftarfranchise.getKategori());
        extras.putString(EXTRA_BERDIRI,daftarfranchise.getBerdiri_sejak());
        extras.putString(EXTRA_INVESTASI,daftarfranchise.getInvestasi()+"");
        extras.putString(EXTRA_WEBSITE,daftarfranchise.getWebsite());
        extras.putString(EXTRA_ALAMAT,daftarfranchise.getAlamat());
        extras.putString(EXTRA_LOKASI,daftarfranchise.getLokasi());
        extras.putString(EXTRA_TELEPON,daftarfranchise.getTelepon());
        extras.putString(EXTRA_EMAIL,daftarfranchise.getEmail());
        extras.putString(EXTRA_GAMBAR,daftarfranchise.getGambar_franchise());
        intent.putExtra(BUNDLE_EXTRAS,extras);

        startActivity(intent);
    }

    private void initObject(){
        listhomedata = new ArrayList<>();
        listbestdata = new ArrayList<>();

        rvbestfranchise.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvbestfranchise.setHasFixedSize(true);
        homeBestFranchiseAdapter = new HomeBestFranchiseAdapter(listbestdata, getContext());
        rvbestfranchise.setAdapter(homeBestFranchiseAdapter);
        homeBestFranchiseAdapter.setItemisclick(this);


        rvfranchiselist.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvfranchiselist.setHasFixedSize(true);
        homeFranchiseListAdapter = new HomeFranchiseListAdapter(listhomedata, getActivity());
        rvfranchiselist.setAdapter(homeFranchiseListAdapter);
        homeFranchiseListAdapter.setitemclickcallback(this);

        databaseHelper = new DatabaseHelper(this.getActivity());

        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listbestdata.clear();
                listbestdata.addAll(databaseHelper.getBestReviewFranchise());

                listhomedata.clear();
                listhomedata.addAll(databaseHelper.getHomeFranchise());


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                homeFranchiseListAdapter.notifyDataSetChanged();
                homeBestFranchiseAdapter.notifyDataSetChanged();
            }
        }.execute();

    }
}
