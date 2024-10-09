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
const emailError = ref('');
const passwordError = ref('');
const avatarError = ref('');

const disableSubmit = computed(() => !member.email || !member.fullName || !member.password);

const validateFullName = () => {
  const fullNameRegex = /^[가-힣a-zA-Z\s]{2,50}$/;
  if (!fullNameRegex.test(member.fullName)) {
    fullNameError.value = '이름은 2~50자의 한글 또는 영문자여야 합니다.';
    return false;
  }
  fullNameError.value = '';
  return true;
};

const validateEmail = () => {
  const emailRegex = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
  if (!emailRegex.test(member.email)) {
    emailError.value = '유효한 이메일 주소를 입력하세요.';
    return false;
  }
  emailError.value = '';
  return true;
};

const validatePassword = () => {
  if (member.password.length < 8) {
    passwordError.value = '비밀번호는 최소 8자 이상이어야 합니다.';
    return false;
  }

  let conditionsMet = 0;
  if (/[A-Z]/.test(member.password)) conditionsMet++;
  if (/[a-z]/.test(member.password)) conditionsMet++;
  if (/[0-9]/.test(member.password)) conditionsMet++;
  if (/[!@#$%^&*]/.test(member.password)) conditionsMet++;

  if (conditionsMet < 3) {
    passwordError.value = '비밀번호는 대문자, 소문자, 숫자, 특수문자(!@#$%^&*) 중 3가지 이상을 포함해야 합니다.';
    return false;
  }

  passwordError.value = '';
  return true;
};

const validateAvatar = (event) => {
  const file = event.target.files[0];
  if (file) {
    if (file.type !== 'image/png') {
      avatarError.value = 'PNG 파일만 업로드 가능합니다.';
      avatarFileName.value = '';
      event.target.value = ''; // 파일 선택 초기화
    } else {
      avatarError.value = '';
      member.avatar = file;
      avatarFileName.value = file.name; // 파일 이름 저장
    }
  } else {
    avatarError.value = '';
    member.avatar = null;
    avatarFileName.value = '';
  }
};

const validateForm = () => {
  return validateFullName() && validateEmail() && validatePassword();
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

const avatarFileName = ref('');

const triggerFileInput = () => {
  avatar.value.click();
};

const goToHome = () => {
  router.push({ name: 'home' });
};
</script>

<template>
  <div class="profile-page container d-flex flex-column align-items-center justify-content-center" style="min-height: 100vh;">
    <div class="w-100" style="max-width: 500px;">
      <h1 class="mb-3 fw-bold logo text-center" @click="goToHome">finterest</h1>
      <p class="mb-4 text-center text-muted">회원 정보를 수정하세요.</p>
      <form @submit.prevent="onSubmit">
        <div class="text-center mb-4">
          <img :src="avatarPath" class="avatar avatar-lg rounded-circle" alt="User Avatar" />
          <h4 class="mt-2">{{ member.username }}</h4>
        </div>

        <!-- 아바타 이미지 업로드 -->
        <div class="mb-4">
          <label for="avatar" class="form-label">프로필 이미지</label>
          <div class="input-group">
            <input type="text" class="form-control form-control-lg" :value="avatarFileName" placeholder="PNG 파일을 선택하세요" readonly />
            <button class="btn btn-outline-secondary" type="button" @click="triggerFileInput">파일 선택</button>
          </div>
          <input type="file" id="avatar" class="form-control form-control-lg" ref="avatar" accept="image/png" @change="validateAvatar" style="display: none;" />
          <small class="text-danger">{{ avatarError }}</small>
        </div>

        <!-- 이름 입력 필드 -->
        <div class="mb-4">
          <label for="fullName" class="form-label">이름</label>
          <input type="text" id="fullName" class="form-control form-control-lg" placeholder="이름을 입력하세요" v-model="member.fullName" @input="validateFullName" required />
          <small class="text-danger">{{ fullNameError }}</small>
        </div>

        <!-- 이메일 입력 필드 -->
        <div class="mb-4">
          <label for="email" class="form-label">이메일</label>
          <input type="email" id="email" class="form-control form-control-lg" placeholder="이메일을 입력하세요" v-model="member.email" @input="validateEmail" required />
          <small class="text-danger">{{ emailError }}</small>
        </div>

        <!-- 비밀번호 입력 필드 -->
        <div class="mb-4">
          <label for="password" class="form-label">비밀번호</label>
          <input type="password" id="password" class="form-control form-control-lg" placeholder="비밀번호를 입력하세요" v-model="member.password" @input="validatePassword" required />
          <small class="text-danger">{{ passwordError }}</small>
        </div>

        <div v-if="error" class="alert alert-danger" role="alert">
          {{ error }}
        </div>

        <!-- 정보 수정 버튼 -->
        <button type="submit" class="btn btn-primary btn-lg w-100 mb-3" :disabled="disableSubmit">
          정보 수정
        </button>

        <!-- 비밀번호 변경 페이지로 이동 -->
        <div class="text-center">
          <router-link :to="{ name: 'changepassword' }" class="text-primary no-underline">비밀번호를 변경하시겠습니까?</router-link>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
.profile-page {
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

.avatar {
  width: 100px;
  height: 100px;
  object-fit: cover;
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

.btn-outline-secondary {
  color: #6c757d;
  border-color: #6c757d;
}

.btn-outline-secondary:hover {
  background-color: #6c757d;
  color: white;
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
  font-size: 0.9rem;
}

.form-label {
  font-weight: bold;
  margin-bottom: 0.5rem;
}

.input-group .btn {
  padding: 0.5rem 1rem;
  font-size: 1.2rem;
}

.input-group .form-control {
  border-top-right-radius: 0;
  border-bottom-right-radius: 0;
}

.input-group .btn {
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
}
</style>