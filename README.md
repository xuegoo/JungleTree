# JungleTree
[![Build Status](https://ci.jungletree.org/job/JungleTree/job/JungleTree/job/master/badge/icon)](https://ci.jungletree.org/blue/organizations/jenkins/JungleTree%2FJungleTree/activity)

## What is it?

JungleTree is an ongoing project to make powerful, distributed server software for the game Minecraft.
The original project started back in 2011 as a programming challenge, where a single JVM hosted everything.

Since then, JungleTree has been re-worked into a distributed computing platform. This has been possible by taking
advantage of modern server technologies, such as Docker and RabbitMQ.

In the long term, the JungleTree project aims to become the standard for all commercial-grade Minecraft organizations, 
with all source code being freely available.

## In Development

The minimum viable product is currently in development. This process will see the framework form its roots. For the
MVP stage, the goals are as follows:

* Connectivity from the Bedrock and Java Edition clients
* Flat world generation
* A Named Binary Tag library
* Library for sending/receiving messages between application services
* Publicly available Docker images, and docker-compose example configuration

## Documentation

Work has not begun on documentation, due to the ever-changing project structure. It will be produced once JungleTree
reaches the goals highlighted for the MVP.

## Compiling

### Prerequisites

JungleTree is best compiled using Linux, however the process should not differ particularly if you are using macOS/Windows.  
If you have any difficulties compiling under Windows, please don't hesitate to [send us an email](mailto:spam@jungletree.org).

* OpenJDK 9.0.0+

### Compilation

#### Mac / Linux

In your terminal emulator from the project directory, execute:

```
./gradlew clean build
```

#### Windows

From a command prompt or Powershell in the project directory, execute:

```
gradlew.bat clean build
```

# Continuous Integration

The project can be found on the [JungleTree CI server](https://ci.jungletree.org/blue/organizations/jenkins/JungleTree%2FRainforest/activity)

# License

JungleTree is MIT. See LICENSE for more details.

# Special Thanks

* The wonderful contributors over at [wiki.vg](http://wiki.vg/Main_Page). Without their reverse engineering, none of this
would have been possible.

* yawkat for his work in reverse engineering [his own protocol documentation](https://confluence.yawk.at/display/PEPROTOCOL/) for the Bedrock platform

* GoMint for [jRakNet](https://github.com/JungleTree/jRakNet/blob/master/LICENSE.txt), and their server implementation
used as a reference for the Bedrock networking protocol.

* Niclas Olofsson for [MiNet](https://github.com/NiclasOlofsson/MiNET), also used as reference for the Bedrock
networking protocol.

* [Glowstone](https://github.com/GlowstoneMC/Glowstone) for a reference in Java Edition.
