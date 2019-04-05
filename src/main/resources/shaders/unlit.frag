#version 330 core

in VS_OUT {
    vec3 position;
} vs_out;

out vec4 outColor;

void main() {
    outColor = vec4(0, 0, 0, 1);
}
