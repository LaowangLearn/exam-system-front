<template>
  <div class="payment-container">
    <div class="page-header">
      <button class="back-btn" @click="goBack">返回</button>
      <h1>收费管理</h1>
    </div>
    
    <div class="search-section">
      <div class="search-row">
        <input type="text" v-model="searchForm.patientName" placeholder="患者姓名" class="search-input" />
        <select v-model="searchForm.businessType" class="search-input">
          <option value="">业务类型</option>
          <option value="REGISTRATION">挂号费</option>
          <option value="PRESCRIPTION">药费</option>
          <option value="EXAMINATION">检查费</option>
          <option value="TREATMENT">治疗费</option>
        </select>
        <select v-model="searchForm.paymentStatus" class="search-input">
          <option value="">支付状态</option>
          <option value="PENDING">待支付</option>
          <option value="PAID">已支付</option>
          <option value="REFUNDED">已退款</option>
        </select>
        <button class="search-btn" @click="loadData">查询</button>
      </div>
      <button class="add-btn" @click="openAddModal">新增收费</button>
    </div>
    
    <div class="stats-row">
      <div class="stat-item income">
        <span class="stat-num">¥{{ totalIncome }}</span>
        <span class="stat-label">今日收入</span>
      </div>
      <div class="stat-item pending">
        <span class="stat-num">¥{{ pendingAmount }}</span>
        <span class="stat-label">待支付</span>
      </div>
      <div class="stat-item count">
        <span class="stat-num">{{ totalCount }}</span>
        <span class="stat-label">交易笔数</span>
      </div>
    </div>
    
    <div class="table-container">
      <table class="data-table">
        <thead>
          <tr>
            <th>交易号</th>
            <th>患者姓名</th>
            <th>业务类型</th>
            <th>来源ID</th>
            <th>金额</th>
            <th>支付方式</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="payment in paymentList" :key="payment.paymentId">
            <td>{{ payment.tradeNo }}</td>
            <td>{{ payment.patientName }}</td>
            <td>{{ getBusinessType(payment.businessType) }}</td>
            <td>{{ payment.sourceId }}</td>
            <td>¥{{ payment.amount }}</td>
            <td>{{ getPaymentMethod(payment.paymentMethod) }}</td>
            <td><span :class="['status-tag', payment.paymentStatus.toLowerCase()]">{{ getPaymentStatus(payment.paymentStatus) }}</span></td>
            <td>
              <button v-if="payment.paymentStatus === 'PENDING'" class="pay-btn" @click="doPayment(payment)">支付</button>
              <button v-if="payment.paymentStatus === 'PAID'" class="refund-btn" @click="doRefund(payment)">退款</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <div v-if="showModal" class="modal-overlay" @click="closeModal">
      <div class="modal-content" @click.stop>
        <div class="modal-header">
          <h3>新增收费</h3>
          <button class="close-btn" @click="closeModal">×</button>
        </div>
        <div class="modal-body">
          <div class="form-row">
            <div class="form-group">
              <label>患者姓名</label>
              <input type="text" v-model="formData.patientName" class="form-input" />
            </div>
            <div class="form-group">
              <label>业务类型</label>
              <select v-model="formData.businessType" class="form-input">
                <option value="REGISTRATION">挂号费</option>
                <option value="PRESCRIPTION">药费</option>
                <option value="EXAMINATION">检查费</option>
                <option value="TREATMENT">治疗费</option>
              </select>
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label>来源ID</label>
              <input type="text" v-model="formData.sourceId" class="form-input" placeholder="挂号ID/处方ID等" />
            </div>
            <div class="form-group">
              <label>金额</label>
              <input type="number" v-model="formData.amount" class="form-input" />
            </div>
          </div>
          <div class="form-group">
            <label>支付方式</label>
            <select v-model="formData.paymentMethod" class="form-input">
              <option value="CASH">现金</option>
              <option value="WECHAT">微信支付</option>
              <option value="ALIPAY">支付宝</option>
              <option value="CARD">银行卡</option>
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button class="cancel-btn" @click="closeModal">取消</button>
          <button class="submit-btn" @click="submitForm">确认收费</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const searchForm = reactive({
  patientName: '',
  businessType: '',
  paymentStatus: ''
})

const paymentList = ref<any[]>([])
const showModal = ref(false)

const formData = reactive({
  patientName: '',
  businessType: 'REGISTRATION',
  sourceId: '',
  amount: 0,
  paymentMethod: 'WECHAT'
})

const goBack = () => {
  router.push('/his')
}

const getBusinessType = (type: string) => {
  const map: Record<string, string> = {
    'REGISTRATION': '挂号费',
    'PRESCRIPTION': '药费',
    'EXAMINATION': '检查费',
    'TREATMENT': '治疗费'
  }
  return map[type] || type
}

const getPaymentMethod = (method: string) => {
  const map: Record<string, string> = {
    'CASH': '现金',
    'WECHAT': '微信支付',
    'ALIPAY': '支付宝',
    'CARD': '银行卡'
  }
  return map[method] || method
}

const getPaymentStatus = (status: string) => {
  const map: Record<string, string> = {
    'PENDING': '待支付',
    'PAID': '已支付',
    'REFUNDED': '已退款'
  }
  return map[status] || status
}

