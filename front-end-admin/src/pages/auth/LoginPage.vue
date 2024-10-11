<template>
  <div class="login-page container d-flex flex-column align-items-center justify-content-center" style="min-height: 100vh;">
    <h1 class="mb-5 fw-bold logo" @click="goToHome">finterest</h1>
    <form @submit.prevent="login" class="w-100" style="max-width: 400px;">
      
      <!-- 아이디 입력 필드 -->
      <div class="mb-4">
        <input type="text" class="form-control form-control-lg" placeholder="아이디" v-model="user.username" required />
      </div>

      <!-- 패스워드 입력 필드 -->
      <div class="mb-4">
        <input type="password" class="form-control form-control-lg" placeholder="패스워드" v-model="user.password" required />
      </div>

      <!-- 에러 메시지 출력 -->
      <div v-if="error" class="text-danger mb-3">{{ error }}</div>
      
      <!-- 로그인 버튼 -->
      <button type="submit" class="btn btn-primary btn-lg w-100 mb-3" :disabled="disableSubmit">로그인</button>

      <!-- 비밀번호 찾기와 회원가입 링크 -->
      <div class="d-flex justify-content-between">
        <router-link :to="{ name: 'findpassword' }" class="text-primary no-underline">비밀번호 찾기</router-link>
        <router-link :to="{ name: 'join' }" class="text-primary no-underline">회원가입</router-link>
      </div>
    </form>
  </div>
</template>

<script setup>
import { computed, reactive, ref } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useRoute, useRouter } from 'vue-router';

const cr = useRoute();
const router = useRouter();
const auth = useAuthStore();

const user = reactive({
  username: '',
  password: '',
});

const error = ref(''); // 에러 메시지를 저장할 변수

const disableSubmit = computed(() => !(user.username && user.password));

// 로그인 함수
const login = async () => {
  console.log(user);
  try {
    await auth.login(user);
    if (cr.query.next) {
      router.push({ name: cr.query.next });
    } else {
      router.push('/');
    }
  } catch (e) {
    console.log('에러=======', e);
    error.value = e.response?.data?.message || '아이디 또는 비밀번호가 일치하지 않습니다.'; // 서버로부터 받은 에러 메시지를 사용
  }
};

// 홈으로 이동 함수
const goToHome = () => {
  router.push('/');
};
</script>

<style scoped>
.login-page {
  padding: 20px;
  background-color: #ffffff;
}

h1 {
  font-size: 2.5rem;
  color: #00C4D1;
}

.logo {
  cursor: pointer;
  transition: color 0.3s ease;
}

.logo:hover {
  color: #0098a8;
}

.form-control {
  border-radius: 10px;
  padding: 15px;
  font-size: 1.2rem;
}

.btn-primary {
  background-color: #00C4D1;
  border: none;
  transition: background-color 0.3s ease;
}

.btn-primary:hover {
  background-color: #0098a8;
}

.text-primary {
  color: #00C4D1;
  font-weight: bold;
}

.no-underline {
  text-decoration: none;
}

.no-underline:hover {
  text-decoration: underline;
}

.text-danger {
  color: red;
  font-weight: bold;
}
</style>
