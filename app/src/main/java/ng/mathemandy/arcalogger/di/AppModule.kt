package ng.mathemandy.arcalogger.di

import ng.mathemandy.arcalogger.api.ApiDefinition
import ng.mathemandy.arcalogger.api.HttpFactory
import ng.mathemandy.arcalogger.workmanager.LoggerWorker
import ng.mathemandy.arcalogger.workmanager.LoggerWorkerService
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module


val workerScopedModule = module {
    single { LoggerWorkerService() }
    worker { LoggerWorker(get(), get(), get()) }
}

val networkModule  = module  {
    single  {HttpFactory.provideOKHttp(get())}
    single  { HttpFactory.provideHttpLoggingInterceptor() }
    single  { HttpFactory.provideGson() }
    single  { HttpFactory.provideRetrofit(get(), get()) }
}

val apiDefinitionModule  = module {
    single { ApiDefinition(get()) }
}
val allModules  =  workerScopedModule + networkModule + apiDefinitionModule