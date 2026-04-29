<template>
  <div class="patient-container">
    <div class="page-header">
      <button class="back-btn" @click="goBack">返回</button>
      <h1>患者管理</h1>
    </div>
    
    <div class="search-section">
      <div class="search-row">
        <input type="text" v-model="searchForm.patientName" placeholder="患者姓名" class="search-input" />
        <input type="text" v-model="searchForm.idCard" placeholder="身份证号" class="search-input" />
        <input type="text" v-model="searchForm.phone" placeholder="联系电话" class="search-input" />
        <button class="search-btn" @click="loadData">查询</button>
      </div>
      <button class="add-btn" @click="openAddModal">新增患者</button>
    </div>
    
    <div class="table-container">
      <table class="data-table">
        <thead>
          <tr>
            <th>患者ID</th>
            <th>姓名</th>
            <th>身份证号</th>
            <th>性别</th>
            <th>年龄</th>
            <th>电话</th>
            <th>血型</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="patient in patientList" :key="patient.patientId">
            <td>{{ patient.patientId }}</td>
            <td>{{ patient.patientName }}</td>
            <td>{{ maskIdCard(patient.idCard) }}</td>
            <td>{{ patient.gender === 'M' ? '男' : '女' }}</td>
            <td>{{ calculateAge(patient.birthDate) }}</td>
            <td>{{ maskPhone(patient.phone) }}</td>
            <td>{{ patient.bloodType }}</td>
            <td><span :class="['status-tag', patient.status.toLowerCase()]">{{ patient.status === 'ACTIVE' ? '正常' : '停用' }}</span></td>
            <td>
              <button class="edit-btn" @click="openEditModal(patient)">编辑</button>
              <button class="delete-btn" @click="deletePatient(patient.patientId)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage <= 1">上一页</button>
      <span>第 {{ currentPage }} 页 / 共 {{ totalPages }} 页</span>
      <button @click="nextPage" :disabled="currentPage >= totalPages">下一页</button>
    </div>
    
    <div v-if="showModal" class="modal-overlay" @click="closeModal">
      <div class="modal-content" @click.stop>
        <div class="modal-header">
          <h3>{{ isEdit ? '编辑患者' : '新增患者' }}</h3>
          <button class="close-btn" @click="closeModal">×</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>姓名</label>
            <input type="text" v-model="formData.patientName" class="form-input" />
          </div>
          <div class="form-group">
            <label>身份证号</label>
            <input type="text" v-model="formData.idCard" class="form-input" />
          </div>
          <div class="form-group">
            <label>性别</label>
            <select v-model="formData.gender" class="form-input">
              <option value="M">男</option>
              <option value="F">女</option>
            </select>
          </div>
          <div class="form-group">
            <label>出生日期</label>
            <input type="date" v-model="formData.birthDate" class="form-input" />
          </div>
          <div class="form-group">
            <label>联系电话</label>
            <input type="text" v-model="formData.phone" class="form-input" />
          </div>
          <div class="form-group">
            <label>血型</label>
            <select v-model="formData.bloodType" class="form-input">
              <option value="A">A型</option>
              <option value="B">B型</option>
              <option value="AB">AB型</option>
              <option value="O">O型</option>
            </select>
          </div>
          <div class="form-group">
            <label>过敏史</label>
            <textarea v-model="formData.allergicHistory" class="form-input"></textarea>
          </div>
        </div>
        <div class="modal-footer">
          <button class="cancel-btn" @click="closeModal">取消</button>
          <button class="submit-btn" @click="submitForm">{{ isEdit ? '保存' : '添加' }}</button>
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
  idCard: '',
  phone: ''
})

const patientList = ref<any[]>([])
const currentPage = ref(1)
const totalPages = ref(1)
const showModal = ref(false)
const isEdit = ref(false)

const formData = reactive({
  patientId: '',
  patientName: '',
  idCard: '',
  gender: 'M',
  birthDate: '',
  phone: '',
  bloodType: 'A',
  allergicHistory: ''
})

const goBack = () => {
  router.push('/his')
}

const maskIdCard = (idCard: string) => {
  if (!idCard) return ''
  return idCard.replace(/(\d{4})\d{10}(\d{4})/, '$1**********$2')
}

const maskPhone = (phone: string) => {
  if (!phone) return ''
  return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
}

const calculateAge = (birthDate: string) => {
  if (!birthDate) return '-'
  const birth = new Date(birthDate)
  const now = new Date()
  let age = now.getFullYear() - birth.getFullYear()
  if (now.getMonth() < birth.getMonth() || 
      (now.getMonth() === birth.getMonth() && now.getDate() < birth.getDate())) {
    age--
  }
  return age
}

const loadData = () => {
  patientList.value = [
    { patientId: 'P001', patientName: '张三', idCard: '110101199001011234', gender: 'M', birthDate: '1990-01-01', phone: '13800138001', bloodType: 'A', allergicHistory: '无', status: 'ACTIVE' },
    { patientId: 'P002', patientName: '李四', idCard: '110101198505156789', gender: 'F', birthDate: '1985-05-15', phone: '13900139002', bloodType: 'B', allergicHistory: '青霉素', status: 'ACTIVE' },
    { patientId: 'P003', patientName: '王五', idCard: '110101197812013456', gender: 'M', birthDate: '1978-12-01', phone: '13700137003', bloodType: 'O', allergicHistory: '无', status: 'ACTIVE' },
    { patientId: 'P004', patientName: '赵六', idCard: '110101199508207890', gender: 'F', birthDate: '1995-08-20', phone: '13600136004', bloodType: 'AB', allergicHistory: '花粉', status: 'ACTIVE' },
    { patientId: 'P005', patientName: '钱七', idCard: '110101198803102345', gender: 'M', birthDate: '1988-03-10', phone: '13500135005', bloodType: 'A', allergicHistory: '无', status: 'DEACTIVE' }
  ]
  totalPages.value = 1
}

const openAddModal = () => {
  isEdit.value = false
  formData.patientId = ''
  formData.patientName = ''
  formData.idCard = ''
  formData.gender = 'M'
  formData.birthDate = ''
  formData.phone = ''
  formData.bloodType = 'A'
  formData.allergicHistory = ''
  showModal.value = true
}

const openEditModal = (patient: any) => {
  isEdit.value = true
  formData.patientId = patient.patientId
  formData.patientName = patient.patientName
  formData.idCard = patient.idCard
  formData.gender = patient.gender
  formData.birthDate = patient.birthDate?.split('T')[0] || ''
  formData.phone = patient.phone
  formData.bloodType = patient.bloodType
  formData.allergicHistory = patient.allergicHistory
  showModal.value = true
}

const closeModal = () => {
  showModal.value = false
}

const submitForm = () => {
  showModal.value = false
  loadData()
}

const deletePatient = (id: string) => {
  if (confirm('确定要删除该患者吗？')) {
    loadData()
  }
}

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
    loadData()
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
    loadData()
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.patient-container {
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

.status-tag.active {
  background: #e8f5e8;
  color: #2e7d32;
}

.status-tag.deactive {
  background: #ffebee;
  color: #c62828;
}

.edit-btn, .delete-btn {
  padding: 5px 12px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 5px;
}

.edit-btn {
  background: #4CAF50;
  color: white;
}

.delete-btn {
  background: #f44336;
  color: white;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
  margin-top: 20px;
}

.pagination button {
  padding: 8px 16px;
  border: 1px solid #ddd;
  border-radius: 5px;
  cursor: pointer;
}

.pagination button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
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

.form-group {
  margin-bottom: 15px;
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
