package com.williamsumitromytextview.qurwateam.view.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.model.database.DatabaseHelper;
import com.williamsumitromytextview.qurwateam.model.session.SessionManagement;
import com.williamsumitromytextview.qurwateam.view.activity.DetailFranchise;
import com.williamsumitromytextview.qurwateam.view.activity.MainActivity;
import com.williamsumitromytextview.qurwateam.view.adapter.ChatArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FranchiseDetailReviewandRating extends Fragment {
    private static final String TAG = "ChatActivity";
    private ChatArrayAdapter chatArrayAdapter;
    @BindView(R.id.msgview) RecyclerView listView;
    @BindView(R.id.msg) EditText chatText;
    @BindView(R.id.send) Button buttonSend;
    private ArrayList listdata;
    private SessionManagement session;

    private DatabaseHelper databaseHelper;

    public FranchiseDetailReviewandRating() {
        // Required empty public constructor
    }

    private String email_session, id_franchise;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_franchise_detail_reviewand_rating, container, false);
        ButterKnife.bind(this,view);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setHasFixedSize(true);

        //DB
        listdata = new ArrayList<>();
        session = new SessionManagement(getActivity().getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        email_session = user.get(SessionManagement.KEY_EMAIL);

        chatArrayAdapter = new ChatArrayAdapter(listdata,getContext());
        chatArrayAdapter.notifyDataSetChanged();
        listView.setAdapter(chatArrayAdapter);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

        DetailFranchise detailFranchise = (DetailFranchise) getActivity();
        String data = detailFranchise.MyDFid();
        id_franchise = String.valueOf(data);


        databaseHelper = new DatabaseHelper(this.getActivity());
        getDataFromSQLite(id_franchise);
        return view;
    }

    private boolean sendChatMessage() {
        databaseHelper = new DatabaseHelper(this.getActivity());
        databaseHelper.addChat(email_session,id_franchise,chatText.getText().toString());
        chatText.setText("");
        Toast.makeText(getActivity().getApplication(),"Review berhasil di submit ",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        startActivity(intent);
        return true;
    }

    private void getDataFromSQLite(final String id_franchise) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                chatArrayAdapter.notifyDataSetChanged();
                chatArrayAdapter.notifyDataSetChanged();
            }

            @Override
            protected Void doInBackground(Void... params) {
                listdata.clear();
                listdata.addAll(databaseHelper.getChat(id_franchise));
                return null;
            }
        }.execute();

    }



}
