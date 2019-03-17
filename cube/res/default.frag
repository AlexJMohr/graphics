#version 330 core

in vec3 pos;

out vec4 color;

void main() {
	//color = vec4(1, 0, 1, 1);
	color = vec4(pos * 0.5 + vec3(0.5), 1.0);
}
