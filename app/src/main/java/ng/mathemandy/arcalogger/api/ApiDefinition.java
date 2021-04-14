package ng.mathemandy.arcalogger.api;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ng.mathemandy.arcalogger.BuildConfig;
import ng.mathemandy.arcalogger.api.models.ModelRequest;
import ng.mathemandy.arcalogger.api.models.ModelResponse;
import retrofit2.Retrofit;

/**
 * Creates a network call to the internet
 * */
public class ApiDefinition {

    Retrofit retrofit;

    /**
     *  @param retrofit is provided using koin DI
     * */
    public ApiDefinition(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    /*
    * uploads data to server
    * @param log is the data to be uploaded
    * */
    public Single<ModelResponse> uploadData(ModelRequest log) {
        ApiService apiService = retrofit.create(ApiService.class);
        return apiService.uploadLogStatus(log)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
