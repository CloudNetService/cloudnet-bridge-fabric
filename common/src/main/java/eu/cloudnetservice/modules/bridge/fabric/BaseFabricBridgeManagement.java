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

import eu.cloudnetservice.driver.registry.ServiceRegistry;
import eu.cloudnetservice.driver.service.ServiceInfoSnapshot;
import eu.cloudnetservice.modules.bridge.impl.platform.PlatformBridgeManagement;
import eu.cloudnetservice.modules.bridge.player.NetworkPlayerServerInfo;
import eu.cloudnetservice.modules.bridge.player.PlayerManager;
import eu.cloudnetservice.wrapper.event.ServiceInfoPropertiesConfigureEvent;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * Base implementation of the fabric bridge management for overrides in version specific modules.
 *
 * @param <S> the player type.
 * @since 2025.08.11
 */
public abstract class BaseFabricBridgeManagement<S> extends PlatformBridgeManagement<S, NetworkPlayerServerInfo> {

  protected final BridgeAccessorSpec<S> bridgeAccessorSpec;

  /**
   * Constructs a new instance of the bridge management.
   *
   * @param bridgeAccessorSpec the accessor specification for the current server version.
   * @throws NullPointerException if the given accessor spec is null.
   */
  protected BaseFabricBridgeManagement(@NotNull BridgeAccessorSpec<S> bridgeAccessorSpec) {
    var injectionHolder = bridgeAccessorSpec.cloudnet_bridge$injectionHolder();
    super(
      injectionHolder.rpcFactory(),
      injectionHolder.eventManager(),
      injectionHolder.networkClient(),
      injectionHolder.taskProvider(),
      injectionHolder.serviceHelper(),
      injectionHolder.serviceInfoHolder(),
      injectionHolder.serviceProvider(),
      injectionHolder.wrapperConfiguration());
    this.bridgeAccessorSpec = bridgeAccessorSpec;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void registerServices(@NotNull ServiceRegistry registry) {
    registry.registerProvider(PlayerManager.class, "PlayerManager", this.playerManager);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isOnAnyFallbackInstance(@NotNull S player) {
    return this.isOnAnyFallbackInstance(this.ownNetworkServiceInfo.serverName(), null, _ -> true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull Optional<ServiceInfoSnapshot> fallback(@NotNull S player) {
    return this.fallback(player, this.ownNetworkServiceInfo.serverName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void appendServiceInformation(@NotNull ServiceInfoPropertiesConfigureEvent configureEvent) {
    super.appendServiceInformation(configureEvent);

    var connectedPlayers = this.bridgeAccessorSpec.cloudnet_bridge$players()
      .stream()
      .map(this::createPlayerInformation)
      .toList();

    var propertyHolder = configureEvent.propertyHolder();
    propertyHolder.append("Players", connectedPlayers);
    propertyHolder.append("Version", this.bridgeAccessorSpec.cloudnet_bridge$serverVersion());
    propertyHolder.append("Online-Count", this.bridgeAccessorSpec.cloudnet_bridge$playerCount());
  }
}
