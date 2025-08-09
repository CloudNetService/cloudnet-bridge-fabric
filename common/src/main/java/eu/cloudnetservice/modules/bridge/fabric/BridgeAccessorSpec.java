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

import eu.cloudnetservice.modules.bridge.impl.platform.PlatformBridgeManagement;
import eu.cloudnetservice.modules.bridge.player.NetworkPlayerServerInfo;
import java.util.Collection;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Accessors for the server version specific implementations of the module.
 *
 * @param <S> the player type.
 * @since 1.0
 */
public interface BridgeAccessorSpec<S> {

  /**
   * Get the maximum player count of the server.
   *
   * @return the maximum player count of the server.
   */
  int cloudnet_bridge$maxPlayers();

  /**
   * Get the current player count of the server.
   *
   * @return the current player count of the server.
   */
  int cloudnet_bridge$playerCount();

  /**
   * Get the current server version.
   *
   * @return the current server version.
   */
  @NotNull
  String cloudnet_bridge$serverVersion();

  /**
   * Get the current motd of the server.
   *
   * @return the current motd of the server.
   */
  @NotNull
  String cloudnet_bridge$motd();

  /**
   * Get the players that are currently on the server.
   *
   * @return the players that are currently on the server.
   */
  @NotNull
  Collection<S> cloudnet_bridge$players();

  /**
   * Get details about a specific player by their given unique id.
   *
   * @param uniqueId the id of the player to get.
   * @return the connected player with the given unique id or {@code null} if the player is not connected.
   * @throws NullPointerException if the given unique id is null.
   */
  @Nullable
  S cloudnet_bridge$player(@NotNull UUID uniqueId);

  /**
   * Get the currently initialized bridge management instance.
   *
   * @return the currently initialized bridge management instance.
   */
  @NotNull
  PlatformBridgeManagement<S, NetworkPlayerServerInfo> cloudnet_bridge$management();

  /**
   * Get the holder for everything that can be injected from cloudnet directly.
   *
   * @return the holder for everything that can be injected from cloudnet directly.
   */
  @NotNull
  CloudNetInjectionHolder cloudnet_bridge$injectionHolder();
}
