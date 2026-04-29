<template>
  <div class="medical-record-container">
    <div class="page-header">
      <button class="back-btn" @click="goBack">返回</button>
      <h1>病历管理</h1>
    </div>
    
    <div class="search-section">
      <div class="search-row">
        <input type="text" v-model="searchForm.patientId" placeholder="患者ID" class="search-input" />
        <input type="text" v-model="searchForm.patientName" placeholder="患者姓名" class="search-input" />
        <select v-model="searchForm.departmentId" class="search-input">
          <option value="">选择科室</option>
          <option value="DEPT001">内科</option>
          <option value="DEPT002">外科</option>
          <option value="DEPT003">儿科</option>
          <option value="DEPT004">妇产科</option>
        </select>
        <button class="search-btn" @click="loadData">查询</button>
      </div>
      <button class="add-btn" @click="openAddModal">新建病历</button>
    </div>
    
    <div class="table-container">
      <table class="data-table">
        <thead>
          <tr>
            <th>病历号</th>
            <th>患者姓名</th>
            <th>科室</th>
            <th>医生</th>
            <th>就诊日期</th>
            <th>就诊类型</th>
            <th>诊断</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="record in recordList" :key="record.recordId">
            <td>{{ record.recordId }}</td>
            <td>{{ record.patientName }}</td>
            <td>{{ record.departmentName }}</td>
            <td>{{ record.doctorName }}</td>
            <td>{{ formatDate(record.visitDate) }}</td>
            <td>{{ getVisitType(record.visitType) }}</td>
            <td>{{ record.diagnosis }}</td>
            <td>
              <button class="view-btn" @click="viewRecord(record)">查看</button>
              <button class="edit-btn" @click="openEditModal(record)">编辑</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <div v-if="showModal" class="modal-overlay" @click="closeModal">
      <div class="modal-content record-modal" @click.stop>
        <div class="modal-header">
          <h3>{{ isEdit ? '编辑病历' : (isView ? '查看病历' : '新建病历') }}</h3>
          <button class="close-btn" @click="closeModal">×</button>
        </div>
        <div class="modal-body">
          <div class="record-header">
            <div class="record-info">
              <span>患者：{{ formData.patientName }}</span>
              <span>科室：{{ getDeptName(formData.departmentId) }}</span>
              <span>医生：{{ getDoctorName(formData.doctorId) }}</span>
              <span>日期：{{ formData.visitDate }}</span>
            </div>
          </div>
          
          <div class="form-section">
            <h4>主诉</h4>
            <textarea v-model="formData.chiefComplaint" class="form-textarea" :disabled="isView"></textarea>
          </div>
          
          <div class="form-section">
            <h4>现病史</h4>
            <textarea v-model="formData.presentIllness" class="form-textarea" :disabled="isView"></textarea>
          </div>
          
          <div class="form-section">
            <h4>既往史</h4>
            <textarea v-model="formData.pastHistory" class="form-textarea" :disabled="isView"></textarea>
          </div>
          
          <div class="form-section">
            <h4>体格检查</h4>
            <textarea v-model="formData.physicalExamination" class="form-textarea" :disabled="isView"></textarea>
          </div>
          
          <div class="form-section">
            <h4>辅助检查</h4>
            <textarea v-model="formData.auxiliaryExamination" class="form-textarea" :disabled="isView"></textarea>
          </div>
          
          <div class="form-section">
            <h4>诊断</h4>
            <input type="text" v-model="formData.diagnosis" class="form-input" :disabled="isView" />
          </div>
          
          <div class="form-section">
            <h4>治疗方案</h4>
            <textarea v-model="formData.treatmentPlan" class="form-textarea" :disabled="isView"></textarea>
          </div>
          
          <div class="form-section">
            <h4>医嘱</h4>
            <textarea v-model="formData.medicalAdvice" class="form-textarea" :disabled="isView"></textarea>
          </div>
        </div>
        <div class="modal-footer" v-if="!isView">
          <button class="cancel-btn" @click="closeModal">取消</button>
          <button class="submit-btn" @click="submitForm">{{ isEdit ? '保存' : '保存病历' }}</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const searchForm = reactive({
  patientId: '',
  patientName: '',
  departmentId: ''
})

const recordList = ref<any[]>([])
const showModal = ref(false)
const isEdit = ref(false)
const isView = ref(false)

const formData = reactive({
  recordId: '',
  patientId: '',
  patientName: '',
  departmentId: '',
  departmentName: '',
  doctorId: '',
  doctorName: '',
  visitDate: '',
  visitType: '',
  chiefComplaint: '',
  presentIllness: '',
  pastHistory: '',
  physicalExamination: '',
  auxiliaryExamination: '',
  diagnosis: '',
  treatmentPlan: '',
  medicalAdvice: ''
})

