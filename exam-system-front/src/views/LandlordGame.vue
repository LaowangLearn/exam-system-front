<template>
  <div class="landlord-container">
    <div class="landlord-header">
      <h1>斗地主</h1>
      <div class="game-controls">
        <el-button type="primary" @click="startGame" :disabled="gameStarted && !gameOver">开始游戏</el-button>
        <el-button type="warning" @click="resetGame">重新开始</el-button>
        <el-button @click="goBack">返回游戏中心</el-button>
      </div>
    </div>
    
    <div class="game-content">
      <div class="game-board">
        <div class="player top">
          <div class="player-info">
            <span class="player-name">{{ players[0].isLandlord ? '👑 地主' : '🎴 农民' }}</span>
          </div>
          <div class="hand-cards">
            <div v-for="(card, index) in players[0].handCards" :key="index" class="card back-card">
              🂠
            </div>
          </div>
        </div>
        
        <div class="middle-area">
          <div class="di-cards">
            <div v-for="(card, index) in diCards" :key="index" class="card" :class="getCardClass(card)">
              <span class="card-value">{{ getCardDisplay(card) }}</span>
            </div>
          </div>
          <div class="play-area">
            <div v-if="playHistory.length > 0" class="last-play">
              <div class="player-name">{{ playHistory[playHistory.length - 1].playerName }}</div>
              <div class="play-cards">
                <div v-for="(card, index) in playHistory[playHistory.length - 1].cards" :key="index" class="card" :class="getCardClass(card)">
                  <span class="card-value">{{ getCardDisplay(card) }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <div class="player left">
          <div class="player-info">
            <span class="player-name">{{ players[1].isLandlord ? '👑 地主' : '🎴 农民' }}</span>
          </div>
          <div class="hand-cards">
            <div v-for="(card, index) in players[1].handCards" :key="index" class="card back-card">
              🂠
            </div>
          </div>
        </div>
        
        <div class="player bottom">
          <div class="player-info">
            <span class="player-name">{{ players[2].isLandlord ? '👑 地主' : '🎴 农民' }}</span>
          </div>
          <div class="hand-cards">
            <div 
              v-for="(card, index) in players[2].handCards" 
              :key="index" 
              class="card" 
              :class="{ 
                [getCardClass(card)]: true, 
                'selected': selectedCards.includes(index),
                'disabled': !canPlay
              }"
              @click="canPlay && toggleCard(index)"
            >
              <span class="card-value">{{ getCardDisplay(card) }}</span>
            </div>
          </div>
          <div class="player-actions">
            <el-button type="primary" @click="playSelectedCards" :disabled="!canPlay || selectedCards.length === 0">出牌</el-button>
            <el-button type="warning" @click="pass" :disabled="!canPlay">不出</el-button>
            <el-button @click="hint" :disabled="!canPlay">提示</el-button>
          </div>
        </div>
      </div>
      
      <div class="game-info">
        <div class="info-item">
          <h3>当前回合</h3>
          <div class="current-turn">{{ currentPlayerName }}</div>
        </div>
        
        <div class="info-item">
          <h3>玩家手牌</h3>
          <ul class="player-cards-count">
            <li>{{ players[0].name }}: {{ players[0].handCards.length }}</li>
            <li>{{ players[1].name }}: {{ players[1].handCards.length }}</li>
            <li>{{ players[2].name }}: {{ players[2].handCards.length }}</li>
          </ul>
        </div>
        
        <div class="info-item">
          <h3>地主牌</h3>
          <div class="di-cards-count">{{ diCards.length === 0 ? '未确定' : '已展示' }}</div>
        </div>
        
        <div class="controls-info">
          <h3>操作说明</h3>
          <ul>
            <li>点击手牌选择/取消选择</li>
            <li>选择后点击"出牌"按钮</li>
            <li>不想出牌点击"不出"</li>
            <li>点击"提示"获得建议</li>
          </ul>
        </div>
      </div>
    </div>
    
    <div v-if="gameOver" class="game-over">
      <h2>{{ winnerName }}</h2>
      <p>{{ winnerDesc }}</p>
      <div class="game-over-buttons">
        <el-button type="primary" @click="resetGame">再来一局</el-button>
      </div>
    </div>
    
    <div class="landlord-footer">
      <p>© 2026 斗地主</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElButton, ElMessage } from 'element-plus'

const router = useRouter()

interface Card {
  suit: string
  value: number
  sortValue: number
}

interface Player {
  name: string
  handCards: Card[]
  isLandlord: boolean
}

interface PlayRecord {
  playerName: string
  cards: Card[]
  type: string
}

