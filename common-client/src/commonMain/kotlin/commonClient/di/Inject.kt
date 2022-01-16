package commonClient.di

import kotlin.annotation.*

@OptIn(ExperimentalMultiplatform::class)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@OptionalExpectation
expect annotation class Inject()