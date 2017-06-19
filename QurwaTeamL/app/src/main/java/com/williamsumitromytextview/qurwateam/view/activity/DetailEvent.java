package com.williamsumitromytextview.qurwateam.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;
import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.model.session.SessionManagement;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailEvent extends AppCompatActivity {

    @BindView(R.id.detailEventJudul) TextView juduldetail;
    @BindView(R.id.detaileventIsi) TextView isidetail;
    @BindView(R.id.detaileventJadwal) TextView jadwaldetail;
    @BindView(R.id.detaileventAlamat) TextView alamatdetail;
    @BindView(R.id.detaileventGambar) ImageView gambardetail;
    @BindView(R.id.detaileventTiket) ImageView ticket;
    @BindView(R.id.toolbarDetailEvent) Toolbar toolbar;

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_JUDUL = "EXTRA_JUDUL";
    private static final String EXTRA_ISI = "EXTRA_ISI";
    private static final String EXTRA_JADWAL = "EXTRA_JADWAL";
    private static final String EXTRA_ALAMAT = "EXTRA_ALAMAT";
    private static final String EXTRA_GAMBAR = "EXTRA_GAMBAR";

    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        ButterKnife.bind(this);
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Context context = this;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);


        juduldetail.setText(extras.getString(EXTRA_JUDUL));
        isidetail.setText(extras.getString(EXTRA_ISI));
        jadwaldetail.setText(extras.getString(EXTRA_JADWAL));
        alamatdetail.setText(extras.getString(EXTRA_ALAMAT));
        Picasso.with(this.getBaseContext()).load("file:///android_asset/"+extras.getString(EXTRA_GAMBAR)).into(gambardetail);

        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = new SessionManagement(getApplicationContext());
                String text = getIntent().getBundleExtra(BUNDLE_EXTRAS).getString(EXTRA_JUDUL) + "P1k#c!dffR$!SSAQ23";
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    Intent intent = new Intent(context, Barcode.class);
                    intent.putExtra("pic",bitmap);
                    context.startActivity(intent);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
