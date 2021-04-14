package ng.mathemandy.arcalogger.api.models;


public class UserLocation {
    private int latitude;
    private int longitude;

    public UserLocation(int longitude, int latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
