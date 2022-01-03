package di

import io.ktor.client.engine.js.*
import org.koin.dsl.module

val jsKoinModule = module {
    module {
        single { Js }
    }
}