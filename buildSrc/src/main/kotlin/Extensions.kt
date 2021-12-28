import org.gradle.api.Project
import org.gradle.kotlin.dsl.get

fun Project.kapt(path: Any) {
    configurations["kapt"].dependencies.add(project.dependencies.create(path))
}

fun Project.ksp(path: Any) {
    configurations["ksp"].dependencies.add(project.dependencies.create(path))
}