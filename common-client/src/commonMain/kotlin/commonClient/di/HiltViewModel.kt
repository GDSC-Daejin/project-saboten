package commonClient.di

@OptionalExpectation
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@OptIn(ExperimentalMultiplatform::class)
expect annotation class HiltViewModel()