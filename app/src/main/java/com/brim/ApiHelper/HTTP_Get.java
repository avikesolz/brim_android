package com.brim.ApiHelper;

import android.os.AsyncTask;

import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.AppConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.Utils.Loger;

import org.json.JSONObject;

import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by apple on 18/08/17.
 */

public abstract class HTTP_Get {
    int TIMEOUT_TIME=10000;
    String exception = "";


    protected abstract void OnSucess(String Response);

    protected abstract void OnErrorApi(String Error);

    protected abstract void OnHttPError(String HttpError);




    public HTTP_Get(final String URL) {


        new AsyncTask<String, String, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                exception="";

            }

            @Override
            protected String doInBackground(String... strings) {
                String responseString="";

                try {
                    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(TIMEOUT_TIME, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();
                    String localApi = AppConstant.BaseUrl  +URL ;
                    Request request = new Request.Builder()
                            .url(localApi)
                            .addHeader("Authorization","Bearer "+ BrimApplication.getInstnace().GetAuthToken())
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();

                    if (response!=null) {
                        responseString = response.body().string();
                        response.close();
                    }

                    InetAddress address = InetAddress.getByName(new URL(localApi).getHost());
                    Loger.MSG("@@KOUSHIK","IP ADDRESS-"+address.getHostAddress());
                    Loger.MSG("@@Avik","AUTHTOKEN-"+BrimApplication.getInstnace().GetAuthToken());

                    Loger.MSG("GET @@", "" + localApi);
                    Loger.MSG("GET @@", "" + responseString);


                } catch (Exception e) {
                    e.printStackTrace();
                    //exception = mcontext.getResources().getString(R.string.exception_message);
                    exception = e.getMessage();
                    return exception;
                }


                return responseString;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(exception!=null && exception.equals(""))
                {
                    OnSucess(s);
                }else {
                    OnErrorApi(exception);
                }
            }
        }.execute(URL);




    }
}
