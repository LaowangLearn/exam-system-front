<template>
  <div class="lowcode-page">
    <div class="page-header">
      <button class="back-btn" @click="goBack">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M15 18l-6-6 6-6" />
        </svg>
        返回
      </button>
      <h1>低代码开发平台</h1>
    </div>
    
    <div class="page-content">
      <div class="sidebar">
        <div class="sidebar-section">
          <h3>组件库</h3>
          <div class="component-list">
            <div v-for="comp in components" :key="comp.id" class="component-item" @click="addComponent(comp)">
              <span class="comp-icon">{{ comp.icon }}</span>
              <span class="comp-name">{{ comp.name }}</span>
            </div>
          </div>
        </div>
        
        <div class="sidebar-section">
          <h3>模板库</h3>
          <div class="template-list">
            <div v-for="template in templates" :key="template.id" class="template-item" @click="applyTemplate(template)">
              <div class="template-preview">{{ template.preview }}</div>
              <div class="template-info">
                <div class="template-name">{{ template.name }}</div>
                <div class="template-type">{{ template.type }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <div class="main-area">
        <div class="toolbar">
          <div class="tool-group">
            <button class="tool-btn" title="保存">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M19 12v7a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2v-7m14 0v-2a2 2 0 0 0-2-2H7a2 2 0 0 0-2 2v2m14 0h-2.586a1 1 0 0 1-.707-.293l-2.414-2.414a1 1 0 0 0-.707-.293h-3.172a1 1 0 0 0-.707.293l-2.414 2.414A1 1 0 0 1 6.586 12H4"></path>
              </svg>
            </button>
            <button class="tool-btn" title="预览">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"></path>
                <path d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"></path>
              </svg>
            </button>
            <button class="tool-btn" title="发布">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"></path>
                <polyline points="17 8 12 3 7 8"></polyline>
                <line x1="12" y1="3" x2="12" y2="15"></line>
              </svg>
            </button>
          </div>
          
          <div class="tool-group">
            <button class="tool-btn" title="撤销">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M3 7v6h6"></path>
                <path d="M21 17a9 9 0 0 0-9-9 9 9 0 0 0-6 2.3L3 13"></path>
              </svg>
            </button>
            <button class="tool-btn" title="重做">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M21 7v6h-6"></path>
                <path d="M3 17a9 9 0 0 0 9 9 9 9 0 0 0 6-2.3L21 13"></path>
              </svg>
            </button>
          </div>
        </div>
        
        <div class="canvas">
          <div class="canvas-header">
            <span class="canvas-title">页面设计</span>
            <span class="canvas-size">响应式布局</span>
          </div>
          <div class="canvas-area">
            <div v-for="item in canvasItems" :key="item.id" :class="['canvas-item', { selected: selectedItem === item.id }]" @click="selectItem(item.id)">
              <div class="item-content">
                <span class="item-icon">{{ item.icon }}</span>
                <span class="item-label">{{ item.label }}</span>
              </div>
              <div class="item-resize"></div>
            </div>
          </div>
        </div>
        
        <div class="properties-panel">
          <div class="panel-header">
            <h3>属性配置</h3>
          </div>
          <div class="properties-content">
            <div v-if="selectedItem" class="property-group">
              <label>组件名称</label>
              <input type="text" v-model="selectedItemName" />
            </div>
            <div class="property-group">
              <label>宽度</label>
              <input type="text" value="100%" />
            </div>
            <div class="property-group">
              <label>高度</label>
              <input type="text" value="auto" />
            </div>
            <div class="property-group">
              <label>背景颜色</label>
              <div class="color-picker">
                <input type="color" value="#ffffff" />
              </div>
            </div>
            <div class="property-group">
              <label>边框</label>
              <select>
                <option>无</option>
                <option>实线</option>
                <option>虚线</option>
              </select>
            </div>
            <div class="property-group">
              <label>圆角</label>
              <input type="range" min="0" max="20" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/data')
}

const components = [
  { id: 1, name: '文本', icon: '📝' },
  { id: 2, name: '输入框', icon: '📦' },
  { id: 3, name: '按钮', icon: '🔘' },
  { id: 4, name: '表格', icon: '📊' },
  { id: 5, name: '表单', icon: '📋' },
  { id: 6, name: '图表', icon: '📈' },
  { id: 7, name: '图片', icon: '🖼️' },
  { id: 8, name: '导航', icon: '🧭' },
  { id: 9, name: '卡片', icon: '🃏' },
  { id: 10, name: '容器', icon: '📦' }
]

const templates = [
  { id: 1, name: '登录页面', type: '页面', preview: '🔐' },
  { id: 2, name: '数据表格', type: '组件', preview: '📊' },
  { id: 3, name: '表单页面', type: '页面', preview: '📝' },
  { id: 4, name: '仪表盘', type: '页面', preview: '📈' }
]

const canvasItems = ref([
  { id: 1, icon: '📝', label: '标题文本', type: 'text' },
  { id: 2, icon: '📦', label: '输入框', type: 'input' },
  { id: 3, icon: '🔘', label: '提交按钮', type: 'button' },
  { id: 4, icon: '📊', label: '数据表格', type: 'table' }
])

