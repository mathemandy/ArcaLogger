package ng.mathemandy.arcalogger.workmanager;

import android.content.Context;
import androidx.work.RxWorker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Single;
import kotlin.Lazy;
import ng.mathemandy.arcalogger.api.ApiDefinition;
import ng.mathemandy.arcalogger.api.models.ModelResponse;

import static org.koin.java.KoinJavaComponent.inject;

/*
 * Worker for scheduling work to be done in the background.
 * Using Rxworker so i can be able to get parse the result from
 * @param apidefinition
 * */
public class LoggerWorker extends RxWorker {

    private final Lazy<ApiDefinition> apiDefinition = inject(ApiDefinition.class);
    private final Lazy<LoggerWorkerService> loggerWorkerService = inject(LoggerWorkerService.class);

    public LoggerWorker(Context appContext, @NotNull WorkerParameters params) {
        super(appContext, params);
    }


    /*
     * Performs the work to be done.
     * Returns Result.Success whether or not the call failed so that work manager can enqueue the next task.
     * */

    @NotNull
    @Override
    public Single<Result> createWork() {
        return apiDefinition.getValue()
                .uploadData(loggerWorkerService.getValue().getUploadData())
                .onErrorReturnItem(new ModelResponse())
                .map(modelResponse -> Result.success());
    }
}
