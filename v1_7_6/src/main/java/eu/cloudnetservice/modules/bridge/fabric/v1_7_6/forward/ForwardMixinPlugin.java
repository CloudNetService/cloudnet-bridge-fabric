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

package eu.cloudnetservice.modules.bridge.fabric.v1_7_6.forward;

import eu.cloudnetservice.modules.bridge.fabric.BaseMixinConfigPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Mixin plugin to conditionally apply forwarding mixins only if they are actually needed.
 *
 * @since 2025.08.26
 */
public final class ForwardMixinPlugin extends BaseMixinConfigPlugin {

  /**
   * If cloudnet ip forwarding support is disabled.
   */
  private static final boolean DISABLE_CLOUDNET_FORWARDING = Boolean.getBoolean("cloudnet.ipforward.disabled");

  /**
   * Conditionally applies the player forwarding mixins if they are enabled.
   *
   * @param targetClassName fully qualified class name of the target class
   * @param mixinClassName  fully qualified class name of the mixin
   * @return true if the given mixin class should be applied, false otherwise.
   */
  @Override
  public boolean shouldApplyMixin(@NotNull String targetClassName, @NotNull String mixinClassName) {
    var isForwardMixin = mixinClassName.contains(".forward.");
    return !isForwardMixin || !DISABLE_CLOUDNET_FORWARDING;
  }
}
