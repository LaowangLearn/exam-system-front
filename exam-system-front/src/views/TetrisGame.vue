<template>
  <div class="tetris-container">
    <div class="tetris-header">
      <h1>俄罗斯方块</h1>
      <div class="game-controls">
        <el-button type="primary" @click="startGame" :disabled="gameStarted && !gameOver">开始游戏</el-button>
        <el-button type="warning" @click="pauseGame" :disabled="!gameStarted || gameOver">暂停游戏</el-button>
        <el-button type="danger" @click="resetGame">重新开始</el-button>
        <el-button @click="goBack">返回游戏中心</el-button>
      </div>
    </div>
    
    <div class="game-content">
      <div class="game-area">
        <div class="tetris-board">
          <div v-for="(row, rowIndex) in board" :key="rowIndex" class="tetris-row">
            <div 
              v-for="(cell, colIndex) in row" 
              :key="colIndex" 
              class="tetris-cell"
              :class="{ 'filled': cell > 0, 'current': isCurrentPiece(rowIndex, colIndex) }"
              :style="{ backgroundColor: isCurrentPiece(rowIndex, colIndex) ? getCellColor(currentPiece?.type || 0) : getCellColor(cell) }"
            ></div>
          </div>
        </div>
        
        <div class="game-info">
          <div class="info-item">
            <h3>下一个方块</h3>
            <div class="next-piece">
              <div v-for="(row, rowIndex) in 4" :key="rowIndex" class="next-row">
                <div 
                  v-for="(cell, colIndex) in 4" 
                  :key="colIndex" 
                  class="next-cell"
                  :class="{ 'filled': isNextPiece(rowIndex, colIndex) }"
                  :style="{ backgroundColor: isNextPiece(rowIndex, colIndex) ? getCellColor(nextPiece?.type || 0) : '#f0f0f0' }"
                ></div>
              </div>
            </div>
          </div>
          
          <div class="info-item">
            <h3>分数</h3>
            <div class="score">{{ score }}</div>
          </div>
          
          <div class="info-item">
            <h3>等级</h3>
            <div class="level">{{ level }}</div>
          </div>
          
          <div class="info-item">
            <h3>行数</h3>
            <div class="lines">{{ lines }}</div>
          </div>
          
          <div class="info-item">
            <h3>复活次数</h3>
            <div class="revive-count">{{ reviveCount }}</div>
          </div>
          
          <div class="controls-info">
            <h3>操作说明</h3>
            <ul>
              <li>← → 移动方块</li>
              <li>↓ 逆时针旋转</li>
              <li>↑ 顺时针旋转</li>
              <li>空格 直接下落</li>
            </ul>
          </div>
        </div>
      </div>
      
      <div v-if="gameOver" class="game-over">
        <h2>游戏结束</h2>
        <p>最终得分: {{ score }}</p>
        <p>消除行数: {{ lines }}</p>
        <p v-if="reviveCount > 0">剩余复活次数: {{ reviveCount }}</p>
        <div class="game-over-buttons">
          <el-button v-if="reviveCount > 0" type="success" @click="reviveGame">复活 ({{ reviveCount }}次)</el-button>
          <el-button type="primary" @click="resetGame">再来一局</el-button>
        </div>
      </div>
    </div>
    
    <div class="tetris-footer">
      <p>© 2026 俄罗斯方块</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElButton } from 'element-plus'

const router = useRouter()

// 游戏常量
const BOARD_WIDTH = 10
const BOARD_HEIGHT = 20

// 方块形状定义
const TETROMINOS = {
  I: {
    shape: [[0, 0, 0, 0], [1, 1, 1, 1], [0, 0, 0, 0], [0, 0, 0, 0]],
    color: '#00FFFF'
  },
  J: {
    shape: [[1, 0, 0], [1, 1, 1], [0, 0, 0]],
    color: '#0000FF'
  },
  L: {
    shape: [[0, 0, 1], [1, 1, 1], [0, 0, 0]],
    color: '#FF7F00'
  },
  O: {
    shape: [[1, 1], [1, 1]],
    color: '#FFFF00'
  },
  S: {
    shape: [[0, 1, 1], [1, 1, 0], [0, 0, 0]],
    color: '#00FF00'
  },
  T: {
    shape: [[0, 1, 0], [1, 1, 1], [0, 0, 0]],
    color: '#800080'
  },
  Z: {
    shape: [[1, 1, 0], [0, 1, 1], [0, 0, 0]],
    color: '#FF0000'
  }
}

