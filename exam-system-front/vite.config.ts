import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5174 // 本地开发服务的端口
  },
  // 👇 新增：生产构建配置
  build: {
    rollupOptions: {
      output: {
        // 配置不同类型文件的命名规则，加上8位内容哈希
        // [name]：保留原文件名
        // [hash:8]：根据文件内容生成8位哈希值，文件内容不变哈希就不变
        // [ext]：保留原文件后缀

        // 处理CSS、图片、字体等静态资源
        assetFileNames: '[name].[hash:8].[ext]',
        // 处理代码分割后生成的chunk文件
        chunkFileNames: '[name].[hash:8].js',
        // 处理入口JS文件
        entryFileNames: '[name].[hash:8].js'
      }
    },
    // 可选：生产环境关闭sourcemap，减少打包体积
    sourcemap: false
  }
})