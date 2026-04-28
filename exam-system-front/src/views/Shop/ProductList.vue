<template>
  <div class="shop-container">
    <button class="back-btn" @click="goBack">← 返回营销客户类</button>
    <div class="page-header">
      <div class="header-title">
        <div class="title-icon">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M2 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z"></path>
          </svg>
        </div>
        <h1>电商商城系统</h1>
      </div>
      <button class="add-btn" @click="showAddModal = true">+ 新增商品</button>
    </div>

    <div class="stats-row">
      <div class="stat-card">
        <div class="stat-icon primary">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
            <line x1="16" y1="2" x2="16" y2="6"></line>
            <line x1="8" y1="2" x2="8" y2="6"></line>
            <line x1="3" y1="10" x2="21" y2="10"></line>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.total }}</h3>
          <p>商品总数</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon success">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
            <polyline points="22 4 12 14.01 9 11.01"></polyline>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.active }}</h3>
          <p>在售商品</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon warning">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <path d="M21 11.5a8.38 8.38 0 0 1-.9 3.8 8.5 8.5 0 0 1-7.6 4.7 8.38 8.38 0 0 1-3.8-.9L3 21l1.9-5.7a8.38 8.38 0 0 1-.9-3.8 8.5 8.5 0 0 1 4.7-7.6 8.38 8.38 0 0 1 3.8-.9h.5a8.48 8.48 0 0 1 8 8v.5z"></path>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.totalSales }}</h3>
          <p>总销量</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon info">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <circle cx="12" cy="12" r="10"></circle>
            <polyline points="12 6 12 12 16 14"></polyline>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.totalRevenue }}</h3>
          <p>总销售额</p>
        </div>
      </div>
    </div>

    <div class="search-bar">
      <div class="search-input-group">
        <svg class="search-icon" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="11" cy="11" r="8"></circle>
          <path d="M21 21l-4.35-4.35"></path>
        </svg>
        <input v-model="searchName" placeholder="搜索商品名称..." @keyup.enter="loadProducts" />
      </div>
      <select v-model="searchCategory" @change="loadProducts">
        <option value="">全部分类</option>
        <option value="电子产品">电子产品</option>
        <option value="服装鞋帽">服装鞋帽</option>
        <option value="食品饮料">食品饮料</option>
      </select>
      <button class="search-btn" @click="loadProducts">搜索</button>
    </div>

    <div v-if="loading" class="loading-overlay">
      <div class="loading-spinner"></div>
      <span>加载中...</span>
    </div>

    <div class="product-grid">
      <div v-if="!loading && products.length === 0" class="empty-state">
        <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="#9ca3af" stroke-width="1.5">
          <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
          <line x1="16" y1="2" x2="16" y2="6"></line>
          <line x1="8" y1="2" x2="8" y2="6"></line>
          <line x1="3" y1="10" x2="21" y2="10"></line>
        </svg>
        <p>暂无商品数据</p>
      </div>
      <div v-for="product in products" :key="product.id" class="product-card">
        <div class="product-image">
          <svg width="100" height="100" viewBox="0 0 24 24" fill="none" stroke="#ec4899" stroke-width="1.5">
            <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
            <line x1="16" y1="2" x2="16" y2="6"></line>
            <line x1="8" y1="2" x2="8" y2="6"></line>
            <line x1="3" y1="10" x2="21" y2="10"></line>
          </svg>
        </div>
        <div class="product-info">
          <h3>{{ product.name }}</h3>
          <p class="price">¥{{ product.price.toFixed(2) }}</p>
          <p class="spec">{{ product.spec }}</p>
          <div class="product-meta">
            <span>库存: {{ product.stock }}</span>
            <span>销量: {{ product.sales }}</span>
          </div>
          <div class="product-actions">
            <button class="action-btn view" @click="viewProduct(product)">查看</button>
            <button class="action-btn edit" @click="editProduct(product)">编辑</button>
            <button class="action-btn delete" @click="deleteProduct(product.id)">删除</button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="showAddModal" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <h2>{{ editingProduct ? '编辑商品' : '新增商品' }}</h2>
        <form @submit.prevent="saveProduct">
          <div class="form-row">
            <div class="form-group">
              <label>商品名称</label>
              <input v-model="formData.name" required />
            </div>
            <div class="form-group">
              <label>分类</label>
              <select v-model="formData.category">
                <option value="电子产品">电子产品</option>
                <option value="服装鞋帽">服装鞋帽</option>
                <option value="食品饮料">食品饮料</option>
              </select>
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label>规格</label>
              <input v-model="formData.spec" />
            </div>
            <div class="form-group">
              <label>单位</label>
              <input v-model="formData.unit" />
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label>价格</label>
              <input v-model="formData.price" type="number" step="0.01" />
            </div>
            <div class="form-group">
              <label>库存</label>
              <input v-model="formData.stock" type="number" />
            </div>
          </div>
          <div class="form-group">
            <label>描述</label>
            <textarea v-model="formData.description"></textarea>
          </div>
          <div class="form-actions">
            <button type="button" @click="closeModal">取消</button>
            <button type="submit">保存</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const products = ref<any[]>([])
