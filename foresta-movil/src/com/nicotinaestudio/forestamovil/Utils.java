package com.nicotinaestudio.forestamovil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class Utils {
	
	public static final String s3IdentityPoolID = "us-east-1:3984eca8-dd73-4083-8cb1-e822de69ef28";
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
