plugins {
    `java-library`
    alias(libs.plugins.jcommon)
    alias(libs.plugins.bundler)
}

group = "net.okocraft.oofportalcanceller"
version = "1.0.0"

val apiVersion = "1.19"

jcommon {
    javaVersion = JavaVersion.VERSION_21

    setupPaperRepository()

    commonDependencies {
        compileOnly(libs.paper.api)
    }
}

bundler {
    copyToRootBuildDirectory("OOFPortalCanceller-${project.version}")
    replacePluginVersionForPaper(project.version, apiVersion)
}
