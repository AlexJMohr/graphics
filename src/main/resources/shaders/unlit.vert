#version 330 core

layout (location = 0) in vec3 position;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

out VS_OUT {
    vec3 position;
} vs_out;

void main() {
    // vertex position in screen space
    gl_Position = projection * view * model * vec4(position, 1.0);

    // fragment position in world space
    vs_out.position = vec3(model * vec4(position, 1.0));
}
