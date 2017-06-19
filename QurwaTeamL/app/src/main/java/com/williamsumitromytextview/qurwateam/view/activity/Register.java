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

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Register extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = Register.this;

    @BindView(R.id.Register) LinearLayout linearLayout;
    @BindView(R.id.textInputLayoutName) TextInputLayout textInputLayoutName;
    @BindView(R.id.textInputLayoutEmail) TextInputLayout textInputLayoutEmail;
    @BindView(R.id.textInputLayoutPassword) TextInputLayout textInputLayoutPassword;
    @BindView(R.id.textInputLayoutConfirmPassword) TextInputLayout textInputLayoutConfirmPassword;
    @BindView(R.id.textInputEditTextName) TextInputEditText textInputEditTextName;
    @BindView(R.id.textInputEditTextEmail) TextInputEditText textInputEditTextEmail;
    @BindView(R.id.textInputEditTextPassword) TextInputEditText textInputEditTextPassword;
    @BindView(R.id.textInputEditTextConfirmPassword) TextInputEditText textInputEditTextConfirmPassword;
    @BindView(R.id.btn_register) ActionProcessButton ButtonRegister;
    @BindView(R.id.appCompatTextViewLoginLink) TextView TextViewLoginLink;

    private DatabaseHelper databaseHelper;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        initListeners();
        initObjects();
    }
    private void initListeners() {
        ButtonRegister.setOnClickListener(this);
        TextViewLoginLink.setOnClickListener(this);

    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        user = new User();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_register:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewLoginLink:
                TextViewLoginLink.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getApplicationContext(),Login.class);
                                startActivity(i);
                                finish();
                            }
                        }
                );
                break;
        }
    }
    private void postDataToSQLite() {
        textInputLayoutName.setErrorEnabled(false);
        textInputLayoutEmail.setErrorEnabled(false);
        textInputLayoutPassword.setErrorEnabled(false);
        textInputLayoutConfirmPassword.setErrorEnabled(false);

        if (TextUtils.isEmpty(textInputEditTextName.getText())) {
            textInputLayoutName.setErrorEnabled(true);
            textInputLayoutName.setError("Name is required");
            return;
        } else if (textInputEditTextName.getText().length() < 3) {
            textInputLayoutName.setErrorEnabled(true);
            textInputLayoutName.setError("Name minimal 3");
            return;

        } else if (TextUtils.isEmpty(textInputEditTextEmail.getText())) {
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError("Email is required");
            return;
        } else if (!AuthActivity.isemailvalid(textInputEditTextEmail.getText().toString())) {
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError("Email is not valid");
            return;
        } else if (TextUtils.isEmpty(textInputEditTextPassword.getText())) {
            textInputLayoutPassword.setErrorEnabled(true);
            textInputLayoutPassword.setError("Password is required");
            return;
        } else if (!AuthActivity.ispasswordvalid(textInputEditTextPassword.getText().toString())) {
            textInputLayoutPassword.setErrorEnabled(true);
            textInputLayoutPassword.setError("Password is not valid. Password must contains at least 1 lowercase, 1 uppercase, 1 number, 1 special character and minimum 8 characters");
            return;
        } else if (TextUtils.isEmpty(textInputEditTextConfirmPassword.getText())) {
            textInputLayoutConfirmPassword.setErrorEnabled(true);
            textInputLayoutConfirmPassword.setError("Re-Password is required");
            return;
        } else if (!textInputEditTextPassword.getText().toString().equals(textInputEditTextConfirmPassword.getText().toString())) {
            textInputLayoutConfirmPassword.setErrorEnabled(true);
            textInputLayoutConfirmPassword.setError("Password not match");
            return;

        }
        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            user.setName(textInputEditTextName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            databaseHelper.addUser(user);

            Snackbar.make(linearLayout, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
            ButtonRegister.setMode(ActionProcessButton.Mode.ENDLESS);
            ButtonRegister.setProgress(1);
            ButtonRegister.setEnabled(false);

            Timer buttonTimer = new Timer();
            buttonTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            ButtonRegister.setEnabled(true);
                            ButtonRegister.setMode(ActionProcessButton.Mode.PROGRESS);
                            ButtonRegister.setProgress(100);
                            startActivity(new Intent(Register.this, Login.class));
                            finish();
                        }
                    });
                }
            }, 2000);


        } else {
            Snackbar.make(linearLayout, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }

}
