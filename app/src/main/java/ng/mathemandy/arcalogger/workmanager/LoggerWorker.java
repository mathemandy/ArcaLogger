package ng.mathemandy.arcalogger.workmanager;

import android.content.Context;

import androidx.work.CoroutineWorker;
import androidx.work.WorkerParameters;

import ng.mathemandy.arcalogger.api.ApiDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.coroutines.Continuation;

public class LoggerWorker extends CoroutineWorker {

    ApiDefinition apiDefinition;
    LoggerWorkerService loggerWorkerService;

    public LoggerWorker(@NotNull LoggerWorkerService loggerWorkerService,
                        ApiDefinition apiDefinition,
                        Context appContext, @NotNull WorkerParameters params) {
        super(appContext, params);
        this.apiDefinition  = apiDefinition;
        this.loggerWorkerService  = loggerWorkerService;
    }

    @Nullable
    @Override
    public Object doWork(@NotNull Continuation<? super Result> continuation) {

        apiDefinition.uploadData(loggerWorkerService.getUploadDate());

        return null;
    }

}
