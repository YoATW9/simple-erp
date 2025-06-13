<template>
  <div class="inventory-container">
    <div class="header-actions">
      <h2>Inventory Management</h2>
      <el-button type="primary" @click="showAddDialog">
        <el-icon><Plus /></el-icon>
        Add Product
      </el-button>
    </div>

    <el-card>
      <el-table
        v-loading="loading"
        :data="products"
        style="width: 100%"
      >
        <el-table-column prop="sku" label="SKU" width="120" />
        <el-table-column prop="name" label="Name" />
        <el-table-column prop="category" label="Category" width="150" />
        <el-table-column prop="price" label="Price" width="120">
          <template #default="scope">
            ${{ scope.row.price }}
          </template>
        </el-table-column>
        <el-table-column prop="stockQuantity" label="Stock" width="120">
          <template #default="scope">
            <el-tag
              :type="scope.row.stockQuantity > 10 ? 'success' : 'danger'"
            >
              {{ scope.row.stockQuantity }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Actions" width="200">
          <template #default="scope">
            <el-button-group>
              <el-button
                size="small"
                @click="handleEdit(scope.row)"
              >
                Edit
              </el-button>
              <el-button
                size="small"
                type="danger"
                @click="handleDelete(scope.row)"
              >
                Delete
              </el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- Add/Edit Dialog -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? 'Edit Product' : 'Add Product'"
      width="500px"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="SKU" prop="sku">
          <el-input v-model="form.sku" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="Category" prop="category">
          <el-input v-model="form.category" />
        </el-form-item>
        <el-form-item label="Price" prop="price">
          <el-input-number
            v-model="form.price"
            :precision="2"
            :step="0.1"
            :min="0"
          />
        </el-form-item>
        <el-form-item label="Stock" prop="stockQuantity">
          <el-input-number
            v-model="form.stockQuantity"
            :min="0"
            :step="1"
          />
        </el-form-item>
        <el-form-item label="Description" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="3"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">Cancel</el-button>
          <el-button type="primary" @click="handleSubmit">
            {{ isEdit ? 'Update' : 'Create' }}
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'
import axios from 'axios'

interface Product {
  id?: number
  sku: string
  name: string
  category: string
  price: string | number
  stockQuantity: number
  description: string
}

const loading = ref(false)
const products = ref<Product[]>([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()

const form = ref<Product>({
  sku: '',
  name: '',
  category: '',
  price: '0.00',
  stockQuantity: 0,
  description: ''
})

const rules: FormRules = {
  sku: [
    { required: true, message: 'Please input SKU', trigger: 'blur' },
    { min: 3, max: 20, message: 'Length should be 3 to 20', trigger: 'blur' }
  ],
  name: [
    { required: true, message: 'Please input product name', trigger: 'blur' }
  ],
  category: [
    { required: true, message: 'Please input category', trigger: 'blur' }
  ],
  price: [
    { required: true, message: 'Please input price', trigger: 'blur' },
    { pattern: /^\d+(\.\d{1,2})?$/, message: 'Please enter a valid price with up to 2 decimal places', trigger: 'blur' }
  ],
  stockQuantity: [
    { required: true, message: 'Please input stock quantity', trigger: 'blur' }
  ]
}

const fetchProducts = async () => {
  loading.value = true
  try {
    const response = await axios.get('http://localhost:8080/api/products')
    products.value = response.data.map((product: any) => ({
      ...product,
      price: parseFloat(product.price)
    }))
  } catch (error) {
    ElMessage.error('Failed to fetch products')
  } finally {
    loading.value = false
  }
}

const showAddDialog = () => {
  isEdit.value = false
  form.value = {
    sku: '',
    name: '',
    category: '',
    price: '0.00',
    stockQuantity: 0,
    description: ''
  }
  dialogVisible.value = true
}

const handleEdit = (row: Product) => {
  isEdit.value = true
  form.value = {
    ...row,
    price: typeof row.price === 'number' ? row.price.toFixed(2) : row.price
  }
  dialogVisible.value = true
}

const handleDelete = async (row: Product) => {
  try {
    await ElMessageBox.confirm(
      'Are you sure you want to delete this product?',
      'Warning',
      {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }
    )
    
    await axios.delete(`http://localhost:8080/api/products/${row.id}`)
    ElMessage.success('Product deleted successfully')
    fetchProducts()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('Failed to delete product')
    }
  }
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const productData = {
          ...form.value,
          price: parseFloat(form.value.price.toString())
        }
        
        if (isEdit.value && form.value.id) {
          await axios.put(`http://localhost:8080/api/products/${form.value.id}`, productData)
          ElMessage.success('Product updated successfully')
        } else {
          await axios.post('http://localhost:8080/api/products', productData)
          ElMessage.success('Product created successfully')
        }
        dialogVisible.value = false
        fetchProducts()
      } catch (error: any) {
        if (error.response?.data?.message) {
          ElMessage.error(error.response.data.message)
        } else {
          ElMessage.error('Failed to save product')
        }
      }
    }
  })
}

onMounted(() => {
  fetchProducts()
})
</script>

<style scoped>
.inventory-container {
  padding: 20px;
}

.header-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header-actions h2 {
  margin: 0;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style> 