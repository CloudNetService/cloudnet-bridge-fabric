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

package eu.cloudnetservice.modules.bridge.fabric.next.mixin.forward;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Expands the hostname length to fit the forwarded bungee information.
 *
 * @since 2025.08.11
 */
@Environment(EnvType.SERVER)
@Mixin(ClientIntentionPacket.class)
public abstract class ClientIntentionPacketMixin {

  @Redirect(
    at = @At(value = "INVOKE", target = "Lnet/minecraft/network/FriendlyByteBuf;readUtf(I)Ljava/lang/String;"),
    method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V"
  )
  private static String cloudnet_bridge$read(@NotNull FriendlyByteBuf buf, int amount) {
    return buf.readUtf(Short.MAX_VALUE);
  }
}
