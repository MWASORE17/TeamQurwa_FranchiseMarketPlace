package com.williamsumitromytextview.qurwateam.view.fragment;


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
import com.williamsumitromytextview.qurwateam.view.activity.DetailFranchise;
import com.williamsumitromytextview.qurwateam.view.adapter.ListOutletAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FranchiseDetailDaftarOutlet extends Fragment {

    @BindView(R.id.recyclerviewDaftarOutlet) RecyclerView recView;
    private ListOutletAdapter adapterDaftarOutlet;
    private ArrayList listdata;
    private DatabaseHelper databaseHelper;

    private String id_franchise;
    public FranchiseDetailDaftarOutlet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_franchise_detail_daftar_outlet, container, false);
        ButterKnife.bind(this,view);


        initObject();

        DetailFranchise detailFranchise = (DetailFranchise) getActivity();
        final String data = detailFranchise.MyDFid();
        id_franchise = String.valueOf(data);


        databaseHelper = new DatabaseHelper(this.getActivity());
        getDataFromSQLite(id_franchise);

        return view;
    }

    private void initObject(){
        listdata = new ArrayList();
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterDaftarOutlet = new ListOutletAdapter(listdata, getActivity());
        recView.setAdapter(adapterDaftarOutlet);

    }

    private void getDataFromSQLite(final String id_franchise) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                listdata.clear();
                listdata.addAll(databaseHelper.getOutlet_DF(id_franchise));


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapterDaftarOutlet.notifyDataSetChanged();
            }
        }.execute();

    }



}
