<template>
  <div class="oa-schedules">
    <button class="back-btn" @click="goBack">返回企业系统</button>
    <div class="page-header">
      <h1>日程管理</h1>
      <button class="add-btn" @click="openAddScheduleDialog">添加日程</button>
    </div>

    <div class="schedule-filter">
      <div class="filter-row">
        <input type="date" v-model="dateFilter" />
        <select v-model="typeFilter">
          <option value="">全部类型</option>
          <option value="MEETING">会议</option>
          <option value="TASK">任务</option>
          <option value="EVENT">活动</option>
        </select>
        <button class="search-btn" @click="filterSchedules">筛选</button>
      </div>
    </div>

    <div class="schedule-calendar">
      <div class="calendar-header">
        <h2>{{ currentMonth }}</h2>
        <div class="calendar-nav">
          <button @click="prevMonth">上个月</button>
          <button @click="today">今天</button>
          <button @click="nextMonth">下个月</button>
        </div>
      </div>
      <div class="calendar-grid">
        <div class="weekday-header" v-for="day in weekdays" :key="day">{{ day }}</div>
        <div v-for="day in calendarDays" :key="day.date" class="calendar-day" :class="{ 'today': day.isToday, 'has-schedule': day.hasSchedule }">
          <div class="day-number">{{ day.day }}</div>
          <div class="day-schedules">
            <div v-for="schedule in getDaySchedules(day.date)" :key="schedule.id" class="schedule-item" :class="schedule.type">
              <span class="schedule-time">{{ schedule.startTime }}</span>
              <span class="schedule-title">{{ schedule.title }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="schedule-list">
      <h3>今日日程</h3>
      <div v-if="todaySchedules.length === 0" class="empty-message">
        今日无日程
      </div>
      <div v-else class="today-schedule-list">
        <div v-for="schedule in todaySchedules" :key="schedule.id" class="today-schedule-item">
          <div class="schedule-time">{{ schedule.startTime }} - {{ schedule.endTime }}</div>
          <div class="schedule-info">
            <h4>{{ schedule.title }}</h4>
            <p>{{ schedule.description }}</p>
            <p class="schedule-location">{{ schedule.location }}</p>
            <span class="schedule-type" :class="schedule.type">{{ schedule.type }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 新建日程对话框 -->
    <div v-if="showScheduleDialog" class="dialog-overlay" @click="closeScheduleDialog">
      <div class="dialog" @click.stop>
        <h2>{{ editingSchedule ? '编辑日程' : '新建日程' }}</h2>
        <form @submit.prevent="saveSchedule">
          <div class="form-group">
            <label>标题</label>
            <input type="text" v-model="form.title" required />
          </div>
          <div class="form-group">
            <label>描述</label>
            <textarea v-model="form.description" rows="3"></textarea>
          </div>
          <div class="form-group">
            <label>开始时间</label>
            <input type="datetime-local" v-model="form.startTime" required />
          </div>
          <div class="form-group">
            <label>结束时间</label>
            <input type="datetime-local" v-model="form.endTime" required />
          </div>
          <div class="form-group">
            <label>地点</label>
            <input type="text" v-model="form.location" />
          </div>
          <div class="form-group">
            <label>类型</label>
            <select v-model="form.type" required>
              <option value="MEETING">会议</option>
              <option value="TASK">任务</option>
              <option value="EVENT">活动</option>
            </select>
          </div>
          <div class="form-group">
            <label>参与人</label>
            <select v-model="form.participants" multiple>
              <option v-for="user in users" :key="user.id" :value="user.id">
                {{ user.name }}
              </option>
            </select>
          </div>
          <div class="dialog-actions">
            <button type="button" class="cancel-btn" @click="closeScheduleDialog">取消</button>
            <button type="submit" class="save-btn">保存</button>
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

const goBack = () => {
  router.push('/enterprise')
}

// 日程列表
const schedules = ref([
  { id: 1, title: '周例会', description: '每周团队会议', startTime: '09:00', endTime: '10:00', location: '会议室A', type: 'MEETING', creatorId: 1, createTime: '2026-02-09 08:00' },
  { id: 2, title: '项目评审会', description: '新项目评审', startTime: '14:00', endTime: '16:00', location: '会议室B', type: 'MEETING', creatorId: 1, createTime: '2026-02-08 10:00' },
  { id: 3, title: '代码审查', description: '审查新提交的代码', startTime: '10:30', endTime: '11:30', location: '工位', type: 'TASK', creatorId: 1, createTime: '2026-02-09 09:00' }
])

// 用户列表
const users = ref([
  { id: 1, name: '管理员' },
  { id: 2, name: '技术经理' },
  { id: 3, name: '普通用户' }
])

// 筛选条件
const dateFilter = ref(new Date().toISOString().split('T')[0])
const typeFilter = ref('')

// 日历相关
const currentDate = ref(new Date())
const weekdays = ['周日', '周一', '周二', '周三', '周四', '周五', '周六']

// 对话框状态
const showScheduleDialog = ref(false)
const editingSchedule = ref(false)
const form = ref({
  id: 0,
  title: '',
  description: '',
  startTime: '',
  endTime: '',
  location: '',
  type: 'MEETING',
  participants: []
})

// 当前月份
const currentMonth = computed(() => {
  return currentDate.value.toLocaleDateString('zh-CN', { year: 'numeric', month: 'long' })
})

// 日历天数
const calendarDays = computed(() => {
  const year = currentDate.value.getFullYear()
  const month = currentDate.value.getMonth()
  const firstDay = new Date(year, month, 1)
  const lastDay = new Date(year, month + 1, 0)
  const startDate = new Date(firstDay)
  startDate.setDate(startDate.getDate() - firstDay.getDay())
  
  const days = []
  for (let i = 0; i < 42; i++) {
    const date = new Date(startDate)
    date.setDate(startDate.getDate() + i)
    const today = new Date()
    const isToday = date.getDate() === today.getDate() && date.getMonth() === today.getMonth() && date.getFullYear() === today.getFullYear()
    const hasSchedule = schedules.value.some(schedule => {
      const scheduleDate = new Date(schedule.createTime).toDateString()
      return date.toDateString() === scheduleDate
    })
    
    days.push({
      date: date.toISOString().split('T')[0],
      day: date.getDate(),
      isToday,
      hasSchedule
    })
  }
  return days
})

// 今日日程
const todaySchedules = computed(() => {
  const today = new Date().toDateString()
  return schedules.value.filter(schedule => {
    const scheduleDate = new Date(schedule.createTime).toDateString()
    return scheduleDate === today
  })
})

// 获取某天的日程
const getDaySchedules = (date: string) => {
  return schedules.value.filter(schedule => {
    const scheduleDate = new Date(schedule.createTime).toISOString().split('T')[0]
    return scheduleDate === date
  })
}

// 上个月
const prevMonth = () => {
  currentDate.value = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() - 1, 1)
}

// 今天
const today = () => {
  currentDate.value = new Date()
}

// 下个月
const nextMonth = () => {
  currentDate.value = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() + 1, 1)
}

