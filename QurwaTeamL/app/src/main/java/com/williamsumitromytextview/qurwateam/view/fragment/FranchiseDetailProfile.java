package com.williamsumitromytextview.qurwateam.view.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.model.database.DatabaseHelper;
import com.williamsumitromytextview.qurwateam.model.entity.DaftarFranchise;
import com.williamsumitromytextview.qurwateam.model.session.SessionManagement;
import com.williamsumitromytextview.qurwateam.view.activity.DetailFranchise;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FranchiseDetailProfile extends Fragment implements View.OnClickListener{

    @BindView(R.id.homedetailprofileName) TextView nama_franchise;
    @BindView(R.id.homedetailprofileJenis) TextView jenis;
    @BindView(R.id.homedetailprofileKategori) TextView kategori;
    @BindView(R.id.homedetailprofileBerdiriSejak) TextView berdiri;
    @BindView(R.id.homedetailprofileInvestasi) TextView investasi;
    @BindView(R.id.homedetailprofileAlamat) TextView alamat;
    @BindView(R.id.homedetailprofileLokasi) TextView lokasi;
    @BindView(R.id.homedetailprofileTelepon) TextView telepon;
    @BindView(R.id.homedetailprofileEmail) TextView email;
    @BindView(R.id.homedetailprofileWebsite) TextView website;
    @BindView(R.id.homedetailprofileDeskripsi) TextView keterangan;
    @BindView(R.id.franchisedetailprofile_bookmark) ImageView bookmark;
    @BindView(R.id.franchisedetailprofile_call) ImageView call;
    @BindView(R.id.franchisedetailprofile_email) ImageView message;
    @BindView(R.id.franchisedetailprofile_container) NestedScrollView nestedScrollView;

    private SessionManagement session;
    private String email_session;
    DatabaseHelper db;
    private boolean isBookmark = false;
    public FranchiseDetailProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_franchise_detail_profile, container, false);
        ButterKnife.bind(this,view);
        bookmark.setOnClickListener(this);
        call.setOnClickListener(this);
        message.setOnClickListener(this);

        session = new SessionManagement(getActivity().getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        email_session = user.get(SessionManagement.KEY_EMAIL);
        db = new DatabaseHelper(getActivity().getApplicationContext());

        setview();

        DetailFranchise df = (DetailFranchise) getActivity();
        String id_fr = df.MyDFid();

        if (!db.checkBookmark(user.get(SessionManagement.KEY_EMAIL),id_fr))
        {
            bookmark.setImageResource(R.drawable.tagcircle);
            isBookmark = false;
        }
        else{
            bookmark.setImageResource(R.drawable.tagcircle1);
            isBookmark = true;

        }
        return view;
    }



    private void setview(){
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DetailFranchise detailFranchise = (DetailFranchise) getActivity();
        DaftarFranchise data = detailFranchise.MyData();
        nama_franchise.setText(data.getNama_franchise());
        keterangan.setText(data.getKeterangan());
        jenis.setText(data.getJenis());
        kategori.setText(data.getKategori());
        berdiri.setText(data.getBerdiri_sejak());
        investasi.setText(format.format(data.getInvestasi()));
        website.setText(data.getWebsite());
        alamat.setText(data.getAlamat());
        lokasi.setText(data.getLokasi());
        telepon.setText(data.getTelepon());
        email.setText(data.getEmail());



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){


            case R.id.franchisedetailprofile_bookmark:
                try{
                    session = new SessionManagement(getActivity().getApplicationContext());
                    final HashMap<String,String> user = session.getUserDetails();
                    db = new DatabaseHelper(getActivity().getApplicationContext());

                    DetailFranchise detailFranchise = (DetailFranchise) getActivity();
                    final String id_franchise = detailFranchise.MyDFid();

                    if (db.checkBookmark(user.get(SessionManagement.KEY_EMAIL),id_franchise))
                    {
                        bookmark.setImageResource(R.drawable.tagcircle);
                        isBookmark = false;
                        db.deleteBookmark(user.get(SessionManagement.KEY_EMAIL),id_franchise);
                        Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.franchisedetailprofile_container), "Bookmark has been delete", Snackbar.LENGTH_SHORT).setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bookmark.setImageResource(R.drawable.tagcircle1);
                                isBookmark = true;
                                db.addBookmark(user.get(SessionManagement.KEY_EMAIL),id_franchise);
                                Snackbar snackbar1 = Snackbar.make(getActivity().findViewById(R.id.franchisedetailprofile_container), "Franchise has been Bookmark", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
                        });
                        snackbar.show();

                    }
                    else{
                        bookmark.setImageResource(R.drawable.tagcircle1);
                        isBookmark = true;
                        db.addBookmark(user.get(SessionManagement.KEY_EMAIL),id_franchise);
                        Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.franchisedetailprofile_container), "Franchise has been Bookmark", Snackbar.LENGTH_SHORT).setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bookmark.setImageResource(R.drawable.tagcircle);
                                isBookmark = false;
                                db.deleteBookmark(user.get(SessionManagement.KEY_EMAIL),id_franchise);
                                Snackbar snackbar1 = Snackbar.make(getActivity().findViewById(R.id.franchisedetailprofile_container), "Bookmark has been Undo", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
                        });
                        snackbar.show();
                    }
                }
                catch (Exception e){

                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.franchisedetailprofile_email:
                Intent email = new Intent(Intent.ACTION_SEND);
                DetailFranchise d = (DetailFranchise) getActivity();
                DaftarFranchise dfF = d.MyData();
                String sto = dfF.getEmail();
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{sto});
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose app to send email"));
                break;
            case R.id.franchisedetailprofile_call:
                DetailFranchise df = (DetailFranchise) getActivity();
                DaftarFranchise dt = df.MyData();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.setData(Uri.parse("tel:"+dt.getTelepon()));
                getActivity().startActivity(callIntent);
                break;
        }

    }
}
