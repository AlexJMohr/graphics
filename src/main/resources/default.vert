#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normal;
layout (location = 2) in vec2 texcoord;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;
uniform mat3 normalMatrix;

out vec3 fragPosition;
out vec3 fragNormal;
out vec2 fragTexCoord;

void main() {
    // vertex position in screen space
	gl_Position = projection * view * model * vec4(position, 1.0);

	// fragment position in world space
	fragPosition = vec3(model * vec4(position, 1.0));

	// transform normals to world space
	fragNormal = normalMatrix * normal;

	// pass texture coordinates to fragment shader untouched
	fragTexCoord = texcoord;
}
