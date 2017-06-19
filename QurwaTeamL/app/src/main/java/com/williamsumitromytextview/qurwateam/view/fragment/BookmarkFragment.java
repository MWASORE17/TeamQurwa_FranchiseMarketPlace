package com.williamsumitromytextview.qurwateam.view.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.model.database.DatabaseHelper;
import com.williamsumitromytextview.qurwateam.model.entity.DaftarFranchise;
import com.williamsumitromytextview.qurwateam.model.session.SessionManagement;
import com.williamsumitromytextview.qurwateam.view.activity.DetailFranchise;
import com.williamsumitromytextview.qurwateam.view.adapter.BookmarkAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends Fragment implements BookmarkAdapter.itemClickCallBack {

    @BindView(R.id.rec_bookmark) RecyclerView rvBookmark;
    @BindView(R.id.swiper) SwipeRefreshLayout swiper;
    SessionManagement session;
    private ArrayList listdata;
    private DatabaseHelper databaseHelper;
    BookmarkAdapter bookmarkAdapter;

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

    public BookmarkFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);

        ButterKnife.bind(this,view);
        refresh();

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                        swiper.setRefreshing(false);
                    }
                },1000);
            }
        });
        return view;

    }
    public void refresh(){
        listdata = new ArrayList<>();
        rvBookmark.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBookmark.setHasFixedSize(true);

        session = new SessionManagement(getActivity().getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        bookmarkAdapter = new BookmarkAdapter(listdata, getActivity());
        rvBookmark.setAdapter(bookmarkAdapter);
        bookmarkAdapter.setitemclickcallback(this);

        databaseHelper = new DatabaseHelper(this.getActivity());
        getDataFromSQLite(user.get(SessionManagement.KEY_EMAIL));
    }


    private void getDataFromSQLite(final String email) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                bookmarkAdapter.notifyDataSetChanged();
            }

            @Override
            protected Void doInBackground(Void... params) {
                listdata.clear();
                listdata.addAll(databaseHelper.getBookmark(email));
                return null;
            }
        }.execute();

    }

    @Override
    public void onItemClick(int p) {
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

}
