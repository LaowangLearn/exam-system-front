<template>
  <div class="home-page">
    <div class="page-header">
      <button class="back-btn" @click="goBack">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M15 18l-6-6 6-6" />
        </svg>
        返回
      </button>
      <h1>家校互通系统</h1>
    </div>
    
    <div class="page-content">
      <div class="stats-row">
        <div class="stat-card">
          <div class="stat-icon">👨‍👩‍👧</div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.parents }}</div>
            <div class="stat-label">家长注册数</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">📧</div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.messages }}</div>
            <div class="stat-label">今日消息</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">📝</div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.homework }}</div>
            <div class="stat-label">今日作业</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">📊</div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.notices }}</div>
            <div class="stat-label">最新通知</div>
          </div>
        </div>
      </div>
      
      <div class="content-grid">
        <div class="panel">
          <div class="panel-header">
            <h3>家校消息</h3>
            <button class="compose-btn" @click="showCompose = true">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="12" y1="5" x2="12" y2="19"></line>
                <line x1="5" y1="12" x2="19" y2="12"></line>
              </svg>
              发送消息
            </button>
          </div>
          <div class="message-list">
            <div v-for="msg in messages" :key="msg.id" class="message-item">
              <div class="msg-avatar">{{ msg.avatar }}</div>
              <div class="msg-content">
                <div class="msg-header">
                  <span class="msg-name">{{ msg.name }}</span>
                  <span class="msg-time">{{ msg.time }}</span>
                </div>
                <div class="msg-text">{{ msg.content }}</div>
              </div>
              <div v-if="msg.unread" class="msg-unread"></div>
            </div>
          </div>
        </div>
        
        <div class="panel">
          <div class="panel-header">
            <h3>作业发布</h3>
            <button class="add-btn" @click="showAddHomework = true">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="12" y1="5" x2="12" y2="19"></line>
                <line x1="5" y1="12" x2="19" y2="12"></line>
              </svg>
              布置作业
            </button>
          </div>
          <div class="homework-list">
            <div v-for="hw in homeworks" :key="hw.id" class="homework-item">
              <div class="hw-subject" :class="hw.subject">{{ getSubjectIcon(hw.subject) }}</div>
              <div class="hw-info">
                <div class="hw-title">{{ hw.title }}</div>
                <div class="hw-meta">{{ hw.subject }} · {{ hw.teacher }} · {{ hw.dueDate }}</div>
              </div>
              <div :class="['hw-status', hw.status]">
                {{ hw.status === 'published' ? '已发布' : '草稿' }}
              </div>
            </div>
          </div>
        </div>
        
        <div class="panel">
          <div class="panel-header">
            <h3>成绩通知</h3>
          </div>
          <div class="score-list">
            <div v-for="score in scores" :key="score.id" class="score-item">
              <div class="score-subject">{{ score.subject }}</div>
              <div class="score-info">
                <div class="score-value">{{ score.score }}</div>
                <div class="score-label">{{ score.exam }}</div>
              </div>
              <div :class="['score-grade', getGradeClass(score.score)]">
                {{ getGrade(score.score) }}
              </div>
            </div>
          </div>
        </div>
        
        <div class="panel">
          <div class="panel-header">
            <h3>学校通知</h3>
          </div>
          <div class="notice-list">
            <div v-for="notice in notices" :key="notice.id" class="notice-item">
              <div :class="['notice-icon', notice.type]">{{ getNoticeIcon(notice.type) }}</div>
              <div class="notice-info">
                <div class="notice-title">{{ notice.title }}</div>
                <div class="notice-time">{{ notice.time }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <div class="quick-actions">
        <div class="action-card" @click="goToParentApp">
          <div class="action-icon">📱</div>
          <div class="action-info">
            <h4>家长端App</h4>
            <p>扫码下载家长端应用</p>
          </div>
          <div class="action-arrow">→</div>
        </div>
        <div class="action-card" @click="goToCommunication">
          <div class="action-icon">💬</div>
          <div class="action-info">
            <h4>在线沟通</h4>
            <p>与家长实时在线沟通</p>
          </div>
          <div class="action-arrow">→</div>
        </div>
        <div class="action-card" @click="goToStatistics">
          <div class="action-icon">📈</div>
          <div class="action-info">
            <h4>沟通统计</h4>
            <p>查看家校沟通数据分析</p>
          </div>
          <div class="action-arrow">→</div>
        </div>
      </div>
    </div>
    
    <div v-if="showCompose" class="modal-overlay" @click.self="showCompose = false">
      <div class="modal">
        <div class="modal-header">
          <h3>发送消息</h3>
          <button class="modal-close" @click="showCompose = false">×</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>接收对象</label>
            <select v-model="newMessage.target">
              <option value="all">全体家长</option>
              <option value="class">班级家长</option>
              <option value="single">单个家长</option>
            </select>
          </div>
          <div class="form-group">
            <label>消息标题</label>
            <input v-model="newMessage.title" type="text" placeholder="请输入消息标题" />
          </div>
          <div class="form-group">
            <label>消息内容</label>
            <textarea v-model="newMessage.content" placeholder="请输入消息内容" rows="4"></textarea>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn-cancel" @click="showCompose = false">取消</button>
          <button class="btn-confirm" @click="sendMessage">发送</button>
        </div>
      </div>
    </div>
    
    <div v-if="showAddHomework" class="modal-overlay" @click.self="showAddHomework = false">
      <div class="modal">
        <div class="modal-header">
          <h3>布置作业</h3>
          <button class="modal-close" @click="showAddHomework = false">×</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>学科</label>
            <select v-model="newHomework.subject">
              <option value="语文">语文</option>
              <option value="数学">数学</option>
              <option value="英语">英语</option>
              <option value="物理">物理</option>
              <option value="化学">化学</option>
              <option value="生物">生物</option>
            </select>
          </div>
          <div class="form-group">
            <label>作业标题</label>
            <input v-model="newHomework.title" type="text" placeholder="请输入作业标题" />
          </div>
          <div class="form-group">
            <label>作业内容</label>
            <textarea v-model="newHomework.content" placeholder="请输入作业内容" rows="4"></textarea>
          </div>
          <div class="form-group">
            <label>截止日期</label>
            <input v-model="newHomework.dueDate" type="date" />
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn-cancel" @click="showAddHomework = false">取消</button>
          <button class="btn-confirm" @click="addHomework">发布作业</button>
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
  router.push('/education')
}

