<template>
  <el-tooltip
    :content="isDark ? 'Switch to Light Mode' : 'Switch to Dark Mode'"
    placement="bottom"
  >
    <el-button
      class="theme-switch"
      :class="{ 'is-dark': isDark }"
      circle
      @click="toggleTheme"
    >
      <el-icon v-if="isDark" class="sun-icon">
        <Sunny />
      </el-icon>
      <el-icon v-else class="moon-icon">
        <Moon />
      </el-icon>
    </el-button>
  </el-tooltip>
</template>

<script setup lang="ts">
import { Sunny, Moon } from '@element-plus/icons-vue'
import { useTheme } from '@/composables/useTheme'

const { isDark, toggleTheme } = useTheme()
</script>

<style scoped>
.theme-switch {
  margin-right: 16px;
  padding: 8px;
  border: none;
  background: transparent;
  color: var(--el-text-color-primary);
  transition: all 0.3s ease;
}

.theme-switch:hover {
  transform: scale(1.1);
  background: var(--el-color-primary-light-9);
  color: var(--el-color-primary);
}

.theme-switch.is-dark {
  color: var(--el-color-primary-light-3);
}

.theme-switch.is-dark:hover {
  color: var(--el-color-primary-light-5);
  background: var(--el-color-primary-dark-2);
}

.sun-icon,
.moon-icon {
  font-size: 18px;
  transition: transform 0.3s ease;
}

.theme-switch:hover .sun-icon,
.theme-switch:hover .moon-icon {
  transform: rotate(30deg);
}
</style> 