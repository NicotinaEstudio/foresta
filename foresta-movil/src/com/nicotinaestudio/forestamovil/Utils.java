package com.nicotinaestudio.forestamovil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class Utils {
	
	public static final String s3IdentityPoolID = "s3IdentityPoolID";
	public static final String s3BucketName = "foresta";
	
	public static final String appLogName = "RetoForestal";
	
	public static void Log(final String m){
		android.util.Log.i(appLogName, m);
	}
	
	public static boolean isNetworkAvailable(final Context ctx) {
		ConnectivityManager connectivityManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();

		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	
	public static void enviarSMS(String texto){
		
	}
}
