#version 330 core

in vec3 fragPosition;
in vec3 fragNormal;
in vec2 fragTexCoord;

uniform vec4 ambient;
uniform vec4 diffuse;
uniform vec4 specular;

uniform vec3 viewPosition;
uniform vec3 lightPosition;

uniform bool hasTexture;
uniform bool hasNormalMap;

uniform sampler2D tex;
uniform sampler2D normalMap;

out vec4 outColor;

void main() {
    // Make sure the fragment normal is actually normalized
    vec3 normal = normalize(fragNormal);

    // Diffuse shading
    vec3 lightDir = normalize(lightPosition - fragPosition);
    vec3 lightDiffuse = diffuse.xyz * max(dot(normal, lightDir), 0);

    // Specular shading
    float specularStrength = 0.5;
    vec3 viewDir = normalize(viewPosition - fragPosition);
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0), 32);
    vec3 lightSpecular = specularStrength * spec * specular.xyz;

    vec3 result;
	if (hasTexture) {
        result = (ambient.xyz + lightDiffuse + lightSpecular) * texture2D(tex, fragTexCoord).xyz;
	} else {
        result = (ambient.xyz + lightDiffuse + lightSpecular) * diffuse.xyz;
	}
    outColor = vec4(result, 1);
}
