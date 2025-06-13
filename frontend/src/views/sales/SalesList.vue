<template>
  <div class="sales-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Sales List</span>
          <el-button type="primary" @click="handleAdd">New Sale</el-button>
        </div>
      </template>
      
      <el-table :data="sales" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="date" label="Date" width="180" />
        <el-table-column prop="customer" label="Customer" />
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

    <!-- Add/Edit Dialog -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? 'Edit Sale' : 'New Sale'"
      width="800px"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="120px"
      >
        <el-form-item label="Customer" prop="customerId">
          <el-select
            v-model="form.customerId"
            placeholder="Select customer"
            style="width: 100%"
          >
            <el-option
              v-for="customer in customers"
              :key="customer.id"
              :label="customer.name"
              :value="customer.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="Order Number" prop="orderNumber">
          <el-input v-model="form.orderNumber" :disabled="isEdit" />
        </el-form-item>

        <el-form-item label="Status" prop="status">
          <el-select v-model="form.status" placeholder="Select status" style="width: 100%">
            <el-option label="Pending" value="PENDING" />
            <el-option label="Processing" value="PROCESSING" />
            <el-option label="Completed" value="COMPLETED" />
            <el-option label="Cancelled" value="CANCELLED" />
          </el-select>
        </el-form-item>

        <el-divider>Order Items</el-divider>

        <div v-for="(item, index) in form.items" :key="index" class="order-item">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item
                :label="index === 0 ? 'Product' : ''"
                :prop="'items.' + index + '.productId'"
                :rules="{ required: true, message: 'Please select a product', trigger: 'change' }"
              >
                <el-select
                  v-model="item.productId"
                  placeholder="Select product"
                  style="width: 100%"
                  @change="handleProductChange(index)"
                >
                  <el-option
                    v-for="product in products"
                    :key="product.id"
                    :label="product.name"
                    :value="product.id"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item
                :label="index === 0 ? 'Quantity' : ''"
                :prop="'items.' + index + '.quantity'"
                :rules="{ required: true, message: 'Please input quantity', trigger: 'blur' }"
              >
                <el-input-number
                  v-model="item.quantity"
                  :min="1"
                  @change="calculateItemTotal(index)"
                />
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item
                :label="index === 0 ? 'Unit Price' : ''"
                :prop="'items.' + index + '.unitPrice'"
              >
                <el-input-number
                  v-model="item.unitPrice"
                  :precision="2"
                  :step="0.1"
                  :min="0"
                  :disabled="true"
                />
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item
                :label="index === 0 ? 'Total' : ''"
                :prop="'items.' + index + '.totalPrice'"
              >
                <el-input-number
                  v-model="item.totalPrice"
                  :precision="2"
                  :step="0.1"
                  :min="0"
                  :disabled="true"
                />
              </el-form-item>
            </el-col>
            <el-col :span="4" class="item-actions">
              <el-button
                type="danger"
                circle
                @click="removeItem(index)"
                :disabled="form.items.length === 1"
              >
                <el-icon><Delete /></el-icon>
              </el-button>
            </el-col>
          </el-row>
        </div>

        <div class="add-item">
          <el-button type="primary" @click="addItem">
            <el-icon><Plus /></el-icon>
            Add Item
          </el-button>
        </div>

        <el-divider />

        <el-form-item label="Total Amount">
          <el-input-number
            v-model="form.totalAmount"
            :precision="2"
            :step="0.1"
            :min="0"
            :disabled="true"
            style="width: 200px"
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
import { Plus, Delete } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'
import axios from 'axios'

interface Customer {
  id: number
  name: string
  email: string
  phoneNumber: string
  address: string
}

interface Product {
  id: number
  name: string
  sku: string
  price: number
  stockQuantity: number
}

interface OrderItem {
  productId: number
  quantity: number
  unitPrice: number
  totalPrice: number
}

interface Sale {
  id: number
  orderNumber: string
  customerId: number
  customerName: string
  orderDate: string
  totalAmount: number
  status: string
  items: OrderItem[]
}

