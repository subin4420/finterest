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
      event.target.value = ''; // 파일 선택 초기화
    } else {
      avatarError.value = '';
      member.avatar = file;
    }
  } else {
    avatarError.value = '';
    member.avatar = null;
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
</script>

<template>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card shadow">
          <div class="card-header text-white">
            <h2 class="mb-0"><i class="fa-solid fa-user-gear me-2"></i>회원 정보</h2>
          </div>
          <div class="card-body">
            <form @submit.prevent="onSubmit">
              <div class="text-center mb-4">
                <img :src="avatarPath" class="avatar avatar-lg rounded-circle" alt="User Avatar" />
                <h4 class="mt-2">{{ member.username }}</h4>
              </div>

              <div class="mb-4">
                <label for="avatar" class="form-label">
                  <i class="fa-solid fa-user-astronaut me-2"></i>아바타 이미지 (PNG 파일만 가능):
                </label>
                <input 
                  type="file" 
                  class="form-control" 
                  ref="avatar" 
                  id="avatar" 
                  accept="image/png" 
                  @change="validateAvatar" 
                />
                <small class="text-danger">{{ avatarError }}</small>
              </div>

              <div class="mb-4">
                <label for="fullName" class="form-label">
                  <i class="fa-solid fa-user me-2"></i>이름
                </label>
                <input type="text" class="form-control" placeholder="이름" id="fullName" v-model="member.fullName" @input="validateFullName" />
                <small class="text-danger">{{ fullNameError }}</small>
              </div>

              <div class="mb-4">
                <label for="email" class="form-label">
                  <i class="fa-solid fa-envelope me-2"></i>이메일
                </label>
                <input type="email" class="form-control" placeholder="Email" id="email" v-model="member.email" @input="validateEmail" />
                <small class="text-danger">{{ emailError }}</small>
              </div>

              <div class="mb-4">
                <label for="password" class="form-label">
                  <i class="fa-solid fa-lock me-2"></i>비밀번호
                </label>
                <input type="password" class="form-control" placeholder="비밀번호" id="password" v-model="member.password" @input="validatePassword" />
                <small class="text-danger">{{ passwordError }}</small>
              </div>

              <div v-if="error" class="alert alert-danger" role="alert">
                {{ error }}
              </div>

              <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-primary" :disabled="disableSubmit">
                  <i class="fa-solid fa-user-plus me-2"></i>정보 수정
                </button>
                <router-link class="btn btn-outline-primary" to="/auth/changepassword">
                  <i class="fa-solid fa-lock me-2"></i>비밀번호 변경
                </router-link>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.avatar {
  width: 100px;
  height: 100px;
  object-fit: cover;
}

.card {
  border: none;
  border-radius: 15px;
  overflow: hidden;
}

.card-header {
  background-color: #29CED9;
}

.form-control:focus {
  border-color: #00C4D1;
  box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}

.btn-primary {
  background-color: #00C4D1;
  border-color: #00C4D1;
}

.btn-primary:hover, .btn-primary:focus {
  background-color: #00C4D1;
  border-color: #00C4D1;
}

.btn-outline-primary {
  color: #00C4D1;
  border-color: #00C4D1;
}

.btn-outline-primary:hover {
  background-color: #00C4D1;
  color: white;
}
</style>