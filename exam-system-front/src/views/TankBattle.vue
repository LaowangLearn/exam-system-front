<template>
  <div class="tank-container">
    <div class="tank-header">
      <h1>坦克大战</h1>
      <div class="game-controls">
        <el-button type="primary" @click="startGame" :disabled="gameStarted && !gameOver">开始游戏</el-button>
        <el-button type="warning" @click="togglePause" :disabled="!gameStarted || gameOver">{{ isPaused ? '继续' : '暂停' }}</el-button>
        <el-button type="danger" @click="resetGame">重新开始</el-button>
        <el-button @click="goBack">返回游戏中心</el-button>
      </div>
    </div>
    
    <div class="game-content">
      <div class="game-area">
        <div class="game-board" ref="gameBoard">
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
          
          <div class="controls-info">
            <h3>操作说明</h3>
            <ul>
              <li>W/↑ - 向上移动</li>
              <li>S/↓ - 向下移动</li>
              <li>A/← - 向左移动</li>
              <li>D/→ - 向右移动</li>
              <li>空格/J - 发射子弹</li>
            </ul>
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
        <p>按暂停按钮或P键继续</p>
      </div>
    </div>
    
    <div class="tank-footer">
      <p>© 2026 坦克大战</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { ElButton } from 'element-plus'

// 导入坦克大战图片资源
import playerUp from '@/assets/images/tank/pltankU.gif'
import playerDown from '@/assets/images/tank/pltankD.gif'
import playerLeft from '@/assets/images/tank/pltankL.gif'
import playerRight from '@/assets/images/tank/pltankR.gif'
import enemyUp from '@/assets/images/tank/pltankU.gif'
import enemyDown from '@/assets/images/tank/pltankD.gif'
import enemyLeft from '@/assets/images/tank/pltankL.gif'
import enemyRight from '@/assets/images/tank/pltankR.gif'
import brick from '@/assets/images/tank/brick.png'
import steel from '@/assets/images/tank/steel.png'
import water from '@/assets/images/tank/water.png'
import grass from '@/assets/images/tank/grass.png'
import base from '@/assets/images/tank/base.png'
import baseDestroyed from '@/assets/images/tank/base-destroyed.png'
import bullet from '@/assets/images/tank/bullet.png'
import enemyBullet from '@/assets/images/tank/enemy-bullet.png'

const router = useRouter()

const MAP_WIDTH = 26
const MAP_HEIGHT = 26
const CELL_SIZE = 20
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

let gameLoopId: number | null = null
let lastTime = 0
let enemySpawnTimer = 0
let moveKeysPressed: Record<string, boolean> = {}
let playerMoveTimer = 0
const PLAYER_MOVE_INTERVAL = 120

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
  player.value = { x: 4, y: 24, dir: DIR_UP, alive: true }
  enemies.value = []
  playerBullets.value = []
  enemyBullets.value = []
  score.value = 0
  lives.value = 3
  remainingEnemies.value = 20
  currentLevel.value = 1
  enemySpawnTimer = 0
  playerMoveTimer = 0
  moveKeysPressed = {}
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

const updatePlayerMovement = (deltaTime: number) => {
  if (!player.value.alive || isPaused.value) return
  
  playerMoveTimer += deltaTime
  if (playerMoveTimer < PLAYER_MOVE_INTERVAL) return
  playerMoveTimer = 0
  
  if (moveKeysPressed['w'] || moveKeysPressed['arrowup']) {
    movePlayer(DIR_UP)
  } else if (moveKeysPressed['s'] || moveKeysPressed['arrowdown']) {
    movePlayer(DIR_DOWN)
  } else if (moveKeysPressed['a'] || moveKeysPressed['arrowleft']) {
    movePlayer(DIR_LEFT)
  } else if (moveKeysPressed['d'] || moveKeysPressed['arrowright']) {
    movePlayer(DIR_RIGHT)
  }
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
        player.value = { x: 4, y: 24, dir: DIR_UP, alive: true }
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
    
    updatePlayerMovement(deltaTime)
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
  const key = event.key.toLowerCase()
  
  if (['w', 'arrowup', 's', 'arrowdown', 'a', 'arrowleft', 'd', 'arrowright', ' ', 'j', 'p'].includes(key)) {
    event.preventDefault()
  }
  
  if (!gameStarted.value || gameOver.value || isPaused.value) return
  
  const wasPressed = moveKeysPressed[key]
  moveKeysPressed[key] = true
  
  if (!wasPressed) {
    if (key === 'w' || key === 'arrowup') {
      movePlayer(DIR_UP)
    } else if (key === 's' || key === 'arrowdown') {
      movePlayer(DIR_DOWN)
    } else if (key === 'a' || key === 'arrowleft') {
      movePlayer(DIR_LEFT)
    } else if (key === 'd' || key === 'arrowright') {
      movePlayer(DIR_RIGHT)
    }
  }
  
  if (key === ' ' || key === 'j') {
    playerShoot()
  } else if (key === 'p') {
    togglePause()
  }
}

