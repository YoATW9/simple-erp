<template>
  <div class="customer-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Customer List</span>
          <el-button type="primary" @click="handleAdd">New Customer</el-button>
        </div>
      </template>
      
      <el-table :data="customers" style="width: 100%">
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
const customers = ref([
  {
    id: 1,
    name: 'John Doe',
    email: 'john@example.com',
    phone: '(555) 123-4567',
    address: '123 Main St, City, State'
  },
  {
    id: 2,
    name: 'Jane Smith',
    email: 'jane@example.com',
    phone: '(555) 987-6543',
    address: '456 Oak Ave, City, State'
  }
])

const handleAdd = () => {
  ElMessage.info('Add new customer functionality to be implemented')
}

const handleEdit = (row: any) => {
  ElMessage.info(`Edit customer ${row.id} functionality to be implemented`)
}

const handleDelete = (row: any) => {
  ElMessage.info(`Delete customer ${row.id} functionality to be implemented`)
}
</script>

<style scoped>
.customer-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style> 