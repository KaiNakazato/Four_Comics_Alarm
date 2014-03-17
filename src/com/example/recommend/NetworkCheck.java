package com.example.recommend;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.framecomics.R;

public class NetworkCheck {

	public static boolean netWorkCheck(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null) {
			return info.isConnected();
		} else {
			return false;
		}
	}

	public static void netWOrkErroDialog(Context context) {
		new AlertDialog.Builder(context)
				.setMessage(
						context.getResources().getString(R.string.network_erro))
				.setCancelable(false)
				.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
	}

}
