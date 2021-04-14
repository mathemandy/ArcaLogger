package ng.mathemandy.arcalogger.api;

import io.reactivex.Observable;
import ng.mathemandy.arcalogger.api.models.ModelRequest;
import ng.mathemandy.arcalogger.api.models.ModelResponse;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {


    @POST("logStatus")
    Observable<ModelResponse> uploadLogStatus(@Body ModelRequest log);
}
