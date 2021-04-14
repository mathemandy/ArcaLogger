package ng.mathemandy.arcalogger.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.WorkManager;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;

import kotlinx.coroutines.Dispatchers;
import ng.mathemandy.arcalogger.R;
import ng.mathemandy.arcalogger.workmanager.LoggerWorker;

import static kotlinx.coroutines.CoroutineScopeKt.CoroutineScope;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideAppLaunchIcon();
        runWorkers();
    }

    void hideAppLaunchIcon() {
        PackageManager p = getPackageManager();
        ComponentName name = new ComponentName(this, SplashActivity.class);
        p.setComponentEnabledSetting(name, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    private void runWorkers() {
//        CoroutineScope(Dispatchers.getDefault())
////                .launch {
////            assertTrue(service1.isEmpty())
////
////            // start test
////            enqueuesWorkers()
////
////            // verify it worked
////            assertResponses(timeoutMs = 5_000)
//        }
    }

    private void enqueuesWorkers() {

//        WorkManager.getInstance(this)
//                .cancelAllWork()
//                .getResult()
//                .await()
//
//        enqueueWork<LoggerWorker>(createData(42))
//        enqueueWork<SimpleWorker>(createData(43))
    }

}