const totalIncome = computed(() => {
  return paymentList.value
    .filter(p => p.paymentStatus === 'PAID')
    .reduce((sum, p) => sum + (p.amount || 0), 0)
    .toFixed(2)
})

const pendingAmount = computed(() => {
  return paymentList.value
    .filter(p => p.paymentStatus === 'PENDING')
    .reduce((sum, p) => sum + (p.amount || 0), 0)
    .toFixed(2)
})

const totalCount = computed(() => paymentList.value.length)

const loadData = () => {
  paymentList.value = [
    { paymentId: 'PAY001', tradeNo: 'PAY20240115001', patientName: '张三', businessType: 'REGISTRATION', sourceId: 'REG001', amount: 15.0, paymentMethod: 'WECHAT', paymentStatus: 'PAID' },
    { paymentId: 'PAY002', tradeNo: 'PAY20240115002', patientName: '李四', businessType: 'PRESCRIPTION', sourceId: 'PRE002', amount: 156.0, paymentMethod: 'ALIPAY', paymentStatus: 'PAID' },
    { paymentId: 'PAY003', tradeNo: 'PAY20240115003', patientName: '王五', businessType: 'REGISTRATION', sourceId: 'REG003', amount: 15.0, paymentMethod: 'CASH', paymentStatus: 'PENDING' },
    { paymentId: 'PAY004', tradeNo: 'PAY20240115004', patientName: '赵六', businessType: 'EXAMINATION', sourceId: 'EXAM001', amount: 200.0, paymentMethod: 'WECHAT', paymentStatus: 'PAID' },
    { paymentId: 'PAY005', tradeNo: 'PAY20240115005', patientName: '钱七', businessType: 'TREATMENT', sourceId: 'TRT001', amount: 500.0, paymentMethod: 'CARD', paymentStatus: 'PAID' },
    { paymentId: 'PAY006', tradeNo: 'PAY20240115006', patientName: '孙八', businessType: 'PRESCRIPTION', sourceId: 'PRE003', amount: 45.0, paymentMethod: 'WECHAT', paymentStatus: 'REFUNDED' }
  ]
}

const openAddModal = () => {
  formData.patientName = ''
  formData.businessType = 'REGISTRATION'
  formData.sourceId = ''
  formData.amount = 0
  formData.paymentMethod = 'WECHAT'
  showModal.value = true
}

const closeModal = () => {
  showModal.value = false
}

const submitForm = () => {
  showModal.value = false
  loadData()
}

const doPayment = (payment: any) => {
  if (confirm(`确认支付 ¥${payment.amount} 吗？`)) {
    loadData()
  }
}

const doRefund = (payment: any) => {
  if (confirm(`确认退款 ¥${payment.amount} 吗？`)) {
    loadData()
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.payment-container {
  min-height: 100vh;
  background: #f5f7fa;
  padding: 20px;
}

.page-header {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-bottom: 20px;
}

.back-btn {
  background: #667eea;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  cursor: pointer;
}

.page-header h1 {
  font-size: 1.8rem;
  color: #333;
}

.search-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  flex-wrap: wrap;
  gap: 10px;
}

.search-row {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.search-input {
  padding: 10px 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  width: 180px;
}

.search-btn {
  background: #4CAF50;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  cursor: pointer;
}

.add-btn {
  background: #667eea;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  cursor: pointer;
}

.stats-row {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.stat-item {
  background: white;
  padding: 20px 30px;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
}

.stat-item.income .stat-num {
  color: #4CAF50;
}

.stat-item.pending .stat-num {
  color: #ff9800;
}

.stat-num {
  font-size: 2rem;
  font-weight: bold;
}

.stat-label {
  color: #666;
  font-size: 0.9rem;
}

.table-container {
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  overflow: hidden;
}

.data-table {
  width: 100%;
  border-collapse: collapse;
}

.data-table th,
.data-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

.data-table th {
  background: #f8f9fa;
  font-weight: 600;
  color: #333;
}

.data-table tr:hover {
  background: #f5f7fa;
}

.status-tag {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 500;
}

.status-tag.pending {
  background: #fff3e0;
  color: #e65100;
}

.status-tag.paid {
  background: #e8f5e8;
  color: #2e7d32;
}

.status-tag.refunded {
  background: #f5f5f5;
  color: #757575;
}

.pay-btn, .refund-btn {
  padding: 5px 12px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 5px;
  font-size: 0.8rem;
}

.pay-btn {
  background: #2196F3;
  color: white;
}

.refund-btn {
  background: #f44336;
  color: white;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0,0,0,0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  border-radius: 12px;
  width: 90%;
  max-width: 500px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #eee;
}

.close-btn {
  background: none;
  border: none;
  font-size: 2rem;
  cursor: pointer;
  color: #999;
}

.modal-body {
  padding: 20px;
}

.form-row {
  display: flex;
  gap: 20px;
}

.form-group {
  margin-bottom: 15px;
  flex: 1;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 500;
}

.form-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-sizing: border-box;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  padding: 20px;
  border-top: 1px solid #eee;
}

.cancel-btn {
  padding: 10px 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  cursor: pointer;
}

.submit-btn {
  padding: 10px 20px;
  background: #667eea;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}
</style>
