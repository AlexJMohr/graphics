#version 330 core

layout (location = 0) in vec3 position;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

out vec3 pos;

void main() {
	mat4 mvp = projection * view * model;
	gl_Position = mvp * vec4(position, 1.0);
	pos = position;
}