const TETROMINO_NAMES = Object.keys(TETROMINOS) as Array<keyof typeof TETROMINOS>

// 游戏状态
const board = ref<number[][]>([])
const currentPiece = ref<any>(null)
const nextPiece = ref<any>(null)
const gameStarted = ref(false)
const gameOver = ref(false)
const isPaused = ref(false)
const score = ref(0)
const level = ref(1)
const lines = ref(0)
const dropInterval = ref(1000)
const reviveCount = ref(66) // 默认66次复活机会
let dropTimer: number | null = null
let lastTime = 0

// 初始化游戏板
const initBoard = () => {
  const newBoard = []
  for (let y = 0; y < BOARD_HEIGHT; y++) {
    newBoard[y] = []
    for (let x = 0; x < BOARD_WIDTH; x++) {
      newBoard[y][x] = 0
    }
  }
  board.value = newBoard
}

// 随机生成方块
const generatePiece = () => {
  const randomName = TETROMINO_NAMES[Math.floor(Math.random() * TETROMINO_NAMES.length)]
  const tetromino = TETROMINOS[randomName]
  return {
    type: randomName,
    shape: tetromino.shape,
    color: tetromino.color,
    x: Math.floor(BOARD_WIDTH / 2) - Math.floor(tetromino.shape[0].length / 2),
    y: 0
  }
}

// 检查碰撞
const checkCollision = (piece: any, offsetX: number = 0, offsetY: number = 0) => {
  for (let y = 0; y < piece.shape.length; y++) {
    for (let x = 0; x < piece.shape[y].length; x++) {
      if (piece.shape[y][x]) {
        const newX = piece.x + x + offsetX
        const newY = piece.y + y + offsetY
        
        if (
          newX < 0 || 
          newX >= BOARD_WIDTH || 
          newY >= BOARD_HEIGHT ||
          (newY >= 0 && board.value[newY][newX] > 0)
        ) {
          return true
        }
      }
    }
  }
  return false
}

// 旋转方块
const rotatePiece = (piece: any, clockwise: boolean = true) => {
  if (clockwise) {
    // 顺时针旋转
    const rotatedShape = piece.shape[0].map((_, index) => 
      piece.shape.map(row => row[index]).reverse()
    )
    return {
      ...piece,
      shape: rotatedShape
    }
  } else {
    // 逆时针旋转
    const rotatedShape = piece.shape[0].map((_, index) => 
      piece.shape.map(row => row[index])
    ).reverse()
    return {
      ...piece,
      shape: rotatedShape
    }
  }
}

// 锁定方块到游戏板
const lockPiece = () => {
  if (!currentPiece.value) return
  
  for (let y = 0; y < currentPiece.value.shape.length; y++) {
    for (let x = 0; x < currentPiece.value.shape[y].length; x++) {
      if (currentPiece.value.shape[y][x]) {
        const boardY = currentPiece.value.y + y
        const boardX = currentPiece.value.x + x
        if (boardY >= 0) {
          board.value[boardY][boardX] = currentPiece.value.type.charCodeAt(0)
        }
      }
    }
  }
  
  clearLines()
  currentPiece.value = nextPiece.value
  nextPiece.value = generatePiece()
  
  if (checkCollision(currentPiece.value)) {
    gameOver.value = true
    gameStarted.value = false
    if (dropTimer) {
      cancelAnimationFrame(dropTimer)
      dropTimer = null
    }
  }
}

// 消除完整的行
const clearLines = () => {
  let linesCleared = 0
  for (let y = BOARD_HEIGHT - 1; y >= 0; y--) {
    if (board.value[y].every(cell => cell > 0)) {
      board.value.splice(y, 1)
      board.value.unshift(Array(BOARD_WIDTH).fill(0))
      linesCleared++
      y++ // 重新检查当前行
    }
  }
  
  if (linesCleared > 0) {
    lines.value += linesCleared
    score.value += calculateScore(linesCleared)
    updateLevel()
  }
}

