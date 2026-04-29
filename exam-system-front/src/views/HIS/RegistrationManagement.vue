<template>
  <div class="registration-container">
    <div class="page-header">
      <button class="back-btn" @click="goBack">返回</button>
      <h1>挂号管理</h1>
    </div>
    
    <div class="search-section">
      <div class="search-row">
        <input type="text" v-model="searchForm.patientName" placeholder="患者姓名" class="search-input" />
        <select v-model="searchForm.departmentId" class="search-input">
          <option value="">选择科室</option>
          <option value="DEPT001">内科</option>
          <option value="DEPT002">外科</option>
          <option value="DEPT003">儿科</option>
          <option value="DEPT004">妇产科</option>
          <option value="DEPT005">急诊科</option>
        </select>
        <select v-model="searchForm.visitStatus" class="search-input">
          <option value="">就诊状态</option>
          <option value="PENDING">待就诊</option>
          <option value="COMPLETED">已完成</option>
          <option value="CANCELLED">已取消</option>
        </select>
        <button class="search-btn" @click="loadData">查询</button>
      </div>
      <button class="add-btn" @click="openAddModal">新建挂号</button>
    </div>
    
    <div class="stats-row">
      <div class="stat-item">
        <span class="stat-num">{{ pendingCount }}</span>
        <span class="stat-label">待就诊</span>
      </div>
      <div class="stat-item">
        <span class="stat-num">{{ completedCount }}</span>
        <span class="stat-label">已完成</span>
      </div>
      <div class="stat-item">
        <span class="stat-num">{{ totalCount }}</span>
        <span class="stat-label">今日挂号</span>
      </div>
    </div>
    
    <div class="table-container">
      <table class="data-table">
        <thead>
          <tr>
            <th>挂号ID</th>
            <th>患者姓名</th>
            <th>科室</th>
            <th>医生</th>
            <th>预约时间</th>
            <th>状态</th>
            <th>费用</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="reg in registrationList" :key="reg.registrationId">
            <td>{{ reg.registrationId }}</td>
            <td>{{ reg.patientName }}</td>
            <td>{{ reg.departmentName }}</td>
            <td>{{ reg.doctorName }}</td>
            <td>{{ formatDateTime(reg.appointmentTime) }}</td>
            <td><span :class="['status-tag', reg.visitStatus.toLowerCase()]">{{ getStatusText(reg.visitStatus) }}</span></td>
            <td>¥{{ reg.registrationFee }}</td>
            <td>
              <button v-if="reg.visitStatus === 'PENDING'" class="edit-btn" @click="editRegistration(reg)">修改</button>
              <button v-if="reg.visitStatus === 'PENDING'" class="cancel-btn" @click="cancelRegistration(reg.registrationId)">取消</button>
              <button v-if="reg.visitStatus === 'PENDING'" class="pay-btn" @click="payRegistration(reg)">缴费</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <div v-if="showModal" class="modal-overlay" @click="closeModal">
      <div class="modal-content" @click.stop>
        <div class="modal-header">
          <h3>{{ isEdit ? '修改挂号' : '新建挂号' }}</h3>
          <button class="close-btn" @click="closeModal">×</button>
        </div>
        <div class="modal-body">
          <div class="form-row">
            <div class="form-group">
              <label>患者姓名</label>
              <input type="text" v-model="formData.patientName" class="form-input" />
            </div>
            <div class="form-group">
              <label>就诊科室</label>
              <select v-model="formData.departmentId" class="form-input">
                <option value="DEPT001">内科</option>
                <option value="DEPT002">外科</option>
                <option value="DEPT003">儿科</option>
                <option value="DEPT004">妇产科</option>
                <option value="DEPT005">急诊科</option>
              </select>
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label>就诊医生</label>
              <select v-model="formData.doctorId" class="form-input">
                <option value="DOC001">王医生</option>
                <option value="DOC002">李医生</option>
                <option value="DOC003">张医生</option>
                <option value="DOC004">刘医生</option>
              </select>
            </div>
            <div class="form-group">
              <label>预约时间</label>
              <input type="datetime-local" v-model="formData.appointmentTime" class="form-input" />
            </div>
          </div>
          <div class="form-group">
            <label>病情描述</label>
            <textarea v-model="formData.illnessDescription" class="form-input"></textarea>
          </div>
          <div class="form-group">
            <label>挂号费用</label>
            <input type="number" v-model="formData.registrationFee" class="form-input" />
          </div>
        </div>
        <div class="modal-footer">
          <button class="cancel-btn" @click="closeModal">取消</button>
          <button class="submit-btn" @click="submitForm">{{ isEdit ? '保存' : '确认挂号' }}</button>
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
  departmentId: '',
  visitStatus: ''
})

const registrationList = ref<any[]>([])
const showModal = ref(false)
const isEdit = ref(false)

