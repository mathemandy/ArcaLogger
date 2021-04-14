package ng.mathemandy.arcalogger.workmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import com.an.deviceinfo.device.model.Battery;
import com.an.deviceinfo.device.model.Device;
import com.an.deviceinfo.device.model.Network;
import com.an.deviceinfo.location.DeviceLocation;
import com.an.deviceinfo.location.LocationInfo;
import com.an.deviceinfo.userapps.UserAppInfo;
import com.an.deviceinfo.userapps.UserApps;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ng.mathemandy.arcalogger.api.models.ModelRequest;
import ng.mathemandy.arcalogger.api.models.UserLocation;

import static android.content.Context.WIFI_SERVICE;

public class LoggerWorkerService {

    Context context;

    public LoggerWorkerService(Context context) {
        super();
        this.context = context;
    }

    ModelRequest getUploadData() {
        return new ModelRequest(
                getAppVersion(),
                hasGoogleMap(),
                getCurrentDateTime(),
                getSignalStrength(),
                getUserApps().size(),
                new UserLocation(getLongitude(),
                        getLatitude()),
                getSerialNumber(),
                getNetworkType(),
                getConnectivityStatus(),
                getBatteryLevel()
        );
    }

    int getBatteryLevel() {
        Battery battery = new Battery(context);
        return battery.getBatteryPercent();
    }

    String getAppVersion() {
        String googleMapVersion = "N/A";
        if (hasGoogleMap()) {
            UserApps app = findAppById("com.google.android.apps.maps");
            googleMapVersion = app.getVersionName();
        }
        return googleMapVersion;
    }

    UserApps findAppById(String id) {
        for (UserApps userApp : getUserApps()) {
            if (userApp.getPackageName().equalsIgnoreCase(id)) {
                return userApp;
            }
        }
        return null;
    }

    Boolean hasGoogleMap() {
        return findAppById("com.google.android.apps.maps") != null;
    }

    List<UserApps> getUserApps() {
        UserAppInfo userAppInfo = new UserAppInfo(context);
        return userAppInfo.getInstalledApps(false);
    }

    String getNetworkType() {
        Network network = new Network(context);
        return network.getNetworkType();
    }

    DeviceLocation getDeviceLocationInfo() {
        LocationInfo locationInfo = new LocationInfo(context);
        return locationInfo.getLocation();
    }

    int getLongitude() {
        if (getDeviceLocationInfo().getLongitude() != null) {
            return getDeviceLocationInfo().getLongitude().intValue();
        }

        return 0;
    }

    int getLatitude() {
        if (getDeviceLocationInfo().getLatitude() != null) {
            return getDeviceLocationInfo().getLatitude().intValue();
        }

        return 0;
    }

    String getSerialNumber() {
        Device device = new Device(context);
        String serialNumber = device.getSerial();
        if (serialNumber.equals(Build.UNKNOWN)) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    serialNumber = Build.getSerial();
                } else {
                    serialNumber = Build.SERIAL;
                }
            } catch (SecurityException e) {
                e.printStackTrace();
                Log.d("error", String.valueOf(e.getMessage()));
            }
        }
        return serialNumber;
    }

    int getSignalStrength() {
        return signalStrength(context);
    }

    String getConnectivityStatus() {
        String connectivityStatus = "";
        if (isNetworkAvailable(context)) {
            connectivityStatus = "connected";
        } else {
            connectivityStatus = "not connected";
        }
        return connectivityStatus;
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context == null) return false;


        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true;
                    }
                }
            } else {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int signalStrength(Context context) {
        int signalStrength = 0;
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            signalStrength = wifiInfo.getLinkSpeed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signalStrength;
    }

    public static String getCurrentDateTime() {
        Date today;
        SimpleDateFormat formatter;
        today = new Date();
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return formatter.format(today);

    }

}
