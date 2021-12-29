package app.saboten.commonClient.di

import app.saboten.commonClient.data.repository.PostRepositoryImp
import app.saboten.commonClient.data.repository.UserRepositoryImp
import app.saboten.commonClient.domain.repository.PostRepository
import app.saboten.commonClient.domain.repository.UserRepository
import app.saboten.commonClient.domain.usecase.post.GetPosts
import app.saboten.commonClient.domain.usecase.user.GetMe
import app.saboten.commonClient.presentation.HomeScreenViewModel
import org.koin.dsl.module

internal val domainModule = module {
    single<PostRepository> { PostRepositoryImp() }
    single<UserRepository> { UserRepositoryImp() }
}

internal val dataModule = module {
    single { GetMe(get()) }
    single { GetPosts(get()) }
}

internal val presentationModule = module {
    single { HomeScreenViewModel(get(), get()) }
}