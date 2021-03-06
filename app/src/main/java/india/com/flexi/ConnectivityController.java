package india.com.flexi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class ConnectivityController {
	static InternetCheckInterFace mCheckInterFace;
	static String mSourceEvent;

	public static final String TAG = ConnectivityController.class.getName();

	/**
	 * Checking is connected to Internet
	 */

	public static boolean isNetworkAvailable(Context context,
			InternetCheckInterFace checkInterFace, String sourceEvent) {
		mCheckInterFace = checkInterFace;
		mSourceEvent = sourceEvent;
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {

				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			Log.e(TAG, "Unable to check is internet Avaliable", e);
		}
		return false;
	}

	public static boolean isNetworkAvailable(Context context) {

		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {

				return true;
			}

		} catch (Exception e) {
			Log.e(TAG, "Unable to check is internet Avaliable", e);
		}
		return false;
	}

}
