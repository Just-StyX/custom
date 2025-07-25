plugins {
	java
	id("org.springframework.boot") version libs.versions.spring.boot.version.get()
	id("io.spring.dependency-management") version libs.versions.spring.dependency.management.get()
}

group = "jsl-group"
version = libs.versions.application.version.get()

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(libs.versions.java.version.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.springactuator)
	implementation(libs.micrometer.bom)
	implementation(libs.spring.aop)
	implementation(libs.prometheus.monitoring)
	implementation(libs.thymeleaf)
	implementation(libs.springweb)
	testImplementation(libs.springtest)
	testRuntimeOnly(libs.springtestlauncher)
}

tasks.jar {
	enabled = false
}

tasks.bootBuildImage {
	imageName = "${project.name}:${project.version}" // Set image name and tag
	environment = mapOf("BP_JVM_VERSION" to "24.*") // Set build environment variable
	docker {
		publishRegistry {
			url = project.findProperty("REGISTRY_URL") as? String? // Docker registry URL
			username = project.findProperty("REGISTRY_USERNAME") as? String? // Registry username
			password = project.findProperty("registryToken") as? String? // Registry token
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
