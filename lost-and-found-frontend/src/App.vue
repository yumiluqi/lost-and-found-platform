<script setup>
import { RouterView } from 'vue-router'
import NavBar from './components/NavBar.vue'
</script>

<template>
  <div class="app-container">
    <!-- 引入导航栏 -->
    <NavBar />
    <!-- 路由出口，你的页面会渲染在这里 -->
    <RouterView />
  </div>
</template>

<style>
/* 强制清除所有默认边距 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
body, html, #app, .app-container {
  width: 100%;
  min-height: 100vh;
  background-color: #f5f7fa;
}
</style>