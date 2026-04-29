<template>
  <div class="education-page">
    <div class="page-header">
      <button class="back-btn" @click="goBack">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M15 18l-6-6 6-6" />
        </svg>
        返回
      </button>
      <h1>教务管理系统</h1>
    </div>
    
    <div class="page-content">
      <div class="tabs">
        <button 
          v-for="tab in tabs" 
          :key="tab.key" 
          :class="['tab-btn', { active: activeTab === tab.key }]"
          @click="activeTab = tab.key"
        >
          {{ tab.label }}
        </button>
      </div>
      
      <div class="tab-content">
        <div v-if="activeTab === 'courses'" class="courses-panel">
          <div class="panel-header">
            <h2>课程管理</h2>
            <button class="add-btn" @click="showAddCourse = true">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="12" y1="5" x2="12" y2="19"></line>
                <line x1="5" y1="12" x2="19" y2="12"></line>
              </svg>
              添加课程
            </button>
          </div>
          <div class="courses-table">
            <table>
              <thead>
                <tr>
                  <th>课程名称</th>
                  <th>授课教师</th>
                  <th>学分</th>
                  <th>课时</th>
                  <th>开课学期</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="course in courses" :key="course.id">
                  <td>{{ course.name }}</td>
                  <td>{{ course.teacher }}</td>
                  <td>{{ course.credits }}</td>
                  <td>{{ course.hours }}</td>
                  <td>{{ course.semester }}</td>
                  <td>
                    <span :class="['status-badge', course.status]">{{ course.status === 'active' ? '进行中' : '已结束' }}</span>
                  </td>
                  <td>
                    <button class="action-btn edit" @click="editCourse(course)">编辑</button>
                    <button class="action-btn delete" @click="deleteCourse(course.id)">删除</button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        
        <div v-if="activeTab === 'students'" class="students-panel">
          <div class="panel-header">
            <h2>学籍管理</h2>
            <div class="search-box">
              <input type="text" v-model="searchStudent" placeholder="搜索学生姓名/学号" />
              <button class="search-btn">搜索</button>
            </div>
          </div>
          <div class="students-table">
            <table>
              <thead>
                <tr>
                  <th>学号</th>
                  <th>姓名</th>
                  <th>性别</th>
                  <th>年级</th>
                  <th>专业</th>
                  <th>班级</th>
                  <th>状态</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="student in filteredStudents" :key="student.id">
                  <td>{{ student.studentNo }}</td>
                  <td>{{ student.name }}</td>
                  <td>{{ student.gender === 'male' ? '男' : '女' }}</td>
                  <td>{{ student.grade }}</td>
                  <td>{{ student.major }}</td>
                  <td>{{ student.class }}</td>
                  <td>
                    <span :class="['status-badge', student.status]">
                      {{ student.status === 'active' ? '在读' : student.status === 'graduated' ? '已毕业' : '休学' }}
                    </span>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        
        <div v-if="activeTab === 'scores'" class="scores-panel">
          <div class="panel-header">
            <h2>成绩管理</h2>
            <select v-model="selectedCourse" class="course-select">
              <option value="">选择课程</option>
              <option v-for="course in courses" :key="course.id" :value="course.id">{{ course.name }}</option>
            </select>
          </div>
          <div class="scores-table">
            <table>
              <thead>
                <tr>
                  <th>学号</th>
                  <th>姓名</th>
                  <th>平时成绩</th>
                  <th>期末成绩</th>
                  <th>总评成绩</th>
                  <th>等级</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="score in scores" :key="score.id">
                  <td>{{ score.studentNo }}</td>
                  <td>{{ score.name }}</td>
                  <td>{{ score.usualScore }}</td>
                  <td>{{ score.finalScore }}</td>
                  <td>{{ score.totalScore }}</td>
                  <td>
                    <span :class="['grade-badge', getGradeClass(score.totalScore)]">{{ getGrade(score.totalScore) }}</span>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        
        <div v-if="activeTab === 'electives'" class="electives-panel">
          <div class="panel-header">
            <h2>选课系统</h2>
            <button class="refresh-btn">刷新数据</button>
          </div>
          <div class="electives-grid">
            <div v-for="course in electiveCourses" :key="course.id" class="elective-card">
              <div class="card-header">
                <h3>{{ course.name }}</h3>
                <span :class="['capacity-badge', course.capacity <= course.enrolled ? 'full' : '']">
                  {{ course.enrolled }}/{{ course.capacity }}
                </span>
              </div>
              <p class="teacher">{{ course.teacher }}</p>
              <p class="time">{{ course.time }}</p>
              <p class="location">{{ course.location }}</p>
              <button 
                :disabled="course.capacity <= course.enrolled"
                :class="['enroll-btn', course.capacity <= course.enrolled ? 'disabled' : '']"
                @click="enrollCourse(course)"
              >
                {{ course.capacity <= course.enrolled ? '已满' : '选课' }}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <div v-if="showAddCourse" class="modal-overlay" @click.self="showAddCourse = false">
      <div class="modal">
        <div class="modal-header">
          <h3>添加课程</h3>
          <button class="modal-close" @click="showAddCourse = false">×</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>课程名称</label>
            <input v-model="newCourse.name" type="text" placeholder="请输入课程名称" />
          </div>
          <div class="form-group">
            <label>授课教师</label>
            <input v-model="newCourse.teacher" type="text" placeholder="请输入教师姓名" />
          </div>
          <div class="form-row">
            <div class="form-group">
              <label>学分</label>
              <input v-model="newCourse.credits" type="number" placeholder="学分" />
            </div>
            <div class="form-group">
              <label>课时</label>
              <input v-model="newCourse.hours" type="number" placeholder="课时" />
            </div>
          </div>
          <div class="form-group">
            <label>开课学期</label>
            <select v-model="newCourse.semester">
              <option value="2024-1">2024-1</option>
              <option value="2024-2">2024-2</option>
              <option value="2025-1">2025-1</option>
              <option value="2025-2">2025-2</option>
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn-cancel" @click="showAddCourse = false">取消</button>
          <button class="btn-confirm" @click="addCourse">确定</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/education')
}

