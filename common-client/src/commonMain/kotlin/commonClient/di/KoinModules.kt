package commonClient.di

import commonClient.data.cache.MeCache
import commonClient.data.remote.SabotenApiHttpClient
import commonClient.data.remote.endpoints.UserApi
import commonClient.data.remote.endpoints.UserApiImp
import commonClient.data.repository.CategoryRepositoryImp
import commonClient.data.repository.UserRepositoryImp
import commonClient.domain.repository.CategoryRepository
import commonClient.domain.repository.UserRepository
import commonClient.domain.usecase.user.GetMeUseCase
import commonClient.presentation.HomeScreenViewModel
import io.ktor.client.engine.*
import org.koin.dsl.module


fun dataModule() = module {

    single { SabotenApiHttpClient(get<HttpClientEngineFactory<*>>(), get()) }
    single<UserApi> { UserApiImp(get()) }
    single { MeCache(get()) }

    /* Repository */
    single<UserRepository> { UserRepositoryImp(get(), get()) }
    single<CategoryRepository> { CategoryRepositoryImp(get()) }

}

fun domainModule() = module {

    /* UseCase */
    single { GetMeUseCase(get()) }

}

fun presentationModule() = module {
    single { HomeScreenViewModel(get()) }
}