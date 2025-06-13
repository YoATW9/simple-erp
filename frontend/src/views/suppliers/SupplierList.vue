<template>
  <div class="supplier-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Supplier List</span>
          <el-button type="primary" @click="handleAdd">New Supplier</el-button>
        </div>
      </template>
      
      <el-table :data="suppliers" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="Name" />
        <el-table-column prop="email" label="Email" />
        <el-table-column prop="phone" label="Phone" width="150" />
        <el-table-column prop="address" label="Address" />
        <el-table-column label="Actions" width="150">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">Edit</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessage } from 'element-plus'

// Mock data - replace with actual API calls
const suppliers = ref([
  {
    id: 1,
    name: 'ABC Supplies',
    email: 'contact@abcsupplies.com',
    phone: '(555) 111-2222',
    address: '789 Business Blvd, City, State'
  },
  {
    id: 2,
    name: 'XYZ Company',
    email: 'info@xyzcompany.com',
    phone: '(555) 333-4444',
    address: '321 Industrial Park, City, State'
  }
])

const handleAdd = () => {
  ElMessage.info('Add new supplier functionality to be implemented')
}

const handleEdit = (row: any) => {
  ElMessage.info(`Edit supplier ${row.id} functionality to be implemented`)
}

const handleDelete = (row: any) => {
  ElMessage.info(`Delete supplier ${row.id} functionality to be implemented`)
}
</script>

<style scoped>
.supplier-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style> 