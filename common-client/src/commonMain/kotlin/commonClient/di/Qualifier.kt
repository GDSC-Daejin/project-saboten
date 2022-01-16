package commonClient.di

@OptIn(ExperimentalMultiplatform::class)
@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@OptionalExpectation
expect annotation class Qualifier()