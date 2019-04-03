#version 330 core

in vec3 norm;
in vec2 texCoord;

uniform vec4 ambient;
uniform vec4 diffuse;
uniform vec4 specular;

uniform bool hasTexture;
uniform bool hasNormalMap;

uniform sampler2D tex;
uniform sampler2D normalMap;

out vec4 color;

void main() {
	color = diffuse;
	if (hasTexture) {
		color = texture2D(tex, texCoord) * ambient;
	}

	vec4 normal = texture2D(normalMap, texCoord);
}
