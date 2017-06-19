package com.williamsumitromytextview.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.scan_btn) Button scan_btn;
    @BindView(R.id.verify) TextView verify;
    @BindView(R.id.eventname) TextView eventname;
    @BindView(R.id.gambarevent) ImageView gambarevent;
    private ArrayList eventlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        eventlist = (ArrayList) DataListEvent.getListEvent();



        final Activity activity = this;
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                int index = DataListEvent.getindex(result.getContents());
                if(index != -1){
                    verify.setText("Verified");
                    eventname.setText(DataListEvent.getJudulEvent(index));
                    gambarevent.setImageResource(DataListEvent.getGambarEvent(index));
                }
                else{
                    verify.setText("Unidentified");
                    eventname.setText("Unidentified");
                    gambarevent.setImageDrawable(getResources().getDrawable(R.drawable.error));
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }























}
