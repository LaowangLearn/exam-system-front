<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <span>实时监控</span>
      </template>
      <div class="monitor-grid">
        <div v-for="i in 8" :key="i" class="monitor-item">
          <div class="monitor-video">
            <el-icon size="48" color="#999"><VideoCamera /></el-icon>
            <span>摄像头 {{ i }}</span>
          </div>
          <div class="monitor-info">
            <span class="status online">在线</span>
            <el-button link type="primary" size="small">查看</el-button>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
</script>

<style scoped>
.page-container { width: 100%; }
.monitor-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; }
.monitor-item { border: 1px solid #eee; border-radius: 8px; overflow: hidden; }
.monitor-video { height: 180px; background: #000; display: flex; flex-direction: column; align-items: center; justify-content: center; color: #999; }
.monitor-info { padding: 10px; display: flex; justify-content: space-between; align-items: center; }
.status { padding: 2px 8px; border-radius: 4px; font-size: 12px; }
.status.online { background: #e7f7e7; color: #67c23a; }
</style>
