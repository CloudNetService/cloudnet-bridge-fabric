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

package eu.cloudnetservice.modules.bridge.fabric.v1_8_2;

import eu.cloudnetservice.driver.service.ServiceInfoSnapshot;
import eu.cloudnetservice.modules.bridge.fabric.BaseFabricBridgeManagement;
import eu.cloudnetservice.modules.bridge.fabric.BridgeAccessorSpec;
import eu.cloudnetservice.modules.bridge.impl.util.BridgeHostAndPortUtil;
import eu.cloudnetservice.modules.bridge.player.NetworkPlayerServerInfo;
import eu.cloudnetservice.modules.bridge.player.ServicePlayer;
import eu.cloudnetservice.modules.bridge.player.executor.PlayerExecutor;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import net.minecraft.entity.player.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FabricBridgeManagementv182 extends BaseFabricBridgeManagement<ServerPlayerEntity> {

  /**
   * Constructs a new instance of the bridge management.
   *
   * @param bridgeAccessorSpec the accessor specification for the current server version.
   * @throws NullPointerException if the given accessor spec is null.
   */
  public FabricBridgeManagementv182(@NotNull BridgeAccessorSpec<ServerPlayerEntity> bridgeAccessorSpec) {
    super(bridgeAccessorSpec);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull ServicePlayer wrapPlayer(@NotNull ServerPlayerEntity player) {
    return new ServicePlayer(player.getUuid(), player.getGameProfile().getName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull NetworkPlayerServerInfo createPlayerInformation(@NotNull ServerPlayerEntity player) {
    return new NetworkPlayerServerInfo(
      player.getUuid(),
      player.getGameProfile().getName(),
      null,
      BridgeHostAndPortUtil.fromSocketAddress(player.networkHandler.connection.getAddress()),
      this.ownNetworkServiceInfo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull BiFunction<ServerPlayerEntity, String, Boolean> permissionFunction() {
    return (player, _) -> player.canUseCommand(4, "");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull Optional<ServiceInfoSnapshot> fallback(
    @NotNull ServerPlayerEntity player,
    @Nullable String currServer
  ) {
    return this.fallback(player.getUuid(), currServer, null, _ -> player.canUseCommand(4, ""));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleFallbackConnectionSuccess(@NotNull ServerPlayerEntity player) {
    this.handleFallbackConnectionSuccess(player.getUuid());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeFallbackProfile(@NotNull ServerPlayerEntity player) {
    this.removeFallbackProfile(player.getUuid());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull PlayerExecutor directPlayerExecutor(@NotNull UUID uniqueId) {
    throw new UnsupportedOperationException("not implemented on fabric");
  }
}
