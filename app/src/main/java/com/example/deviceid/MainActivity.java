package com.example.deviceid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = getAdMobDeviceId(android_id).toUpperCase();
        Log.i("device id", "device id=" + deviceId);

        TextView textViewAdMobDeviceId = (TextView) findViewById(R.id.textViewDeviceId);
        textViewAdMobDeviceId.setText(deviceId);


        CopyTextToClipboard("AdMobDeviceId", deviceId);

        TextView textViewHintMsg = (TextView) findViewById(R.id.textViewHintMsg);
        textViewHintMsg.setText("(Already copy your device id to clipboard!)");

        //        Log.i("CopyDeviceId", "Already copy your deviceId \"" + deviceId + "\" to clipboard!");
    }

    private void CopyTextToClipboard(String label, String deviceId) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, deviceId);
        clipboard.setPrimaryClip(clip);
        Log.i("CopyTextToClipboard", "Already copy text \"" + deviceId + "\" to clipboard!");
    }

    public String getAdMobDeviceId(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}