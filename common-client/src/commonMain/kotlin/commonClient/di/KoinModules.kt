package commonClient.di

import commonClient.data.remote.SabotenApiHttpClient
import commonClient.data.remote.UserApi
import commonClient.data.remote.UserApiImp
import commonClient.data.repository.UserRepositoryImp
import commonClient.domain.repository.UserRepository
import commonClient.domain.usecase.user.GetMe
import commonClient.presentation.HomeScreenViewModel
import io.ktor.client.engine.*
import org.koin.dsl.module


fun dataModule() = module {

    single { SabotenApiHttpClient(get<HttpClientEngineFactory<*>>(), get()) }
    single<UserApi> { UserApiImp(get()) }

    /* Repository */
    single<UserRepository> { UserRepositoryImp(get()) }

}

fun domainModule() = module {

    /* UseCase */
    single { GetMe(get()) }

}

fun presentationModule() = module {
    single { HomeScreenViewModel(get()) }
}