const loading = ref(false)
const sales = ref<Sale[]>([])
const customers = ref<Customer[]>([])
const products = ref<Product[]>([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()

const form = ref({
  customerId: undefined as number | undefined,
  orderNumber: '',
  status: 'PENDING',
  totalAmount: 0,
  items: [
    {
      productId: undefined as number | undefined,
      quantity: 1,
      unitPrice: 0,
      totalPrice: 0
    }
  ]
})

const rules: FormRules = {
  customerId: [
    { required: true, message: 'Please select a customer', trigger: 'change' }
  ],
  orderNumber: [
    { required: true, message: 'Please input order number', trigger: 'blur' }
  ],
  status: [
    { required: true, message: 'Please select status', trigger: 'change' }
  ]
}

const fetchSales = async () => {
  loading.value = true
  try {
    const response = await axios.get('http://localhost:8080/api/sales')
    sales.value = response.data
  } catch (error) {
    ElMessage.error('Failed to fetch sales')
  } finally {
    loading.value = false
  }
}

const fetchCustomers = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/customers')
    customers.value = response.data
  } catch (error) {
    ElMessage.error('Failed to fetch customers')
  }
}

const fetchProducts = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/products')
    products.value = response.data
  } catch (error) {
    ElMessage.error('Failed to fetch products')
  }
}

const handleAdd = () => {
  isEdit.value = false
  form.value = {
    customerId: undefined,
    orderNumber: generateOrderNumber(),
    status: 'PENDING',
    totalAmount: 0,
    items: [
      {
        productId: undefined,
        quantity: 1,
        unitPrice: 0,
        totalPrice: 0
      }
    ]
  }
  dialogVisible.value = true
}

const generateOrderNumber = () => {
  const date = new Date()
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const random = Math.floor(Math.random() * 10000).toString().padStart(4, '0')
  return `SO-${year}${month}${day}-${random}`
}

const handleProductChange = (index: number) => {
  const product = products.value.find(p => p.id === form.value.items[index].productId)
  if (product) {
    form.value.items[index].unitPrice = product.price
    calculateItemTotal(index)
  }
}

const calculateItemTotal = (index: number) => {
  const item = form.value.items[index]
  item.totalPrice = item.quantity * item.unitPrice
  calculateTotalAmount()
}

const calculateTotalAmount = () => {
  form.value.totalAmount = form.value.items.reduce((total, item) => total + item.totalPrice, 0)
}

const addItem = () => {
  form.value.items.push({
    productId: undefined as number | undefined,
    quantity: 1,
    unitPrice: 0,
    totalPrice: 0
  })
}

const removeItem = (index: number) => {
  form.value.items.splice(index, 1)
  calculateTotalAmount()
}

const handleSubmit = async () => {
  if (!formRef.value) return

  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const saleData = {
          ...form.value,
          orderDate: new Date().toISOString()
        }

        if (isEdit.value) {
          await axios.put(`http://localhost:8080/api/sales/${form.value.id}`, saleData)
          ElMessage.success('Sale updated successfully')
        } else {
          await axios.post('http://localhost:8080/api/sales', saleData)
          ElMessage.success('Sale created successfully')
        }
        dialogVisible.value = false
        fetchSales()
      } catch (error: any) {
        if (error.response?.data?.message) {
          ElMessage.error(error.response.data.message)
        } else {
          ElMessage.error('Failed to save sale')
        }
      }
    }
  })
}

const handleEdit = (row: Sale) => {
  isEdit.value = true
  form.value = {
    ...row,
    customerId: row.customerId,
    items: row.items.map(item => ({
      productId: item.productId,
      quantity: item.quantity,
      unitPrice: item.unitPrice,
      totalPrice: item.totalPrice
    }))
  }
  dialogVisible.value = true
}

const handleDelete = async (row: Sale) => {
  try {
    await ElMessageBox.confirm(
      'Are you sure you want to delete this sale?',
      'Warning',
      {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }
    )
    
    await axios.delete(`http://localhost:8080/api/sales/${row.id}`)
    ElMessage.success('Sale deleted successfully')
    fetchSales()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('Failed to delete sale')
    }
  }
}

onMounted(() => {
  fetchSales()
  fetchCustomers()
  fetchProducts()
})
</script>

<style scoped>
.sales-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.order-item {
  margin-bottom: 20px;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
}

.item-actions {
  display: flex;
  align-items: flex-end;
  padding-bottom: 22px;
}

.add-item {
  margin: 20px 0;
  text-align: center;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style> 