const tabs = [
  { key: 'courses', label: '课程管理' },
  { key: 'students', label: '学籍管理' },
  { key: 'scores', label: '成绩管理' },
  { key: 'electives', label: '选课系统' }
]

const activeTab = ref('courses')
const searchStudent = ref('')
const selectedCourse = ref('')
const showAddCourse = ref(false)

const newCourse = ref({
  name: '',
  teacher: '',
  credits: 0,
  hours: 0,
  semester: '2025-1'
})

const courses = ref([
  { id: 1, name: '高等数学', teacher: '张教授', credits: 4, hours: 64, semester: '2025-1', status: 'active' },
  { id: 2, name: '大学物理', teacher: '李教授', credits: 4, hours: 64, semester: '2025-1', status: 'active' },
  { id: 3, name: '程序设计基础', teacher: '王老师', credits: 3, hours: 48, semester: '2025-1', status: 'active' },
  { id: 4, name: '英语', teacher: '陈老师', credits: 3, hours: 48, semester: '2025-1', status: 'active' },
  { id: 5, name: '线性代数', teacher: '赵教授', credits: 3, hours: 48, semester: '2024-2', status: 'ended' },
  { id: 6, name: '数据结构', teacher: '刘老师', credits: 4, hours: 64, semester: '2025-1', status: 'active' }
])

