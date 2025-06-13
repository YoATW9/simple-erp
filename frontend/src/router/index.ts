import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: () => import('../layouts/MainLayout.vue'),
      children: [
        {
          path: '',
          name: 'dashboard',
          component: () => import('../views/Dashboard.vue')
        },
        {
          path: 'inventory',
          name: 'inventory',
          component: () => import('../views/inventory/InventoryList.vue')
        },
        {
          path: 'sales',
          name: 'sales',
          component: () => import('../views/sales/SalesList.vue')
        },
        {
          path: 'purchases',
          name: 'purchases',
          component: () => import('../views/purchases/PurchaseList.vue')
        },
        {
          path: 'customers',
          name: 'customers',
          component: () => import('../views/customers/CustomerList.vue')
        },
        {
          path: 'suppliers',
          name: 'suppliers',
          component: () => import('../views/suppliers/SupplierList.vue')
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/Login.vue')
    }
  ]
})

export default router 