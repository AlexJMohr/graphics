# Alex Mohr Graphics

A collection of graphics projects

## Dependencies
- Maven
- Java 11 (lower versions might work)
- LWJGL 3.2.1

See the root [pom.xml](pom.xml) for more details.

## Modules

|Module|Description|
|------|---|
|common|The "engine" module that contains most of the interesting code. Other modules are just examples.|
|cube|A spinning coloured cube.|
|model-loading|Loads textured meshes using Assimp.|

## TODO

1. Reorganize common module
2. ModelLoader should return a Model object which contains the meshes and materials that were loaded