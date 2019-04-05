#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normal;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;
uniform mat3 normalMatrix;

out VS_OUT {
    vec3 position;
    vec3 normal;
} vs_out;

void main() {
    // vertex position in screen space
    gl_Position = projection * view * model * vec4(position, 1.0);

    // fragment position in world space
    vs_out.position = vec3(model * vec4(position, 1.0));

    // transform normals to world space
    vs_out.normal = normalMatrix * normal;
}
