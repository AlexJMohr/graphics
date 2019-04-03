package com.alexjmohr.graphics;

import static org.lwjgl.assimp.Assimp.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.joml.Vector4f;
import org.lwjgl.*;
import org.lwjgl.assimp.*;
import org.lwjgl.system.MemoryUtil;

/**
 * Loads 3D model scenes using Assimp
 * @author Alex Mohr
 *
 */
public class ModelLoader {

	/**
	 * List of loaded meshes
	 */
	private ArrayList<Mesh> meshes;
	
//	/**
//	 * List of loaded materials
//	 */
	private ArrayList<Material> materials;
	
	public ModelLoader() {
		meshes = new ArrayList<Mesh>();
		materials = new ArrayList<Material>();
	}
	
	/**
	 * Loads a model from the given file path, and saves it in this object's data structures.
	 * @param resourcePath the file to load the model from
	 */
	public void loadModel(String resourcePath, String texturesDir) throws Exception {

		// Import file from absolute path
		AIScene aiScene = aiImportFile(
			resourcePath,
			aiProcess_CalcTangentSpace |
			aiProcess_GenNormals |
			// aiProcess_GenUVCoords |
			// aiProcess_JoinIdenticalVertices |
			aiProcess_OptimizeMeshes |
			// aiProcess_SortByPType |
			aiProcess_Triangulate
		);
		if (aiScene == null) {
			throw new RuntimeException("Failed to load model: " + aiGetErrorString());
		}

		int numMaterials = aiScene.mNumMaterials();
		PointerBuffer aiMaterials = aiScene.mMaterials();
		for (int i = 0; i < numMaterials; i++) {
			AIMaterial aiMaterial = AIMaterial.create(aiMaterials.get(i));
			processMaterial(aiMaterial, texturesDir);
		}
		
		int numMeshes = aiScene.mNumMeshes();
		PointerBuffer aiMeshes = aiScene.mMeshes();
		for (int i = 0; i < numMeshes; i++) {
			AIMesh aiMesh = AIMesh.create(aiMeshes.get(i));
			processMesh(aiMesh);
		}
	}

	/**
	 * Process the given AIMaterial
	 * @param aiMaterial the AIMaterial to process
	 */
	private void processMaterial(AIMaterial aiMaterial, String texturesDir) throws Exception {
		AIColor4D colour = AIColor4D.create();

		// Load texture
		AIString path = AIString.calloc();
		aiGetMaterialTexture(aiMaterial, aiTextureType_DIFFUSE, 0, path, (IntBuffer) null, null, null, null, null, null);
		String texPath = path.dataString();
		Texture texture = null;
		if (texPath != null && texPath.length() > 0) {
			String textureFile = texturesDir + "/" + texPath;
			textureFile = textureFile.replace("//", "/");
			texture = TextureCache.getInstance().getTexture(textureFile);
		}

		// Get ambient colour
		Vector4f ambient = null;
		int result = aiGetMaterialColor(aiMaterial, AI_MATKEY_COLOR_AMBIENT, aiTextureType_NONE, 0, colour);
		if (result == 0) {
			ambient = new Vector4f(colour.r(), colour.g(), colour.b(), colour.a());
		}

		// Get diffuse colour
		Vector4f diffuse = null;
		result = aiGetMaterialColor(aiMaterial, AI_MATKEY_COLOR_DIFFUSE, aiTextureType_NONE, 0, colour);
		if (result == 0) {
			diffuse = new Vector4f(colour.r(), colour.g(), colour.b(), colour.a());
		}

		// Get specular colour
		Vector4f specular = null;
		result = aiGetMaterialColor(aiMaterial, AI_MATKEY_COLOR_DIFFUSE, aiTextureType_NONE, 0, colour);
		if (result == 0) {
			diffuse = new Vector4f(colour.r(), colour.g(), colour.b(), colour.a());
		}

		// Create the material with given colours and texture
		Material material = new Material(diffuse, ambient, specular);
		material.setTexture(texture);
		materials.add(material);
	}
	
	/**
	 * Process the given AIMesh and saves it the list of meshes
	 * @param aiMesh the AIMesh to process
	 */
	private void processMesh(AIMesh aiMesh) {
		FloatBuffer vertBuffer = MemoryUtil.memAllocFloat(aiMesh.mNumVertices() * 3);
		FloatBuffer normBuffer = MemoryUtil.memAllocFloat(aiMesh.mNumVertices() * 3);
		FloatBuffer texCoordBuffer = MemoryUtil.memAllocFloat(aiMesh.mNumVertices() * 2);
		IntBuffer indexBuffer = MemoryUtil.memAllocInt(aiMesh.mNumFaces() * 3);

		// process vertices
		AIVector3D.Buffer aiVertices = aiMesh.mVertices();
		while (aiVertices.hasRemaining()) {
			AIVector3D aiVertex = aiVertices.get();
			vertBuffer.put(aiVertex.x());
			vertBuffer.put(aiVertex.y());
			vertBuffer.put(aiVertex.z());
		}
		vertBuffer.flip();

		// process normals
		AIVector3D.Buffer aiNormals = aiMesh.mNormals();
		while (aiNormals.hasRemaining()) {
			AIVector3D aiNormal = aiNormals.get();
			normBuffer.put(aiNormal.x());
			normBuffer.put(aiNormal.y());
			normBuffer.put(aiNormal.z());
		}
		normBuffer.flip();
		
		// process texture coordinates
		AIVector3D.Buffer aiTexCoords = aiMesh.mTextureCoords(0);
		int numTexCoords = aiTexCoords != null ? aiTexCoords.remaining() : 0;
		for (int i = 0; i < numTexCoords; i++) {
			AIVector3D aiTexCoord = aiTexCoords.get(i);
			texCoordBuffer.put(aiTexCoord.x());
			texCoordBuffer.put(1.0f - aiTexCoord.y());
		}
		texCoordBuffer.flip();

		// process indices
		AIFace.Buffer aiFaces = aiMesh.mFaces();
		while (aiFaces.hasRemaining()) {
			AIFace aiFace = aiFaces.get();
			IntBuffer faceBuffer = aiFace.mIndices();
			// indexBuffer.put(faceBuffer);
			while (faceBuffer.hasRemaining()) {
				indexBuffer.put(faceBuffer.get());
			}
		}
		indexBuffer.flip();

		// Get the mesh's material
		Material material;
		int materialIndex = aiMesh.mMaterialIndex();
		if (materialIndex >= 0 && materialIndex < materials.size()) {
			material = materials.get(materialIndex);
		} else {
			material = new Material();
		}
		
		// Create the mesh and add it to the meshes array
		Mesh mesh = new Mesh(vertBuffer, normBuffer, texCoordBuffer, indexBuffer);
		mesh.setMaterial(material);
		meshes.add(mesh);
	}
	
	/**
	 * Gets the number of meshes that have been loaded
	 * @return the number of loaded meshes
	 */
	public int getNumMeshes() {
		return meshes.size();
	}
	
	/**
	 * Get the mesh at the specified index
	 * @param index the index of the mesh to get
	 * @return the mesh
	 */
	public Mesh getMesh(int index) {
		try {
			return meshes.get(index);
		} catch (IndexOutOfBoundsException ex) {
			// rethrow the exception with a more descriptive message
			throw new IndexOutOfBoundsException("Requested mesh out of bounds of mesh loader's meshes list");
		}
	}

	/**
	 * Deletes the loaded meshes and materials
	 */
	public void delete() {
		for (Mesh mesh : meshes) {
			mesh.delete();
		}
		meshes.clear();
	}
}
