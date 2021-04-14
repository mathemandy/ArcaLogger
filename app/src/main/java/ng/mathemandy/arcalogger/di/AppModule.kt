package ng.mathemandy.arcalogger.di

import ng.mathemandy.arcalogger.workmanager.LoggerWorker
import ng.mathemandy.arcalogger.workmanager.LoggerWorkerService
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val appModule  = module {

}
val workerScopedModule = module {
    single { LoggerWorkerService() }
    worker { LoggerWorker(get(), get(), get()) }
}

val allModules  = appModule + workerScopedModule