const SUITS = ['♠', '♥', '♣', '♦']
const VALUES = ['3', '4', '5', '6', '7', '8', '9', '10', 'J', 'Q', 'K', 'A', '2']
const JOKERS = ['小王', '大王']

const deck = ref<Card[]>([])
const players = ref<Player[]>([
  { name: '玩家1', handCards: [], isLandlord: false },
  { name: '玩家2', handCards: [], isLandlord: false },
  { name: '我', handCards: [], isLandlord: false }
])

const diCards = ref<Card[]>([])
const currentPlayer = ref(0)
const lastPlay = ref<PlayRecord | null>(null)
const playHistory = ref<PlayRecord[]>([])
const selectedCards = ref<number[]>([])
const gameStarted = ref(false)
const gameOver = ref(false)
const canPlay = ref(false)

const currentPlayerName = computed(() => players.value[currentPlayer.value].name)
const winnerName = computed(() => {
  const winner = players.value.find(p => p.handCards.length === 0)
  if (winner) {
    if (winner.isLandlord) return '🎉 地主获胜！'
    return '🎉 农民获胜！'
  }
  return ''
})
const winnerDesc = computed(() => {
  const winner = players.value.find(p => p.handCards.length === 0)
  if (winner) {
    return `${winner.name} 率先出完所有牌！`
  }
  return ''
})

const createDeck = () => {
  const newDeck: Card[] = []
  
  for (const suit of SUITS) {
    for (let i = 0; i < VALUES.length; i++) {
      newDeck.push({
        suit,
        value: i,
        sortValue: i
      })
    }
  }
  
  newDeck.push({ suit: '🃏', value: 13, sortValue: 13 })
  newDeck.push({ suit: '🃏', value: 14, sortValue: 14 })
  
  return newDeck
}

const shuffle = (cards: Card[]) => {
  const shuffled = [...cards]
  for (let i = shuffled.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1))
    ;[shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]]
  }
  return shuffled
}

const sortCards = (cards: Card[]) => {
  return [...cards].sort((a, b) => b.sortValue - a.sortValue)
}

const getCardDisplay = (card: Card) => {
  if (card.value === 13) return '小王'
  if (card.value === 14) return '大王'
  return card.suit + VALUES[card.value]
}

const getCardClass = (card: Card) => {
  if (card.value === 13 || card.value === 14) return 'joker'
  if (card.suit === '♥' || card.suit === '♦') return 'red'
  return 'black'
}

const checkCardType = (cards: Card[]) => {
  const sortedCards = sortCards(cards)
  const values = sortedCards.map(c => c.sortValue)
  const uniqueValues = [...new Set(values)]
  
  if (cards.length === 0) return null
  
  if (cards.length === 1) return { type: 'single', value: values[0] }
  
  if (cards.length === 2 && uniqueValues.length === 1) {
    return { type: 'pair', value: values[0] }
  }
  
  if (cards.length === 2 && values.includes(13) && values.includes(14)) {
    return { type: 'rocket', value: 14 }
  }
  
  if (cards.length === 3 && uniqueValues.length === 1) {
    return { type: 'triple', value: values[0] }
  }
  
  if (cards.length === 4 && uniqueValues.length === 1) {
    return { type: 'bomb', value: values[0] }
  }
  
  if (cards.length === 4 && uniqueValues.length === 2) {
    const counts = values.reduce((acc, v) => {
      acc[v] = (acc[v] || 0) + 1
      return acc
    }, {} as Record<number, number>)
    const countValues = Object.values(counts)
    if (countValues.includes(3) && countValues.includes(1)) {
      const tripleValue = Object.keys(counts).find(k => counts[+k] === 3)
      return { type: 'tripleOne', value: +tripleValue! }
    }
  }
  
  if (cards.length === 5 && uniqueValues.length === 2) {
    const counts = values.reduce((acc, v) => {
      acc[v] = (acc[v] || 0) + 1
      return acc
    }, {} as Record<number, number>)
    const countValues = Object.values(counts)
    if (countValues.includes(3) && countValues.includes(2)) {
      const tripleValue = Object.keys(counts).find(k => counts[+k] === 3)
      return { type: 'tripleTwo', value: +tripleValue! }
    }
  }
  
  if (cards.length >= 5 && uniqueValues.length === cards.length) {
    const min = Math.min(...values)
    const max = Math.max(...values)
    if (max - min === cards.length - 1 && max < 12) {
      return { type: 'straight', value: max, length: cards.length }
    }
  }
  
  if (cards.length >= 6 && cards.length % 2 === 0 && uniqueValues.length === cards.length / 2) {
    const counts = values.reduce((acc, v) => {
      acc[v] = (acc[v] || 0) + 1
      return acc
    }, {} as Record<number, number>)
    if (Object.values(counts).every(c => c === 2)) {
      const pairValues = Object.keys(counts).map(Number).sort((a, b) => a - b)
      const min = Math.min(...pairValues)
      const max = Math.max(...pairValues)
      if (max - min === pairValues.length - 1 && max < 12) {
        return { type: 'doubleStraight', value: max, length: pairValues.length }
      }
    }
  }
  
  if (cards.length >= 6 && cards.length % 3 === 0 && uniqueValues.length === cards.length / 3) {
    const counts = values.reduce((acc, v) => {
      acc[v] = (acc[v] || 0) + 1
      return acc
    }, {} as Record<number, number>)
    if (Object.values(counts).every(c => c === 3)) {
      const tripleValues = Object.keys(counts).map(Number).sort((a, b) => a - b)
      const min = Math.min(...tripleValues)
      const max = Math.max(...tripleValues)
      if (max - min === tripleValues.length - 1 && max < 12) {
        return { type: 'plane', value: max, length: tripleValues.length }
      }
    }
  }
  
  return null
}

