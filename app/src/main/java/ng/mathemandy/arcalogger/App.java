package ng.mathemandy.arcalogger;

import android.app.Application;

import org.koin.android.java.KoinAndroidApplication;
import org.koin.core.KoinApplication;

import ng.mathemandy.arcalogger.di.AppModuleKt;

import static org.koin.core.context.DefaultContextExtKt.startKoin;


public class App  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Start Koin
        KoinApplication koin = KoinAndroidApplication.create(this)
                .modules(AppModuleKt.getAllModules());
        startKoin(koin);
    }
}
