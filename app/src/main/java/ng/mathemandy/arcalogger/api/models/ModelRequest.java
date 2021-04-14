package ng.mathemandy.arcalogger.api.models;

public class ModelRequest {
    String appVersion;
    int batteryLevel;
    String dataUsageCollationTime;
    UserLocation userLocation;

}

class UserLocation {
    int latitude;
    int longitude;
}