const stats = ref({ total: 0, active: 0, totalSales: 0, totalRevenue: '¥0' })
const searchName = ref('')
const searchCategory = ref('')
const showAddModal = ref(false)
const editingProduct = ref<any>(null)
const loading = ref(false)

const formData = ref({
  name: '',
  category: '电子产品',
  spec: '',
  unit: '',
  price: '',
  stock: '',
  description: ''
})

const loadProducts = async () => {
  loading.value = true
  try {
    const response = await fetch(`/shop/products/search?name=${searchName.value}&category=${searchCategory.value}`)
    const result = await response.json()
    products.value = result.data || result
  } catch (error) {
    products.value = [
      { id: 1, productCode: 'PROD202602090001', name: '智能手机Pro Max', category: '电子产品', spec: '12GB+256GB', unit: '台', price: 6999, stock: 100, sales: 500 },
      { id: 2, productCode: 'PROD202602090002', name: '无线蓝牙耳机', category: '电子产品', spec: '降噪版', unit: '副', price: 299, stock: 500, sales: 2000 },
      { id: 3, productCode: 'PROD202602090003', name: '运动T恤', category: '服装鞋帽', spec: 'L码', unit: '件', price: 99, stock: 1000, sales: 1500 },
      { id: 4, productCode: 'PROD202602090004', name: '矿泉水', category: '食品饮料', spec: '500ml', unit: '瓶', price: 2, stock: 5000, sales: 10000 },
      { id: 5, productCode: 'PROD202602090005', name: '平板电脑Air', category: '电子产品', spec: '8GB+128GB', unit: '台', price: 3499, stock: 80, sales: 300 },
      { id: 6, productCode: 'PROD202602090006', name: '休闲运动鞋', category: '服装鞋帽', spec: '42码', unit: '双', price: 299, stock: 300, sales: 800 }
    ]
    stats.value = { total: 6, active: 6, totalSales: 15100, totalRevenue: '¥8,670,000' }
  }
  loading.value = false
}

const viewProduct = (product: any) => {
  console.log('View product:', product)
}

const goBack = () => {
  router.push('/marketing')
}

const editProduct = (product: any) => {
  editingProduct.value = product
  formData.value = {
    name: product.name,
    category: product.category,
    spec: product.spec,
    unit: product.unit,
    price: product.price.toString(),
    stock: product.stock.toString(),
    description: product.description || ''
  }
  showAddModal.value = true
}

const deleteProduct = async (id: number) => {
  if (confirm('确定删除该商品？')) {
    try {
      await fetch(`/shop/products/${id}`, { method: 'DELETE' })
      loadProducts()
    } catch (error) {
      products.value = products.value.filter(p => p.id !== id)
    }
  }
}

