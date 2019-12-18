package com.example.finin.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.example.finin.utils.NetworkUtils.isNetworkConnected;

/**
 * @author yogesh
 * @date 18/12/19
 */

public class ConnectivityReceiver
        extends BroadcastReceiver {

    private final String TAG = ConnectivityReceiver.class.getSimpleName();

    public static ConnectivityReceiverListener connectivityReceiverListener;
    private String mCurrentState = "";

    public ConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent arg1) {
        Log.d(TAG, "onReceive: ");
        if (connectivityReceiverListener != null) {
            if (!mCurrentState.equalsIgnoreCase(String.valueOf(isNetworkConnected(context)))) {
                mCurrentState = String.valueOf(isNetworkConnected(context));
                connectivityReceiverListener.onNetworkConnectionChanged(isNetworkConnected(context));
            }
        }
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
