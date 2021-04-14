package ng.mathemandy.arcalogger.workmanager;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.RxWorker;
import androidx.work.WorkerParameters;
import io.reactivex.Single;
import ng.mathemandy.arcalogger.api.ApiDefinition;
import org.jetbrains.annotations.NotNull;

public class LoggerWorker extends RxWorker {

    ApiDefinition apiDefinition;
    LoggerWorkerService loggerWorkerService;

    public LoggerWorker(@NotNull LoggerWorkerService loggerWorkerService,
                        ApiDefinition apiDefinition,
                        Context appContext, @NotNull WorkerParameters params) {
        super(appContext, params);
        this.apiDefinition  = apiDefinition;
        this.loggerWorkerService  = loggerWorkerService;
    }

    @NonNull
    @NotNull
    @Override
    public Single<Result> createWork() {
        return apiDefinition.uploadData(loggerWorkerService.getUploadDate()).map(modelResponse -> Result.success());
    }

}
