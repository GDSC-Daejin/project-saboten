package commonClient.di

import commonClient.data.remote.SabotenApiHttpClient
import commonClient.data.repository.UserRepositoryImp
import commonClient.domain.repository.UserRepository
import commonClient.domain.usecase.user.GetMe
import commonClient.presentation.HomeScreenViewModel
import io.ktor.client.engine.*
import org.koin.dsl.module


fun dataModule() = module {

    single { SabotenApiHttpClient(get<HttpClientEngineFactory<*>>()) }

    /* Repository */
    single<UserRepository> { UserRepositoryImp() }

}

fun domainModule() = module {

    /* UseCase */
    single { GetMe(get()) }

}

fun presentationModule() = module {
    single { HomeScreenViewModel(get()) }
}