const selectedItem = ref(1)
const selectedItemName = ref('标题文本')

const addComponent = (comp) => {
  canvasItems.value.push({
    id: Date.now(),
    icon: comp.icon,
    label: comp.name,
    type: comp.name.toLowerCase()
  })
}

const selectItem = (id) => {
  selectedItem.value = id
  const item = canvasItems.value.find(i => i.id === id)
  if (item) {
    selectedItemName.value = item.label
  }
}

const applyTemplate = (template) => {
  alert(`应用模板: ${template.name}`)
}
</script>

<style scoped>
.lowcode-page {
  min-height: 100vh;
  background: #f5f7fa;
  display: flex;
  flex-direction: column;
}

.page-header {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 15px 20px;
  background: white;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
}

.back-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background: #22c55e;
  border: none;
  border-radius: 8px;
  color: white;
  cursor: pointer;
  font-size: 14px;
}

.page-header h1 {
  margin: 0;
  font-size: 20px;
  color: #333;
}

.page-content {
  flex: 1;
  display: flex;
  gap: 20px;
  padding: 20px;
}

.sidebar {
  width: 250px;
  background: white;
  border-radius: 12px;
  padding: 15px;
  overflow-y: auto;
}

.sidebar-section {
  margin-bottom: 20px;
}

.sidebar-section h3 {
  margin: 0 0 12px;
  font-size: 14px;
  color: #666;
  font-weight: 600;
}

.component-list {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 8px;
}

.component-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 12px;
  background: #f8f9fa;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
}

.component-item:hover {
  background: #e3f2fd;
}

.comp-icon {
  font-size: 24px;
  margin-bottom: 4px;
}

.comp-name {
  font-size: 12px;
  color: #666;
}

.template-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.template-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  background: #f8f9fa;
  border-radius: 8px;
  cursor: pointer;
}

.template-preview {
  width: 40px;
  height: 40px;
  background: linear-gradient(135deg, #22c55e 0%, #16a34a 100%);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
}

.template-info {
  flex: 1;
}

.template-name {
  font-size: 13px;
  color: #333;
  font-weight: 500;
}

.template-type {
  font-size: 11px;
  color: #999;
}

.main-area {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.toolbar {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 15px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0,0,0,0.05);
}

.tool-group {
  display: flex;
  align-items: center;
  gap: 5px;
  padding-right: 10px;
  border-right: 1px solid #eee;
}

.tool-group:last-child {
  border-right: none;
}

.tool-btn {
  padding: 10px;
  background: #f8f9fa;
  border: none;
  border-radius: 8px;
  color: #666;
  cursor: pointer;
  transition: all 0.3s;
}

.tool-btn:hover {
  background: #e3f2fd;
  color: #3b82f6;
}

.canvas {
  flex: 1;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
  display: flex;
  flex-direction: column;
}

.canvas-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  background: #f8f9fa;
  border-radius: 12px 12px 0 0;
}

.canvas-title {
  font-size: 14px;
  font-weight: 600;
  color: #333;
}

.canvas-size {
  font-size: 12px;
  color: #999;
}

.canvas-area {
  flex: 1;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 15px;
  background: #fafafa;
  min-height: 400px;
}

.canvas-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 20px;
  background: white;
  border: 2px dashed transparent;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.3s;
}

.canvas-item:hover {
  border-color: #22c55e;
}

.canvas-item.selected {
  border-color: #22c55e;
  background: #f0fdf4;
}

.item-content {
  display: flex;
  align-items: center;
  gap: 12px;
}

.item-icon {
  font-size: 24px;
}

.item-label {
  font-size: 14px;
  color: #333;
}

.item-resize {
  width: 10px;
  height: 10px;
  background: #ddd;
  border-radius: 50%;
  margin-left: auto;
  cursor: nwse-resize;
}

.properties-panel {
  width: 280px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
}

.panel-header {
  padding: 15px 20px;
  border-bottom: 1px solid #eee;
}

.panel-header h3 {
  margin: 0;
  font-size: 14px;
  color: #333;
  font-weight: 600;
}

.properties-content {
  padding: 15px 20px;
}

.property-group {
  margin-bottom: 15px;
}

.property-group label {
  display: block;
  margin-bottom: 6px;
  font-size: 12px;
  color: #666;
  font-weight: 500;
}

.property-group input[type="text"],
.property-group select {
  width: 100%;
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 13px;
  box-sizing: border-box;
}

.color-picker input[type="color"] {
  width: 100%;
  height: 36px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.property-group input[type="range"] {
  width: 100%;
}

@media (max-width: 1200px) {
  .sidebar {
    width: 200px;
  }
  
  .properties-panel {
    width: 240px;
  }
}

@media (max-width: 900px) {
  .page-content {
    flex-direction: column;
  }
  
  .sidebar, .properties-panel {
    width: 100%;
  }
  
  .main-area {
    order: -1;
  }
}
</style>