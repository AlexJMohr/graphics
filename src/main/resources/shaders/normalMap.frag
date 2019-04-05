#version 330 core

in VS_OUT {
    vec3 position;
    vec3 normal;
    vec2 texcoord;
    vec3 tangent;
    vec3 bitangent;
    mat3 tbn;
} vs_out;

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
        col = texture(material.texture, vs_out.texcoord).xyz;
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
        col = texture(material.texture, vs_out.texcoord).xyz;
    }
    vec3 ambient = light.ambient * col;
    vec3 diffuse = light.diffuse * diff * col;
    vec3 specular = light.specular * spec * col;
    return ambient + diffuse + specular;
}

void main() {
    vec3 normal = normalize(vs_out.normal);

    // If material has normal map, use it instead of vertex normal
    if (material.hasNormalMap) {
        normal = texture(material.normalMap, vs_out.texcoord).rgb;
        normal = normalize(normal * 2 - 1); // scale range [0, 1] to [-1, 1]
        normal = normalize(vs_out.tbn * normal); // tangent space normal
    }

    //normal = texture(material.normalMap, vs_out.texcoord).rgb * 2 - 1;
    vec3 viewDir = normalize(viewPosition - vs_out.position);

    // Directional light
    vec3 result = calcDirLight(dirLight, normal, viewDir);
    // Point light
    result += calcPointLight(pointLight, normal, vs_out.position, viewDir);
    // Spot light
    // result += calcSpotLight(spotLight, normal, vs_out.position, viewDir);

    outColor = vec4(result, 1);
}
