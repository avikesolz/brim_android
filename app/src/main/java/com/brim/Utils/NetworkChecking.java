package com.brim.Utils;

/**
 * Created by su on 13/9/16.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkChecking {

        private Context _context;

        public NetworkChecking(Context context) {
            this._context = context;
        }

        /**
         * Checking for all possible internet providers
         * **/
        public boolean isConnectingToInternet() {

            try {
                ConnectivityManager connectivity = (ConnectivityManager) _context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivity != null) {
                    NetworkInfo[] info = connectivity.getAllNetworkInfo();
                    if (info != null)
                        for (int i = 0; i < info.length; i++)
                            if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                                return true;
                            }

                }
                Log.d("Network Status::","------try");
                return false;
            } catch (Exception e) {

                Log.d("Network Status::","------catch");
                return false;
            }

        }
    }

