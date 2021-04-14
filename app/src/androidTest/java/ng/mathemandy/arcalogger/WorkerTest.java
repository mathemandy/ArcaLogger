package ng.mathemandy.arcalogger;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.work.ListenableWorker;
import androidx.work.testing.TestListenableWorkerBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ng.mathemandy.arcalogger.workmanager.LoggerWorker;

import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class WorkerTest {
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void testLogger() {
        LoggerWorker worker = TestListenableWorkerBuilder.from(context, LoggerWorker.class)
                .build();
        worker.createWork().subscribe(result ->
                assertThat(result, is(ListenableWorker.Result.success())));
    }
}

