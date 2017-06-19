package com.williamsumitromytextview.qurwateam.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.model.database.DatabaseHelper;
import com.williamsumitromytextview.qurwateam.model.entity.DaftarFranchise;
import com.williamsumitromytextview.qurwateam.view.activity.DetailFranchise;
import com.williamsumitromytextview.qurwateam.view.adapter.ListFranchiseAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarfranchiseFragment extends Fragment implements ListFranchiseAdapter.itemisclick{
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
    @BindView(R.id.franchiselist_rv) RecyclerView rv;
    private ArrayList<DaftarFranchise> listdata;
    private ListFranchiseAdapter adapter;
    private DatabaseHelper databaseHelper;

    Context context;

    public DaftarfranchiseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daftarfranchise, container, false);
        ButterKnife.bind(this,view);
        initObject();

        return view;
    }

    @Override
    public void onitemclicked(int p) {

        DaftarFranchise daftarfranchise = (DaftarFranchise) listdata.get(p);
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
        
        listdata = new ArrayList<>();

        adapter = new ListFranchiseAdapter(listdata,DaftarfranchiseFragment.this.getContext());
        
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
        
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        adapter.setItemisclick(this);
        
        databaseHelper = new DatabaseHelper(this.getActivity());
        
        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listdata.clear();
                listdata.addAll(databaseHelper.getAllDaftarFrancise());


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
