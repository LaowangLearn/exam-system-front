<template>
  <div class="game-center-container">
    <div class="game-center-header">
      <h1>游戏中心</h1>
      <p>休闲娱乐，放松心情</p>
      <el-button type="primary" @click="goBack">返回系统中心</el-button>
    </div>
    
    <div class="game-content">
      <div class="game-card" @click="showTetrisOptions = true">
        <div class="game-icon">
          <svg width="80" height="80" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
            <path d="M8 12h8"></path>
            <path d="M12 8v8"></path>
          </svg>
        </div>
        <h3>俄罗斯方块</h3>
        <p>经典益智游戏，挑战你的反应能力</p>
        <div class="game-footer">
          <span class="play-btn">选择版本</span>
        </div>
      </div>
      
      <div class="game-card tank-card" @click="showTankOptions = true">
        <div class="game-icon">
          <svg width="80" height="80" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M4 12h4l1.5-6h5L16 12h4"></path>
            <path d="M4 12v4a2 2 0 002 2h12a2 2 0 002-2v-4"></path>
            <path d="M12 6v-2"></path>
            <path d="M9 4h6"></path>
            <circle cx="8" cy="18" r="2"></circle>
            <circle cx="16" cy="18" r="2"></circle>
          </svg>
        </div>
        <h3>坦克大战</h3>
        <p>经典射击游戏，保卫你的基地</p>
        <div class="game-footer tank-footer">
          <span class="play-btn">选择版本</span>
        </div>
      </div>
    </div>
    
    <div class="game-center-footer">
      <p>© 2026 游戏中心</p>
    </div>
    
    <el-dialog v-model="showTetrisOptions" title="选择俄罗斯方块版本" width="500px" class="tetris-dialog">
      <div class="version-options">
        <div class="version-card desktop" @click="goToTetrisDesktop">
          <div class="version-icon">
            <svg width="60" height="60" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <rect x="2" y="3" width="20" height="14" rx="2"></rect>
              <line x1="8" y1="21" x2="16" y2="21"></line>
              <line x1="12" y1="17" x2="12" y2="21"></line>
            </svg>
          </div>
          <h3>电脑版</h3>
          <p>使用键盘方向键操作</p>
          <span class="enter-btn">点击进入</span>
        </div>
        <div class="version-card mobile" @click="goToTetrisMobile">
          <div class="version-icon">
            <svg width="60" height="60" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <rect x="5" y="2" width="14" height="20" rx="2"></rect>
              <line x1="12" y1="18" x2="12" y2="18"></line>
            </svg>
          </div>
          <h3>手机号版</h3>
          <p>使用手机号输入控制</p>
          <span class="enter-btn">点击进入</span>
        </div>
      </div>
    </el-dialog>
    
    <el-dialog v-model="showTankOptions" title="选择坦克大战版本" width="500px" class="tank-dialog">
      <div class="version-options">
        <div class="version-card desktop-tank" @click="goToTankDesktop">
          <div class="version-icon">
            <svg width="60" height="60" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <rect x="2" y="3" width="20" height="14" rx="2"></rect>
              <line x1="8" y1="21" x2="16" y2="21"></line>
              <line x1="12" y1="17" x2="12" y2="21"></line>
            </svg>
          </div>
          <h3>电脑版</h3>
          <p>WASD移动，空格射击</p>
          <span class="enter-btn">点击进入</span>
        </div>
        <div class="version-card mobile-tank" @click="goToTankMobile">
          <div class="version-icon">
            <svg width="60" height="60" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <rect x="5" y="2" width="14" height="20" rx="2"></rect>
              <line x1="12" y1="18" x2="12" y2="18"></line>
            </svg>
          </div>
          <h3>手机号版</h3>
          <p>使用数字键盘控制</p>
          <span class="enter-btn">点击进入</span>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElButton } from 'element-plus'

const router = useRouter()

const showTetrisOptions = ref(false)
const showTankOptions = ref(false)

const goBack = () => {
  router.push('/')
}

const goToTetrisDesktop = () => {
  showTetrisOptions.value = false
  router.push('/game-center/tetris')
}

const goToTetrisMobile = () => {
  showTetrisOptions.value = false
  router.push('/game-center/tetris-mobile')
}

const goToTankDesktop = () => {
  showTankOptions.value = false
  router.push('/game-center/tank-battle')
}

const goToTankMobile = () => {
  showTankOptions.value = false
  router.push('/game-center/tank-battle-mobile')
}
</script>

<style scoped>
.game-center-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 20px;
}

.game-center-header {
  text-align: center;
  margin-bottom: 50px;
  color: #333;
}

