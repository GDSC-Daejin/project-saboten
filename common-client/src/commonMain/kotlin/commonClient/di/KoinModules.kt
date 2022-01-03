package commonClient.di

import commonClient.data.remote.SabotenApiHttpClient
import commonClient.data.repository.PostRepositoryImp
import commonClient.data.repository.UserRepositoryImp
import commonClient.domain.repository.PostRepository
import commonClient.domain.repository.UserRepository
import commonClient.domain.usecase.post.GetPosts
import commonClient.domain.usecase.user.GetMe
import commonClient.presentation.HomeScreenViewModel
import io.ktor.client.engine.*
import org.koin.dsl.module


fun dataModule() = module {

    single { SabotenApiHttpClient(get<HttpClientEngineFactory<*>>()) }

    /* Repository */
    single<PostRepository> { PostRepositoryImp() }
    single<UserRepository> { UserRepositoryImp() }

}

fun domainModule() = module {

    /* UseCase */
    single { GetMe(get()) }
    single { GetPosts(get()) }

}

fun presentationModule() = module {
    single { HomeScreenViewModel(get(), get()) }
}