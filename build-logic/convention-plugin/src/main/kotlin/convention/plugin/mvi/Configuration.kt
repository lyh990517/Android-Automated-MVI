package convention.plugin.mvi

import org.gradle.api.GradleException
import org.gradle.api.Project
import java.io.File

data class Configuration(
    val srcRoot: File,
    val basePackage: String,
    val featurePath: String,
) {
    private val basePath = basePackage.replace('.', '/')
    val featureName = featurePath.replaceFirstChar { it.uppercaseChar() }
    val featureDir = srcRoot.resolve("$basePath/$featurePath")
    val navigationDir = srcRoot.resolve("$basePath/$NAVIGATION")
    val appDir = srcRoot.resolve(basePath)
    val intentDir = featureDir.resolve(INTENT).ensure()
    val viewDir = featureDir.resolve(VIEW).ensure()
    val viewModelDir = viewDir.resolve(VIEW_MODEL).ensure()
    val componentDir = viewDir.resolve(VIEW_COMPONENT).ensure()

    private fun File.ensure() = apply { mkdirs() }

    companion object {
        private const val NAVIGATION = "navigation"
        private const val INTENT = "intent"
        private const val VIEW = "view"
        private const val VIEW_MODEL = "model"
        private const val VIEW_COMPONENT = "component"
        private const val FEATURE_PATH = "featurePath"
        private const val BASE_PACKAGE = "basePackage"
        private const val FEATURE_MODULE = "feature/src/main/kotlin"

        fun Project.toConfiguration(): Configuration {
            val featurePath = findProperty(FEATURE_PATH)?.toString() ?: throw GradleException("❌ Please pass -PfeaturePath=FeatureName")
            val basePackage = findProperty(BASE_PACKAGE)?.toString() ?: throw GradleException("❌ Please pass -PbasePackage=com.example.app")

            return Configuration(
                srcRoot = rootProject.file(FEATURE_MODULE),
                basePackage = basePackage,
                featurePath = featurePath
            )
        }
    }
}
