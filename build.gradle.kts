plugins {
	kotlin("jvm") version "1.9.22" apply false
	kotlin("plugin.spring") version "1.9.22" apply false
	kotlin("plugin.jpa") version "1.9.22" apply false
	kotlin("js") version "1.9.22" apply false  // Add this
	kotlin("multiplatform") version "1.9.22" apply false
	kotlin("plugin.serialization") version "1.9.22" apply false
	id("org.springframework.boot") version "3.4.0" apply false
	id("io.spring.dependency-management") version "1.1.6" apply false
}

allprojects {
	group = "by.bratchykau"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

// Configuration applied to all subprojects
subprojects {
	when (project.name) {
		"src" -> {
			apply(plugin = "org.jetbrains.kotlin.jvm")
			apply(plugin = "org.jetbrains.kotlin.plugin.spring")
			apply(plugin = "org.springframework.boot")
			apply(plugin = "io.spring.dependency-management")

			tasks.getByName("processResources") {
				dependsOn(":frontend:build")
				doLast {
					copy {
						from("${project(":frontend").buildDir}/dist")
						into("${buildDir}/resources/main/static")
					}
				}
			}


			dependencies {
				"implementation"("org.jetbrains.kotlin:kotlin-reflect")
				"implementation"("org.springframework.boot:spring-boot-starter-data-jpa")
				"implementation"("org.springframework.boot:spring-boot-starter-validation")
				"implementation"("org.springframework.boot:spring-boot-starter-web")
				"implementation"("org.postgresql:postgresql")
				"implementation"("org.flywaydb:flyway-core:10.4.1")
				"implementation"("org.flywaydb:flyway-database-postgresql:10.4.1")
				"implementation"("org.springframework.boot:spring-boot-starter-actuator")
				"implementation"("org.springframework.retry:spring-retry")
				"implementation"("org.springframework.boot:spring-boot-starter-aop")

				"developmentOnly"("org.springframework.boot:spring-boot-devtools")

				"testImplementation"("org.springframework.boot:spring-boot-starter-test")
				"testImplementation"("org.jetbrains.kotlin:kotlin-test-junit5")
				"testImplementation"("org.mockito:mockito-core:5.11.0")
				"testImplementation"("org.mockito.kotlin:mockito-kotlin:5.2.1")
				"testImplementation"("org.junit.jupiter:junit-jupiter:5.10.2")
				"testImplementation"("org.junit.jupiter:junit-jupiter-api")
				"testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
			}

			// Configure Java version
			configure<JavaPluginExtension> {
				toolchain {
					languageVersion.set(JavaLanguageVersion.of(21))
				}
			}

			// Configure Kotlin compilation
			tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
				kotlinOptions {
					freeCompilerArgs = listOf("-Xjsr305=strict")
					jvmTarget = "21"
				}
			}

			// Configure testing
			tasks.withType<Test> {
				useJUnitPlatform()
			}

			// Configure JPA entity classes
			configure<org.jetbrains.kotlin.allopen.gradle.AllOpenExtension> {
				annotation("jakarta.persistence.Entity")
				annotation("jakarta.persistence.MappedSuperclass")
				annotation("jakarta.persistence.Embeddable")
			}
		}
		"frontend" -> {
			apply(plugin = "org.jetbrains.kotlin.js")
		}
	}
}

// Root project should be empty
//tasks.register("clean", Delete::class) {
//	delete(rootProject.buildDir)
//}

tasks.register<ProjectStateGenerator>("generateProjectState") {
	group = "documentation"
	description = "Generates PROJECT_STATE.md with current project state"
}