<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <template #header>
            <div class="card-header">
              <span>Total Sales</span>
              <el-tag type="success">+15%</el-tag>
            </div>
          </template>
          <div class="card-value">$24,500</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <template #header>
            <div class="card-header">
              <span>Total Orders</span>
              <el-tag type="warning">+8%</el-tag>
            </div>
          </template>
          <div class="card-value">156</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <template #header>
            <div class="card-header">
              <span>Total Customers</span>
              <el-tag type="info">+12%</el-tag>
            </div>
          </template>
          <div class="card-value">1,234</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <template #header>
            <div class="card-header">
              <span>Inventory Items</span>
              <el-tag type="danger">-3%</el-tag>
            </div>
          </template>
          <div class="card-value">892</div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="chart-row">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>Sales Overview</span>
            </div>
          </template>
          <div ref="salesChart" class="chart"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>Top Products</span>
            </div>
          </template>
          <div ref="productsChart" class="chart"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

const salesChart = ref<HTMLElement | null>(null)
const productsChart = ref<HTMLElement | null>(null)

onMounted(() => {
  if (salesChart.value) {
    const chart = echarts.init(salesChart.value)
    chart.setOption({
      tooltip: {
        trigger: 'axis'
      },
      xAxis: {
        type: 'category',
        data: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun']
      },
      yAxis: {
        type: 'value'
      },
      series: [{
        data: [15000, 18000, 22000, 19500, 24000, 24500],
        type: 'line',
        smooth: true
      }]
    })
  }

  if (productsChart.value) {
    const chart = echarts.init(productsChart.value)
    chart.setOption({
      tooltip: {
        trigger: 'item'
      },
      series: [{
        type: 'pie',
        radius: '70%',
        data: [
          { value: 35, name: 'Product A' },
          { value: 25, name: 'Product B' },
          { value: 20, name: 'Product C' },
          { value: 15, name: 'Product D' },
          { value: 5, name: 'Others' }
        ]
      }]
    })
  }
})
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.stat-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

.chart-row {
  margin-top: 20px;
}

.chart {
  height: 300px;
}
</style> 