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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
  repositories {
    gradlePluginPortal()
    maven {
      name = "Fabric"
      url = uri("https://maven.fabricmc.net/")
    }
    maven {
      name = "Legacy-Fabric"
      url = uri("https://maven.legacyfabric.net/")
    }
  }
}

rootProject.name = "cloudnet-bridge-fabric"

include(":common")
include(":v1_9_4")
include(":v1_10_2")
include(":v1_11_2")
include(":v1_12_2")
include(":v1_13_2")
include(":v1_14_4")
include(":v1_15")
include(":v1_16_2")
include(":v1_17")
include(":v1_18_2")
include(":v1_19_4")
include(":v1_20_5")
include(":v1_21_6")
