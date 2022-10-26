package commonClient.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module
import org.koin.ksp.generated.module

expect fun platformModule(): org.koin.core.module.Module

fun sharedModule() = module {
    includes(
        SharedModule().module,
        platformModule()
    )
}

@Module
@ComponentScan("commonClient")
class SharedModule