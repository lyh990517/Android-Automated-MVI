package convention.plugin.extension.function

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun DependencyHandlerScope.androidTestImplementation(project: Provider<MinimalExternalModuleDependency>) {
    add("androidTestImplementation", project)
}

internal fun DependencyHandlerScope.debugImplementation(project: Provider<MinimalExternalModuleDependency>) {
    add("debugImplementation", project)
}

internal fun DependencyHandlerScope.implementation(project: Project) {
    add("implementation", project)
}

internal fun DependencyHandlerScope.implementation(project: Provider<MinimalExternalModuleDependency>) {
    add("implementation", project)
}

internal fun DependencyHandlerScope.kapt(path: Provider<MinimalExternalModuleDependency>) {
    add("kapt", path)
}

internal fun DependencyHandlerScope.ksp(path: Provider<MinimalExternalModuleDependency>) {
    add("ksp", path)
}

internal fun DependencyHandlerScope.testImplementation(project: Provider<MinimalExternalModuleDependency>) {
    add("testImplementation", project)
}
