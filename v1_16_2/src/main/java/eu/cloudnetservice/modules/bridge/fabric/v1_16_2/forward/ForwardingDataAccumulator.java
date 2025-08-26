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

package eu.cloudnetservice.modules.bridge.fabric.v1_16_2.forward;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.netty.util.AttributeKey;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * Accumulator for forwarded player data.
 *
 * @since 2025.08.26
 */
public final class ForwardingDataAccumulator {

  /**
   * Key to get the forward data accumulator from a netty channel.
   */
  public static final AttributeKey<ForwardingDataAccumulator> ACCUMULATOR_KEY =
    AttributeKey.newInstance("cloudnet_bridge$forward_data_accumulator");

  /**
   * List of forwarded properties. Should only be appended to.
   */
  public final List<Property> properties = new ArrayList<>();

  /**
   * The unique id of the forwarded player.
   */
  public UUID uniqueId;

  /**
   * Converts the accumulated data into a game profile.
   *
   * @param name the name of the player.
   * @return the constructed game profile from the accumulated data.
   * @throws NullPointerException if the given name is null or this accumulator wasn't filled properly.
   */
  public @NotNull GameProfile toGameProfile(@NotNull String name) {
    var profile = new GameProfile(this.uniqueId, name);
    for (var property : this.properties) {
      profile.getProperties().put(property.getName(), property);
    }

    return profile;
  }
}
