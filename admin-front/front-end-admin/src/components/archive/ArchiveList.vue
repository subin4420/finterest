<template>
    <div>
      <h2>Archives</h2>
      <button @click="createMaterial">Create Material</button>
      <ul>
        <li v-for="material in materials" :key="material.material_id">
          {{ material.title }} - {{ material.description }}
          <button @click="editMaterial(material)">Edit</button>
          <button @click="deleteMaterial(material.material_id)">Delete</button>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  import ArchiveService from '@/services/ArchiveService'
  
  export default {
    data() {
      return {
        materials: []
      }
    },
    created() {
      this.fetchMaterials()
    },
    methods: {
      async fetchMaterials() {
        const response = await ArchiveService.getArchives()
        this.materials = response.data
      },
      createMaterial() {
        this.$router.push('/archives/create')
      },
      editMaterial(material) {
        this.$router.push(`/archives/edit/${material.material_id}`)
      },
      async deleteMaterial(materialId) {
        await ArchiveService.deleteMaterial(materialId)
        this.fetchMaterials()
      }
    }
  }
  </script>
  