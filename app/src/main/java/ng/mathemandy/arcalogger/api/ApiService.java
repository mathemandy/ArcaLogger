package ng.mathemandy.arcalogger.api;

import io.reactivex.Single;
import ng.mathemandy.arcalogger.BuildConfig;
import ng.mathemandy.arcalogger.api.models.ModelRequest;
import ng.mathemandy.arcalogger.api.models.ModelResponse;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Interface for posting data to Server
 * */

public interface ApiService {

    @Headers("key: " + BuildConfig.API_KEY)
    @POST("logStatus")
    Single<ModelResponse> uploadLogStatus(@Body ModelRequest log);
}
