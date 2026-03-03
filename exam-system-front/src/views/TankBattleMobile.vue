<template>
  <div class="tank-container">
    <div class="tank-header">
      <h1>坦克大战 - 手机号版</h1>
      <div class="game-controls">
        <el-button type="primary" @click="startGame" :disabled="gameStarted && !gameOver">开始游戏</el-button>
        <el-button type="warning" @click="togglePause" :disabled="!gameStarted || gameOver">{{ isPaused ? '继续' : '暂停' }}</el-button>
        <el-button type="danger" @click="resetGame">重新开始</el-button>
        <el-button @click="goBack">返回游戏中心</el-button>
      </div>
    </div>
    
    <div class="game-content">
      <div class="game-area">
        <div class="game-board">
          <div v-for="(row, y) in gameMap" :key="y" class="map-row">
            <div 
              v-for="(cell, x) in row" 
              :key="x" 
              class="map-cell"
              :class="getCellClass(cell, x, y)"
            ></div>
          </div>
        </div>
        
        <div class="game-info">
          <div class="info-item">
            <h3>分数</h3>
            <div class="score">{{ score }}</div>
          </div>
          
          <div class="info-item">
            <h3>生命</h3>
            <div class="lives">
              <span v-for="i in lives" :key="i" class="life-icon">♥</span>
              <span v-for="i in (3 - lives)" :key="'empty-' + i" class="life-icon empty">♡</span>
            </div>
          </div>
          
          <div class="info-item">
            <h3>关卡</h3>
            <div class="level">{{ currentLevel }}</div>
          </div>
          
          <div class="info-item">
            <h3>敌人</h3>
            <div class="enemies">{{ remainingEnemies }}</div>
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
            <button class="numpad-btn" @click="handleNumber(1)">1<br/><span>左上</span></button>
            <button class="numpad-btn" @click="handleNumber(2)">2<br/><span>上移</span></button>
            <button class="numpad-btn" @click="handleNumber(3)">3<br/><span>右上</span></button>
          </div>
          <div class="numpad-row">
            <button class="numpad-btn" @click="handleNumber(4)">4<br/><span>左移</span></button>
            <button class="numpad-btn fire" @click="handleNumber(5)">5<br/><span>发射</span></button>
            <button class="numpad-btn" @click="handleNumber(6)">6<br/><span>右移</span></button>
          </div>
          <div class="numpad-row">
            <button class="numpad-btn" @click="handleNumber(7)">7<br/><span>左下</span></button>
            <button class="numpad-btn" @click="handleNumber(8)">8<br/><span>下移</span></button>
            <button class="numpad-btn" @click="handleNumber(9)">9<br/><span>右下</span></button>
          </div>
          <div class="numpad-row">
            <button class="numpad-btn zero" @click="handleNumber(0)">0<br/><span>重置/暂停</span></button>
          </div>
        </div>
        
        <div class="controls-legend">
          <h4>操作说明</h4>
          <div class="legend-grid">
            <span>1 - 左上斜移</span>
            <span>2 - 向上移动</span>
            <span>3 - 右上斜移</span>
            <span>4 - 向左移动</span>
            <span>5 - 发射子弹</span>
            <span>6 - 向右移动</span>
            <span>7 - 左下斜移</span>
            <span>8 - 向下移动</span>
            <span>9 - 右下斜移</span>
            <span>0 - 重置/暂停</span>
          </div>
        </div>
      </div>
      
      <div v-if="gameOver" class="game-over">
        <h2>{{ isWin ? '胜利！' : '游戏结束' }}</h2>
        <p>最终得分: {{ score }}</p>
        <el-button type="primary" @click="resetGame">再来一局</el-button>
      </div>
      
      <div v-if="gameStarted && !gameOver && isPaused" class="pause-overlay">
        <h2>游戏暂停</h2>
        <p>按0键或点击继续</p>
      </div>
    </div>
    
    <div class="tank-footer">
      <p>© 2026 坦克大战 - 手机号版</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElButton } from 'element-plus'

const router = useRouter()

const MAP_WIDTH = 26
const MAP_HEIGHT = 26
const TILE_EMPTY = 0
const TILE_BRICK = 1
const TILE_STEEL = 2
const TILE_WATER = 3
const TILE_GRASS = 4
const TILE_BASE = 5
const TILE_BASE_DESTROYED = 6

