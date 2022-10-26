package commonClient.di

import commonClient.data.cache.createDataStore
import commonClient.data.cache.dataStoreFileName
import commonClient.presentation.AppViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

actual fun platformModule() = module {
    single { createDataStore { androidContext().filesDir.resolve(dataStoreFileName).absolutePath } }

    viewModel { AppViewModel(get(), get(), get()) }
}