const showCompose = ref(false)
const showAddHomework = ref(false)

const stats = ref({
  parents: 3256,
  messages: 234,
  homework: 12,
  notices: 5
})

const messages = ref([
  { id: 1, avatar: '👨‍👩‍👧', name: '张三家长', content: '老师您好，请问明天的家长会几点开始？', time: '10:30', unread: true },
  { id: 2, avatar: '👨‍👩‍👧', name: '李四家长', content: '孩子最近学习情况怎么样？', time: '09:15', unread: false },
  { id: 3, avatar: '👨‍👩‍👧', name: '王五家长', content: '作业已经收到，谢谢老师！', time: '08:45', unread: false },
  { id: 4, avatar: '👨‍👩‍👧', name: '赵六家长', content: '请问周末有没有补课安排？', time: '昨天', unread: false }
])

const homeworks = ref([
  { id: 1, subject: '数学', title: '完成练习册第35-40页', teacher: '李老师', dueDate: '2025-01-15', status: 'published' },
  { id: 2, subject: '语文', title: '作文：我的寒假生活', teacher: '王老师', dueDate: '2025-01-16', status: 'published' },
  { id: 3, subject: '英语', title: '背诵Unit5单词', teacher: '陈老师', dueDate: '2025-01-15', status: 'published' },
  { id: 4, subject: '物理', title: '完成实验报告', teacher: '张老师', dueDate: '2025-01-17', status: 'draft' }
])

