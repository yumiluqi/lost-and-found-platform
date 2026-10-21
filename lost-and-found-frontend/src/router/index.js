import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'home', component: () => import('../views/HomeView.vue') },
    { path: '/login', name: 'login', component: () => import('../views/LoginView.vue') },
    { path: '/register', name: 'register', component: () => import('../views/RegisterView.vue') },
    { 
      path: '/post/:id', 
      name: 'postDetail', 
      component: () => import('../views/PostDetailView.vue') 
    },
    { 
      path: '/publish', 
      name: 'publish', 
      component: () => import('../views/PublishView.vue'),
      meta: { requiresAuth: true } // 需要登录
    },
    { 
      path: '/my-posts', 
      name: 'myPosts', 
      component: () => import('../views/MyPostsView.vue'),
      meta: { requiresAuth: true } // 需要登录
    }
  ],
})

// 路由全局前置守卫：控制权限
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.meta.requiresAuth && !userStore.token) {
    ElMessage.warning('请先登录后再操作！')
    next('/login')
  } else {
    next()
  }
})

export default router