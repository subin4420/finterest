<script setup>
import { computed, reactive, ref } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useRoute, useRouter } from 'vue-router';

const cr = useRoute();
const router = useRouter();
const auth = useAuthStore();

  //////////////////////////////////////////////////////////
const member = reactive({
  username: '',
  password: '',
});

const error = ref('');

const disableSubmit = computed(() => !(member.username && member.password));

const login = async () => {
  console.log(member);
  try {
    await auth.login(member);
    if (cr.query.next) {
      router.push({ name: cr.query.next });
    } else {
      // 일반
      router.push('/');
    }
  } catch (e) {
    // 로그인 에러
    console.log('에러=======', e);
    error.value = e.response.data;
  }
};
  //////////////////////////////////////////////////////////
</script>

<template>
  <div class="mt-5 mx-auto" style="width: 500px">
    <h1 class="my-5">
      <i class="fa-solid fa-right-to-bracket"></i>
      로그인
    </h1>

    <form @submit.prevent="login">
      <div class="mb-3 mt-3">
        <label for="username" class="form-label">
          <i class="fa-solid fa-user"></i>
          사용자 ID:
        </label>
        <input type="text" class="form-control" placeholder="사용자 ID" v-model="member.username" required />
      </div>

      <div class="mb-3">
        <label for="password" class="form-label">
          <i class="fa-solid fa-lock"></i>
          비밀번호:
        </label>
        <input type="password" class="form-control" placeholder="비밀번호" v-model="member.password" required />
      </div>

      <div v-if="error" class="text-danger">{{ error }}</div>

      <button type="submit" class="btn btn-primary mt-4" :disabled="disableSubmit">
        <i class="fa-solid fa-right-to-bracket"></i>
        로그인
      </button>
    </form>

  </div>
</template>