const scores = ref([
  { id: 1, subject: '数学', score: 92, exam: '期中考试' },
  { id: 2, subject: '语文', score: 88, exam: '期中考试' },
  { id: 3, subject: '英语', score: 95, exam: '期中考试' },
  { id: 4, subject: '物理', score: 85, exam: '期中考试' }
])

const notices = ref([
  { id: 1, title: '关于寒假放假安排的通知', time: '2025-01-10', type: 'info' },
  { id: 2, title: '本周六家长会通知', time: '2025-01-09', type: 'important' },
  { id: 3, title: '冬季流感预防提醒', time: '2025-01-08', type: 'warning' },
  { id: 4, title: '校园安全注意事项', time: '2025-01-05', type: 'info' }
])

const newMessage = ref({
  target: 'all',
  title: '',
  content: ''
})

const newHomework = ref({
  subject: '数学',
  title: '',
  content: '',
  dueDate: ''
})

const getSubjectIcon = (subject) => {
  const icons = { '语文': '📖', '数学': '📐', '英语': '🔤', '物理': '⚛️', '化学': '🧪', '生物': '🧬' }
  return icons[subject] || '📝'
}

const getGrade = (score) => {
  if (score >= 90) return '优秀'
  if (score >= 80) return '良好'
  if (score >= 70) return '中等'
  if (score >= 60) return '及格'
  return '需努力'
}

const getGradeClass = (score) => {
  if (score >= 90) return 'excellent'
  if (score >= 80) return 'good'
  if (score >= 70) return 'medium'
  if (score >= 60) return 'pass'
  return 'fail'
}

const getNoticeIcon = (type) => {
  const icons = { info: '📋', important: '📌', warning: '⚠️' }
  return icons[type] || '📢'
}

const sendMessage = () => {
  alert('消息发送成功！')
  showCompose.value = false
  newMessage.value = { target: 'all', title: '', content: '' }
}

const addHomework = () => {
  homeworks.value.push({
    id: Date.now(),
    subject: newHomework.value.subject,
    title: newHomework.value.title,
    teacher: '当前用户',
    dueDate: newHomework.value.dueDate,
    status: 'published'
  })
  showAddHomework.value = false
  newHomework.value = { subject: '数学', title: '', content: '', dueDate: '' }
}

const goToParentApp = () => {
  alert('跳转到家长端App下载页面')
}

const goToCommunication = () => {
  alert('跳转到在线沟通页面')
}

const goToStatistics = () => {
  alert('跳转到沟通统计页面')
}
</script>

<style scoped>
.home-page {
  min-height: 100vh;
  background: #f5f7fa;
}

.page-header {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px 30px;
  background: white;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
}

.back-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background: #4facfe;
  border: none;
  border-radius: 8px;
  color: white;
  cursor: pointer;
  font-size: 14px;
}

.page-header h1 {
  margin: 0;
  font-size: 24px;
  color: #333;
}

.page-content {
  padding: 20px 30px;
}

.stats-row {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 15px;
  margin-bottom: 20px;
}

.stat-card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 15px;
}

.stat-icon {
  font-size: 28px;
}

.stat-info {
  display: flex;
  flex-direction: column;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

.stat-label {
  font-size: 12px;
  color: #999;
}

.content-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  margin-bottom: 20px;
}

.panel {
  background: white;
  border-radius: 12px;
  padding: 20px;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.panel-header h3 {
  margin: 0;
  font-size: 16px;
  color: #333;
}

.compose-btn, .add-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 8px 16px;
  background: #4facfe;
  border: none;
  border-radius: 6px;
  color: white;
  cursor: pointer;
  font-size: 13px;
}

.add-btn {
  background: #667eea;
}

.message-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.message-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 8px;
  position: relative;
}

.msg-avatar {
  width: 45px;
  height: 45px;
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 22px;
}

.msg-content {
  flex: 1;
}

.msg-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
}

.msg-name {
  font-size: 14px;
  font-weight: 500;
  color: #333;
}