const handleKeyup = (event: KeyboardEvent) => {
  const key = event.key.toLowerCase()
  moveKeysPressed[key] = false
}

onMounted(() => {
  initGame()
  window.addEventListener('keydown', handleKeydown)
  window.addEventListener('keyup', handleKeyup)
})

onUnmounted(() => {
  window.removeEventListener('keydown', handleKeydown)
  window.removeEventListener('keyup', handleKeyup)
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
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 100%);
  padding: 20px;
  color: white;
}

.tank-header {
  text-align: center;
  margin-bottom: 20px;
  width: 100%;
  max-width: 900px;
}

.tank-header h1 {
  font-size: 2.5rem;
  margin-bottom: 15px;
  font-weight: 700;
  background: linear-gradient(135deg, #ffd700 0%, #ff8c00 100%);
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
  max-width: 900px;
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
  border: 4px solid #444;
  background: #000;
  display: flex;
  flex-direction: column;
}

.map-row {
  display: flex;
}

.map-cell {
  width: 18px;
  height: 18px;
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
  position: relative;
}

.map-cell.player-up {
  background: #FFD700 !important;
  background-image: url('@/assets/images/tank/pltankU.gif');
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
}

.map-cell.player-down {
  background: #FFD700 !important;
  background-image: url('@/assets/images/tank/pltankD.gif');
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
}

.map-cell.player-left {
  background: #FFD700 !important;
  background-image: url('@/assets/images/tank/pltankL.gif');
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
}

.map-cell.player-right {
  background: #FFD700 !important;
  background-image: url('@/assets/images/tank/pltankR.gif');
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
}

.map-cell.enemy {
  background: #e74c3c !important;
}

.map-cell.player-bullet {
  background: #fff !important;
  box-shadow: 0 0 4px #fff;
  border-radius: 50%;
}

.map-cell.enemy-bullet {
  background: #ff6b6b !important;
  box-shadow: 0 0 4px #ff6b6b;
  border-radius: 50%;
}

.game-info {
  display: flex;
  flex-direction: column;
  gap: 15px;
  min-width: 150px;
}

.info-item {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 215, 0, 0.3);
  border-radius: 8px;
  padding: 12px;
  text-align: center;
}

.info-item h3 {
  margin-bottom: 8px;
  font-size: 0.9rem;
  color: #FFD700;
}

.score, .level, .enemies {
  font-size: 1.5rem;
  font-weight: 700;
  color: #fff;
}

.lives {
  font-size: 1.3rem;
}

.life-icon {
  color: #e74c3c;
  margin: 0 2px;
}

.life-icon.empty {
  opacity: 0.3;
}

.controls-info {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 8px;
  padding: 12px;
  font-size: 0.8rem;
}

.controls-info h3 {
  color: #FFD700;
  margin-bottom: 8px;
  text-align: center;
}

.controls-info ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.controls-info li {
  margin-bottom: 4px;
  color: #aaa;
}

.game-over, .pause-overlay {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, 0.95);
  border: 3px solid #FFD700;
  border-radius: 16px;
  padding: 40px;
  text-align: center;
  z-index: 100;
}

.game-over h2, .pause-overlay h2 {
  font-size: 2rem;
  margin-bottom: 15px;
  color: #FFD700;
}

.game-over p, .pause-overlay p {
  font-size: 1rem;
  margin-bottom: 10px;
  color: #fff;
}

.tank-footer {
  margin-top: 20px;
  color: rgba(255, 255, 255, 0.6);
  font-size: 0.8rem;
}
</style>
