<template>
  <div class="prescription-container">
    <div class="page-header">
      <button class="back-btn" @click="goBack">返回</button>
      <h1>处方管理</h1>
    </div>
    
    <div class="search-section">
      <div class="search-row">
        <input type="text" v-model="searchForm.patientName" placeholder="患者姓名" class="search-input" />
        <select v-model="searchForm.dispensingStatus" class="search-input">
          <option value="">发药状态</option>
          <option value="UNDISPENSED">未发药</option>
          <option value="DISPENSED">已发药</option>
        </select>
        <select v-model="searchForm.paymentStatus" class="search-input">
          <option value="">支付状态</option>
          <option value="UNPAID">未支付</option>
          <option value="PAID">已支付</option>
        </select>
        <button class="search-btn" @click="loadData">查询</button>
      </div>
      <button class="add-btn" @click="openAddModal">新建处方</button>
    </div>
    
    <div class="table-container">
      <table class="data-table">
        <thead>
          <tr>
            <th>处方号</th>
            <th>患者姓名</th>
            <th>医生</th>
            <th>科室</th>
            <th>诊断</th>
            <th>金额</th>
            <th>支付状态</th>
            <th>发药状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="pres in prescriptionList" :key="pres.prescriptionId">
            <td>{{ pres.prescriptionId }}</td>
            <td>{{ pres.patientName }}</td>
            <td>{{ pres.doctorName }}</td>
            <td>{{ pres.departmentName }}</td>
            <td>{{ pres.diagnosis }}</td>
            <td>¥{{ pres.totalAmount }}</td>
            <td><span :class="['status-tag', pres.paymentStatus.toLowerCase()]">{{ getPaymentStatus(pres.paymentStatus) }}</span></td>
            <td><span :class="['status-tag', pres.dispensingStatus.toLowerCase()]">{{ getDispensingStatus(pres.dispensingStatus) }}</span></td>
            <td>
              <button v-if="pres.dispensingStatus === 'UNDISPENSED'" class="dispense-btn" @click="dispensePrescription(pres)">发药</button>
              <button class="view-btn" @click="viewPrescription(pres)">查看详情</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <div v-if="showModal" class="modal-overlay" @click="closeModal">
      <div class="modal-content" @click.stop>
        <div class="modal-header">
          <h3>{{ isEdit ? '编辑处方' : '新建处方' }}</h3>
          <button class="close-btn" @click="closeModal">×</button>
        </div>
        <div class="modal-body">
          <div class="form-row">
            <div class="form-group">
              <label>患者姓名</label>
              <input type="text" v-model="formData.patientName" class="form-input" />
            </div>
            <div class="form-group">
              <label>医生</label>
              <select v-model="formData.doctorId" class="form-input">
                <option value="DOC001">王医生</option>
                <option value="DOC002">李医生</option>
                <option value="DOC003">张医生</option>
              </select>
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label>科室</label>
              <select v-model="formData.departmentId" class="form-input">
                <option value="DEPT001">内科</option>
                <option value="DEPT002">外科</option>
                <option value="DEPT003">儿科</option>
              </select>
            </div>
            <div class="form-group">
              <label>诊断</label>
              <input type="text" v-model="formData.diagnosis" class="form-input" />
            </div>
          </div>
          <div class="form-section">
            <h4>药品明细</h4>
            <div class="medicine-list">
              <div v-for="(item, index) in formData.medicines" :key="index" class="medicine-row">
                <input type="text" v-model="item.name" placeholder="药品名称" class="med-input" />
                <input type="text" v-model="item.spec" placeholder="规格" class="med-input" />
                <input type="number" v-model="item.quantity" placeholder="数量" class="med-input" />
                <input type="number" v-model="item.price" placeholder="单价" class="med-input" />
                <button v-if="formData.medicines.length > 1" class="remove-med-btn" @click="removeMedicine(index)">×</button>
              </div>
            </div>
            <button class="add-med-btn" @click="addMedicine">+ 添加药品</button>
          </div>
          <div class="form-group total-group">
            <label>总金额</label>
            <input type="number" v-model="formData.totalAmount" class="form-input" readonly />
          </div>
        </div>
        <div class="modal-footer">
          <button class="cancel-btn" @click="closeModal">取消</button>
          <button class="submit-btn" @click="submitForm">{{ isEdit ? '保存' : '开具处方' }}</button>
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
  dispensingStatus: '',
  paymentStatus: ''
})

const prescriptionList = ref<any[]>([])
const showModal = ref(false)
const isEdit = ref(false)

