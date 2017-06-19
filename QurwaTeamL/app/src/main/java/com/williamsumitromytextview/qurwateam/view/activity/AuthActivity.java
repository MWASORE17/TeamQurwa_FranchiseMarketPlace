package com.williamsumitromytextview.qurwateam.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.williamsumitromytextview.qurwateam.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by william on 15/06/2017.
 */

public class AuthActivity {
    public static boolean isemailvalid(String email) {
        String _expression = "^[a-z]([a-z0-9-\\.]+)?+@[a-z]+\\.[a-z]{2,4}+(\\.[a-z]{2,4})?$";
        CharSequence _email = email;
        Pattern _pattern = Pattern.compile(_expression, Pattern.CASE_INSENSITIVE);
        Matcher _mathcer = _pattern.matcher(_email);

        if (_mathcer.matches()) {
            return true;
        }
        return false;
    }

    public static boolean ispasswordvalid(String password) {
        String _expression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[@!\\*?$&\\^#-])[\\w@!\\*?$&\\^#-]{8,}$";
        CharSequence _password = password;
        Pattern _pattern = Pattern.compile(_expression, Pattern.CASE_INSENSITIVE);
        Matcher _mathcer = _pattern.matcher(_password);

        if (_mathcer.matches()) {
            return true;
        }
        return false;
    }
}
