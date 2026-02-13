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

import org.apache.tools.ant.filters.ReplaceTokens

plugins {
  id("net.fabricmc.fabric-loom")
}

val minecraftVersion = "26.1-snapshot-7"
val supportedVersionRange = ">=$minecraftVersion <26.2"

dependencies {
  shaded(projects.common)
  implementation(libs.fabricLoader)
  minecraft("com.mojang:minecraft:$minecraftVersion")
}

loom {
  accessWidenerPath = project.file("src/main/resources/cloudnet_version_bridge.accesswidener")
}

tasks.shadowJar {
  archiveFileName = "bridge_${minecraftVersion.replace(".", "")}.jar"
}

tasks.processResources {
  val tokens = mapOf(
    "mc_version" to minecraftVersion,
    "mc_version_range" to supportedVersionRange,
    "mc_version_dashed" to minecraftVersion.replace('.', '_'),
  )
  inputs.properties(tokens)
  filesMatching("*.json") {
    filter(ReplaceTokens::class, mapOf("tokens" to tokens))
  }

  from(rootProject.isolated.projectDirectory.dir(".mod_resources")) {
    into("")
    include("*.json")
  }
}
