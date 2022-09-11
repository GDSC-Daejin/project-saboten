package commonClient.di.modules

import commonClient.data.remote.endpoints.*
import commonClient.data.repository.*
import commonClient.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    /* API */
    @get:[Binds]
    val UserApiImp.userApi: UserApi

    @get:[Binds]
    val PostApiImp.postApi: PostApi

    @get:[Binds]
    val AuthApiImp.authApi: AuthApi

    @get:[Binds]
    val CategoryApiImp.categoryApi: CategoryApi

    /* Repositories */
    @get:[Binds]
    val UserRepositoryImp.userRepository: UserRepository

    @get:[Binds]
    val CategoryRepositoryImp.categoryRepository: CategoryRepository

    @get:[Binds]
    val AppThemeSettingsRepositoryImp.appThemeSettingsRepository: AppThemeSettingsRepository

    @get:[Binds]
    val AuthRepositoryImp.authRepository: AuthRepository

    @get:[Binds]
    val PostRepositoryImp.postRepository : PostRepository

}