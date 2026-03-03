<template>
  <div class="tetris-container">
    <div class="tetris-header">
      <h1>俄罗斯方块 - 手机号版</h1>
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
        </div>
      </div>
      
      <div class="phone-input-section">
        <div class="phone-display">
          <div class="display-label">输入控制数字</div>
          <div class="phone-input">{{ phoneInput || '_' }}</div>
        </div>
        
        <div class="numpad">
          <div class="numpad-row">
            <button class="numpad-btn" @click="handleNumber(1)">1<br/><span>左转</span></button>
            <button class="numpad-btn" @click="handleNumber(2)">2<br/><span>下移</span></button>
            <button class="numpad-btn" @click="handleNumber(3)">3<br/><span>右转</span></button>
          </div>
          <div class="numpad-row">
            <button class="numpad-btn" @click="handleNumber(4)">4<br/><span>左移</span></button>
            <button class="numpad-btn" @click="handleNumber(5)">5<br/><span>旋转</span></button>
            <button class="numpad-btn" @click="handleNumber(6)">6<br/><span>右移</span></button>
          </div>
          <div class="numpad-row">
            <button class="numpad-btn" @click="handleNumber(7)">7<br/><span>暂停</span></button>
            <button class="numpad-btn" @click="handleNumber(8)">8<br/><span>加速</span></button>
            <button class="numpad-btn" @click="handleNumber(9)">9<br/><span>直落</span></button>
          </div>
          <div class="numpad-row">
            <button class="numpad-btn zero" @click="handleNumber(0)">0<br/><span>重置</span></button>
          </div>
        </div>
        
        <div class="controls-legend">
          <h4>操作说明</h4>
          <div class="legend-grid">
            <span>1 - 左转</span>
            <span>2 - 下移</span>
            <span>3 - 右转</span>
            <span>4 - 左移</span>
            <span>5 - 旋转</span>
            <span>6 - 右移</span>
            <span>7 - 暂停</span>
            <span>8 - 加速下落</span>
            <span>9 - 直接下落</span>
            <span>0 - 重置游戏</span>
          </div>
        </div>
      </div>
      
      <div v-if="gameOver" class="game-over">
        <h2>游戏结束</h2>
        <p>最终得分: {{ score }}</p>
        <p>消除行数: {{ lines }}</p>
        <el-button type="primary" @click="resetGame">再来一局</el-button>
      </div>
    </div>
    
    <div class="tetris-footer">
      <p>© 2026 俄罗斯方块 - 手机号版</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElButton } from 'element-plus'

const router = useRouter()

const BOARD_WIDTH = 10
const BOARD_HEIGHT = 20

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
const phoneInput = ref('')
let dropTimer: number | null = null
let lastTime = 0

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

const rotatePiece = (piece: any, clockwise: boolean = true) => {
  if (clockwise) {
    const rotatedShape = piece.shape[0].map((_, index) => 
      piece.shape.map(row => row[index]).reverse()
    )
    return { ...piece, shape: rotatedShape }
  } else {
    const rotatedShape = piece.shape[0].map((_, index) => 
      piece.shape.map(row => row[row.length - 1 - index])
    )
    return { ...piece, shape: rotatedShape }
  }
}

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

const clearLines = () => {
  let linesCleared = 0
  for (let y = BOARD_HEIGHT - 1; y >= 0; y--) {
    if (board.value[y].every(cell => cell > 0)) {
      board.value.splice(y, 1)
      board.value.unshift(Array(BOARD_WIDTH).fill(0))
      linesCleared++
      y++
    }
  }
  
  if (linesCleared > 0) {
    lines.value += linesCleared
    score.value += calculateScore(linesCleared)
    updateLevel()
  }
}

const calculateScore = (linesCleared: number) => {
  const baseScores = [0, 100, 300, 500, 800]
  return baseScores[linesCleared] * level.value
}

