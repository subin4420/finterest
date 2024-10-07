<script setup>
import { computed, reactive, ref, watch } from 'vue';
import { useAuthStore } from '@/stores/auth';
import authApi from '@/api/authApi';
import { useRouter } from 'vue-router';

const auth = useAuthStore();
const router = useRouter();

const avatar = ref(null);
const avatarTimestamp = ref(Date.now());

const avatarPath = computed(() => `/api/users/${auth.username}/avatar?t=${avatarTimestamp.value}`);

const member = reactive({
  username: auth.username,
  fullName: auth.fullName,
  email: auth.email,
  password: '',
  avatar: null,
});

const error = ref('');
const fullNameError = ref('');
const passwordError = ref('');

const disableSubmit = computed(() => !member.email || !member.fullName || !member.password);

const validateForm = () => {
  fullNameError.value = member.fullName ? '' : '이름을 입력해야 합니다.';
  passwordError.value = member.password ? '' : '비밀번호를 입력해야 합니다.';
  return !fullNameError.value && !passwordError.value;
};

const onSubmit = async () => {
  if (!validateForm()) return;

  if (avatar.value.files.length > 0) {
    member.avatar = avatar.value.files[0];
  }

  if (!confirm('수정하시겠습니까?')) return;

  try {
    await authApi.update(member);
    error.value = '';
    auth.changeProfile(member);
    avatarTimestamp.value = Date.now(); // 타임스탬프 업데이트
    alert('정보를 수정하였습니다.');
    router.push('/'); // 홈 화면으로 이동
  } catch (e) {
    console.log(e);
    error.value = e.response.data;
  }
};

// 아바타 변경 감지
watch(() => auth.avatarUpdated, () => {
  avatarTimestamp.value = Date.now();
});
</script>

<template>
  <div class="mt-5 mx-auto" style="width: 500px">
    <h1><i class="fa-solid fa-user-gear my-3"></i> 회원 정보</h1>

    <form @submit.prevent="onSubmit">
      <div class="mb-3 mt-3"><img :src="avatarPath" class="avatar avatar-lg me-4" /> {{ member.username }}</div>
      <div class="mb-3 mt-3">
        <label for="avatar" class="form-label">
          <i class="fa-solid fa-user-astronaut"></i>
          아바타 이미지:
        </label>
        <input type="file" class="form-control" ref="avatar" id="avatar" accept="image/png, image/jpeg" />
      </div>

      <div class="mb-3 mt-3">
        <label for="fullName" class="form-label">
          <i class="fa-solid fa-user"></i>
          이름
        </label>
        <input type="text" class="form-control" placeholder="이름" id="fullName" v-model="member.fullName" />
        <small class="text-danger" v-if="fullNameError">{{ fullNameError }}</small>
      </div>

      <div class="mb-3 mt-3">
        <label for="email" class="form-label">
          <i class="fa-solid fa-envelope"></i>
          email
        </label>
        <input type="email" class="form-control" placeholder="Email" id="email" v-model="member.email" />
      </div>

      <div class="mb-3">
        <label for="password" class="form-label">
          <i class="fa-solid fa-lock"></i>
          비밀번호:
        </label>
        <input type="password" class="form-control" placeholder="비밀번호" id="password" v-model="member.password" />
        <small class="text-danger" v-if="passwordError">{{ passwordError }}</small>
      </div>

      <div v-if="error" class="text-danger">{{ error }}</div>

      <div class="text-center">
        <button type="submit" class="btn btn-primary mt-4 me-3" :disabled="disableSubmit">
          <i class="fa-solid fa-user-plus"></i>
          확인
        </button>
        <router-link class="btn btn-primary mt-4" to="/auth/changepassword">
          <i class="fa-solid fa-lock"></i>
          비밀번호 변경
        </router-link>
      </div>
    </form>
  </div>
</template>
