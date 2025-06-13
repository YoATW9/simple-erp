import { ref, computed, watchEffect } from 'vue'

const THEME_KEY = 'theme-mode'

export type ThemeMode = 'light' | 'dark'

export function useTheme() {
  const theme = ref<ThemeMode>(
    (localStorage.getItem(THEME_KEY) as ThemeMode) || 'light'
  )

  const isDark = computed(() => theme.value === 'dark')

  const setTheme = (mode: ThemeMode) => {
    theme.value = mode
    localStorage.setItem(THEME_KEY, mode)
    document.documentElement.classList.toggle('dark', mode === 'dark')
  }

  // Watch for changes and apply theme
  watchEffect(() => {
    setTheme(theme.value)
  })

  const toggleTheme = () => {
    setTheme(theme.value === 'light' ? 'dark' : 'light')
  }

  return {
    theme,
    setTheme,
    toggleTheme,
    isDark
  }
} 