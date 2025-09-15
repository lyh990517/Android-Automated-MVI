package convention.plugin.mvi

sealed interface Navigation {
    val configuration: Configuration

    fun update()

    data class Route(
        override val configuration: Configuration
    ) : Navigation {
        override fun update() {
            with(configuration) {
                val routeFile = navigationDir.resolve("Route.kt")

                if (routeFile.exists()) {
                    val content = routeFile.readText()
                    val newRoute = "\n    @Serializable\n    data object $featureName : Route"
                    val updatedContent = content.replace("}", "$newRoute\n}")
                    routeFile.writeText(updatedContent)
                }
            }
        }
    }

    data class NavHost(
        override val configuration: Configuration
    ) : Navigation {
        override fun update() {
            with(configuration) {
                val appFile = appDir.resolve("App.kt")

                if (appFile.exists()) {
                    val content = appFile.readText()
                    val newComposable = "            composable<Route.$featureName> {\n                $featureName(modifier = Modifier.fillMaxSize())\n            }"
                    val importStatement = "import $basePackage.$featurePath.view.$featureName"

                    var updatedContent = content
                    if (!content.contains(importStatement)) {
                        val lastImportIndex = content.lastIndexOf("import ")
                        val nextLineIndex = content.indexOf('\n', lastImportIndex)
                        val beforeImport = content.substring(0, nextLineIndex)
                        val afterImport = content.substring(nextLineIndex)
                        updatedContent = "$beforeImport\n$importStatement$afterImport"
                    }

                    val navHostEndIndex = updatedContent.lastIndexOf("        }")
                    if (navHostEndIndex != -1) {
                        val beforeNavHostEnd = updatedContent.substring(0, navHostEndIndex)
                        val afterNavHostEnd = updatedContent.substring(navHostEndIndex)
                        updatedContent = "$beforeNavHostEnd\n$newComposable\n$afterNavHostEnd"
                    }

                    appFile.writeText(updatedContent)
                }
            }
        }
    }

    data class NavController(
        override val configuration: Configuration
    ) : Navigation {
        override fun update() {
            with(configuration) {
                val navControllerFile = navigationDir.resolve("NavController.kt")

                if (navControllerFile.exists()) {
                    val content = navControllerFile.readText()
                    val newFunction = """
                fun NavController.navigateTo$featureName(
                    navOptions: NavOptions = navOptions {
                        launchSingleTop = true
                    }
                ) {
                    navigate(
                        route = Route.$featureName,
                        navOptions = navOptions
                    )
                }
                """.trimIndent()

                    if (content.contains("fun NavController.navigateTo$featureName(")) {
                        return
                    }

                    val updatedContent = content + newFunction
                    navControllerFile.writeText(updatedContent)
                }
            }
        }
    }

    companion object {
        fun update(
            configuration: Configuration
        ) {
            with(configuration) {
                println("  🛣️  Updating Route.kt with ${featureName}...")
                Route(this).update()

                println("  🏠 Updating App.kt NavHost with ${featureName}...")
                NavHost(this).update()

                println("  🎯 Updating NavController.kt with navigateTo${featureName}()...")
                NavController(this).update()
            }
        }
    }
}
