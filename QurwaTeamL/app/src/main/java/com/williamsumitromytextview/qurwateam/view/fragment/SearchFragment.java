package com.williamsumitromytextview.qurwateam.view.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener, ListFranchiseAdapter.itemisclick{
    @BindView(R.id.recyclerViewDaftarFranchise) RecyclerView recyclerViewDaftarFranchise;

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

    private ArrayList<DaftarFranchise> listDaftarFranchise;
    private ListFranchiseAdapter listFranchiseAdapter;
    private ArrayList<DaftarFranchise> newList;
    private DatabaseHelper databaseHelper;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        initObject();
        return view;
    }
    private void initObject(){
        listDaftarFranchise = new ArrayList<>();
        listFranchiseAdapter = new ListFranchiseAdapter(listDaftarFranchise,this.getContext());

        RecyclerView.LayoutManager mLayoutmanager= new GridLayoutManager(getActivity(),2);
        recyclerViewDaftarFranchise.setLayoutManager(mLayoutmanager);
        recyclerViewDaftarFranchise.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDaftarFranchise.setHasFixedSize(true);
        recyclerViewDaftarFranchise.setAdapter(listFranchiseAdapter);
        listFranchiseAdapter.setItemisclick(this);
        databaseHelper = new DatabaseHelper(this.getActivity());

        getDataFromSqlite();
    }
    private void getDataFromSqlite(){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                listFranchiseAdapter.notifyDataSetChanged();
            }

            @Override
            protected Void doInBackground(Void... params) {
                listDaftarFranchise.clear();
                listDaftarFranchise.addAll(databaseHelper.getAllDaftarFrancise());
                return null;
            }
        }.execute();
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        newList = new ArrayList<>();
        for (DaftarFranchise daftarFranchise : listDaftarFranchise)
        {
            String name = daftarFranchise.getNama_franchise().toLowerCase();
            if(name.contains(newText)){
                newList.add(daftarFranchise);
            }
        }
        listFranchiseAdapter.setFilter(newList);
        recyclerViewDaftarFranchise.setAdapter(listFranchiseAdapter);
        listFranchiseAdapter.setItemisclick(this);
        return true;
    }

    @Override
    public void onitemclicked(int p) {
        DaftarFranchise daftarfranchise = (DaftarFranchise) listDaftarFranchise.get(p);
        Intent intent = new Intent(getActivity(), DetailFranchise.class);

        Bundle extras = new Bundle();
        extras.putString(EXTRA_ID, String.valueOf(daftarfranchise.getId()));
        extras.putString(EXTRA_NAMA, daftarfranchise.getNama_franchise());
        extras.putString(EXTRA_KETERANGAN, daftarfranchise.getKeterangan());
        extras.putString(EXTRA_JENIS, daftarfranchise.getJenis());
        extras.putString(EXTRA_KATEGORI, daftarfranchise.getKategori());
        extras.putString(EXTRA_BERDIRI, daftarfranchise.getBerdiri_sejak());
        extras.putString(EXTRA_INVESTASI, daftarfranchise.getInvestasi() + "");
        extras.putString(EXTRA_WEBSITE, daftarfranchise.getWebsite());
        extras.putString(EXTRA_ALAMAT, daftarfranchise.getAlamat());
        extras.putString(EXTRA_LOKASI, daftarfranchise.getLokasi());
        extras.putString(EXTRA_TELEPON, daftarfranchise.getTelepon());
        extras.putString(EXTRA_EMAIL, daftarfranchise.getEmail());
        extras.putString(EXTRA_GAMBAR, daftarfranchise.getGambar_franchise());
        intent.putExtra(BUNDLE_EXTRAS, extras);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.daftar_franchise_search,menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
