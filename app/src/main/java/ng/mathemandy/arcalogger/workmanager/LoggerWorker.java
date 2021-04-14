package ng.mathemandy.arcalogger.workmanager;

import android.content.Context;

import androidx.work.CoroutineWorker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.coroutines.Continuation;

public class LoggerWorker extends CoroutineWorker {


    public LoggerWorker(@NotNull LoggerWorkerService loggerWorkerService,
                        Context appContext, @NotNull WorkerParameters params) {
        super(appContext, params);
    }

    @Nullable
    @Override
    public Object doWork(@NotNull Continuation<? super Result> continuation) {
        return null;
    }

}