const DIR_UP = 0
const DIR_DOWN = 1
const DIR_LEFT = 2
const DIR_RIGHT = 3

const gameMap = ref<number[][]>([])
const player = ref({ x: 8, y: 24, dir: DIR_UP, alive: true })
const enemies = ref<any[]>([])
const playerBullets = ref<any[]>([])
const enemyBullets = ref<any[]>([])

const gameStarted = ref(false)
const gameOver = ref(false)
const isWin = ref(false)
const isPaused = ref(false)
const score = ref(0)
const lives = ref(3)
const currentLevel = ref(1)
const remainingEnemies = ref(20)
const phoneInput = ref('')

let gameLoopId: number | null = null
let lastTime = 0
let enemySpawnTimer = 0

const baseMap = [
  [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
  [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
  [0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0],
  [0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0],
  [0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0],
  [0,0,1,1,0,0,1,1,0,0,1,1,2,2,1,1,0,0,1,1,0,0,1,1,0,0],
  [0,0,1,1,0,0,1,1,0,0,1,1,2,2,1,1,0,0,1,1,0,0,1,1,0,0],
  [0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0],
  [0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0],
  [0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0],
  [0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0],
  [1,1,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,1,1],
  [2,2,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,2,2],
  [0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0],
  [0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0],
  [0,0,1,1,0,0,1,1,0,0,1,1,1,1,1,1,0,0,1,1,0,0,1,1,0,0],
  [0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0],
  [0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0],
  [0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0],
  [0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0],
  [0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0],
  [0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0],
  [0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0],
  [0,0,0,0,0,0,0,0,1,1,1,1,0,0,1,1,1,1,0,0,0,0,0,0,0,0],
  [0,0,0,0,0,0,0,0,1,1,5,5,5,5,5,5,1,1,0,0,0,0,0,0,0,0],
  [0,0,0,0,0,0,0,0,1,1,5,5,5,5,5,5,1,1,0,0,0,0,0,0,0,0],
]

const initGame = () => {
  gameMap.value = baseMap.map(row => [...row])
  player.value = { x: 8, y: 24, dir: DIR_UP, alive: true }
  enemies.value = []
  playerBullets.value = []
  enemyBullets.value = []
  score.value = 0
  lives.value = 3
  remainingEnemies.value = 20
  currentLevel.value = 1
  enemySpawnTimer = 0
}

const spawnEnemy = () => {
  if (enemies.value.length >= 4 || remainingEnemies.value <= 0) return
  
  const spawnPoints = [
    { x: 0, y: 0 },
    { x: 12, y: 0 },
    { x: 24, y: 0 }
  ]
  
  for (const point of spawnPoints) {
    const occupied = enemies.value.some(e => 
      Math.abs(e.x - point.x) < 2 && Math.abs(e.y - point.y) < 2
    )
    if (!occupied) {
      enemies.value.push({
        x: point.x,
        y: point.y,
        dir: DIR_DOWN,
        type: Math.floor(Math.random() * 3),
        moveTimer: 0,
        shootTimer: 0,
        changeDirTimer: 0
      })
      remainingEnemies.value--
      break
    }
  }
}

const canMove = (x: number, y: number, size: number = 2) => {
  if (x < 0 || y < 0 || x + size > MAP_WIDTH || y + size > MAP_HEIGHT) return false
  
  for (let dy = 0; dy < size; dy++) {
    for (let dx = 0; dx < size; dx++) {
      const cell = gameMap.value[y + dy]?.[x + dx]
      if (cell === TILE_BRICK || cell === TILE_STEEL || cell === TILE_WATER || cell === TILE_BASE) {
        return false
      }
    }
  }
  return true
}

const checkTankCollision = (x: number, y: number, excludeSelf: any = null) => {
  if (excludeSelf !== player.value && Math.abs(player.value.x - x) < 2 && Math.abs(player.value.y - y) < 2) {
    return true
  }
  
  for (const enemy of enemies.value) {
    if (enemy !== excludeSelf && Math.abs(enemy.x - x) < 2 && Math.abs(enemy.y - y) < 2) {
      return true
    }
  }
  return false
}

const movePlayer = (dir: number) => {
  if (!player.value.alive || isPaused.value) return
  
  player.value.dir = dir
  let newX = player.value.x
  let newY = player.value.y
  
  switch (dir) {
    case DIR_UP: newY--; break
    case DIR_DOWN: newY++; break
    case DIR_LEFT: newX--; break
    case DIR_RIGHT: newX++; break
  }
  
  if (canMove(newX, newY) && !checkTankCollision(newX, newY, player.value)) {
    player.value.x = newX
    player.value.y = newY
  }
}

const movePlayerDiagonal = (dx: number, dy: number) => {
  if (!player.value.alive || isPaused.value) return
  
  const newX = player.value.x + dx
  const newY = player.value.y + dy
  
  if (canMove(newX, newY) && !checkTankCollision(newX, newY, player.value)) {
    player.value.x = newX
    player.value.y = newY
    if (dx < 0) player.value.dir = DIR_LEFT
    else if (dx > 0) player.value.dir = DIR_RIGHT
    if (dy < 0) player.value.dir = DIR_UP
    else if (dy > 0) player.value.dir = DIR_DOWN
  }
}

const playerShoot = () => {
  if (!player.value.alive || isPaused.value) return
  if (playerBullets.value.length >= 2) return
  
  let bx = player.value.x
  let by = player.value.y
  
  switch (player.value.dir) {
    case DIR_UP: bx++; by--; break
    case DIR_DOWN: bx++; by += 2; break
    case DIR_LEFT: by++; break
    case DIR_RIGHT: bx += 2; by++; break
  }
  
  playerBullets.value.push({ x: bx, y: by, dir: player.value.dir })
}

const updateEnemies = (deltaTime: number) => {
  for (const enemy of enemies.value) {
    enemy.moveTimer += deltaTime
    enemy.shootTimer += deltaTime
    enemy.changeDirTimer += deltaTime
    
    if (enemy.changeDirTimer > 2000 + Math.random() * 2000) {
      enemy.dir = Math.floor(Math.random() * 4)
      enemy.changeDirTimer = 0
    }
    
    if (enemy.moveTimer > 200) {
      let newX = enemy.x
      let newY = enemy.y
      
      switch (enemy.dir) {
        case DIR_UP: newY--; break
        case DIR_DOWN: newY++; break
        case DIR_LEFT: newX--; break
        case DIR_RIGHT: newX++; break
      }
      
      if (canMove(newX, newY) && !checkTankCollision(newX, newY, enemy)) {
        enemy.x = newX
        enemy.y = newY
      } else {
        enemy.dir = Math.floor(Math.random() * 4)
      }
      enemy.moveTimer = 0
    }
    
    if (enemy.shootTimer > 1000 + Math.random() * 1500) {
      let bx = enemy.x
      let by = enemy.y
      
      switch (enemy.dir) {
        case DIR_UP: bx++; by--; break
        case DIR_DOWN: bx++; by += 2; break
        case DIR_LEFT: by++; break
        case DIR_RIGHT: bx += 2; by++; break
      }
      
      enemyBullets.value.push({ x: bx, y: by, dir: enemy.dir })
      enemy.shootTimer = 0
    }
  }
}

const updateBullets = () => {
  const moveBullet = (bullet: any) => {
    switch (bullet.dir) {
      case DIR_UP: bullet.y--; break
      case DIR_DOWN: bullet.y++; break
      case DIR_LEFT: bullet.x--; break
      case DIR_RIGHT: bullet.x++; break
    }
  }
  
  playerBullets.value = playerBullets.value.filter(bullet => {
    moveBullet(bullet)
    
    if (bullet.x < 0 || bullet.x >= MAP_WIDTH || bullet.y < 0 || bullet.y >= MAP_HEIGHT) {
      return false
    }
    
    const cell = gameMap.value[bullet.y]?.[bullet.x]
    if (cell === TILE_BRICK) {
      gameMap.value[bullet.y][bullet.x] = TILE_EMPTY
      return false
    }
    if (cell === TILE_STEEL) {
      return false
    }
    if (cell === TILE_BASE || cell === TILE_BASE_DESTROYED) {
      gameMap.value[bullet.y][bullet.x] = TILE_BASE_DESTROYED
      endGame(false)
      return false
    }
    
    for (let i = enemies.value.length - 1; i >= 0; i--) {
      const enemy = enemies.value[i]
      if (bullet.x >= enemy.x && bullet.x < enemy.x + 2 && bullet.y >= enemy.y && bullet.y < enemy.y + 2) {
        enemies.value.splice(i, 1)
        score.value += 100
        if (enemies.value.length === 0 && remainingEnemies.value === 0) {
          endGame(true)
        }
        return false
      }
    }
    
    return true
  })
  
  enemyBullets.value = enemyBullets.value.filter(bullet => {
    moveBullet(bullet)
    
    if (bullet.x < 0 || bullet.x >= MAP_WIDTH || bullet.y < 0 || bullet.y >= MAP_HEIGHT) {
      return false
    }
    
    const cell = gameMap.value[bullet.y]?.[bullet.x]
    if (cell === TILE_BRICK) {
      gameMap.value[bullet.y][bullet.x] = TILE_EMPTY
      return false
    }
    if (cell === TILE_STEEL) {
      return false
    }
    if (cell === TILE_BASE || cell === TILE_BASE_DESTROYED) {
      gameMap.value[bullet.y][bullet.x] = TILE_BASE_DESTROYED
      endGame(false)
      return false
    }
    
    if (bullet.x >= player.value.x && bullet.x < player.value.x + 2 && 
        bullet.y >= player.value.y && bullet.y < player.value.y + 2) {
      lives.value--
      if (lives.value <= 0) {
        endGame(false)
      } else {
        player.value = { x: 8, y: 24, dir: DIR_UP, alive: true }
      }
      return false
    }
    
    return true
  })
}

const gameLoop = (timestamp: number) => {
  if (!gameStarted.value || gameOver.value) {
    gameLoopId = requestAnimationFrame(gameLoop)
    return
  }
  
  const deltaTime = timestamp - lastTime
  lastTime = timestamp
  
  if (!isPaused.value) {
    enemySpawnTimer += deltaTime
    if (enemySpawnTimer > 3000) {
      spawnEnemy()
      enemySpawnTimer = 0
    }
    
    updateEnemies(deltaTime)
    updateBullets()
  }
  
  gameLoopId = requestAnimationFrame(gameLoop)
}

const endGame = (win: boolean) => {
  gameOver.value = true
  isWin.value = win
}

const startGame = () => {
  initGame()
  gameStarted.value = true
  gameOver.value = false
  isPaused.value = false
  isPaused.value = false
  lastTime = performance.now()
  gameLoopId = requestAnimationFrame(gameLoop)
  spawnEnemy()
}

const togglePause = () => {
  isPaused.value = !isPaused.value
}

const resetGame = () => {
  if (gameLoopId) {
    cancelAnimationFrame(gameLoopId)
  }
  gameStarted.value = false
  gameOver.value = false
  initGame()
}

const goBack = () => {
  if (gameLoopId) {
    cancelAnimationFrame(gameLoopId)
  }
  router.push('/game-center')
}

const handleNumber = (num: number) => {
  phoneInput.value = num.toString()
  setTimeout(() => { phoneInput.value = '' }, 300)
  
  switch (num) {
    case 1:
      movePlayerDiagonal(-1, -1)
      break
    case 2:
      movePlayer(DIR_UP)
      break
    case 3:
      movePlayerDiagonal(1, -1)
      break
    case 4:
      movePlayer(DIR_LEFT)
      break
    case 5:
      playerShoot()
      break
    case 6:
      movePlayer(DIR_RIGHT)
      break
    case 7:
      movePlayerDiagonal(-1, 1)
      break
    case 8:
      movePlayer(DIR_DOWN)
      break
    case 9:
      movePlayerDiagonal(1, 1)
      break
    case 0:
      if (gameStarted.value && !gameOver.value) {
        togglePause()
      }
      if (gameOver.value) {
        resetGame()
      }
      break
  }
}

const getCellClass = (cell: number, x: number, y: number) => {
  const classes: string[] = []
  
  switch (cell) {
    case TILE_BRICK: classes.push('brick'); break
    case TILE_STEEL: classes.push('steel'); break
    case TILE_WATER: classes.push('water'); break
    case TILE_GRASS: classes.push('grass'); break
    case TILE_BASE: classes.push('base'); break
    case TILE_BASE_DESTROYED: classes.push('base-destroyed'); break
  }
  
  if (player.value.alive && x >= player.value.x && x < player.value.x + 2 && y >= player.value.y && y < player.value.y + 2) {
    classes.push('player')
    if (player.value.dir === DIR_UP) classes.push('player-up')
    else if (player.value.dir === DIR_DOWN) classes.push('player-down')
    else if (player.value.dir === DIR_LEFT) classes.push('player-left')
    else if (player.value.dir === DIR_RIGHT) classes.push('player-right')
  }
  
  for (const enemy of enemies.value) {
    if (x >= enemy.x && x < enemy.x + 2 && y >= enemy.y && y < enemy.y + 2) {
      classes.push('enemy')
      if (enemy.dir === DIR_UP) classes.push('enemy-up')
      else if (enemy.dir === DIR_DOWN) classes.push('enemy-down')
      else if (enemy.dir === DIR_LEFT) classes.push('enemy-left')
      else if (enemy.dir === DIR_RIGHT) classes.push('enemy-right')
    }
  }
  
  for (const bullet of playerBullets.value) {
    if (bullet.x === x && bullet.y === y) {
      classes.push('player-bullet')
    }
  }
  
  for (const bullet of enemyBullets.value) {
    if (bullet.x === x && bullet.y === y) {
      classes.push('enemy-bullet')
    }
  }
  
  return classes.join(' ')
}

const handleKeydown = (event: KeyboardEvent) => {
  if (!gameStarted.value || gameOver.value) return
  
  if (['1', '2', '3', '4', '5', '6', '7', '8', '9', '0'].includes(event.key)) {
    event.preventDefault()
    const num = parseInt(event.key)
    handleNumber(num)
  }
}

onMounted(() => {
  initGame()
  window.addEventListener('keydown', handleKeydown)
})

onUnmounted(() => {
  window.removeEventListener('keydown', handleKeydown)
  if (gameLoopId) {
    cancelAnimationFrame(gameLoopId)
  }
})
</script>

<style scoped>
.tank-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: linear-gradient(135deg, #0f0f23 0%, #1a1a3e 50%, #2d1b4e 100%);
  padding: 20px;
  color: white;
}

.tank-header {
  text-align: center;
  margin-bottom: 20px;
  width: 100%;
  max-width: 1100px;
}

.tank-header h1 {
  font-size: 2.2rem;
  margin-bottom: 15px;
  font-weight: 700;
  background: linear-gradient(135deg, #00ff88 0%, #00ccff 100%);
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
  gap: 30px;
  align-items: flex-start;
  width: 100%;
  max-width: 1100px;
  position: relative;
  flex-wrap: wrap;
  justify-content: center;
}

.game-area {
  display: flex;
  gap: 20px;
  align-items: flex-start;
}

.game-board {
  border: 4px solid #333;
  background: #000;
  display: flex;
  flex-direction: column;
}

.map-row {
  display: flex;
}

.map-cell {
  width: 16px;
  height: 16px;
  background: #000;
}

.map-cell.brick {
  background: linear-gradient(135deg, #8B4513 0%, #A0522D 50%, #8B4513 100%);
  box-shadow: inset 0 0 2px rgba(0,0,0,0.5);
}

.map-cell.steel {
  background: linear-gradient(135deg, #708090 0%, #A9A9A9 50%, #708090 100%);
  box-shadow: inset 0 0 3px rgba(255,255,255,0.3);
}

.map-cell.water {
  background: linear-gradient(135deg, #1e90ff 0%, #4169e1 100%);
  animation: water 0.5s infinite;
}

@keyframes water {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.7; }
}

.map-cell.grass {
  background: #228B22;
  position: relative;
  z-index: 10;
}

.map-cell.base {
  background: linear-gradient(135deg, #FFD700 0%, #FFA500 100%);
  box-shadow: 0 0 5px #FFD700;
}

.map-cell.base-destroyed {
  background: #333;
}

.map-cell.player {
  background: #00ff88 !important;
  position: relative;
}

.map-cell.player-up::after,
.map-cell.player-down::after,
.map-cell.player-left::after,
.map-cell.player-right::after {
  content: '';
  position: absolute;
  background: #00ccff;
}

.map-cell.player-up::after { top: 0; left: 50%; transform: translateX(-50%); width: 4px; height: 6px; }
.map-cell.player-down::after { bottom: 0; left: 50%; transform: translateX(-50%); width: 4px; height: 6px; }
.map-cell.player-left::after { left: 0; top: 50%; transform: translateY(-50%); width: 6px; height: 4px; }
.map-cell.player-right::after { right: 0; top: 50%; transform: translateY(-50%); width: 6px; height: 4px; }

.map-cell.enemy {
  background: #ff4757 !important;
}

.map-cell.player-bullet {
  background: #fff !important;
  box-shadow: 0 0 4px #00ff88;
  border-radius: 50%;
}

.map-cell.enemy-bullet {
  background: #ff6b6b !important;
  box-shadow: 0 0 4px #ff4757;
  border-radius: 50%;
}

.game-info {
  display: flex;
  flex-direction: column;
  gap: 12px;
  min-width: 120px;
}

.info-item {
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(0, 255, 136, 0.3);
  border-radius: 8px;
  padding: 10px;
  text-align: center;
}

.info-item h3 {
  margin-bottom: 6px;
  font-size: 0.85rem;
  color: #00ff88;
}

.score, .level, .enemies {
  font-size: 1.3rem;
  font-weight: 700;
  color: #fff;
}

.lives {
  font-size: 1.2rem;
}

.life-icon {
  color: #ff4757;
  margin: 0 2px;
}

.life-icon.empty {
  opacity: 0.3;
}

.phone-input-section {
  display: flex;
  flex-direction: column;
  gap: 15px;
  align-items: center;
}

.phone-display {
  background: rgba(0, 0, 0, 0.6);
  border: 2px solid #00ff88;
  border-radius: 12px;
  padding: 12px 25px;
  text-align: center;
  min-width: 180px;
}

.display-label {
  font-size: 0.8rem;
  color: #888;
  margin-bottom: 5px;
}

.phone-input {
  font-size: 2.2rem;
  font-weight: 700;
  color: #00ff88;
  font-family: 'Courier New', monospace;
  min-height: 45px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.numpad {
  background: rgba(255, 255, 255, 0.03);
  border-radius: 16px;
  padding: 12px;
}

.numpad-row {
  display: flex;
  gap: 8px;
  margin-bottom: 8px;
  justify-content: center;
}

.numpad-row:last-child {
  margin-bottom: 0;
}

.numpad-btn {
  width: 65px;
  height: 65px;
  border: 2px solid rgba(0, 255, 136, 0.4);
  border-radius: 12px;
  background: linear-gradient(135deg, rgba(0, 255, 136, 0.15) 0%, rgba(0, 204, 255, 0.15) 100%);
  color: #fff;
  font-size: 1.2rem;
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
  background: linear-gradient(135deg, rgba(0, 255, 136, 0.3) 0%, rgba(0, 204, 255, 0.3) 100%);
  border-color: #00ff88;
  transform: scale(1.05);
}

.numpad-btn:active {
  transform: scale(0.95);
}

.numpad-btn.fire {
  border-color: rgba(255, 71, 87, 0.6);
  background: linear-gradient(135deg, rgba(255, 71, 87, 0.25) 0%, rgba(255, 107, 107, 0.25) 100%);
}

.numpad-btn.fire:hover {
  border-color: #ff4757;
}

.numpad-btn span {
  font-size: 0.65rem;
  opacity: 0.7;
  font-weight: 400;
}

.numpad-btn.zero {
  width: 211px;
}

.controls-legend {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  padding: 12px;
  max-width: 260px;
}

.controls-legend h4 {
  margin: 0 0 10px 0;
  color: #00ff88;
  font-size: 0.9rem;
  text-align: center;
}

.legend-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 6px;
  font-size: 0.75rem;
  color: #aaa;
}

.game-over, .pause-overlay {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, 0.95);
  border: 3px solid #00ff88;
  border-radius: 16px;
  padding: 35px;
  text-align: center;
  z-index: 100;
}

.game-over h2, .pause-overlay h2 {
  font-size: 1.8rem;
  margin-bottom: 12px;
  color: #00ff88;
}

.game-over p, .pause-overlay p {
  font-size: 1rem;
  margin-bottom: 10px;
  color: #fff;
}

.tank-footer {
  margin-top: 20px;
  color: rgba(255, 255, 255, 0.5);
  font-size: 0.75rem;
}
</style>
