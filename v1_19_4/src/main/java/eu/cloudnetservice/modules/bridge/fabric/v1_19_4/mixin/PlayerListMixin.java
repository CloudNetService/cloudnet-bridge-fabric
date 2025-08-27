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

package eu.cloudnetservice.modules.bridge.fabric.v1_19_4.mixin;

import eu.cloudnetservice.modules.bridge.fabric.BaseFabricBridgeManagement;
import eu.cloudnetservice.modules.bridge.fabric.BridgeAccessorSpec;
import net.minecraft.network.Connection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Handles the join and leave of players from the current service.
 *
 * @since 2025.08.27
 */
@Mixin(PlayerList.class)
public abstract class PlayerListMixin {

  /**
   * The server this player list is associated with.
   */
  @Final
  @Shadow
  private MinecraftServer server;

  @Inject(at = @At("TAIL"), method = "placeNewPlayer")
  public void cloudnet_bridge$placeNewPlayer(
    @NotNull Connection connection,
    @NotNull ServerPlayer player,
    @NotNull CallbackInfo callbackInfo
  ) {
    var bridgeManagement = this.cloudnet_bridge$management();
    bridgeManagement.handlePlayerJoin(player);
  }

  @Inject(at = @At("TAIL"), method = "remove")
  public void cloudnet_bridge$removePlayer(@NotNull ServerPlayer player, @NotNull CallbackInfo callbackInfo) {
    var bridgeManagement = this.cloudnet_bridge$management();
    bridgeManagement.handlePlayerLeave(player);
  }

  /**
   * Get the current base bridge management instance.
   *
   * @return the current base bridge management instance.
   */
  @Unique
  @SuppressWarnings("unchecked")
  private @NotNull BaseFabricBridgeManagement<ServerPlayer> cloudnet_bridge$management() {
    var accessorSpec = (BridgeAccessorSpec<ServerPlayer>) this.server;
    return (BaseFabricBridgeManagement<ServerPlayer>) accessorSpec.cloudnet_bridge$management();
  }
}
