# CloudNet Bridge Fabric Implementations

![Build](https://github.com/CloudNetService/cloudnet-bridge-fabric/actions/workflows/ci.yml/badge.svg)
![Release](https://img.shields.io/github/v/release/CloudNetService/cloudnet-bridge-fabric?sort=date&logo=github)

## What is the purpose of this repo?

This repository holds the actual CloudNet-Bridge Fabric implementations for different minecraft versions. Find a table
of all supported minecraft versions below. **Note**: generally speaking all fabric versions are supported, however, the
listed versions have extensive support (such as providing player information to the node and bungeecord player info
forwarding). If you want support for a specific minecraft version, feel free to
open [an issue](https://github.com/CloudNetService/cloudnet-bridge-fabric/issues/new) or a pull request.

The core bridge mod is still within the main CloudNet repository, this repo only provides the implementations for
specific minecraft versions.

## Supported Minecraft versions

| Minecraft Version      | Subproject | Supported                                             |
|------------------------|------------|-------------------------------------------------------|
| 1.21.11                | v1_21_11   | ![LTS](https://img.shields.io/badge/Long_Term-005000) |
| 1.21.9, 1.21.10        | v1_21_9    | ![LTS](https://img.shields.io/badge/Long_Term-005000) |
| 1.21.6, 1.21.7, 1.21.8 | v1_21_6    | ![LTS](https://img.shields.io/badge/Long_Term-005000) |
| 1.20.5, 1.20.6         | v1_20_5    | ![LTS](https://img.shields.io/badge/Long_Term-005000) |
| 1.19.4                 | v1_19_4    | ![LTS](https://img.shields.io/badge/Long_Term-005000) |
| 1.18.2                 | v1_18_2    | ![LTS](https://img.shields.io/badge/Long_Term-005000) |
| 1.17, 1.17.1           | v1_17      | ![LTS](https://img.shields.io/badge/Long_Term-005000) |
| 1.16.2 - 1.16.5        | v1_16_2    | ![LTS](https://img.shields.io/badge/Long_Term-005000) |
| 1.15, 1.15.1, 1.15.2   | v1_15      | ![LTS](https://img.shields.io/badge/Long_Term-005000) |
| 1.14.4                 | v1_14_4    | ![LTS](https://img.shields.io/badge/Long_Term-005000) |
| 1.13.2                 | v1_13_2    | ![LTS](https://img.shields.io/badge/Long_Term-005000) |
| 1.12.2                 | v1_12_2    | ![LTS](https://img.shields.io/badge/Long_Term-005000) |
| 1.11.2                 | v1_11_2    | ![LTS](https://img.shields.io/badge/Long_Term-005000) |
| 1.10.2                 | v1_10_2    | ![LTS](https://img.shields.io/badge/Long_Term-005000) |
| 1.9.4                  | v1_9_4     | ![LTS](https://img.shields.io/badge/Long_Term-005000) |
| 1.8.2 - 1.8.9          | v1_8_2     | ![LTS](https://img.shields.io/badge/Long_Term-005000) |
| 1.7.6 - 1.7.10         | v1_7_6     | ![LTS](https://img.shields.io/badge/Long_Term-005000) |

## Links

- [Support Discord](https://discord.cloudnetservice.eu)
- [Main CloudNet Repository](https://github.com/CloudNetService/CloudNet)
- [Issue Tracker](https://github.com/CloudNetService/cloudnet-bridge-fabric/issues)
- [Latest Release](https://github.com/CloudNetService/cloudnet-bridge-fabric/releases/latest)

## Compile from source

To compile this project you need JDK 25 and an internet connection. Then clone this repository and run `./gradlew build`
inside the cloned project.
