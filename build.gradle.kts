import pl.allegro.tech.build.axion.release.domain.ChecksConfig
import pl.allegro.tech.build.axion.release.domain.TagNameSerializationConfig

plugins {
    id("java")
    id("maven")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.10.0"
    id("pl.allegro.tech.build.axion-release") version "1.9.3"
}

scmVersion {

    versionIncrementer("incrementMinor")

    ignoreUncommittedChanges = true
    useHighestVersion = true

    tag(closureOf<TagNameSerializationConfig> {
        prefix = ""
        versionSeparator = ""
    })

    checks(closureOf<ChecksConfig> {
        aheadOfRemote = false
        uncommittedChanges = false
    })
}

group = "uk.co.mulecode"
version = scmVersion.version

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {

    implementation("org.apache.commons:commons-collections4:4.0")
    implementation("org.apache.commons:commons-lang3:3.9")
    implementation("org.json:json:20180813")
    implementation("com.jayway.jsonpath:json-path:2.4.0")
    implementation("commons-io:commons-io:2.6")
    testImplementation("junit", "junit", "4.12")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testImplementation("com.jayway.jsonpath:json-path-assert:2.4.0")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

pluginBundle {
    website = "https://github.com/rafael-bm/file-template-gradle-plugin"
    vcsUrl = "https://github.com/rafael-bm/file-template-gradle-plugin"
    tags = listOf("file", "template", "replace", "placeholder", "plugins")
}

gradlePlugin {
    plugins {
        create("fileTemplate") {
            id = "uk.co.mulecode.file-template"
            displayName = "File Template"
            description = "Replace placeholders from template files."
            implementationClass = "uk.co.mulecode.filetemplate.plugin.FileTemplatePlugin"
        }
    }
}