<template>
  <div class="app-container">
    <router-view />
  </div>
</template>

<script setup lang="ts">
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  width: 100%;
  height: 100%;
  overflow-x: hidden;
}

#app {
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  min-height: 100vh;
}

.app-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #0f0f1a 0%, #1a1a2e 50%, #16213e 100%);
}

::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 3px;
}

::-webkit-scrollbar-thumb {
  background: rgba(0, 240, 255, 0.3);
  border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
  background: rgba(0, 240, 255, 0.5);
}

.el-table {
  --el-table-bg-color: rgba(255, 255, 255, 0.03) !important;
  --el-table-tr-bg-color: rgba(255, 255, 255, 0.03) !important;
  --el-table-header-bg-color: rgba(0, 240, 255, 0.1) !important;
  --el-table-row-hover-bg-color: rgba(0, 240, 255, 0.08) !important;
  --el-table-border-color: rgba(255, 255, 255, 0.1) !important;
  --el-table-text-color: #e0e0e0 !important;
  --el-table-header-text-color: #00f0ff !important;
}

.el-button--primary {
  --el-button-bg-color: rgba(0, 240, 255, 0.15);
  --el-button-border-color: rgba(0, 240, 255, 0.4);
  --el-button-hover-bg-color: rgba(0, 240, 255, 0.25);
  --el-button-hover-border-color: rgba(0, 240, 255, 0.6);
  --el-button-text-color: #00f0ff;
}

.el-button--danger {
  --el-button-bg-color: rgba(255, 82, 82, 0.15);
  --el-button-border-color: rgba(255, 82, 82, 0.4);
  --el-button-hover-bg-color: rgba(255, 82, 82, 0.25);
  --el-button-hover-border-color: rgba(255, 82, 82, 0.6);
  --el-button-text-color: #ff5252;
}

.el-input__wrapper {
  background: rgba(255, 255, 255, 0.08) !important;
  border: 1px solid rgba(255, 255, 255, 0.15) !important;
  box-shadow: none !important;
}

.el-input__wrapper:hover {
  border-color: rgba(0, 240, 255, 0.4) !important;
}

.el-input__wrapper.is-focus {
  border-color: #00f0ff !important;
  box-shadow: 0 0 12px rgba(0, 240, 255, 0.3) !important;
}

.el-input__inner {
  color: #ffffff !important;
}

.el-input__inner::placeholder {
  color: rgba(255, 255, 255, 0.4) !important;
}

.el-form-item__label {
  color: rgba(255, 255, 255, 0.8) !important;
}

.el-card {
  --el-card-bg-color: rgba(255, 255, 255, 0.05);
  --el-card-border-color: rgba(255, 255, 255, 0.1);
}

.el-tag {
  border-radius: 8px;
}

.el-page-header__content {
  color: #ffffff;
}
</style>
