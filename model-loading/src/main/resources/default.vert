#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normal;
layout (location = 2) in vec2 texcoord;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

out vec3 norm;
out vec2 texCoord;

void main() {
	mat4 mvp = projection * view * model;
	gl_Position = mvp * vec4(position, 1.0);
	norm = normal;
	texCoord = texcoord;
}