const formData = reactive({
  prescriptionId: '',
  patientName: '',
  doctorId: '',
  doctorName: '',
  departmentId: '',
  diagnosis: '',
  medicines: [{ name: '', spec: '', quantity: 1, price: 0 }],
  totalAmount: 0
})

const goBack = () => {
  router.push('/his')
}

const getPaymentStatus = (status: string) => {
  const map: Record<string, string> = {
    'UNPAID': '未支付',
    'PAID': '已支付'
  }
  return map[status] || status
}

const getDispensingStatus = (status: string) => {
  const map: Record<string, string> = {
    'UNDISPENSED': '未发药',
    'DISPENSED': '已发药'
  }
  return map[status] || status
}

const loadData = () => {
  prescriptionList.value = [
    { prescriptionId: 'PRE001', patientName: '张三', doctorId: 'DOC001', doctorName: '王医生', departmentId: 'DEPT001', departmentName: '内科', diagnosis: '上呼吸道感染', totalAmount: 68.5, paymentStatus: 'PAID', dispensingStatus: 'UNDISPENSED' },
    { prescriptionId: 'PRE002', patientName: '李四', doctorId: 'DOC002', doctorName: '李医生', departmentId: 'DEPT002', departmentName: '外科', diagnosis: '急性阑尾炎', totalAmount: 156.0, paymentStatus: 'PAID', dispensingStatus: 'DISPENSED' },
    { prescriptionId: 'PRE003', patientName: '王五', doctorId: 'DOC003', doctorName: '张医生', departmentId: 'DEPT003', departmentName: '儿科', diagnosis: '病毒性感冒', totalAmount: 45.0, paymentStatus: 'UNPAID', dispensingStatus: 'UNDISPENSED' },
    { prescriptionId: 'PRE004', patientName: '赵六', doctorId: 'DOC004', doctorName: '刘医生', departmentId: 'DEPT004', departmentName: '妇产科', diagnosis: '孕期保健', totalAmount: 120.0, paymentStatus: 'PAID', dispensingStatus: 'DISPENSED' }
  ]
}

const addMedicine = () => {
  formData.medicines.push({ name: '', spec: '', quantity: 1, price: 0 })
}

const removeMedicine = (index: number) => {
  formData.medicines.splice(index, 1)
}

const openAddModal = () => {
  isEdit.value = false
  formData.prescriptionId = ''
  formData.patientName = ''
  formData.doctorId = 'DOC001'
  formData.departmentId = 'DEPT001'
  formData.diagnosis = ''
  formData.medicines = [{ name: '', spec: '', quantity: 1, price: 0 }]
  formData.totalAmount = 0
  showModal.value = true
}

const viewPrescription = (pres: any) => {
  alert(`处方详情：\n处方号：${pres.prescriptionId}\n患者：${pres.patientName}\n医生：${pres.doctorName}\n诊断：${pres.diagnosis}\n金额：¥${pres.totalAmount}`)
}

const dispensePrescription = (pres: any) => {
  if (pres.paymentStatus === 'UNPAID') {
    alert('请先完成支付')
    return
  }
  if (confirm(`确认发药给 ${pres.patientName} 吗？`)) {
    loadData()
  }
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
.prescription-container {
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

.status-tag.unpaid {
  background: #ffebee;
  color: #c62828;
}

.status-tag.paid {
  background: #e8f5e8;
  color: #2e7d32;
}

.status-tag.undispensed {
  background: #fff3e0;
  color: #e65100;
}

.status-tag.dispensed {
  background: #e3f2fd;
  color: #1976d2;
}

.dispense-btn, .view-btn {
  padding: 5px 12px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 5px;
  font-size: 0.8rem;
}

.dispense-btn {
  background: #4CAF50;
  color: white;
}

.view-btn {
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
  overflow-y: auto;
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

.total-group .form-input {
  background: #f5f5f5;
}

.form-section {
  margin-bottom: 20px;
}

.form-section h4 {
  margin: 0 0 10px 0;
}

.medicine-list {
  margin-bottom: 10px;
}

.medicine-row {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
}

.med-input {
  flex: 1;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.remove-med-btn {
  background: #f44336;
  color: white;
  border: none;
  padding: 0 10px;
  border-radius: 5px;
  cursor: pointer;
}

.add-med-btn {
  background: #667eea;
  color: white;
  border: none;
  padding: 8px 15px;
  border-radius: 5px;
  cursor: pointer;
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