const saveProduct = async () => {
  try {
    if (editingProduct.value) {
      await fetch(`/shop/products/${editingProduct.value.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ...formData.value, id: editingProduct.value.id })
      })
    } else {
      await fetch('/shop/products', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData.value)
      })
    }
    closeModal()
    loadProducts()
  } catch (error) {
    closeModal()
    loadProducts()
  }
}

const closeModal = () => {
  showAddModal.value = false
  editingProduct.value = null
  formData.value = {
    name: '',
    category: '电子产品',
    spec: '',
    unit: '',
    price: '',
    stock: '',
    description: ''
  }
}

onMounted(() => {
  loadProducts()
})
</script>

<style scoped>
.shop-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #fff0f5 0%, #f5f7fa 50%, #f0fdf4 100%);
  padding: 30px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #ec4899 0%, #f43f5e 100%);
  color: white;
  border: none;
  padding: 12px 28px;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(236, 72, 153, 0.4);
  z-index: 10;
  display: flex;
  align-items: center;
  gap: 8px;
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(236, 72, 153, 0.6);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding: 20px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.header-title {
  display: flex;
  align-items: center;
  gap: 15px;
}

.title-icon {
  color: #ec4899;
  font-size: 24px;
}

.page-header h1 {
  font-size: 1.75rem;
  color: #1f2937;
  margin: 0;
  font-weight: 700;
}

.add-btn {
  background: linear-gradient(135deg, #ec4899 0%, #f43f5e 100%);
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 8px;
  box-shadow: 0 4px 15px rgba(236, 72, 153, 0.3);
}

.add-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(236, 72, 153, 0.4);
}

.search-bar {
  display: flex;
  gap: 15px;
  margin-bottom: 25px;
  align-items: center;
}

.search-input-group {
  flex: 1;
  max-width: 350px;
  position: relative;
}

.search-icon {
  position: absolute;
  left: 15px;
  top: 50%;
  transform: translateY(-50%);
  color: #9ca3af;
}

.search-input-group input {
  width: 100%;
  padding: 12px 15px 12px 45px;
  border: 2px solid #e5e7eb;
  border-radius: 10px;
  font-size: 1rem;
  transition: all 0.3s ease;
}

.search-input-group input:focus {
  outline: none;
  border-color: #ec4899;
  box-shadow: 0 0 0 3px rgba(236, 72, 153, 0.1);
}

.search-bar select {
  padding: 12px 20px;
  border: 2px solid #e5e7eb;
  border-radius: 10px;
  font-size: 1rem;
  min-width: 140px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.search-bar select:focus {
  outline: none;
  border-color: #ec4899;
}

.search-btn {
  padding: 12px 28px;
  background: linear-gradient(135deg, #ec4899 0%, #f43f5e 100%);
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(236, 72, 153, 0.3);
}

.search-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(236, 72, 153, 0.4);
}

.stats-row {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 20px;
  margin-bottom: 25px;
}

.stat-card {
  background: white;
  border-radius: 12px;
  padding: 24px;
  display: flex;
  align-items: center;
  box-shadow: 0 2px 15px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.stat-icon {
  width: 56px;
  height: 56px;
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
}

.stat-icon.primary {
  background: linear-gradient(135deg, #ec4899 0%, #f43f5e 100%);
}

.stat-icon.success {
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
}

.stat-icon.warning {
  background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
}

.stat-icon.info {
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
}

.stat-content h3 {
  font-size: 1.75rem;
  margin: 0;
  color: #1f2937;
  font-weight: 700;
}

.stat-content p {
  margin: 6px 0 0 0;
  color: #6b7280;
  font-size: 0.9rem;
}

.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.8);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  z-index: 100;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #e5e7eb;
  border-top-color: #ec4899;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 20px;
}

.empty-state {
  grid-column: 1 / -1;
  text-align: center;
  padding: 60px;
  color: #9ca3af;
}

.product-card {
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 15px rgba(0, 0, 0, 0.06);
  overflow: hidden;
  transition: all 0.3s ease;
}

.product-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.12);
}

.product-image {
  height: 140px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #fdf2f8 0%, #faf5ff 100%);
}

.product-info {
  padding: 20px;
}

.product-info h3 {
  margin: 0 0 12px 0;
  font-size: 1.1rem;
  color: #1f2937;
  font-weight: 600;
}

.price {
  font-size: 1.4rem;
  font-weight: 700;
  color: #ec4899;
  margin: 0 0 8px 0;
}

.spec {
  font-size: 0.85rem;
  color: #6b7280;
  margin: 0 0 12px 0;
}

.product-meta {
  display: flex;
  justify-content: space-between;
  font-size: 0.85rem;
  color: #9ca3af;
  margin-bottom: 15px;
  padding: 10px;
  background: #f9fafb;
  border-radius: 8px;
}

.product-actions {
  display: flex;
  gap: 10px;
}

.action-btn {
  flex: 1;
  padding: 10px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 0.85rem;
  font-weight: 500;
  transition: all 0.2s ease;
}

.action-btn.view {
  background: #fce7f3;
  color: #be185d;
}

.action-btn.view:hover {
  background: #fbcfe8;
}

.action-btn.edit {
  background: #fef3c7;
  color: #d97706;
}

.action-btn.edit:hover {
  background: #fde68a;
}

.action-btn.delete {
  background: #fee2e2;
  color: #dc2626;
}

.action-btn.delete:hover {
  background: #fecaca;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 100;
}

.modal-content {
  background: white;
  border-radius: 8px;
  padding: 20px;
  width: 90%;
  max-width: 500px;
}

.modal-content h2 {
  margin-top: 0;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 15px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 600;
}

.form-group input, .form-group select, .form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
}

.form-group textarea {
  min-height: 80px;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}

.form-actions button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.form-actions button[type="button"] {
  background: #e2e8f0;
}

.form-actions button[type="submit"] {
  background: #ec4899;
  color: white;
}
</style>