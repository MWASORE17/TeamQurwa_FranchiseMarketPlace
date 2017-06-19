package com.williamsumitromytextview.qurwateam.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.model.entity.DaftarFranchise;
import com.williamsumitromytextview.qurwateam.view.fragment.FranchiseDetailDaftarOutlet;
import com.williamsumitromytextview.qurwateam.view.fragment.FranchiseDetailProfile;
import com.williamsumitromytextview.qurwateam.view.fragment.FranchiseDetailReviewandRating;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFranchise extends AppCompatActivity {
    private String nama_franchise;
    private String keterangan;
    private String jenis;
    private String kategori;
    private String berdiri_sejak;
    private Long investasi;
    private String website;
    private String telepon;
    private String email;
    private String gambar;
    private String lokasi;
    private String alamat;
    private Integer id;

    @BindView(R.id.detailfranchise_toolbar) Toolbar toolbar;
    @BindView(R.id.detailfranchise_viewpager) ViewPager viewPager;
    @BindView(R.id.detailfranchise_tablayout) TabLayout tabLayout;
    @BindView(R.id.detailfranchise_collaptoolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.detailfranchise_image) ImageView imageView;
    public static int int_items = 3;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_franchise);
        ButterKnife.bind(this);
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        getIntentExtra();
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Picasso.with(this.getBaseContext()).load("file:///android_asset/"+gambar).into(imageView);


    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        id = intent.getIntExtra(EXTRA_ID,0);
        nama_franchise = intent.getStringExtra(EXTRA_NAMA);
        keterangan = intent.getStringExtra(EXTRA_KETERANGAN);
        jenis = intent.getStringExtra(EXTRA_JENIS);
        kategori = intent.getStringExtra(EXTRA_KATEGORI);
        berdiri_sejak = intent.getStringExtra(EXTRA_BERDIRI);
        investasi = intent.getLongExtra(EXTRA_INVESTASI,0);
        website = intent.getStringExtra(EXTRA_WEBSITE);
        telepon = intent.getStringExtra(EXTRA_TELEPON);
        email = intent.getStringExtra(EXTRA_EMAIL);
        gambar = intent.getStringExtra(EXTRA_GAMBAR);
        lokasi = intent.getStringExtra(EXTRA_LOKASI);
        alamat = intent.getStringExtra(EXTRA_ALAMAT);

    }

    public DaftarFranchise MyData(){
        DaftarFranchise daftarFranchise = new DaftarFranchise();

        daftarFranchise.setNama_franchise(nama_franchise);
        daftarFranchise.setKeterangan(keterangan);
        daftarFranchise.setJenis(jenis);
        daftarFranchise.setKategori(kategori);
        daftarFranchise.setBerdiri_sejak(berdiri_sejak);
        daftarFranchise.setInvestasi(Long.valueOf(investasi));
        daftarFranchise.setWebsite(website);
        daftarFranchise.setAlamat(alamat);
        daftarFranchise.setLokasi(lokasi);
        daftarFranchise.setTelepon(telepon);
        daftarFranchise.setEmail(email);

        return daftarFranchise;
    }

    public final String MyDFid(){

        return String.valueOf(id);
    }
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FranchiseDetailProfile();
                case 1:
                    return new FranchiseDetailDaftarOutlet();
                case 2:
                    return new FranchiseDetailReviewandRating();
            }
            return null;
        }

        @Override
        public int getCount() {
            return int_items;

        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Profile";
                case 1:
                    return "Outlet List";
                case 2:
                    return "Review";
            }
            return null;
        }
    }

}
