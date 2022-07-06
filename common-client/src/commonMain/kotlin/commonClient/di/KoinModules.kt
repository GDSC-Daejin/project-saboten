package commonClient.di

import com.russhwolf.settings.Settings
import commonClient.data.cache.MeCache
import commonClient.data.remote.SabotenApiHttpClient
import commonClient.data.remote.endpoints.*
import commonClient.data.repository.CategoryRepositoryImp
import commonClient.data.repository.UserRepositoryImp
import commonClient.domain.repository.CategoryRepository
import commonClient.domain.repository.UserRepository
import commonClient.domain.usecase.user.GetMeUseCase
import commonClient.presentation.AppViewModel
import commonClient.presentation.HomeScreenViewModel
import commonClient.utils.AuthTokenManager
import commonClient.utils.EncryptedSettingsHolder
import io.ktor.client.engine.*
import org.koin.core.qualifier.named
import org.koin.dsl.module


fun dataModule() = module {

    single {
        SabotenApiHttpClient(get<HttpClientEngineFactory<*>>())
    }

    single<UserApi> { UserApiImp(get()) }
    single<CategoryApi> { CategoryApiImp(get()) }
    single<AuthApi> { AuthApiImp(get()) }

    single { MeCache(get()) }

    /* Repository */
    single<UserRepository> { UserRepositoryImp(get(), get()) }
    single<CategoryRepository> { CategoryRepositoryImp(get()) }

    single { AuthTokenManager(get()) }
}

fun domainModule() = module {

    /* UseCase */
    single { GetMeUseCase(get()) }

}

fun presentationModule() = module {
    single { AppViewModel(get(), get(), get()) }
    single { HomeScreenViewModel(get()) }
}