const formData = reactive({
  registrationId: '',
  patientName: '',
  departmentId: '',
  departmentName: '',
  doctorId: '',
  doctorName: '',
  appointmentTime: '',
  illnessDescription: '',
  registrationFee: 0
})

const goBack = () => {
  router.push('/his')
}

const pendingCount = computed(() => registrationList.value.filter(r => r.visitStatus === 'PENDING').length)
const completedCount = computed(() => registrationList.value.filter(r => r.visitStatus === 'COMPLETED').length)
const totalCount = computed(() => registrationList.value.length)

const getStatusText = (status: string) => {
  const map: Record<string, string> = {
    'PENDING': '待就诊',
    'COMPLETED': '已完成',
    'CANCELLED': '已取消'
  }
  return map[status] || status
}

const formatDateTime = (dateTime: string) => {
  if (!dateTime) return ''
  return dateTime.replace('T', ' ')
}

const loadData = () => {
  registrationList.value = [
    { registrationId: 'REG001', patientName: '张三', departmentId: 'DEPT001', departmentName: '内科', doctorId: 'DOC001', doctorName: '王医生', appointmentTime: '2024-01-15 09:00', visitStatus: 'PENDING', registrationFee: 15, paymentStatus: 'UNPAID' },
    { registrationId: 'REG002', patientName: '李四', departmentId: 'DEPT002', departmentName: '外科', doctorId: 'DOC002', doctorName: '李医生', appointmentTime: '2024-01-15 10:00', visitStatus: 'PENDING', registrationFee: 20, paymentStatus: 'PAID' },
    { registrationId: 'REG003', patientName: '王五', departmentId: 'DEPT003', departmentName: '儿科', doctorId: 'DOC003', doctorName: '张医生', appointmentTime: '2024-01-15 11:00', visitStatus: 'COMPLETED', registrationFee: 15, paymentStatus: 'PAID' },
    { registrationId: 'REG004', patientName: '赵六', departmentId: 'DEPT004', departmentName: '妇产科', doctorId: 'DOC004', doctorName: '刘医生', appointmentTime: '2024-01-15 14:00', visitStatus: 'PENDING', registrationFee: 20, paymentStatus: 'UNPAID' },
    { registrationId: 'REG005', patientName: '钱七', departmentId: 'DEPT001', departmentName: '内科', doctorId: 'DOC001', doctorName: '王医生', appointmentTime: '2024-01-15 08:00', visitStatus: 'COMPLETED', registrationFee: 15, paymentStatus: 'PAID' },
    { registrationId: 'REG006', patientName: '孙八', departmentId: 'DEPT005', departmentName: '急诊科', doctorId: 'DOC002', doctorName: '李医生', appointmentTime: '2024-01-15 09:30', visitStatus: 'CANCELLED', registrationFee: 30, paymentStatus: 'REFUNDED' }
  ]
}

const openAddModal = () => {
  isEdit.value = false
  formData.registrationId = ''
  formData.patientName = ''
  formData.departmentId = 'DEPT001'
  formData.doctorId = 'DOC001'
  formData.appointmentTime = ''
  formData.illnessDescription = ''
  formData.registrationFee = 15
  showModal.value = true
}

const editRegistration = (reg: any) => {
  isEdit.value = true
  formData.registrationId = reg.registrationId
  formData.patientName = reg.patientName
  formData.departmentId = reg.departmentId
  formData.doctorId = reg.doctorId
  formData.appointmentTime = reg.appointmentTime.replace(' ', 'T')
  formData.illnessDescription = reg.illnessDescription || ''
  formData.registrationFee = reg.registrationFee
  showModal.value = true
}

const closeModal = () => {
  showModal.value = false
}

const submitForm = () => {
  showModal.value = false
  loadData()
}

const cancelRegistration = (id: string) => {
  if (confirm('确定要取消该挂号吗？')) {
    loadData()
  }
}

const payRegistration = (reg: any) => {
  if (confirm(`确认支付挂号费用 ¥${reg.registrationFee} 吗？`)) {
    loadData()
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.registration-container {
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

.stat-num {
  font-size: 2rem;
  font-weight: bold;
  color: #667eea;
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

.status-tag.completed {
  background: #e8f5e8;
  color: #2e7d32;
}

.status-tag.cancelled {
  background: #f5f5f5;
  color: #757575;
}

.edit-btn, .cancel-btn, .pay-btn {
  padding: 5px 12px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 5px;
  font-size: 0.8rem;
}

.edit-btn {
  background: #4CAF50;
  color: white;
}

.cancel-btn {
  background: #9e9e9e;
  color: white;
}

.pay-btn {
  background: #2196F3;
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
  max-width: 600px;
  max-height: 90vh;
  overflow-y: auto;
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

.modal-footer .cancel-btn {
  padding: 10px 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  cursor: pointer;
  background: #f5f5f5;
  color: #333;
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
