package com.brim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.brim.AppContant.ApiConstant;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.NetworkChecking;

public class LegalPrivacy extends AppCompatActivity {

    WebView we_view;
    ImageView back_button;

    NetworkChecking networkChecking;
    AppProgerssDialog appProgerssDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_privacy);

        networkChecking=new NetworkChecking(this);
        appProgerssDialog=new AppProgerssDialog(this);

        back_button= (ImageView) findViewById(R.id.back_button);
        

        //*****************************************web view setup****************************************

        we_view= (WebView) findViewById(R.id.we_view);
        WebSettings settings = we_view.getSettings();
        settings.setJavaScriptEnabled(true);

        if(networkChecking.isConnectingToInternet()==true) {

            appProgerssDialog.SetTitle(getString(R.string.app_name));
            appProgerssDialog.SetMessage("Loading...");
            appProgerssDialog.Show();

            we_view.loadUrl(ApiConstant.LEGAL_PRIVACY);//***********************************legal and privacy url**********************

        }else{
            appProgerssDialog.Dismiss();
            Toast.makeText(LegalPrivacy.this,getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }

        we_view.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url)
            {
                appProgerssDialog.Dismiss();

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