const students = ref([
  { id: 1, studentNo: '2021001', name: '张三', gender: 'male', grade: '2021级', major: '计算机科学', class: '计科2101' },
  { id: 2, studentNo: '2021002', name: '李四', gender: 'female', grade: '2021级', major: '计算机科学', class: '计科2101' },
  { id: 3, studentNo: '2021003', name: '王五', gender: 'male', grade: '2021级', major: '软件工程', class: '软工2102' },
  { id: 4, studentNo: '2022001', name: '赵六', gender: 'female', grade: '2022级', major: '人工智能', class: 'AI2201' },
  { id: 5, studentNo: '2022002', name: '钱七', gender: 'male', grade: '2022级', major: '数据科学', class: '数科2201' },
  { id: 6, studentNo: '2023001', name: '孙八', gender: 'female', grade: '2023级', major: '计算机科学', class: '计科2301' }
])

students.value.forEach(s => s.status = 'active')
students.value[4].status = 'graduated'

const filteredStudents = computed(() => {
  if (!searchStudent.value) return students.value
  const keyword = searchStudent.value.toLowerCase()
  return students.value.filter(s => 
    s.name.toLowerCase().includes(keyword) || 
    s.studentNo.includes(keyword)
  )
})

const scores = ref([
  { id: 1, studentNo: '2021001', name: '张三', usualScore: 90, finalScore: 85, totalScore: 87 },
  { id: 2, studentNo: '2021002', name: '李四', usualScore: 85, finalScore: 90, totalScore: 88 },
  { id: 3, studentNo: '2021003', name: '王五', usualScore: 80, finalScore: 82, totalScore: 81 },
  { id: 4, studentNo: '2022001', name: '赵六', usualScore: 95, finalScore: 92, totalScore: 93 },
  { id: 5, studentNo: '2022002', name: '钱七', usualScore: 75, finalScore: 78, totalScore: 77 },
  { id: 6, studentNo: '2023001', name: '孙八', usualScore: 88, finalScore: 86, totalScore: 87 }
])

const electiveCourses = ref([
  { id: 1, name: '人工智能导论', teacher: '周教授', time: '周一 1-2节', location: '科技楼A301', enrolled: 28, capacity: 30 },
  { id: 2, name: '机器学习', teacher: '吴教授', time: '周三 3-4节', location: '科技楼B205', enrolled: 35, capacity: 35 },
  { id: 3, name: '深度学习', teacher: '郑教授', time: '周五 1-2节', location: '科技楼A301', enrolled: 20, capacity: 30 },
  { id: 4, name: '计算机视觉', teacher: '冯老师', time: '周二 5-6节', location: '实验楼102', enrolled: 15, capacity: 25 },
  { id: 5, name: '自然语言处理', teacher: '陈教授', time: '周四 3-4节', location: '科技楼B305', enrolled: 22, capacity: 30 },
  { id: 6, name: '大数据分析', teacher: '黄老师', time: '周一 5-6节', location: '实验楼103', enrolled: 18, capacity: 25 }
])

const getGrade = (score) => {
  if (score >= 90) return 'A'
  if (score >= 80) return 'B'
  if (score >= 70) return 'C'
  if (score >= 60) return 'D'
  return 'F'
}

const getGradeClass = (score) => {
  if (score >= 90) return 'grade-a'
  if (score >= 80) return 'grade-b'
  if (score >= 70) return 'grade-c'
  if (score >= 60) return 'grade-d'
  return 'grade-f'
}

const addCourse = () => {
  courses.value.push({
    id: Date.now(),
    ...newCourse.value,
    status: 'active'
  })
  showAddCourse.value = false
  newCourse.value = { name: '', teacher: '', credits: 0, hours: 0, semester: '2025-1' }
}

const editCourse = (course) => {
  alert(`编辑课程: ${course.name}`)
}

const deleteCourse = (id) => {
  if (confirm('确定要删除该课程吗？')) {
    courses.value = courses.value.filter(c => c.id !== id)
  }
}

const enrollCourse = (course) => {
  if (confirm(`确定要选择课程「${course.name}」吗？`)) {
    course.enrolled++
  }
}
</script>

