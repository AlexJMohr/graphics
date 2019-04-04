#version 330 core

in vec3 fragPosition;
in vec3 fragNormal;
in vec2 fragTexCoord;

struct Material {
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float shininess;
    bool hasTexture;
    bool hasNormalMap;
    sampler2D texture;
    sampler2D normalMap;
};
uniform Material material;

struct DirectionalLight {
    vec3 direction;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};
uniform DirectionalLight dirLight;

struct PointLight {
    vec3 position;
    float constant;
    float linear;
    float quadratic;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};
uniform PointLight pointLight;

uniform vec3 viewPosition;
uniform vec3 lightPosition;

out vec4 outColor;

vec3 calcDirLight(DirectionalLight light, vec3 normal, vec3 viewDir) {
    vec3 lightDir = normalize(-light.direction);
    // diffuse shading
    float diff = max(dot(normal, lightDir), 0);
    // specular shading
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0), material.shininess);

    // combine results
    vec3 col = vec3(1);
    if (material.hasTexture) {
        col = texture(material.texture, fragTexCoord).xyz;
    }
    vec3 ambient = light.ambient * col;
    vec3 diffuse = light.diffuse * diff * col;
    vec3 specular = light.specular * spec * col;
    return ambient + diffuse + specular;
}

vec3 calcPointLight(PointLight light, vec3 normal, vec3 fragPos, vec3 viewDir) {
    vec3 lightDir = normalize(light.position - fragPos);
    // diffuse shading
    float diff = max(dot(normal, lightDir), 0);
    // specular shading
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0), material.shininess);
    // attenuation
    float distance = length(light.position - fragPos);
    float attenuation = 1.0 / (light.constant + light.linear * distance + light.quadratic * distance * distance);

    // combine results
    vec3 col = vec3(1);
    if (material.hasTexture) {
        col = texture(material.texture, fragTexCoord).xyz;
    }
    vec3 ambient = light.ambient * col;
    vec3 diffuse = light.diffuse * diff * col;
    vec3 specular = light.specular * spec * col;
    return ambient + diffuse + specular;
}

void main() {
    vec3 normal = normalize(fragNormal);
    //normal = texture(material.normalMap, fragTexCoord).rgb * 2 - 1;
    vec3 viewDir = normalize(viewPosition - fragPosition);

    // Directional light
    vec3 result = calcDirLight(dirLight, normal, viewDir);
    // Point light
    result += calcPointLight(pointLight, normal, fragPosition, viewDir);
    // Spot light
    // result += calcSpotLight(spotLight, normal, fragPosition, viewDir);

    outColor = vec4(result, 1);
}
