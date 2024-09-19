import { defineStore } from 'pinia'
import { getUserInfo } from '@/services/userService'

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: null,
    isAuthenticated: false,
  }),
  actions: {
    async fetchUser() {
      try {
        const data = await getUserInfo();
        this.userInfo = data;
        this.isAuthenticated = true;
      } catch (error) {
        console.error('Failed to fetch user info:', error);
      }
    },
    logout() {
      this.userInfo = null;
      this.isAuthenticated = false;
    }
  }
})
