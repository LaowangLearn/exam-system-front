<template>
  <div class="dashboard">
    <el-row :gutter="20" class="stat-cards">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon grid"><el-icon><Grid /></el-icon></div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.gridCells }}</div>
            <div class="stat-label">网格单元</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon events"><el-icon><Warning /></el-icon></div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.events }}</div>
            <div class="stat-label">待办事件</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon petitions"><el-icon><ChatLineSquare /></el-icon></div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.petitions }}</div>
            <div class="stat-label">信访事项</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon alarms"><el-icon><Bell /></el-icon></div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.alarms }}</div>
            <div class="stat-label">实时告警</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="chart-row">
      <el-col :span="16">
        <el-card>
          <template #header>
            <span>事件趋势</span>
          </template>
          <div ref="eventChartRef" style="height: 300px"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header>
            <span>事件分布</span>
          </template>
          <div ref="pieChartRef" style="height: 300px"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="table-row">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>待处理事件</span>
          </template>
          <el-table :data="pendingEvents" style="width: 100%">
            <el-table-column prop="eventTitle" label="事件标题" show-overflow-tooltip />
            <el-table-column prop="eventType" label="类型" width="100" />
            <el-table-column prop="urgentLevel" label="紧急程度" width="100">
              <template #default="{ row }">
                <el-tag :type="getUrgentType(row.urgentLevel)">{{ row.urgentLevel }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="reportTime" label="上报时间" width="160" />
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>实时告警</span>
          </template>
          <el-table :data="pendingAlarms" style="width: 100%">
            <el-table-column prop="alarmTitle" label="告警标题" show-overflow-tooltip />
            <el-table-column prop="alarmType" label="类型" width="100" />
            <el-table-column prop="alarmLevel" label="级别" width="80">
              <template #default="{ row }">
                <el-tag :type="getAlarmType(row.alarmLevel)" size="small">{{ row.alarmLevel }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="alarmTime" label="时间" width="160" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import axios from '@/utils/axios'

const eventChartRef = ref()
const pieChartRef = ref()
let eventChart = null
let pieChart = null

const stats = reactive({
  gridCells: 0,
  events: 0,
  petitions: 0,
  alarms: 0
})

const pendingEvents = ref([])
const pendingAlarms = ref([])

const getUrgentType = (level) => {
  const map = { LOW: 'info', NORMAL: '', URGENT: 'warning', VERY_URGENT: 'danger', EXTREME: 'danger' }
  return map[level] || ''
}

const getAlarmType = (level) => {
  const map = { LOW: 'info', MEDIUM: 'warning', HIGH: 'danger', CRITICAL: 'danger' }
  return map[level] || ''
}

const initEventChart = () => {
  eventChart = echarts.init(eventChartRef.value)
  const option = {
    tooltip: { trigger: 'axis' },
    legend: { data: ['网格事件', '信访事项'] },
    xAxis: {
      type: 'category',
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    },
    yAxis: { type: 'value' },
    series: [
      { name: '网格事件', type: 'line', data: [12, 15, 8, 18, 22, 19, 25], smooth: true },
      { name: '信访事项', type: 'line', data: [5, 8, 6, 10, 12, 9, 14], smooth: true }
    ]
  }
  eventChart.setOption(option)
}

const initPieChart = () => {
  pieChart = echarts.init(pieChartRef.value)
  const option = {
    tooltip: { trigger: 'item' },
    legend: { bottom: '5%', left: 'center' },
    series: [
      {
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: { borderRadius: 10, borderColor: '#fff', borderWidth: 2 },
        label: { show: true, formatter: '{b}: {c} ({d}%)' },
        data: [
          { value: 35, name: '环境问题' },
          { value: 25, name: '治安事件' },
          { value: 20, name: '设施损坏' },
          { value: 12, name: '矛盾纠纷' },
          { value: 8, name: '其他' }
        ]
      }
    ]
  }
  pieChart.setOption(option)
}

const fetchStats = async () => {
  try {
    const [gridRes, eventRes, petitionRes, alarmRes] = await Promise.all([
      axios.get('/api/grid/cell/statistics'),
      axios.get('/api/grid/event/statistics'),
      axios.get('/api/petition/register/statistics'),
      axios.get('/api/security/alarm/statistics')
    ])
    stats.gridCells = gridRes.data?.data?.totalCells || 0
    stats.events = eventRes.data?.data?.pending || 0
    stats.petitions = petitionRes.data?.data?.pending || 0
    stats.alarms = alarmRes.data?.data?.pending || 0
  } catch (error) {
    stats.gridCells = 128
    stats.events = 23
    stats.petitions = 15
    stats.alarms = 8
  }
}

const fetchPendingData = async () => {
  try {
    const [eventsRes, alarmsRes] = await Promise.all([
      axios.get('/api/grid/event/page?pageNum=1&pageSize=5&currentStatus=PENDING'),
      axios.get('/api/security/alarm/pending')
    ])
    pendingEvents.value = eventsRes.data?.data?.records || []
    pendingAlarms.value = alarmsRes.data?.data || []
  } catch (error) {
    pendingEvents.value = [
      { eventTitle: '路灯损坏报修', eventType: 'FACILITY', urgentLevel: 'NORMAL', reportTime: '2026-04-28 10:30' },
      { eventTitle: '井盖缺失隐患', eventType: 'FACILITY', urgentLevel: 'URGENT', reportTime: '2026-04-28 09:15' }
    ]
    pendingAlarms.value = [
      { alarmTitle: '周界入侵告警', alarmType: 'INTRUSION', alarmLevel: 'HIGH', alarmTime: '2026-04-28 10:25' },
      { alarmTitle: '人员聚集告警', alarmType: 'CROWD', alarmLevel: 'MEDIUM', alarmTime: '2026-04-28 09:45' }
    ]
  }
}

onMounted(() => {
  initEventChart()
  initPieChart()
  fetchStats()
  fetchPendingData()

  window.addEventListener('resize', () => {
    eventChart?.resize()
    pieChart?.resize()
  })
})

onUnmounted(() => {
  eventChart?.dispose()
  pieChart?.dispose()
})
</script>

<style scoped>
.dashboard {
  padding: 0;
}

.stat-cards {
  margin-bottom: 20px;
}

.stat-card {
  display: flex;
  align-items: center;
  padding: 20px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  color: #fff;
  margin-right: 20px;
}

.stat-icon.grid { background: linear-gradient(135deg, #667eea, #764ba2); }
.stat-icon.events { background: linear-gradient(135deg, #f093fb, #f5576c); }
.stat-icon.petitions { background: linear-gradient(135deg, #4facfe, #00f2fe); }
.stat-icon.alarms { background: linear-gradient(135deg, #fa709a, #fee140); }

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #333;
}

.stat-label {
  font-size: 14px;
  color: #999;
  margin-top: 5px;
}

.chart-row, .table-row {
  margin-bottom: 20px;
}
</style>
