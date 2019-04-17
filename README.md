# Alex Mohr Graphics

3D OpenGL graphics project. See the Features section below.

[Click here](https://youtu.be/PzN2fz64qHs) for a video of what is currently implemented.

## Dependencies

- Java 10 (lower versions might work)
- LWJGL 3.2.1

## Features/Todo

- [x] Model Loading
- [x] Textures
- [x] Normal Maps
- [x] Basic Lighting (single light source)
- [ ] Advanced Lighting
    - [x] Directional Light
    - [x] Point Light
    - [ ] Spot Light
    - [ ] Multiple Lights
- [ ] Entity/Component system
- [ ] Shadows
- [ ] Skeletal Animation
- [ ] Particles
- [ ] 2D Font Rendering
- [ ] Sky Box

## Build and run
```bash
./gradlew build
./gradlew run
```

Press 1, 2, 3, and 4 to change shaders to unlit, lit, textured, and normal mapped respectively.