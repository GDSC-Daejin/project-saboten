import org.gradle.api.Project
import org.gradle.kotlin.dsl.get
import java.io.ByteArrayOutputStream

fun Project.kapt(path: Any) {
    configurations["kapt"].dependencies.add(project.dependencies.create(path))
}

fun Project.ksp(path: Any) {
    configurations["ksp"].dependencies.add(project.dependencies.create(path))
}

val Project.gitBranch: String
    get() {
        val byteOut = ByteArrayOutputStream()
        exec {
            commandLine = "git rev-parse --abbrev-ref HEAD".split(" ")
            standardOutput = byteOut
        }
        return String(byteOut.toByteArray()).trim().replace("/", ":")
    }

val Project.gitDescribe: String
    get() {
        val stdout = ByteArrayOutputStream()
        rootProject.exec {
            commandLine = "git rev-parse --short HEAD -c".split(" ")
            standardOutput = stdout
        }
        return stdout.toString().trim()
    }

val Project.commitList: String
    get() {
        val byteOut = ByteArrayOutputStream()
        rootProject.exec {
            commandLine = "git log --pretty=format:\"%cn(%cr)-%s\" -n 5".split(" ")
            standardOutput = byteOut
        }
        return String(byteOut.toByteArray()).trim().replace("\n", "\n\t\t").replace("\"", "")
    }

val Project.developer: String
    get() {
        val byteOut = ByteArrayOutputStream()
        exec {
            commandLine = "git log --pretty=format:\"%cn\" -n 1".split(" ")
            standardOutput = byteOut
        }
        return String(byteOut.toByteArray()).trim().replace("/", ":")
    }

