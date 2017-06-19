package com.williamsumitromytextview.qurwateam.view.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.model.database.DatabaseHelper;
import com.williamsumitromytextview.qurwateam.model.entity.User;
import com.williamsumitromytextview.qurwateam.model.session.SessionManagement;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditUser extends AppCompatActivity {
    private DatabaseHelper databaseHelper;


    @BindView(R.id.toolbarEditProfile) Toolbar toolbar;
    @BindView(R.id.btn_savechange_profil) ActionProcessButton button_save_profile;
    @BindView(R.id.edit_name) TextInputEditText username_edit;
    @BindView(R.id.edit_oldpassword) TextInputEditText oldpassword_edit;
    @BindView(R.id.edit_newpassword) TextInputEditText newpassword_edit;
    @BindView(R.id.edit_confirmaPassword) TextInputEditText confirmpassword_edit;
    @BindView(R.id.textInputEditName) TextInputLayout textInputLayoutName;
    @BindView(R.id.textInputEditOldPassword) TextInputLayout textInputLayoutOldPassword;
    @BindView(R.id.textInputEditNewPassword) TextInputLayout textInputLayoutNewPassword;
    @BindView(R.id.textInputEditConfirmPassword) TextInputLayout textInputLayoutConfirmPassword;
    private SessionManagement session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
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
        databaseHelper = new DatabaseHelper(EditUser.this);
        session = new SessionManagement(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> data = session.getUserDetails();
        final String email = data.get(SessionManagement.KEY_EMAIL);


        button_save_profile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textInputLayoutName.setErrorEnabled(false);
                        textInputLayoutNewPassword.setErrorEnabled(false);
                        textInputLayoutOldPassword.setErrorEnabled(false);
                        textInputLayoutConfirmPassword.setErrorEnabled(false);
                        if (TextUtils.isEmpty(username_edit.getText())) {
                            textInputLayoutName.setErrorEnabled(true);
                            textInputLayoutName.setError("Name is required");
                            return;
                        } else if (username_edit.getText().length() < 3) {
                            textInputLayoutName.setErrorEnabled(true);
                            textInputLayoutName.setError("Name minimal 3");
                            return;
                        } else if (TextUtils.isEmpty(oldpassword_edit.getText())) {
                            textInputLayoutOldPassword.setErrorEnabled(true);
                            textInputLayoutOldPassword.setError("Password is required");
                            return;
                        } else if (!databaseHelper.checkUser(email,oldpassword_edit.getText().toString().trim())) {
                            textInputLayoutOldPassword.setErrorEnabled(true);
                            textInputLayoutOldPassword.setError("Wrong Password");
                            oldpassword_edit.setText("");
                            return;
                        } else if (TextUtils.isEmpty(newpassword_edit.getText())) {
                            textInputLayoutNewPassword.setErrorEnabled(true);
                            textInputLayoutNewPassword.setError("Password is required");
                            return;
                        } else if (!AuthActivity.ispasswordvalid(newpassword_edit.getText().toString())) {
                            textInputLayoutNewPassword.setErrorEnabled(true);
                            textInputLayoutNewPassword.setError("Password is not valid. Password must contains at least 1 lowercase, 1 uppercase, 1 number, 1 special character and minimum 8 characters");
                            return;
                        } else if (oldpassword_edit.getText().toString().equals(newpassword_edit.getText().toString())) {
                            textInputLayoutNewPassword.setErrorEnabled(true);
                            textInputLayoutNewPassword.setError("Password is still the same");
                            return;
                        } else if (TextUtils.isEmpty(confirmpassword_edit.getText())) {
                            textInputLayoutConfirmPassword.setErrorEnabled(true);
                            textInputLayoutConfirmPassword.setError("Re-Password is required");
                            return;
                        } else if (!newpassword_edit.getText().toString().equals(confirmpassword_edit.getText().toString())) {
                            textInputLayoutConfirmPassword.setErrorEnabled(true);
                            textInputLayoutConfirmPassword.setError("Password not match");
                            return;
                        }

                        button_save_profile.setMode(ActionProcessButton.Mode.ENDLESS);
                        button_save_profile.setProgress(1);
                        button_save_profile.setEnabled(false);
                        Timer buttonTimer = new Timer();
                        buttonTimer.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        button_save_profile.setEnabled(true);
                                        button_save_profile.setMode(ActionProcessButton.Mode.PROGRESS);
                                        button_save_profile.setProgress(100);
                                        User user = new User();

                                        user.setEmail(email);
                                        if (username_edit.getText().toString().trim()=="")
                                            return;


                                        if(username_edit.getText().toString().trim() !="" && newpassword_edit.getText().toString().trim()!=""){
                                            user.setName(username_edit.getText().toString());
                                            user.setPassword(newpassword_edit.getText().toString());
                                            databaseHelper.updateUser(user);

                                            Toast.makeText(getApplicationContext(), "Data has been changed", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(getApplicationContext(), "Please re-login", Toast.LENGTH_LONG).show();
                                            session.logoutUser();

                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"Please fill all the requirements",Toast.LENGTH_SHORT).show();
                                        }
                                        finish();
                                    }
                                });
                            }
                        }, 2000);

                    }
                }
        );

    }
}
