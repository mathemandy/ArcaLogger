package ng.mathemandy.arcalogger;

import android.app.Application;

import android.util.Log;
import io.reactivex.plugins.RxJavaPlugins;
import ng.mathemandy.arcalogger.di.AppModuleKt;
import org.koin.android.java.KoinAndroidApplication;
import org.koin.core.KoinApplication;

import static org.koin.core.context.DefaultContextExtKt.startKoin;


public class App  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Start Koin
        KoinApplication koin = KoinAndroidApplication.create(this)
                .modules(AppModuleKt.getAllModules());
        startKoin(koin);
        RxJavaPlugins.setErrorHandler(throwable -> Log.d("Arca", throwable.getMessage()));
    }
}