const updateLevel = () => {
  const newLevel = Math.floor(lines.value / 10) + 1
  if (newLevel > level.value) {
    level.value = newLevel
    dropInterval.value = 1000 - (level.value - 1) * 100
    dropInterval.value = Math.max(dropInterval.value, 100)
  }
}

const movePiece = (offsetX: number, offsetY: number = 0) => {
  if (!currentPiece.value || !gameStarted.value || gameOver.value || isPaused.value) return
  
  if (!checkCollision(currentPiece.value, offsetX, offsetY)) {
    currentPiece.value.x += offsetX
    currentPiece.value.y += offsetY
  } else if (offsetY > 0) {
    lockPiece()
  }
}

const hardDrop = () => {
  if (!currentPiece.value || !gameStarted.value || gameOver.value || isPaused.value) return
  
  while (!checkCollision(currentPiece.value, 0, 1)) {
    currentPiece.value.y++
  }
  lockPiece()
}

const rotateCurrentPiece = (clockwise: boolean = true) => {
  if (!currentPiece.value || !gameStarted.value || gameOver.value || isPaused.value) return
  
  const rotated = rotatePiece(currentPiece.value, clockwise)
  if (!checkCollision(rotated)) {
    currentPiece.value = rotated
  }
}

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

const startGame = () => {
  if (!gameStarted.value) {
    initBoard()
    currentPiece.value = generatePiece()
    nextPiece.value = generatePiece()
    score.value = 0
    level.value = 1
    lines.value = 0
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

const pauseGame = () => {
  isPaused.value = !isPaused.value
}

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
  phoneInput.value = ''
}

const goBack = () => {
  if (dropTimer) {
    cancelAnimationFrame(dropTimer)
    dropTimer = null
  }
  router.push('/game-center')
}

const handleNumber = (num: number) => {
  phoneInput.value = num.toString()
  setTimeout(() => { phoneInput.value = '' }, 300)
  
  switch (num) {
    case 1:
      rotateCurrentPiece(false)
      break
    case 2:
      movePiece(0, 1)
      break
    case 3:
      rotateCurrentPiece(true)
      break
    case 4:
      movePiece(-1)
      break
    case 5:
      rotateCurrentPiece(true)
      break
    case 6:
      movePiece(1)
      break
    case 7:
      if (gameStarted.value && !gameOver.value) {
        pauseGame()
      }
      break
    case 8:
      if (gameStarted.value && !gameOver.value && !isPaused.value) {
        for (let i = 0; i < 3; i++) {
          movePiece(0, 1)
        }
      }
      break
    case 9:
      hardDrop()
      break
    case 0:
      resetGame()
      break
  }
}

const handleKeydown = (event: KeyboardEvent) => {
  if (['1', '2', '3', '4', '5', '6', '7', '8', '9', '0'].includes(event.key)) {
    event.preventDefault()
    const num = parseInt(event.key)
    handleNumber(num)
  }
}

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
.tetris-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  padding: 20px;
  color: white;
}

.tetris-header {
  text-align: center;
  margin-bottom: 30px;
  width: 100%;
  max-width: 1200px;
}