const canBeat = (currentCards: Card[], lastCards: Card[]) => {
  const currentType = checkCardType(currentCards)
  const lastType = checkCardType(lastCards)
  
  if (!currentType) return false
  if (!lastType) return true
  
  if (currentType.type === 'rocket') return true
  
  if (currentType.type === 'bomb' && lastType.type !== 'bomb' && lastType.type !== 'rocket') {
    return true
  }
  
  if (currentType.type === 'bomb' && lastType.type === 'bomb') {
    return currentType.value > lastType.value
  }
  
  if (currentType.type !== lastType.type) return false
  
  if (currentType.length !== undefined && lastType.length !== undefined && currentType.length !== lastType.length) {
    return false
  }
  
  return currentType.value > lastType.value
}

const toggleCard = (index: number) => {
  const idx = selectedCards.value.indexOf(index)
  if (idx > -1) {
    selectedCards.value.splice(idx, 1)
  } else {
    selectedCards.value.push(index)
  }
}

const getSelectedCards = () => {
  return selectedCards.value.map(i => players.value[2].handCards[i])
}

const playSelectedCards = () => {
  const cards = getSelectedCards()
  if (cards.length === 0) return
  
  const cardType = checkCardType(cards)
  if (!cardType) {
    ElMessage.warning('无效的牌型！')
    return
  }
  
  if (lastPlay.value && !canBeat(cards, lastPlay.value.cards)) {
    ElMessage.warning('不能压过上家的牌！')
    return
  }
  
  for (let i = selectedCards.value.length - 1; i >= 0; i--) {
    players.value[2].handCards.splice(selectedCards.value[i], 1)
  }
  
  playHistory.value.push({
    playerName: players.value[2].name,
    cards: [...cards],
    type: cardType.type
  })
  lastPlay.value = playHistory.value[playHistory.value.length - 1]
  
  selectedCards.value = []
  checkGameOver()
  
  if (!gameOver.value) {
    currentPlayer.value = (currentPlayer.value + 1) % 3
    setTimeout(aiPlay, 1000)
  }
}

const pass = () => {
  if (lastPlay.value && lastPlay.value.playerName === players.value[currentPlayer.value].name) {
    ElMessage.warning('你必须出牌！')
    return
  }
  
  ElMessage.info('不出')
  currentPlayer.value = (currentPlayer.value + 1) % 3
  
  const nextPlayerName = players.value[currentPlayer.value].name
  if (lastPlay.value && nextPlayerName === lastPlay.value.playerName) {
    lastPlay.value = null
  }
  
  setTimeout(aiPlay, 1000)
}

const hint = () => {
  const myCards = players.value[2].handCards
  
  if (!lastPlay.value) {
    const single = sortCards(myCards).slice(-1)
    selectedCards.value = single.map(c => players.value[2].handCards.indexOf(c))
    return
  }
  
  for (let i = 0; i < myCards.length; i++) {
    const single = [myCards[i]]
    if (canBeat(single, lastPlay.value.cards)) {
      selectedCards.value = [i]
      return
    }
  }
  
  ElMessage.info('没有找到合适的牌')
}

