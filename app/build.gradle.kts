import convention.plugin.notion.query.NotionQueryBuilder
import convention.plugin.notion.query.sort.Direction
import convention.plugin.notion.query.sort.Timestamp
import java.util.Properties

plugins {
    alias(libs.plugins.convention.plugin.application)
    alias(libs.plugins.convention.plugin.dagger.hilt)
    alias(libs.plugins.convention.plugin.mvi.template)
    alias(libs.plugins.convention.plugin.notion.string)
}

android {
    namespace = "com.yunho.app"
}

stringboard {
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
    }

    notionApiKey = localProperties.getProperty("NOTION_API_KEY", "")
    dataSourceId = localProperties.getProperty("DATA_SOURCE_ID", "")
    outputDir = "${project.rootDir}/feature/src/main/res"
    queryBuilder = NotionQueryBuilder()
        .filter {
            multiSelect { "Part" doesNotContain "Server" } and
                    select { "Status" equals "InProgress" } and
                    richText { "String: BASE" contains "hello" } and
                    richText { "String: KOR" contains "안녕" } and
                    richText { "String: JPN" contains "こんにちは" } and
                    richText { "Resource ID" equals "id-1" } or
                    checkBox { "Deprecated" equals true }
        }.sort {
            property { "Resource ID" by Direction.DESCENDING }
            timestamp { Timestamp.CREATED_TIME by Direction.ASCENDING }
        }
}

dependencies {
    implementation(projects.feature)
    implementation(projects.data)
}
