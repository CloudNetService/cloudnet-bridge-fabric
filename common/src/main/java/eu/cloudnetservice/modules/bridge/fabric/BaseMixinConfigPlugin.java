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

package eu.cloudnetservice.modules.bridge.fabric;

import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

/**
 * Mixin plugin implementation to remove boilerplate from subprojects.
 *
 * @since 1.0
 */
public abstract class BaseMixinConfigPlugin implements IMixinConfigPlugin {

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean shouldApplyMixin(@NotNull String targetClassName, @NotNull String mixinClassName) {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onLoad(@NotNull String mixinPackage) {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @Nullable String getRefMapperConfig() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void acceptTargets(@NotNull Set<String> myTargets, @NotNull Set<String> otherTargets) {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @Nullable List<String> getMixins() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void preApply(
    @NotNull String targetClassName,
    @NotNull ClassNode targetClass,
    @NotNull String mixinClassName,
    @NotNull IMixinInfo mixinInfo
  ) {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void postApply(
    @NotNull String targetClassName,
    @NotNull ClassNode targetClass,
    @NotNull String mixinClassName,
    @NotNull IMixinInfo mixinInfo
  ) {
  }
}