// 计算得分
const calculateScore = (linesCleared: number) => {
  const baseScores = [0, 100, 300, 500, 800] // 0, 1, 2, 3, 4 lines
  return baseScores[linesCleared] * level.value
}

// 更新等级
const updateLevel = () => {
  const newLevel = Math.floor(lines.value / 10) + 1
  if (newLevel > level.value) {
    level.value = newLevel
    dropInterval.value = 1000 - (level.value - 1) * 100
    dropInterval.value = Math.max(dropInterval.value, 100)
  }
}

// 移动方块
const movePiece = (offsetX: number, offsetY: number = 0) => {
  if (!currentPiece.value || !gameStarted.value || gameOver.value || isPaused.value) return
  
  if (!checkCollision(currentPiece.value, offsetX, offsetY)) {
    currentPiece.value.x += offsetX
    currentPiece.value.y += offsetY
  } else if (offsetY > 0) {
    lockPiece()
  }
}

// 硬降
const hardDrop = () => {
  if (!currentPiece.value || !gameStarted.value || gameOver.value || isPaused.value) return
  
  while (!checkCollision(currentPiece.value, 0, 1)) {
    currentPiece.value.y++
  }
  lockPiece()
}

// 旋转方块
const rotateCurrentPiece = (clockwise: boolean = true) => {
  if (!currentPiece.value || !gameStarted.value || gameOver.value || isPaused.value) return
  
  const rotated = rotatePiece(currentPiece.value, clockwise)
  if (!checkCollision(rotated)) {
    currentPiece.value = rotated
  }
}

// 游戏循环
const gameLoop = (timestamp: number) => {
  if (!gameStarted.value || gameOver.value || isPaused.value) {
    dropTimer = requestAnimationFrame(gameLoop)
    return
  }
  
  if (timestamp - lastTime > dropInterval.value) {
    movePiece(0, 1)
    lastTime = timestamp
  }
  
  dropTimer = requestAnimationFrame(gameLoop)
}

// 开始游戏
const startGame = () => {
  if (!gameStarted.value) {
    initBoard()
    currentPiece.value = generatePiece()
    nextPiece.value = generatePiece()
    score.value = 0
    level.value = 1
    lines.value = 0
    reviveCount.value = 66 // 开始新游戏时重置复活次数
    dropInterval.value = 1000
    gameStarted.value = true
    gameOver.value = false
    isPaused.value = false
    lastTime = performance.now()
    dropTimer = requestAnimationFrame(gameLoop)
  } else if (isPaused.value) {
    isPaused.value = false
    lastTime = performance.now()
  }
}

// 暂停游戏
const pauseGame = () => {
  isPaused.value = !isPaused.value
}

// 重新开始游戏
const resetGame = () => {
  if (dropTimer) {
    cancelAnimationFrame(dropTimer)
    dropTimer = null
  }
  gameStarted.value = false
  gameOver.value = false
  isPaused.value = false
  initBoard()
  currentPiece.value = null
  nextPiece.value = null
  score.value = 0
  level.value = 1
  lines.value = 0
  reviveCount.value = 66 // 重置游戏时恢复复活次数
}

// 复活游戏
const reviveGame = () => {
  if (reviveCount.value > 0) {
    reviveCount.value-- // 消耗一次复活次数
    
    // 清除最上面6行方块
    for (let y = 0; y < 6 && y < BOARD_HEIGHT; y++) {
      for (let x = 0; x < BOARD_WIDTH; x++) {
        board.value[y][x] = 0
      }
    }
    
    // 生成新的当前方块
    currentPiece.value = generatePiece()
    // 保持下一个方块不变
    // 保留分数、等级、行数等数据
    
    gameOver.value = false // 取消游戏结束状态
    gameStarted.value = true // 开始游戏
    isPaused.value = false // 取消暂停状态
    lastTime = performance.now() // 重置时间
    dropTimer = requestAnimationFrame(gameLoop) // 重新开始游戏循环
  }
}

