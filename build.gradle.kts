/*
 * Copyright 2025-present CloudNetService team & contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.spotless.LineEnding
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar.Companion.shadowJar
import org.apache.tools.ant.filters.ReplaceTokens
import java.nio.charset.StandardCharsets

plugins {
  alias(libs.plugins.spotless)
  alias(libs.plugins.shadow) apply false
  alias(libs.plugins.legacyLoom) apply false
  alias(libs.plugins.fabricLoomRemap) apply false
  alias(libs.plugins.fabricLoomNoRemap) apply false
}

val targetJavaVersion = JavaVersion.VERSION_25

allprojects {
  group = "eu.cloudnetservice.cloudnet"
}

subprojects {
  apply(plugin = "java-library")
  apply(plugin = "com.gradleup.shadow")
  apply(plugin = "com.diffplug.spotless")

  repositories {
    mavenCentral()
    maven {
      name = "Fabric"
      url = uri("https://maven.fabricmc.net/")
    }
    maven {
      name = "Legacy-Fabric"
      url = uri("https://maven.legacyfabric.net/")
    }
    maven {
      name = "derklaro-releases"
      url = uri("https://repository.derklaro.dev/releases/")
    }
    maven {
      name = "Maven Central Snapshots"
      url = uri("https://central.sonatype.com/repository/maven-snapshots/")
    }
    maven {
      name = "Parchment"
      url = uri("https://maven.parchmentmc.org/")
    }
    maven {
      name = "Mojang Libraries"
      url = uri("https://libraries.minecraft.net/")
    }
  }

  dependencies {
    "compileOnly"(rootProject.libs.annotations)
  }

  tasks.withType<JavaCompile> {
    sourceCompatibility = targetJavaVersion.toString()
    targetCompatibility = targetJavaVersion.toString()

    options.encoding = "UTF-8"
    options.isIncremental = true

    options.compilerArgs.add("-proc:full")
    options.compilerArgs.add("--enable-preview")
    options.compilerArgs.addAll(
      listOf(
        "-Xlint:all",         // enable all warnings
        "-Xlint:-preview",    // reduce warning size for the following warning types
        "-Xlint:-unchecked",
        "-Xlint:-classfile",
        "-Xlint:-processing",
        "-Xlint:-deprecation",
      )
    )
  }

  tasks.withType<ProcessResources> {
    val tokens = mapOf(
      "project_version" to version,
      "target_java_major" to targetJavaVersion.majorVersion,
      "fabric_loader_version" to rootProject.libs.versions.fabricLoader.get(),
    )
    inputs.properties(tokens)
    filesMatching("*.json") {
      filter(ReplaceTokens::class, mapOf("tokens" to tokens))
    }
  }

  extensions.configure<SpotlessExtension> {
    java {
      lineEndings = LineEnding.UNIX
      encoding = StandardCharsets.UTF_8
      licenseHeaderFile(rootProject.file("license_header.txt"))
    }
  }

  configurations {
    val shaded = register("shaded")
    getByName("compileOnly").extendsFrom(shaded.get())

    all {
      exclude("org.checkerframework", "checker-qual")
    }
  }

  tasks.shadowJar {
    // only include dependencies that were specifically configured (using the 'shaded' configuration)
    configurations = setOf(project.configurations["shaded"])
  }
}
