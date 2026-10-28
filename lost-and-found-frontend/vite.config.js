import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  // 👇 这就是新加的代理配置 👇
  server: {
    proxy: {
      '/api': {
        target: 'http://192.168.245.88:8080', // 指向你队友的后端地址
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '') // 把 /api 替换为空
      }
    }
  }
})