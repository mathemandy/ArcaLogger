package ng.mathemandy.arcalogger.api;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ng.mathemandy.arcalogger.api.models.ModelRequest;
import ng.mathemandy.arcalogger.api.models.ModelResponse;
import retrofit2.Retrofit;

public class ApiDefinition {

    Retrofit retrofit;
    ApiService apiService = retrofit.create(ApiService.class);

    public ApiDefinition(Retrofit retrofit) {
        this.retrofit  = retrofit;
    }

   public Single<ModelResponse> uploadData(ModelRequest log) {
        return apiService.uploadLogStatus(log)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