.msg-time {
  font-size: 12px;
  color: #999;
}

.msg-text {
  font-size: 13px;
  color: #666;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.msg-unread {
  width: 8px;
  height: 8px;
  background: #ff6b6b;
  border-radius: 50%;
}

.homework-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.homework-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 8px;
}

.hw-subject {
  width: 40px;
  height: 40px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
}

.hw-subject.语文 { background: #ffebee; }
.hw-subject.数学 { background: #e3f2fd; }
.hw-subject.英语 { background: #e8f5e9; }
.hw-subject.物理 { background: #fff3e0; }
.hw-subject.化学 { background: #f3e5f5; }
.hw-subject.生物 { background: #e8f5e9; }

.hw-info {
  flex: 1;
}

.hw-title {
  font-size: 14px;
  font-weight: 500;
  color: #333;
  margin-bottom: 4px;
}

.hw-meta {
  font-size: 12px;
  color: #999;
}

.hw-status {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
}

.hw-status.published {
  background: #e8f5e9;
  color: #2e7d32;
}

.hw-status.draft {
  background: #f5f5f5;
  color: #999;
}

.score-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.score-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 8px;
}

.score-subject {
  font-size: 16px;
  font-weight: 500;
  color: #666;
  width: 60px;
}

.score-info {
  flex: 1;
  display: flex;
  align-items: baseline;
  gap: 10px;
}

.score-value {
  font-size: 28px;
  font-weight: bold;
  color: #333;
}

.score-label {
  font-size: 12px;
  color: #999;
}

.score-grade {
  padding: 6px 14px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 500;
}

.score-grade.excellent { background: #e8f5e9; color: #2e7d32; }
.score-grade.good { background: #e3f2fd; color: #1565c0; }
.score-grade.medium { background: #fff3e0; color: #ef6c00; }
.score-grade.pass { background: #fce4ec; color: #c2185b; }
.score-grade.fail { background: #f4e0e0; color: #c62828; }

.notice-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.notice-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 8px;
}

.notice-icon {
  font-size: 20px;
}

.notice-info {
  flex: 1;
}

.notice-title {
  font-size: 14px;
  color: #333;
  margin-bottom: 4px;
}

.notice-time {
  font-size: 12px;
  color: #999;
}

.quick-actions {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.action-card {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 20px;
  background: white;
  border-radius: 12px;
  cursor: pointer;
  transition: box-shadow 0.3s;
}

.action-card:hover {
  box-shadow: 0 4px 20px rgba(0,0,0,0.1);
}

.action-icon {
  font-size: 36px;
}

.action-info {
  flex: 1;
}

.action-info h4 {
  margin: 0 0 5px;
  font-size: 16px;
  color: #333;
}

.action-info p {
  margin: 0;
  font-size: 13px;
  color: #999;
}

.action-arrow {
  font-size: 20px;
  color: #ccc;
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
}

.modal {
  background: white;
  border-radius: 12px;
  width: 450px;
  max-width: 90%;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #eee;
}

.modal-header h3 {
  margin: 0;
}

.modal-close {
  font-size: 24px;
  border: none;
  background: none;
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
  margin-bottom: 6px;
  font-size: 14px;
  color: #666;
}

.form-group input, .form-group select, .form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
  box-sizing: border-box;
}

.form-group textarea {
  resize: vertical;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  padding: 20px;
  border-top: 1px solid #eee;
}

.btn-cancel {
  padding: 10px 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background: white;
  cursor: pointer;
}

.btn-confirm {
  padding: 10px 20px;
  background: #4facfe;
  border: none;
  border-radius: 8px;
  color: white;
  cursor: pointer;
}

@media (max-width: 900px) {
  .stats-row {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .content-grid {
    grid-template-columns: 1fr;
  }
  
  .quick-actions {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 600px) {
  .stats-row {
    grid-template-columns: 1fr;
  }
  
  .quick-actions {
    grid-template-columns: 1fr;
  }
}
</style>