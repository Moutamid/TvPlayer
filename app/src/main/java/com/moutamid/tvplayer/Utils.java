package com.moutamid.tvplayer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


public class Utils {

	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		/*if (mConnectManager != null) {
			NetworkInfo[] mNetworkInfo = mConnectManager.getAllNetworkInfo();
			for (int i = 0; i < mNetworkInfo.length; i++) {
				if (mNetworkInfo[i].getState() == NetworkInfo.State.CONNECTED)
					return true;
			}
		}
		return false;*/

		NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

		boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

/*		boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
				connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);*/
		
		return isConnected;
	}
}
