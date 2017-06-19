package com.williamsumitromytextview.qurwateam.view.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.williamsumitromytextview.qurwateam.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Rate_Our_AppFragment extends Fragment {

    Intent rateApp, email;
    @BindView(R.id.rate_mail) ImageView sent;
    @BindView(R.id.rate_rating) Button rate;
    @BindView(R.id.textInputEditTextSubjectEmail) EditText subject;
    @BindView(R.id.textInputEditTextMessages) EditText feedback;
    String sto, ssubject, sfeedback;

    public Rate_Our_AppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate__our__app, container, false);
        ButterKnife.bind(this,view);

        rateApp = new Intent(Intent.ACTION_VIEW);
        email = new Intent(Intent.ACTION_SEND);



        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateApp.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.ninegag.android.app"));

                startActivity(rateApp);
            }
        });

        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sto = "william_sumitro@yahoo.com";
                ssubject = subject.getText().toString();
                sfeedback = feedback.getText().toString();

                email.putExtra(Intent.EXTRA_EMAIL, new String[]{sto});
                email.putExtra(Intent.EXTRA_SUBJECT, ssubject);
                email.putExtra(Intent.EXTRA_TEXT, sfeedback);

                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose app to send email"));
            }
        });

        return view;

    }


}
