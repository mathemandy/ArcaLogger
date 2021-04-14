package ng.mathemandy.arcalogger.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PersistenceStorage {
    private static final String SCHEDULE_KEY = "505";
    SharedPreferences preferences;


    public PersistenceStorage(Context context) {
        this.preferences = PreferenceManager.
                getDefaultSharedPreferences(context);

    }

    public void saveScheduledLog(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(SCHEDULE_KEY, true);
        editor.apply();
    }

    public Boolean hasScheduled(){
        return  preferences.getBoolean(SCHEDULE_KEY, false);
    }

}
