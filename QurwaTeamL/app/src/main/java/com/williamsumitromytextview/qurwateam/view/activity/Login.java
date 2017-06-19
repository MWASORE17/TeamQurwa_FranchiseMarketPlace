package com.williamsumitromytextview.qurwateam.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.model.database.DatabaseHelper;
import com.williamsumitromytextview.qurwateam.model.entity.User;
import com.williamsumitromytextview.qurwateam.model.session.SessionManagement;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by william on 10/03/2017.
 */

public class Login extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = Login.this;
    @BindView(R.id.Login) LinearLayout linearLayout;
    @BindView(R.id.textInputLayoutEmail) TextInputLayout textInputLayoutEmail;
    @BindView(R.id.textInputLayoutPassword) TextInputLayout textInputLayoutPassword;
    @BindView(R.id.textInputEditTextEmail) TextInputEditText textInputEditTextEmail;
    @BindView(R.id.textInputEditTextPassword) TextInputEditText textInputEditTextPassword;
    @BindView(R.id.btn_Login) ActionProcessButton ButtonLogin;
    @BindView(R.id.textViewLinkRegister) TextView textViewLinkRegister;
    private DatabaseHelper databaseHelper;
    private SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        initListeners();
        initObjects();
    }
    private void initListeners() {
        ButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Login:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                Intent intentRegister = new Intent(getApplicationContext(), Register.class);
                startActivity(intentRegister);
                finish();
                break;
        }
    }
    private void verifyFromSQLite() {


        textInputLayoutEmail.setErrorEnabled(false);
        textInputLayoutPassword.setErrorEnabled(false);
        if (TextUtils.isEmpty(textInputEditTextEmail.getText())) {
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError("Email is required");
            return;
        } else if (!AuthActivity.isemailvalid(textInputEditTextEmail.getText().toString())) {
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError("Email is not valid.");
            return;
        } else if (TextUtils.isEmpty(textInputEditTextPassword.getText())) {
            textInputLayoutPassword.setErrorEnabled(true);
            textInputLayoutPassword.setError("Password is required");
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {

            ButtonLogin.setMode(ActionProcessButton.Mode.ENDLESS);
            ButtonLogin.setProgress(1);
            ButtonLogin.setEnabled(false);

            Timer buttonTimer = new Timer();
            buttonTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            ButtonLogin.setEnabled(true);
                            ButtonLogin.setMode(ActionProcessButton.Mode.PROGRESS);


                            session = new SessionManagement(getApplicationContext());
                            User _user = databaseHelper.getUser(textInputEditTextEmail.getText().toString().trim());

                            session.createLoginSession(_user.getName(), textInputEditTextEmail.getText().toString().trim());

                            emptyInputEditText();
                            ButtonLogin.setProgress(100);
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);

                            finish();
                        }
                    });
                }
            }, 2000);
        } else {
            Snackbar.make(linearLayout, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }

}
