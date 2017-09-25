package com.brim.ApiHelper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.brim.FingerPrintFinalActivity;

/**
 * Created by su on 21/9/17.
 */
@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandeler extends
        FingerprintManager.AuthenticationCallback
{

    private CancellationSignal cancellationSignal;
    private Context appContext;
    private FingerPrintFinalActivity mainActivity;


    public FingerprintHandeler(Context context , FingerPrintFinalActivity mActivity) {
        appContext = context;
        mainActivity = mActivity;

    }

    public void startAuth(FingerprintManager manager,
                          FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();

        if (ActivityCompat.checkSelfPermission(appContext,
                android.Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId,
                                      CharSequence errString) {
        Toast.makeText(appContext,
                "Authentication error\n" + errString,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId,
                                     CharSequence helpString) {
        Toast.makeText(appContext,
                "Authentication help\n" + helpString,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(appContext,
                "Authentication failed.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {

        Toast.makeText(appContext,
                "Authentication succeeded.",
                Toast.LENGTH_LONG).show();


        mainActivity.SuccessfullyMatched();

//        AppData.dtoken=AppData.devicetoken;
//        AppData.fingerprintEnabled=true;
//        Log.d("onAuthentcceeded",AppData.dtoken+" " +String.valueOf(AppData.fingerprintEnabled));

        // mainActivity.GotoNextPage();
    }

}