<style scoped>
.education-page {
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
  background: #667eea;
  border: none;
  border-radius: 8px;
  color: white;
  cursor: pointer;
  font-size: 14px;
  transition: background 0.3s;
}

.back-btn:hover {
  background: #5a6fd6;
}

.page-header h1 {
  margin: 0;
  font-size: 24px;
  color: #333;
}

.page-content {
  padding: 20px 30px;
}

.tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
  padding: 10px;
  background: white;
  border-radius: 10px;
}

.tab-btn {
  padding: 12px 24px;
  border: none;
  border-radius: 8px;
  background: #f0f0f0;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s;
}

.tab-btn.active {
  background: #667eea;
  color: white;
}

.tab-content {
  background: white;
  border-radius: 10px;
  padding: 20px;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.panel-header h2 {
  margin: 0;
  font-size: 18px;
  color: #333;
}

.add-btn, .refresh-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background: #667eea;
  border: none;
  border-radius: 8px;
  color: white;
  cursor: pointer;
  font-size: 14px;
}

.refresh-btn {
  background: #4facfe;
}

.search-box {
  display: flex;
  gap: 10px;
}

.search-box input {
  padding: 10px 16px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
  width: 200px;
}

.search-btn {
  padding: 10px 20px;
  background: #667eea;
  border: none;
  border-radius: 8px;
  color: white;
  cursor: pointer;
}

.course-select {
  padding: 10px 16px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

th {
  background: #f8f9fa;
  font-weight: 600;
  color: #666;
}

.status-badge {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
}

.status-badge.active {
  background: #e8f5e9;
  color: #2e7d32;
}

.status-badge.ended, .status-badge.graduated {
  background: #fce4ec;
  color: #c2185b;
}

.status-badge.suspended {
  background: #fff3e0;
  color: #ef6c00;
}

.action-btn {
  padding: 6px 12px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 12px;
  margin-right: 8px;
}

.action-btn.edit {
  background: #4facfe;
  color: white;
}

.action-btn.delete {
  background: #ff6b6b;
  color: white;
}

.grade-badge {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: bold;
}

.grade-a { background: #e8f5e9; color: #2e7d32; }
.grade-b { background: #e3f2fd; color: #1565c0; }
.grade-c { background: #fff3e0; color: #ef6c00; }
.grade-d { background: #fce4ec; color: #c2185b; }
.grade-f { background: #f4e0e0; color: #c62828; }

.electives-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.elective-card {
  border: 1px solid #eee;
  border-radius: 12px;
  padding: 20px;
  transition: box-shadow 0.3s;
}

.elective-card:hover {
  box-shadow: 0 4px 20px rgba(0,0,0,0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.card-header h3 {
  margin: 0;
  font-size: 16px;
}

.capacity-badge {
  padding: 4px 10px;
  border-radius: 20px;
  font-size: 12px;
  background: #e8f5e9;
  color: #2e7d32;
}

.capacity-badge.full {
  background: #fce4ec;
  color: #c2185b;
}

.elective-card p {
  margin: 6px 0;
  color: #666;
  font-size: 14px;
}

.enroll-btn {
  width: 100%;
  padding: 10px;
  background: #667eea;
  border: none;
  border-radius: 8px;
  color: white;
  cursor: pointer;
  margin-top: 10px;
}

.enroll-btn.disabled {
  background: #ccc;
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
}

.modal {
  background: white;
  border-radius: 12px;
  width: 400px;
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

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 6px;
  font-size: 14px;
  color: #666;
}

.form-group input, .form-group select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
  box-sizing: border-box;
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
  background: #667eea;
  border: none;
  border-radius: 8px;
  color: white;
  cursor: pointer;
}

@media (max-width: 900px) {
  .electives-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 600px) {
  .electives-grid {
    grid-template-columns: 1fr;
  }
  
  .tabs {
    flex-wrap: wrap;
  }
}
</style>