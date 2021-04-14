package ng.mathemandy.arcalogger;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.work.Configuration;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.testing.SynchronousExecutor;
import androidx.work.testing.TestDriver;
import androidx.work.testing.WorkManagerTestInitHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import ng.mathemandy.arcalogger.workmanager.LoggerWorker;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.work.PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class WorkManagerInstrumentationTest {
    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getTargetContext();
        Configuration config = new Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .setExecutor(new SynchronousExecutor())
                .build();

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(
                context, config);
    }

    @Test
    public void testSimpleEchoWorker() throws Exception {
        // Define input data


        // Create request
        PeriodicWorkRequest uploadWorker = new PeriodicWorkRequest.Builder(
                LoggerWorker.class, 15, TimeUnit.MINUTES)
                .build();

        WorkManager workManager = WorkManager.getInstance(getApplicationContext());
        TestDriver testDriver = WorkManagerTestInitHelper.getTestDriver(getApplicationContext());

        // Enqueue and wait for result. This also runs the Worker synchronously
        // because we are using a SynchronousExecutor.
        workManager.enqueue(uploadWorker).getResult().get();

        testDriver.setPeriodDelayMet(uploadWorker.getId());
        // Get WorkInfo and outputData
        WorkInfo workInfo = workManager.getWorkInfoById(uploadWorker.getId()).get();

        // Assert
        assertThat(workInfo.getState(), is(WorkInfo.State.ENQUEUED));

    }
}
