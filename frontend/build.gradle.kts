plugins {
    kotlin("js")
    kotlin("plugin.serialization")
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
            distribution {
                directory = file("${project.buildDir}/dist")
            }
            binaries.executable()
        }
    }
}

dependencies {
    val kotlinWrappersVersion = "18.2.0-pre.687"
    val ktorVersion = "2.3.7"
    val serializationVersion = "1.6.2"

    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$kotlinWrappersVersion")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$kotlinWrappersVersion")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.11.1-pre.687")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-js:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
}