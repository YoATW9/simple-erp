<template>
  <div class="purchase-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Purchase List</span>
          <el-button type="primary" @click="handleAdd">New Purchase</el-button>
        </div>
      </template>
      
      <el-table :data="purchases" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="date" label="Date" width="180" />
        <el-table-column prop="supplier" label="Supplier" />
        <el-table-column prop="total" label="Total" width="120" />
        <el-table-column prop="status" label="Status" width="120" />
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
const purchases = ref([
  {
    id: 1,
    date: '2024-03-20',
    supplier: 'ABC Supplies',
    total: '$2,500.00',
    status: 'Received'
  },
  {
    id: 2,
    date: '2024-03-19',
    supplier: 'XYZ Company',
    total: '$1,800.00',
    status: 'Pending'
  }
])

const handleAdd = () => {
  ElMessage.info('Add new purchase functionality to be implemented')
}

const handleEdit = (row: any) => {
  ElMessage.info(`Edit purchase ${row.id} functionality to be implemented`)
}

const handleDelete = (row: any) => {
  ElMessage.info(`Delete purchase ${row.id} functionality to be implemented`)
}
</script>

<style scoped>
.purchase-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style> 