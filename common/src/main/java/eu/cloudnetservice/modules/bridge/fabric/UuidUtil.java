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

import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for working with uuids.
 *
 * @since 2025.08.26
 */
public final class UuidUtil {

  private static final int HEX_RADIX = 16;
  private static final int UNDASHED_UUID_LENGTH = 32;
  private static final int UNDASHED_UUID_PART_SPLIT_IDX = UNDASHED_UUID_LENGTH / 2;

  private UuidUtil() {
    throw new UnsupportedOperationException();
  }

  /**
   * Parses an undashed unique identifier string into a uuid.
   *
   * @return the parsed uuid from the given undashed uuid input string.
   * @throws IllegalArgumentException if the given input is not parseable.
   */
  public static @NotNull UUID parseUndashedUuid(@NotNull String input) {
    if (input.length() != UNDASHED_UUID_LENGTH) {
      throw new IllegalArgumentException("Undashed uuid input was not the correct length: " + input);
    }

    var msb = Long.parseUnsignedLong(input.substring(0, UNDASHED_UUID_PART_SPLIT_IDX), HEX_RADIX);
    var lsb = Long.parseUnsignedLong(input.substring(UNDASHED_UUID_PART_SPLIT_IDX), HEX_RADIX);
    return new UUID(msb, lsb);
  }
}
