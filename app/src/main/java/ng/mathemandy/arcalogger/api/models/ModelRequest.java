package ng.mathemandy.arcalogger.api.models;

public class ModelRequest {
    private String appVersion;
    private int batteryLevel;
    private String dataUsageCollationTime;
    private UserLocation userLocation;
    private boolean hasApp;
    private int signalStrength;
    private int numberOfApps;
    private String terminalId;
    private String networkType;
    private String connectivityStatus;

    public ModelRequest(String appVersion, boolean hasApp, String dataUsageCollationTime,  int signalStrength, int numberOfApps, UserLocation location, String terminalId, String networkType, String connectivityStatus, int batteryLevel) {
        this.dataUsageCollationTime = dataUsageCollationTime;
        this.appVersion = appVersion;
        this.hasApp = hasApp;
        this.signalStrength = signalStrength;
        this.numberOfApps = numberOfApps;
        this.userLocation = location;
        this.terminalId = terminalId;
        this.networkType = networkType;
        this.connectivityStatus = connectivityStatus;
        this.batteryLevel = batteryLevel;
    }

}
