package br.com.ilhasoft.protejaBrasil.helpers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/**
 * Created by johndalton on 06/05/14.
 */
public class CallHelper {

    public static final int REQUEST_CODE_PERMISSION = 100;

    private final Fragment fragment;

    private String phone;

    public CallHelper(Fragment fragment) {
        this.fragment = fragment;
    }

    public void callPhone(String phone) {
        this.phone = phone;
        if (ContextCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            makeTheCall();
        } else {
            fragment.requestPermissions(new String[]{ Manifest.permission.CALL_PHONE }, REQUEST_CODE_PERMISSION);
        }
    }

    private void makeTheCall() {
        String url = "tel:" + phone;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
        fragment.startActivity(intent);
    }

    public void callOnlyPhone(String phone) {
        if(phone != null) {
            phone = phone.replaceAll("(\\(.+\\))|\\s|-", "");
            callPhone(phone);
        }
    }
}
