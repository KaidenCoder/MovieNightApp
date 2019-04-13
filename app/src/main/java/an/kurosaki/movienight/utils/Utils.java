package an.kurosaki.movienight.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {
    public static boolean isNetworkAvailable(Activity activity) {
        // ConnectivityManager is a class that answers queries about the state of network
        // connectivity. It also notifies applications when network connectivity changes.
        ConnectivityManager manager = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Instantiate a NetworkInfo object. It describes the status of a network interface.
        // Use getActiveNetworkInfo() to get an instance that represents the current network
        // connection. Requires the ACCESS_NETWORK_STATE permission.
        NetworkInfo networkInfo = null;
        if (manager != null) {
            networkInfo = manager.getActiveNetworkInfo();
        }

        boolean isAvailable = false;
        // If network is present and connected to the web
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }
}
