package com.williamsumitromytextview.qurwateam.view.fragment;


import android.content.Context;
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
import com.williamsumitromytextview.qurwateam.model.entity.EventClass;
import com.williamsumitromytextview.qurwateam.view.activity.DetailEvent;
import com.williamsumitromytextview.qurwateam.view.adapter.EventAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment implements EventAdapter.itemClickCallBack {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_JUDUL = "EXTRA_JUDUL";
    private static final String EXTRA_ISI = "EXTRA_ISI";
    private static final String EXTRA_JADWAL = "EXTRA_JADWAL";
    private static final String EXTRA_ALAMAT = "EXTRA_ALAMAT";
    private static final String EXTRA_GAMBAR = "EXTRA_GAMBAR";

    @BindView(R.id.rec_list_Event) RecyclerView recView;
    private EventAdapter eventAdapter;

    private ArrayList listEventClass;
    private DatabaseHelper databaseHelper;
    Context context;
    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this,view);
        context =this.getParentFragment().getContext();
        initObject();

        return view;
    }

    @Override
    public void onItemClick(int p) {
        EventClass event = (EventClass) listEventClass.get(p);
        Intent intent = new Intent(getActivity(), DetailEvent.class);

        Bundle extras = new Bundle();
        extras.putString(EXTRA_JUDUL, event.getJudulEvent());
        extras.putString(EXTRA_ISI, event.getIsiEvent());
        extras.putString(EXTRA_JADWAL, event.getWAKTUEVENT());
        extras.putString(EXTRA_ALAMAT, event.getALAMATEVENT());
        extras.putString(EXTRA_GAMBAR,event.getGambarEvent());
        intent.putExtra(BUNDLE_EXTRAS, extras);
        startActivity(intent);
    }

    private void initObject(){
        listEventClass = new ArrayList<>();
        eventAdapter = new EventAdapter(listEventClass,this.getParentFragment().getContext());


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recView.setLayoutManager(mLayoutManager);
        recView.setItemAnimator(new DefaultItemAnimator());
        recView.setHasFixedSize(true);
        recView.setAdapter(eventAdapter);
        eventAdapter.setitemclickcallback(this);

        databaseHelper = new DatabaseHelper(this.getActivity());

        getDataFromSQLite();
    }
    private void getDataFromSQLite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listEventClass.clear();
                listEventClass.addAll(databaseHelper.getAllEvent());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                eventAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