const deptMap: Record<string, string> = {
  'DEPT001': '内科',
  'DEPT002': '外科',
  'DEPT003': '儿科',
  'DEPT004': '妇产科'
}

const doctorMap: Record<string, string> = {
  'DOC001': '王医生',
  'DOC002': '李医生',
  'DOC003': '张医生',
  'DOC004': '刘医生'
}

const goBack = () => {
  router.push('/his')
}

const formatDate = (date: string) => {
  if (!date) return ''
  return date.split('T')[0]
}

const getVisitType = (type: string) => {
  const map: Record<string, string> = {
    'OUTPATIENT': '门诊',
    'INPATIENT': '住院',
    'EMERGENCY': '急诊'
  }
  return map[type] || type
}

const getDeptName = (id: string) => deptMap[id] || id
const getDoctorName = (id: string) => doctorMap[id] || id

const loadData = () => {
  recordList.value = [
    { recordId: 'MR001', patientId: 'P001', patientName: '张三', departmentId: 'DEPT001', departmentName: '内科', doctorId: 'DOC001', doctorName: '王医生', visitDate: '2024-01-15', visitType: 'OUTPATIENT', chiefComplaint: '咳嗽咳痰3天', diagnosis: '上呼吸道感染' },
    { recordId: 'MR002', patientId: 'P002', patientName: '李四', departmentId: 'DEPT002', departmentName: '外科', doctorId: 'DOC002', doctorName: '李医生', visitDate: '2024-01-14', visitType: 'OUTPATIENT', chiefComplaint: '腹痛1天', diagnosis: '急性阑尾炎' },
    { recordId: 'MR003', patientId: 'P003', patientName: '王五', departmentId: 'DEPT003', departmentName: '儿科', doctorId: 'DOC003', doctorName: '张医生', visitDate: '2024-01-13', visitType: 'OUTPATIENT', chiefComplaint: '发热2天', diagnosis: '病毒性感冒' },
    { recordId: 'MR004', patientId: 'P004', patientName: '赵六', departmentId: 'DEPT004', departmentName: '妇产科', doctorId: 'DOC004', doctorName: '刘医生', visitDate: '2024-01-12', visitType: 'OUTPATIENT', chiefComplaint: '产检', diagnosis: '正常妊娠' }
  ]
}

const openAddModal = () => {
  isEdit.value = false
  isView.value = false
  formData.recordId = ''
  formData.patientName = ''
  formData.departmentId = 'DEPT001'
  formData.doctorId = 'DOC001'
  formData.visitDate = new Date().toISOString().split('T')[0]
  formData.visitType = 'OUTPATIENT'
  formData.chiefComplaint = ''
  formData.presentIllness = ''
  formData.pastHistory = ''
  formData.physicalExamination = ''
  formData.auxiliaryExamination = ''
  formData.diagnosis = ''
  formData.treatmentPlan = ''
  formData.medicalAdvice = ''
  showModal.value = true
}

const openEditModal = (record: any) => {
  isEdit.value = true
  isView.value = false
  Object.assign(formData, record)
  showModal.value = true
}

const viewRecord = (record: any) => {
  isEdit.value = false
  isView.value = true
  Object.assign(formData, record)
  showModal.value = true
}

const closeModal = () => {
  showModal.value = false
}

const submitForm = () => {
  showModal.value = false
  loadData()
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.medical-record-container {
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

.view-btn, .edit-btn {
  padding: 5px 12px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 5px;
  font-size: 0.8rem;
}

.view-btn {
  background: #2196F3;
  color: white;
}

.edit-btn {
  background: #4CAF50;
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
  overflow-y: auto;
}

.modal-content {
  background: white;
  border-radius: 12px;
  width: 90%;
  max-width: 800px;
  max-height: 90vh;
  overflow-y: auto;
}

.record-modal {
  max-width: 900px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #eee;
  position: sticky;
  top: 0;
  background: white;
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

.record-header {
  background: #f8f9fa;
  padding: 15px;
  border-radius: 8px;
  margin-bottom: 20px;
}

.record-info {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
}

.record-info span {
  padding: 5px 10px;
  background: white;
  border-radius: 5px;
}

.form-section {
  margin-bottom: 20px;
}

.form-section h4 {
  margin: 0 0 10px 0;
  color: #333;
  font-size: 1rem;
}

.form-textarea {
  width: 100%;
  min-height: 80px;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-sizing: border-box;
  resize: vertical;
}

.form-textarea:disabled {
  background: #f5f5f5;
  color: #666;
}

.form-input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-sizing: border-box;
}

.form-input:disabled {
  background: #f5f5f5;
  color: #666;
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
