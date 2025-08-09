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

import eu.cloudnetservice.driver.event.EventManager;
import eu.cloudnetservice.driver.network.NetworkClient;
import eu.cloudnetservice.driver.network.rpc.factory.RPCFactory;
import eu.cloudnetservice.driver.provider.CloudServiceProvider;
import eu.cloudnetservice.driver.provider.ServiceTaskProvider;
import eu.cloudnetservice.driver.registry.ServiceRegistry;
import eu.cloudnetservice.modules.bridge.BridgeServiceHelper;
import eu.cloudnetservice.modules.bridge.impl.platform.helper.ServerPlatformHelper;
import eu.cloudnetservice.wrapper.configuration.WrapperConfiguration;
import eu.cloudnetservice.wrapper.holder.ServiceInfoHolder;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;

/**
 * Holder that holds the most commonly used types from cloudnet. Access vía
 * {@link BridgeAccessorSpec#cloudnet_bridge$injectionHolder()}.
 *
 * @since 1.0
 */
@Singleton
public record CloudNetInjectionHolder(
  @NotNull RPCFactory rpcFactory,
  @NotNull EventManager eventManager,
  @NotNull NetworkClient networkClient,
  @NotNull ServiceRegistry serviceRegistry,
  @NotNull ServiceTaskProvider taskProvider,
  @NotNull BridgeServiceHelper serviceHelper,
  @NotNull ServiceInfoHolder serviceInfoHolder,
  @NotNull CloudServiceProvider serviceProvider,
  @NotNull ServerPlatformHelper serverPlatformHelper,
  @NotNull WrapperConfiguration wrapperConfiguration
) {

}
