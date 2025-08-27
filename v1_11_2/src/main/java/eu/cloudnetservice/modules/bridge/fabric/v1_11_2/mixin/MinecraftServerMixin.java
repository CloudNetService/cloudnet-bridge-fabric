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

package eu.cloudnetservice.modules.bridge.fabric.v1_11_2.mixin;

import eu.cloudnetservice.driver.inject.InjectionLayer;
import eu.cloudnetservice.modules.bridge.fabric.BridgeAccessorSpec;
import eu.cloudnetservice.modules.bridge.fabric.CloudNetInjectionHolder;
import eu.cloudnetservice.modules.bridge.fabric.v1_11_2.FabricBridgeManagementv1112;
import eu.cloudnetservice.modules.bridge.impl.platform.PlatformBridgeManagement;
import eu.cloudnetservice.modules.bridge.player.NetworkPlayerServerInfo;
import java.util.Collection;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.ServerMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.SERVER)
@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin implements BridgeAccessorSpec<ServerPlayerEntity> {

  /**
   * CloudNet injection holder.
   */
  @Unique
  private CloudNetInjectionHolder cloudnet_bridge$injectionHolder;
  /**
   * Instantiated bridge management.
   */
  @Unique
  private PlatformBridgeManagement<ServerPlayerEntity, NetworkPlayerServerInfo> cloudnet_bridge$management;

  /**
   * Get the current server motd.
   *
   * @return the current server motd.
   */
  @Shadow
  public abstract String getServerMotd();

  /**
   * Get the player list used by the server.
   *
   * @return the player list used by the server.
   */
  @Shadow
  public abstract PlayerManager getPlayerManager();

  /**
   * Get the metadata of the server.
   *
   * @return the metadata of the server.
   */
  @Shadow
  public abstract ServerMetadata getServerMetadata();

  /**
   * Initializes the bridge management very early in the server construction process.
   */
  @Inject(at = @At("CTOR_HEAD"), method = "<init>")
  public void cloudnet_bridge$init(CallbackInfo ci) {
    this.cloudnet_bridge$injectionHolder = InjectionLayer.ext().instance(CloudNetInjectionHolder.class);
    this.cloudnet_bridge$management = new FabricBridgeManagementv1112(this);
    this.cloudnet_bridge$management.registerServices(this.cloudnet_bridge$injectionHolder.serviceRegistry());
  }

  /**
   * Runs post init tasks before the server starts ticking.
   */
  @Inject(
    at = @At(
      value = "INVOKE",
      shift = At.Shift.AFTER,
      target = "Lnet/minecraft/server/MinecraftServer;setServerMeta(Lnet/minecraft/server/ServerMetadata;)V"
    ),
    method = "run"
  )
  public void cloudnet_bridge$postInit(@NotNull CallbackInfo callbackInfo) {
    // init the bridge properties
    var serviceHelper = this.cloudnet_bridge$injectionHolder.serviceHelper();
    serviceHelper.motd().set(this.cloudnet_bridge$motd());
    serviceHelper.maxPlayers().set(this.cloudnet_bridge$maxPlayers());

    // run post init tasks
    this.cloudnet_bridge$management.postInit();
  }

  /**
   * Disables compression between the server and the proxy.
   */
  @Inject(at = @At("RETURN"), method = "getNetworkCompressionThreshold", cancellable = true)
  public void cloudnet_bridge$getNetworkCompressionThreshold(@NotNull CallbackInfoReturnable<Integer> ci) {
    ci.setReturnValue(-1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int cloudnet_bridge$maxPlayers() {
    return this.getPlayerManager().getMaxPlayerCount();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int cloudnet_bridge$playerCount() {
    return this.getPlayerManager().getCurrentPlayerCount();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull String cloudnet_bridge$serverVersion() {
    return this.getServerMetadata().getVersion().getGameVersion();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull String cloudnet_bridge$motd() {
    return this.getServerMotd();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull Collection<ServerPlayerEntity> cloudnet_bridge$players() {
    return this.getPlayerManager().getPlayers();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @Nullable ServerPlayerEntity cloudnet_bridge$player(@NotNull UUID uniqueId) {
    return this.getPlayerManager().getPlayer(uniqueId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull PlatformBridgeManagement<ServerPlayerEntity, NetworkPlayerServerInfo> cloudnet_bridge$management() {
    return this.cloudnet_bridge$management;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull CloudNetInjectionHolder cloudnet_bridge$injectionHolder() {
    return this.cloudnet_bridge$injectionHolder;
  }
}
