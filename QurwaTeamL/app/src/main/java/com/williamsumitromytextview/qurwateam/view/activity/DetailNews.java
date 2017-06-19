package com.williamsumitromytextview.qurwateam.view.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.williamsumitromytextview.qurwateam.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailNews extends AppCompatActivity {
    @BindView(R.id.detailnewsJudul) TextView judulnews;
    @BindView(R.id.detailnewsTanggal) TextView tanggalnews;
    @BindView(R.id.detailnewsdetail) TextView detailnews;
    @BindView(R.id.detailnewsGambar) ImageView gambarnews;
    @BindView(R.id.detailnewsToolbar) Toolbar toolbar;

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_JUDUL = "EXTRA_JUDUL";
    private static final String EXTRA_GAMBAR = "EXTRA_GAMBAR";
    private static final String EXTRA_DETAIL = "EXTRA_DETAIL";
    private static final String EXTRA_DATE = "EXTRA_DATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        ButterKnife.bind(this);
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);
        judulnews.setText(extras.getString(EXTRA_JUDUL));
        detailnews.setText(extras.getString(EXTRA_DETAIL));
        tanggalnews.setText(extras.getString(EXTRA_DATE));
        Picasso.with(this.getBaseContext()).load("file:///android_asset/"+extras.getString(EXTRA_GAMBAR)).into(gambarnews);


    }
}
