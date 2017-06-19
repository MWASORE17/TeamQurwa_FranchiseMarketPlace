package com.williamsumitromytextview.qurwateam.view.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.model.database.DatabaseHelper;
import com.williamsumitromytextview.qurwateam.model.entity.Berita;
import com.williamsumitromytextview.qurwateam.view.activity.DetailNews;
import com.williamsumitromytextview.qurwateam.view.adapter.NewsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements NewsAdapter.itemClickCallBack {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_JUDUL = "EXTRA_JUDUL";
    private static final String EXTRA_GAMBAR = "EXTRA_GAMBAR";
    private static final String EXTRA_DETAIL = "EXTRA_DETAIL";
    private static final String EXTRA_DATE = "EXTRA_DATE";

    @BindView(R.id.recyclerviewNews) RecyclerView recyclerView;
    private NewsAdapter newsadapter;
    private ArrayList listdata;
    private DatabaseHelper databaseHelper;
    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this,view);
        initNewsObject();
        return view;
    }

    @Override
    public void onItemClick(int p) {
        Berita news = (Berita) listdata.get(p);
        Intent intent = new Intent(getActivity(), DetailNews.class);

        Bundle extras = new Bundle();
        extras.putString(EXTRA_DATE, news.getWaktu_berita());
        extras.putString(EXTRA_DETAIL, news.getIsi_berita());
        extras.putString(EXTRA_GAMBAR, news.getGambar_berita());
        extras.putString(EXTRA_JUDUL, news.getJudul_berita());
        intent.putExtra(BUNDLE_EXTRAS, extras);
        startActivity(intent);

    }

    private void initNewsObject()
    {





        listdata = new ArrayList();
        newsadapter = new NewsAdapter(listdata,this.getParentFragment().getContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(newsadapter);
        newsadapter.setitemclickcallback(this);

        databaseHelper = new DatabaseHelper(this.getActivity());
        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listdata.clear();
                listdata.addAll(databaseHelper.getAllBerita());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                newsadapter.notifyDataSetChanged();
            }
        }.execute();

    }
}