const aiPlay = () => {
  if (currentPlayer.value === 2) {
    canPlay.value = true
    return
  }
  canPlay.value = false
  
  const aiPlayer = players.value[currentPlayer.value]
  const aiCards = aiPlayer.handCards
  
  if (!lastPlay.value || lastPlay.value.playerName === aiPlayer.name) {
    const sortedCards = sortCards(aiCards)
    const playCards = [sortedCards[sortedCards.length - 1]]
    
    const cardType = checkCardType(playCards)!
    const indices = playCards.map(c => aiCards.indexOf(c)).filter(i => i !== -1)
    
    for (let i = indices.length - 1; i >= 0; i--) {
      aiCards.splice(indices[i], 1)
    }
    
    playHistory.value.push({
      playerName: aiPlayer.name,
      cards: [...playCards],
      type: cardType.type
    })
    lastPlay.value = playHistory.value[playHistory.value.length - 1]
    
    ElMessage.success(`${aiPlayer.name} 出牌`)
  } else {
    let found = false
    for (let i = 0; i < aiCards.length; i++) {
      const single = [aiCards[i]]
      if (canBeat(single, lastPlay.value.cards)) {
        const cardType = checkCardType(single)!
        aiCards.splice(i, 1)
        
        playHistory.value.push({
          playerName: aiPlayer.name,
          cards: [...single],
          type: cardType.type
        })
        lastPlay.value = playHistory.value[playHistory.value.length - 1]
        
        found = true
        ElMessage.success(`${aiPlayer.name} 出牌`)
        break
      }
    }
    
    if (!found) {
      ElMessage.info(`${aiPlayer.name} 不出`)
    }
  }
  
  checkGameOver()
  
  if (!gameOver.value) {
    const nextPlayer = (currentPlayer.value + 1) % 3
    if (lastPlay.value && players.value[nextPlayer].name === lastPlay.value.playerName) {
      lastPlay.value = null
    }
    currentPlayer.value = nextPlayer
    
    if (nextPlayer !== 2) {
      setTimeout(aiPlay, 1000)
    } else {
      canPlay.value = true
    }
  }
}

const checkGameOver = () => {
  for (const player of players.value) {
    if (player.handCards.length === 0) {
      gameOver.value = true
      gameStarted.value = false
      canPlay.value = false
      break
    }
  }
}

const startGame = () => {
  deck.value = createDeck()
  const shuffledDeck = shuffle(deck.value)
  
  for (const player of players.value) {
    player.handCards = []
    player.isLandlord = false
  }
  
  diCards.value = shuffledDeck.slice(-3)
  
  for (let i = 0; i < 3; i++) {
    players.value[i].handCards = sortCards(shuffledDeck.slice(i * 17, (i + 1) * 17))
  }
  
  const landlordIndex = Math.floor(Math.random() * 3)
  players.value[landlordIndex].isLandlord = true
  players.value[landlordIndex].handCards = sortCards([...players.value[landlordIndex].handCards, ...diCards.value])
  
  currentPlayer.value = landlordIndex
  lastPlay.value = null
  playHistory.value = []
  selectedCards.value = []
  gameStarted.value = true
  gameOver.value = false
  canPlay.value = false
  
  ElMessage.success(`${players.value[landlordIndex].name} 是地主！`)
  
  if (currentPlayer.value !== 2) {
    setTimeout(aiPlay, 1000)
  } else {
    canPlay.value = true
  }
}

const resetGame = () => {
  startGame()
}

const goBack = () => {
  router.push('/game-center')
}

onMounted(() => {
  deck.value = createDeck()
})
</script>

<style scoped>
:root {
  overflow: hidden;
}

body {
  overflow: hidden;
  margin: 0;
  padding: 0;
}

.landlord-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: linear-gradient(135deg, #1a472a 0%, #2d5a3d 50%, #1a472a 100%);
  padding: 10px;
  color: white;
  box-sizing: border-box;
  overflow: hidden;
}

.landlord-header {
  text-align: center;
  margin-bottom: 20px;
  width: 100%;
  max-width: 1200px;
}

.landlord-header h1 {
  font-size: 2.5rem;
  margin-bottom: 15px;
  font-weight: 700;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
  color: #ffd700;
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
  max-width: 1200px;
  position: relative;
  flex: 1;
  overflow: hidden;
}

.game-board {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  min-height: 600px;
  position: relative;
}

.player {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.player.top {
  position: absolute;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
}

.player.left {
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
}

.player.bottom {
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 100%;
}

.player-info {
  text-align: center;
}

.player-name {
  font-size: 1.2rem;
  font-weight: 600;
  color: #ffd700;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5);
}

.hand-cards {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: -20px;
}

.player.top .hand-cards,
.player.left .hand-cards {
  gap: -15px;
}

.card {
  width: 60px;
  height: 84px;
  background: white;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.1rem;
  font-weight: 600;
  box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.3);
  transition: all 0.2s ease;
  margin-left: -20px;
  cursor: pointer;
  position: relative;
  user-select: none;
}