// 返回游戏中心
const goBack = () => {
  if (dropTimer) {
    cancelAnimationFrame(dropTimer)
    dropTimer = null
  }
  router.push('/game-center')
}

// 检查是否是当前方块的一部分
const isCurrentPiece = (rowIndex: number, colIndex: number) => {
  if (!currentPiece.value) return false
  
  for (let y = 0; y < currentPiece.value.shape.length; y++) {
    for (let x = 0; x < currentPiece.value.shape[y].length; x++) {
      if (
        currentPiece.value.shape[y][x] &&
        currentPiece.value.y + y === rowIndex &&
        currentPiece.value.x + x === colIndex
      ) {
        return true
      }
    }
  }
  return false
}

// 检查是否是下一个方块的一部分
const isNextPiece = (rowIndex: number, colIndex: number) => {
  if (!nextPiece.value) return false
  
  for (let y = 0; y < nextPiece.value.shape.length; y++) {
    for (let x = 0; x < nextPiece.value.shape[y].length; x++) {
      if (
        nextPiece.value.shape[y][x] &&
        y === rowIndex &&
        x === colIndex
      ) {
        return true
      }
    }
  }
  return false
}

// 获取方块颜色
const getCellColor = (cell: number | string) => {
  if (cell === 0 || cell === null || cell === undefined) return '#f0f0f0'
  if (typeof cell === 'string') {
    return TETROMINOS[cell as keyof typeof TETROMINOS]?.color || '#f0f0f0'
  }
  if (typeof cell === 'number' && cell > 0) {
    const pieceType = String.fromCharCode(cell)
    return TETROMINOS[pieceType as keyof typeof TETROMINOS]?.color || '#f0f0f0'
  }
  return '#f0f0f0'
}

// 键盘事件处理
const handleKeydown = (event: KeyboardEvent) => {
  // 阻止方向键和空格键的默认行为，防止页面滚动
  if (['ArrowUp', 'ArrowDown', 'ArrowLeft', 'ArrowRight', ' '].includes(event.key)) {
    event.preventDefault()
  }
  
  if (!gameStarted.value || gameOver.value || isPaused.value) return
  
  switch (event.key) {
    case 'ArrowLeft':
      movePiece(-1)
      break
    case 'ArrowRight':
      movePiece(1)
      break
    case 'ArrowDown':
      rotateCurrentPiece(false) // 逆时针旋转
      break
    case 'ArrowUp':
      rotateCurrentPiece(true) // 顺时针旋转
      break
    case ' ': // 空格键
      hardDrop()
      break
  }
}

// 生命周期钩子
onMounted(() => {
  initBoard()
  nextPiece.value = generatePiece()
  window.addEventListener('keydown', handleKeydown)
})

onUnmounted(() => {
  window.removeEventListener('keydown', handleKeydown)
  if (dropTimer) {
    cancelAnimationFrame(dropTimer)
    dropTimer = null
  }
})
</script>

<style scoped>
/* 全局样式重置，防止页面滚动 */
:root {
  overflow: hidden;
}

body {
  overflow: hidden;
  margin: 0;
  padding: 0;
}

.tetris-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: linear-gradient(135deg, #2c3e50 0%, #4b6cb7 100%);
  padding: 10px;
  color: white;
  box-sizing: border-box;
  overflow: hidden;
}

.tetris-header {
  text-align: center;
  margin-bottom: 20px;
  width: 100%;
  max-width: 900px;
}

.tetris-header h1 {
  font-size: 2.5rem;
  margin-bottom: 15px;
  font-weight: 700;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
}

.game-controls {
  display: flex;
  gap: 10px;
  justify-content: center;
  flex-wrap: wrap;
  margin-bottom: 10px;
}

.game-content {
  display: flex;
  gap: 20px;
  align-items: flex-start;
  width: 100%;
  max-width: 900px;
  position: relative;
  flex: 1;
  overflow: hidden;
}

.game-area {
  display: flex;
  gap: 20px;
  align-items: flex-start;
  flex: 1;
  flex-wrap: wrap;
  justify-content: center;
}