.game-center-header button {
  margin-top: 20px;
}

.game-center-header h1 {
  font-size: 2.8rem;
  margin-bottom: 10px;
  font-weight: 700;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.game-center-header p {
  font-size: 1.3rem;
  opacity: 0.8;
}

.game-content {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 40px;
  max-width: 800px;
  width: 100%;
}

.game-card {
  background: white;
  border-radius: 20px;
  padding: 40px;
  text-align: center;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
  border: 2px solid transparent;
  background-clip: padding-box;
}

.game-card:hover {
  transform: translateY(-15px);
  box-shadow: 0 25px 50px rgba(0, 0, 0, 0.15);
  border-color: #667eea;
}

.game-icon {
  margin-bottom: 30px;
  color: #667eea;
  transition: all 0.3s ease;
}

.game-card:hover .game-icon {
  transform: scale(1.1);
}

.game-card h3 {
  font-size: 1.8rem;
  margin-bottom: 15px;
  color: #333;
  font-weight: 600;
}

.game-card p {
  color: #666;
  margin-bottom: 30px;
  font-size: 1.1rem;
  line-height: 1.5;
}

.game-footer {
  position: absolute;
  bottom: 0;
  right: 0;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 15px 30px;
  border-top-left-radius: 20px;
  font-size: 1.1rem;
  font-weight: 600;
  transition: all 0.3s ease;
}

.game-card:hover .game-footer {
  background: linear-gradient(135deg, #764ba2 0%, #667eea 100%);
  padding: 18px 35px;
}

.game-center-footer {
  margin-top: 50px;
  color: #666;
  opacity: 0.8;
  font-size: 1rem;
}

@media (max-width: 768px) {
  .game-center-header h1 {
    font-size: 2.2rem;
  }
  
  .game-content {
    grid-template-columns: 1fr;
    gap: 30px;
  }
  
  .game-card {
    padding: 30px;
  }
}

.tetris-dialog .el-dialog__body {
  padding: 20px;
}

.version-options {
  display: flex;
  gap: 20px;
  justify-content: center;
}

.version-card {
  flex: 1;
  max-width: 200px;
  padding: 30px 20px;
  border-radius: 16px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 2px solid transparent;
}

.version-card.desktop {
  background: linear-gradient(135deg, #e8f4fc 0%, #d1eefc 100%);
  color: #0284c7;
}

.version-card.desktop:hover {
  border-color: #0284c7;
  transform: translateY(-5px);
  box-shadow: 0 10px 30px rgba(2, 132, 199, 0.2);
}

.version-card.mobile {
  background: linear-gradient(135deg, #fef3e8 0%, #fde3c9 100%);
  color: #ea580c;
}

.version-card.mobile:hover {
  border-color: #ea580c;
  transform: translateY(-5px);
  box-shadow: 0 10px 30px rgba(234, 88, 12, 0.2);
}

.version-icon {
  margin-bottom: 15px;
}

.version-card h3 {
  font-size: 1.3rem;
  margin-bottom: 10px;
  font-weight: 600;
}

.version-card p {
  font-size: 0.9rem;
  opacity: 0.8;
  margin-bottom: 20px;
}

.version-card .enter-btn {
  display: inline-block;
  padding: 8px 20px;
  background: currentColor;
  color: white;
  border-radius: 20px;
  font-size: 0.9rem;
  font-weight: 500;
}

@media (max-width: 500px) {
  .version-options {
    flex-direction: column;
    align-items: center;
  }
  
  .version-card {
    max-width: 100%;
    width: 100%;
  }
}

.game-card.tank-card .game-icon {
  color: #e74c3c;
}

.game-card.tank-card:hover {
  border-color: #e74c3c;
}

.game-card.tank-card .game-footer {
  background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
}

.game-card.tank-card:hover .game-footer {
  background: linear-gradient(135deg, #c0392b 0%, #e74c3c 100%);
}

.version-card.desktop-tank {
  background: linear-gradient(135deg, #e8f8e8 0%, #c8e6c9 100%);
  color: #2e7d32;
}

.version-card.desktop-tank:hover {
  border-color: #2e7d32;
  transform: translateY(-5px);
  box-shadow: 0 10px 30px rgba(46, 125, 50, 0.2);
}

.version-card.mobile-tank {
  background: linear-gradient(135deg, #fff3e0 0%, #ffe0b2 100%);
  color: #e65100;
}

.version-card.mobile-tank:hover {
  border-color: #e65100;
  transform: translateY(-5px);
  box-shadow: 0 10px 30px rgba(230, 81, 0, 0.2);
}

.tank-dialog .el-dialog__body {
  padding: 20px;
}
</style>