.card:first-child {
  margin-left: 0;
}

.card:hover {
  transform: translateY(-10px);
  box-shadow: 4px 4px 12px rgba(0, 0, 0, 0.4);
  z-index: 10;
}

.card.selected {
  transform: translateY(-20px);
  box-shadow: 0 8px 20px rgba(255, 215, 0, 0.5);
  border: 3px solid #ffd700;
  z-index: 20;
}

.card.disabled {
  cursor: not-allowed;
  opacity: 0.7;
}

.card.red {
  color: #e74c3c;
}

.card.black {
  color: #2c3e50;
}

.card.joker {
  color: #9b59b6;
  font-size: 0.9rem;
}

.card.back-card {
  background: linear-gradient(135deg, #c0392b 0%, #96281b 100%);
  color: #ffd700;
  font-size: 1.5rem;
  cursor: default;
}

.card.back-card:hover {
  transform: none;
  box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.3);
}

.card-value {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2px;
}

.middle-area {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 30px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.di-cards {
  display: flex;
  gap: 10px;
}

.di-cards .card {
  margin-left: 0;
}

.play-area {
  min-height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.last-play {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  padding: 15px;
  background: rgba(0, 0, 0, 0.3);
  border-radius: 12px;
}

.last-play .player-name {
  font-size: 1rem;
  color: #ffd700;
}

.play-cards {
  display: flex;
  gap: -15px;
}

.play-cards .card {
  margin-left: -15px;
}

.play-cards .card:first-child {
  margin-left: 0;
}

.player-actions {
  display: flex;
  gap: 10px;
  margin-top: 20px;
  justify-content: center;
}

.game-info {
  display: flex;
  flex-direction: column;
  gap: 15px;
  min-width: 220px;
  flex-shrink: 0;
}

.info-item {
  background: rgba(0, 0, 0, 0.3);
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
  color: #ffd700;
}

.current-turn,
.di-cards-count {
  font-size: 1.3rem;
  font-weight: 600;
  color: #ffffff;
}

.player-cards-count {
  list-style: none;
  padding: 0;
  margin: 0;
  text-align: left;
}

.player-cards-count li {
  margin-bottom: 5px;
  font-size: 0.95rem;
}

.controls-info {
  background: rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 8px;
  padding: 12px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
}

.controls-info h3 {
  margin-bottom: 8px;
  font-size: 1.1rem;
  font-weight: 600;
  color: #ffd700;
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
}

.game-over {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, 0.95);
  border: 3px solid #ffd700;
  border-radius: 16px;
  padding: 40px;
  text-align: center;
  box-shadow: 0 20px 60px rgba(255, 215, 0, 0.4);
  z-index: 100;
}

.game-over h2 {
  font-size: 2.2rem;
  margin-bottom: 15px;
  color: #ffd700;
  font-weight: 700;
}

.game-over p {
  font-size: 1.2rem;
  margin-bottom: 20px;
  color: #f0f0f0;
}

.game-over-buttons {
  display: flex;
  gap: 10px;
  justify-content: center;
  flex-wrap: wrap;
}

.landlord-footer {
  margin-top: 20px;
  color: rgba(255, 255, 255, 0.7);
  font-size: 0.8rem;
}

@media (max-width: 1024px) {
  .game-content {
    flex-direction: column;
    align-items: center;
  }
  
  .game-info {
    width: 100%;
    max-width: 400px;
  }
  
  .game-board {
    min-height: 500px;
    width: 100%;
  }
  
  .card {
    width: 50px;
    height: 70px;
    font-size: 0.95rem;
  }
}

@media (max-width: 768px) {
  .landlord-header h1 {
    font-size: 2rem;
  }
  
  .game-board {
    min-height: 400px;
  }
  
  .card {
    width: 40px;
    height: 56px;
    font-size: 0.8rem;
  }
  
  .player.top .hand-cards,
  .player.left .hand-cards {
    gap: -10px;
  }
  
  .card {
    margin-left: -15px;
  }
  
  .game-info {
    min-width: 180px;
  }
}

@media (max-width: 480px) {
  .card {
    width: 32px;
    height: 45px;
    font-size: 0.7rem;
  }
  
  .card {
    margin-left: -12px;
  }
  
  .landlord-header h1 {
    font-size: 1.6rem;
  }
}
</style>
