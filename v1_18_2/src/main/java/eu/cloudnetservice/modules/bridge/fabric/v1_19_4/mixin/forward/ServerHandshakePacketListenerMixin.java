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

package eu.cloudnetservice.modules.bridge.fabric.v1_19_4.mixin.forward;

import com.google.gson.Gson;
import com.mojang.authlib.properties.Property;
import eu.cloudnetservice.modules.bridge.fabric.Constants;
import eu.cloudnetservice.modules.bridge.fabric.UuidUtil;
import eu.cloudnetservice.modules.bridge.fabric.v1_19_4.forward.ForwardingDataAccumulator;
import java.net.InetSocketAddress;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.server.network.ServerHandshakePacketListenerImpl;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Reads the bungeecord forwarding info from the client intention packet and stores it for later use during the login
 * process.
 *
 * @since 2025.08.26
 */
@Environment(EnvType.SERVER)
@Mixin(ServerHandshakePacketListenerImpl.class)
public abstract class ServerHandshakePacketListenerMixin {

  /**
   * Gson instance to deserialize profile properties.
   */
  @Unique
  private static final Gson cloudnet_bridge$gson = new Gson();

  /**
   * The connection associated with the current listener.
   */
  @Final
  @Shadow
  private Connection connection;

  @Inject(
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/network/Connection;setListener(Lnet/minecraft/network/PacketListener;)V",
      shift = At.Shift.AFTER
    ),
    method = "handleIntention"
  )
  public void cloudnet_bridge$handleIntention(
    @NotNull ClientIntentionPacket packet,
    @NotNull CallbackInfo callbackInfo
  ) {
    if (packet.getIntention() != ConnectionProtocol.LOGIN) {
      return;
    }

    var hostnameParts = packet.getHostName().split("\00");
    if (hostnameParts.length != 3 && hostnameParts.length != 4) {
      // bungee forwarding info missing
      var disconnectReason = new TextComponent(Constants.NO_BUNGEE_FORWARD_INFO_DISCONNECT_REASON)
        .withStyle(ChatFormatting.RED);
      this.connection.send(new ClientboundLoginDisconnectPacket(disconnectReason));
      this.connection.disconnect(disconnectReason);
      return;
    }

    // see: https://github.com/SpigotMC/BungeeCord/blob/e62fc6c2916a991e00177c580986d8b1a22fdb41/proxy/src/main/java/net/md_5/bungee/ServerConnector.java#L114-L123
    var originalHost = hostnameParts[0];
    var actualUserAddress = hostnameParts[1];
    var actualUserUniqueId = hostnameParts[2]; // without dashes
    var actualProfileProperties = hostnameParts.length == 4 ? hostnameParts[3] : null; // json encoded

    // rewrite the user connection address information
    var port = ((InetSocketAddress) this.connection.getRemoteAddress()).getPort();
    this.connection.address = new InetSocketAddress(actualUserAddress, port);
    packet.hostName = originalHost;

    // need to store the forward accumulator into the channel for later retrieval to actually set the data
    var channel = this.connection.channel;
    var forwardDataAccumulator = new ForwardingDataAccumulator();
    channel.attr(ForwardingDataAccumulator.ACCUMULATOR_KEY).set(forwardDataAccumulator);
    forwardDataAccumulator.uniqueId = UuidUtil.parseUndashedUuid(actualUserUniqueId);

    // add profile properties if any were forwarded
    if (actualProfileProperties != null) {
      var profileProperties = cloudnet_bridge$gson.fromJson(actualProfileProperties, Property[].class);
      forwardDataAccumulator.properties.addAll(List.of(profileProperties));
    }
  }
}