.tetris-board {
  border: 3px solid #333;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  padding: 8px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  flex-shrink: 0;
}

.tetris-row {
  display: flex;
}

.tetris-cell {
  width: 28px;
  height: 28px;
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 2px;
  margin: 1px;
  transition: all 0.1s ease;
}

.tetris-cell.filled {
  border: 1px solid rgba(255, 255, 255, 0.5);
}

.tetris-cell.current {
  box-shadow: 0 0 12px rgba(255, 255, 255, 1);
  border: 2px solid rgba(255, 255, 255, 0.9);
  transform: scale(1.05);
  z-index: 1;
  position: relative;
}

.game-info {
  display: flex;
  flex-direction: column;
  gap: 15px;
  min-width: 220px;
  flex-shrink: 0;
}

.info-item {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 8px;
  padding: 12px;
  text-align: center;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
}

.info-item h3 {
  margin-bottom: 8px;
  font-size: 1.1rem;
  font-weight: 600;
  color: #f0f0f0;
}

.score, .level, .lines, .revive-count {
  font-size: 1.8rem;
  font-weight: 700;
  color: #ffeb3b;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5);
}

.next-piece {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 8px;
}

.next-row {
  display: flex;
}

.next-cell {
  width: 22px;
  height: 22px;
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 2px;
  margin: 1px;
}

.next-cell.filled {
  border: 1px solid rgba(255, 255, 255, 0.5);
}

.controls-info {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 8px;
  padding: 12px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
}

.controls-info h3 {
  margin-bottom: 8px;
  font-size: 1.1rem;
  font-weight: 600;
  color: #f0f0f0;
  text-align: center;
}

.controls-info ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.controls-info li {
  margin-bottom: 6px;
  font-size: 0.9rem;
  color: #e0e0e0;
  display: flex;
  align-items: center;
  gap: 8px;
}

.game-over {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, 0.9);
  border: 3px solid #ff3b30;
  border-radius: 16px;
  padding: 30px;
  text-align: center;
  box-shadow: 0 20px 60px rgba(255, 59, 48, 0.4);
  z-index: 100;
}

.game-over h2 {
  font-size: 2rem;
  margin-bottom: 15px;
  color: #ff3b30;
  font-weight: 700;
}

.game-over p {
  font-size: 1.1rem;
  margin-bottom: 8px;
  color: #f0f0f0;
}

.game-over-buttons {
  display: flex;
  gap: 10px;
  justify-content: center;
  margin-top: 20px;
  flex-wrap: wrap;
}

.tetris-footer {
  margin-top: 20px;
  color: rgba(255, 255, 255, 0.7);
  font-size: 0.8rem;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .tetris-container {
    padding: 5px;
  }
  
  .tetris-header h1 {
    font-size: 2rem;
  }
  
  .game-content {
    flex-direction: column;
    align-items: center;
    gap: 15px;
  }
  
  .game-area {
    flex-direction: column;
    align-items: center;
    gap: 15px;
  }
  
  .game-controls {
    flex-wrap: wrap;
    gap: 8px;
  }
  
  .game-controls button {
    font-size: 0.9rem;
    padding: 6px 12px;
  }
  
  .tetris-cell {
    width: 24px;
    height: 24px;
  }
  
  .next-cell {
    width: 18px;
    height: 18px;
  }
  
  .game-info {
    min-width: 200px;
  }
  
  .info-item {
    padding: 10px;
  }
  
  .score, .level, .lines {
    font-size: 1.5rem;
  }
  
  .game-over {
    padding: 20px;
  }
  
  .game-over h2 {
    font-size: 1.8rem;
  }
}

@media (max-width: 480px) {
  .tetris-cell {
    width: 20px;
    height: 20px;
  }
  
  .next-cell {
    width: 16px;
    height: 16px;
  }
  
  .game-info {
    min-width: 180px;
  }
  
  .tetris-header h1 {
    font-size: 1.8rem;
  }
}

/* 防止触摸设备上的滚动 */
@media (hover: none) and (pointer: coarse) {
  .tetris-container {
    touch-action: none;
  }
}
</style>