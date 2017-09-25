package com.brim.ApiHelper;

import android.os.AsyncTask;

import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.AppConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.Utils.Loger;

import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by apple on 18/08/17.
 */

public abstract class HTTP_POST {

    int TIMEOUT_TIME=6000;
    String exception = "";


    protected abstract void OnSucess(String Response);

    protected abstract void OnErrorApi(String Error);

    protected abstract void OnHttPError(String HttpError);




    public HTTP_POST(final String URL, final String BodyParams) {


        new AsyncTask<String, String, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                exception = "";
                Loger.MSG("@@Body",BodyParams);

            }

            @Override
            protected String doInBackground(String... strings) {
                String responseString = "";

                try {
                    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(TIMEOUT_TIME, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();
                    String localApi = AppConstant.BaseUrl + URL ;

                    MediaType text = MediaType.parse("text");
                    RequestBody body = RequestBody.create(text,BodyParams);
                        Request request = new Request.Builder()
                                .url(localApi)
                                .addHeader("Content-Type","application/x-www-form-urlencoded")
                                .post(body)
                                .build();
                        Response response = client.newCall(request).execute();
                        responseString = response.body().string();
                        response.close();
                    InetAddress address = InetAddress.getByName(new URL(localApi).getHost());
                    Loger.MSG("@@KOUSHIK", "IP ADDRESS-" + address.getHostAddress());


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
                if (exception != null && exception.equals("")) {
                    OnSucess(s);
                } else {
                    OnErrorApi(exception);
                }
            }
        }.execute(URL);

    }
    }
