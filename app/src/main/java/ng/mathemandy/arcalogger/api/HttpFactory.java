package ng.mathemandy.arcalogger.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpFactory {

    static String BASE_URL = "https://api.qa.arca-payments.network/tms/devicestatus/v1/";

    public static OkHttpClient provideOKHttp(Interceptor httpLoggerInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(httpLoggerInterceptor);
        return builder.build();
    }

    public static Interceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public static Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    public static Retrofit provideRetrofit(OkHttpClient httpClient, Gson gson) {
        return  new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

}
