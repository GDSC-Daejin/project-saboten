package commonClient.di

import commonClient.data.repository.PostRepositoryImp
import commonClient.data.repository.UserRepositoryImp
import commonClient.domain.repository.PostRepository
import commonClient.domain.repository.UserRepository
import commonClient.domain.usecase.post.GetPosts
import commonClient.domain.usecase.user.GetMe
import commonClient.presentation.HomeScreenViewModel
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