// 打开新建日程对话框
const openAddScheduleDialog = () => {
  editingSchedule.value = false
  form.value = {
    id: 0,
    title: '',
    description: '',
    startTime: new Date().toISOString().slice(0, 16),
    endTime: new Date().toISOString().slice(0, 16),
    location: '',
    type: 'MEETING',
    participants: []
  }
  showScheduleDialog.value = true
}

// 关闭日程对话框
const closeScheduleDialog = () => {
  showScheduleDialog.value = false
}

// 保存日程
const saveSchedule = () => {
  if (editingSchedule.value) {
    // 编辑现有日程
    const index = schedules.value.findIndex(s => s.id === form.value.id)
    if (index !== -1) {
      schedules.value[index] = { ...form.value }
    }
  } else {
    // 添加新日程
    const newSchedule = {
      ...form.value,
      id: schedules.value.length + 1,
      creatorId: 1,
      createTime: new Date().toLocaleString()
    }
    schedules.value.push(newSchedule)
  }
  closeScheduleDialog()
}

// 筛选日程
const filterSchedules = () => {
  // 筛选逻辑
  console.log('筛选日程:', dateFilter.value, typeFilter.value)
}

onMounted(() => {
  // 加载日程数据
  console.log('加载日程数据')
})
</script>

<style scoped>
.oa-schedules {
  min-height: 100vh;
  background: #f5f7fa;
  padding: 20px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 10px 25px;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
  z-index: 10;
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.page-header h1 {
  font-size: 1.5rem;
  color: #333;
  margin: 0;
}

.add-btn {
  background: #667eea;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

.add-btn:hover {
  background: #764ba2;
}

.schedule-filter {
  margin-bottom: 20px;
  background: white;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.filter-row {
  display: flex;
  gap: 10px;
  align-items: center;
}

.filter-row input,
.filter-row select {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.search-btn {
  background: #667eea;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
}

.schedule-calendar {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin-bottom: 30px;
}

.calendar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.calendar-header h2 {
  margin: 0;
  color: #333;
}

.calendar-nav button {
  background: #f8f9fa;
  border: 1px solid #ddd;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
  margin: 0 5px;
}

.calendar-grid {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 1px;
  background: #ddd;
  border-radius: 4px;
  overflow: hidden;
}

.weekday-header {
  background: #f8f9fa;
  padding: 10px;
  text-align: center;
  font-weight: bold;
  color: #333;
}

.calendar-day {
  background: white;
  min-height: 100px;
  padding: 5px;
  position: relative;
}

.calendar-day.today {
  background: #e3f2fd;
}

.calendar-day.has-schedule {
  background: #fff3e0;
}

.day-number {
  font-size: 0.9rem;
  font-weight: bold;
  margin-bottom: 5px;
}

.day-schedules {
  font-size: 0.8rem;
  line-height: 1.2;
}

.schedule-item {
  padding: 2px 4px;
  margin-bottom: 2px;
  border-radius: 2px;
  font-size: 0.7rem;
  overflow: hidden;
  text-overflow: ellipsis;
}

.schedule-item.MEETING {
  background: #e3f2fd;
  color: #1565c0;
}

.schedule-item.TASK {
  background: #e8f5e8;
  color: #2e7d32;
}

.schedule-item.EVENT {
  background: #f3e5f5;
  color: #6a1b9a;
}

.schedule-time {
  font-size: 0.6rem;
  margin-right: 4px;
}

.schedule-list {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.schedule-list h3 {
  margin-top: 0;
  margin-bottom: 20px;
  color: #333;
}

.empty-message {
  text-align: center;
  color: #999;
  padding: 40px 0;
}

.today-schedule-item {
  display: flex;
  padding: 15px;
  border-bottom: 1px solid #e0e0e0;
}

.today-schedule-item:last-child {
  border-bottom: none;
}

.schedule-time {
  width: 100px;
  font-size: 0.9rem;
  color: #666;
  flex-shrink: 0;
}

.schedule-info {
  flex: 1;
}

.schedule-info h4 {
  margin: 0 0 5px 0;
  color: #333;
}

.schedule-info p {
  margin: 0 0 5px 0;
  color: #666;
  font-size: 0.9rem;
}

.schedule-location {
  color: #667eea !important;
  font-size: 0.8rem !important;
}

.schedule-type {
  display: inline-block;
  padding: 2px 8px;
  border-radius: 10px;
  font-size: 0.7rem;
  font-weight: bold;
}

.schedule-type.MEETING {
  background: #e3f2fd;
  color: #1565c0;
}

.schedule-type.TASK {
  background: #e8f5e8;
  color: #2e7d32;
}

.schedule-type.EVENT {
  background: #f3e5f5;
  color: #6a1b9a;
}

/* 对话框样式 */
.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.dialog {
  background: white;
  border-radius: 8px;
  padding: 30px;
  width: 90%;
  max-width: 500px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
}

.dialog h2 {
  margin-top: 0;
  margin-bottom: 20px;
  color: #333;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  color: #333;
  font-weight: bold;
}

.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.form-group textarea {
  resize: vertical;
}

.form-group select[multiple] {
  height: 100px;
}

.dialog-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}

.cancel-btn, .save-btn {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

.cancel-btn {
  background: #6c757d;
  color: white;
}

.save-btn {
  background: #667eea;
  color: white;
}

@media (max-width: 768px) {
  .filter-row {
    flex-direction: column;
    align-items: stretch;
  }

  .calendar-day {
    min-height: 80px;
  }

  .day-schedules {
    font-size: 0.7rem;
  }

  .today-schedule-item {
    flex-direction: column;
  }

  .schedule-time {
    width: 100%;
    margin-bottom: 5px;
  }
}
</style>