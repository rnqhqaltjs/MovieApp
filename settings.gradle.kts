pluginManagement {
    includeBuild("build-logic")

    gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

rootProject.name = "MovieApp"
include(":app")
include(":feature:feed")
include(":core:domain")
include(":core:network")
include(":feature:setup")
include(":feature:main")
include(":core:designsystem")
include(":core:navigation")
include(":feature:alarm")
include(":core:data")
include(":core:model")
include(":core:database")
include(":feature:list")
include(":feature:edit")
include(":core:common")
include(":feature:login")
include(":core:datastore")