.tetris-header h1 {
  font-size: 2.5rem;
  margin-bottom: 20px;
  font-weight: 700;
  background: linear-gradient(135deg, #f39c12 0%, #e74c3c 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.game-controls {
  display: flex;
  gap: 10px;
  justify-content: center;
  flex-wrap: wrap;
}

.game-content {
  display: flex;
  gap: 40px;
  align-items: flex-start;
  width: 100%;
  max-width: 1200px;
  position: relative;
  flex-wrap: wrap;
  justify-content: center;
}

.game-area {
  display: flex;
  gap: 30px;
  align-items: flex-start;
}

.tetris-board {
  border: 3px solid #f39c12;
  background: rgba(0, 0, 0, 0.5);
  border-radius: 8px;
  padding: 10px;
  box-shadow: 0 10px 30px rgba(243, 156, 18, 0.3);
}

.tetris-row {
  display: flex;
}

.tetris-cell {
  width: 28px;
  height: 28px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 2px;
  margin: 1px;
  transition: all 0.1s ease;
}

.tetris-cell.filled {
  border: 1px solid rgba(255, 255, 255, 0.5);
}

.tetris-cell.current {
  box-shadow: 0 0 10px rgba(255, 255, 255, 0.8);
  border: 2px solid rgba(255, 255, 255, 0.9);
}

.game-info {
  display: flex;
  flex-direction: column;
  gap: 15px;
  min-width: 180px;
}

.info-item {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(243, 156, 18, 0.3);
  border-radius: 8px;
  padding: 12px;
  text-align: center;
}

.info-item h3 {
  margin-bottom: 8px;
  font-size: 1rem;
  font-weight: 600;
  color: #f39c12;
}

.score, .level, .lines {
  font-size: 1.8rem;
  font-weight: 700;
  color: #ffeb3b;
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
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 2px;
  margin: 1px;
}

.next-cell.filled {
  border: 1px solid rgba(255, 255, 255, 0.5);
}

.phone-input-section {
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: center;
}

.phone-display {
  background: rgba(0, 0, 0, 0.6);
  border: 2px solid #f39c12;
  border-radius: 12px;
  padding: 15px 30px;
  text-align: center;
  min-width: 200px;
}

.display-label {
  font-size: 0.9rem;
  color: #888;
  margin-bottom: 5px;
}

.phone-input {
  font-size: 2.5rem;
  font-weight: 700;
  color: #f39c12;
  font-family: 'Courier New', monospace;
  min-height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.numpad {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 16px;
  padding: 15px;
}

.numpad-row {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
  justify-content: center;
}

.numpad-row:last-child {
  margin-bottom: 0;
}

.numpad-btn {
  width: 70px;
  height: 70px;
  border: 2px solid rgba(243, 156, 18, 0.5);
  border-radius: 12px;
  background: linear-gradient(135deg, rgba(243, 156, 18, 0.2) 0%, rgba(231, 76, 60, 0.2) 100%);
  color: #fff;
  font-size: 1.3rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 2px;
}

.numpad-btn:hover {
  background: linear-gradient(135deg, rgba(243, 156, 18, 0.4) 0%, rgba(231, 76, 60, 0.4) 100%);
  border-color: #f39c12;
  transform: scale(1.05);
}

.numpad-btn:active {
  transform: scale(0.95);
}

.numpad-btn span {
  font-size: 0.7rem;
  opacity: 0.7;
  font-weight: 400;
}

.numpad-btn.zero {
  width: 230px;
}

.controls-legend {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  padding: 15px;
  max-width: 280px;
}

.controls-legend h4 {
  margin: 0 0 12px 0;
  color: #f39c12;
  font-size: 1rem;
  text-align: center;
}

.legend-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 8px;
  font-size: 0.85rem;
  color: #aaa;
}

.game-over {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, 0.95);
  border: 3px solid #e74c3c;
  border-radius: 16px;
  padding: 40px;
  text-align: center;
  z-index: 100;
}

.game-over h2 {
  font-size: 2.2rem;
  margin-bottom: 15px;
  color: #e74c3c;
  font-weight: 700;
}

.game-over p {
  font-size: 1.1rem;
  margin-bottom: 10px;
  color: #f0f0f0;
}

.tetris-footer {
  margin-top: 30px;
  color: rgba(255, 255, 255, 0.6);
  font-size: 0.85rem;
}

@media (max-width: 900px) {
  .game-content {
    flex-direction: column;
    align-items: center;
  }
  
  .game-area {
    flex-direction: column;
    align-items: center;
  }
  
  .tetris-header h1 {
    font-size: 1.8rem;
  }
  
  .tetris-cell {
    width: 22px;
    height: 22px;
  }
  
  .numpad-btn {
    width: 60px;
    height: 60px;
    font-size: 1.1rem;
  }
  
  .numpad-btn.zero {
    width: 200px;
  }
}
</style>
