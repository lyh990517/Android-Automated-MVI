package convention.plugin

import convention.plugin.mvi.Navigation
import convention.plugin.mvi.Configuration.Companion.toConfiguration
import convention.plugin.mvi.Mvi
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * ./gradlew -PfeaturePath=test -PbasePackage=com.yunho.feature generateMviTemplate
 */
class MviTemplatePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("generateMviTemplate") {
            group = "mvi"
            description = "Generates MVI template files for a given feature, under a configurable base package"

            doLast {
                val configuration = project.toConfiguration()

                with(configuration) {
                    println("▶ Generating MVI for feature='$featurePath' (class=$featureName) under basePackage='$basePackage'")
                    println("📁 Target directory: $featureDir")

                    println("🔧 Creating MVI components...")
                    Mvi.create(this)
                    println("✅ MVI components created successfully")

                    println("🚏 Updating navigation components...")
                    Navigation.update(this)
                    println("✅ Navigation components updated successfully")

                    try {
                        println("📝 Adding generated files to git...")
                        ProcessBuilder("git", "add", featureDir.absolutePath)
                            .directory(project.rootDir)
                            .start()
                            .waitFor()
                        println("✅ Files added to git successfully")
                    } catch (e: Exception) {
                        println("⚠️ Git add failed: ${e.message}")
                    }

                    println("🎉 MVI template for '$featureName' generated successfully at: $featureDir")
                }
            }
        }
    }
}
