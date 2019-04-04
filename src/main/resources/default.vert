#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normal;
layout (location = 2) in vec2 texcoord;
layout (location = 3) in vec3 tangent;
layout (location = 4) in vec3 bitangent;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;
uniform mat3 normalMatrix;

//out vec3 fragPosition;
//out vec3 fragNormal;
//out vec2 fragTexCoord;

out VS_OUT {
	vec3 position;
	vec3 normal;
	vec2 texcoord;
	vec3 tangent;
	vec3 bitangent;
	mat3 tbn;
} vs_out;

void main() {
    // vertex position in screen space
	gl_Position = projection * view * model * vec4(position, 1.0);

	// fragment position in world space
	vs_out.position = vec3(model * vec4(position, 1.0));

	// transform normals to world space
	vs_out.normal = normalMatrix * normal;

	// pass texture coordinates to fragment shader untouched
	vs_out.texcoord = texcoord;

	vs_out.tangent = tangent;
	vs_out.bitangent = bitangent;

	// construct TBN matrix for normal mapping
	vec3 t = normalize(vec3(model * vec4(tangent, 0)));
	vec3 b = normalize(vec3(model * vec4(bitangent, 0)));
	vec3 n = normalize(vec3(model * vec4(normal, 0)));
	vs_out.tbn = mat3(t, b, n);
}
