package ng.mathemandy.arcalogger.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import kotlin.Lazy;
import kotlinx.coroutines.Dispatchers;
import ng.mathemandy.arcalogger.R;
import ng.mathemandy.arcalogger.data.PersistenceStorage;
import ng.mathemandy.arcalogger.workmanager.LoggerWorker;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static androidx.work.PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS;
import static kotlinx.coroutines.CoroutineScopeKt.CoroutineScope;
import static org.koin.java.KoinJavaComponent.inject;

public class SplashActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final int RC_FINE_LOCATION_WIFI_STATE_PHONE_STATE = 459;
    private final Lazy<PersistenceStorage>  persistentStorage  = inject(PersistenceStorage.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        methodRequiresTwoPermission();

    }

    void hideAppLaunchIcon() {
        PackageManager p = getPackageManager();
        ComponentName name = new ComponentName(this, SplashActivity.class);
        p.setComponentEnabledSetting(name, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void runWorker() {
        if (!persistentStorage.getValue().hasScheduled()) {
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();

            // Create periodic worker with contraints & 15 minutes interval
            PeriodicWorkRequest uploadWorker = new PeriodicWorkRequest.Builder(
                    LoggerWorker.class, MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
                    .setConstraints(constraints)
                    .build();

            WorkManager.getInstance(this).enqueue(uploadWorker);
            persistentStorage.getValue().saveScheduledLog();
        }
    }

    @AfterPermissionGranted(RC_FINE_LOCATION_WIFI_STATE_PHONE_STATE)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.READ_PHONE_STATE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            runWorker();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.wifi_and_location__phone_rationale),
                    RC_FINE_LOCATION_WIFI_STATE_PHONE_STATE, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            runWorker();
        }
    }
}