<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import authApi from '@/api/authApi';

const router = useRouter();
const avatar = ref(null);
const checkError = ref('');

const member = reactive({
  username: 'hong',
  fullName: '',  // 추가된 부분
  email: 'hong@gmail.com',
  password: '12',
  password2: '12',
  avatar: null,
});

const disableSubmit = ref(true);

// 유효성 검사 상태
const validationErrors = reactive({
  username: '',
  email: '',
  password: ''
});

// 아이디 유효성 검사 (영어와 숫자만 가능)
const validateUsername = () => {
  const usernameRegex = /^[a-zA-Z0-9]+$/;
  if (!usernameRegex.test(member.username)) {
    validationErrors.username = '아이디는 영어와 숫자의 조합만 가능합니다.';
    return false;
  }
  validationErrors.username = '';
  return true;
};

// 이메일 유효성 검사
const validateEmail = () => {
  const emailRegex = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
  if (!emailRegex.test(member.email)) {
    validationErrors.email = '유효한 이메일 주소를 입력하세요.';
    return false;
  }
  validationErrors.email = '';
  return true;
};

// 비밀번호 유효성 검사
const validatePassword = () => {
  const passwordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\W).{8,}$/;
  if (!passwordRegex.test(member.password)) {
    validationErrors.password = '비밀번호는 최소 8자 이상, 대문자, 소문자, 숫자, 특수 문자를 포함해야 합니다.';
    return false;
  }
  validationErrors.password = '';
  return true;
};

const checkUsername = async () => {
  if (!validateUsername()) return;

  disableSubmit.value = await authApi.checkUsername(member.username);
  checkError.value = disableSubmit.value ? '이미 사용중인 ID입니다.' : '사용가능한 ID입니다.';
};

const join = async () => {
  // 입력 값 유효성 검사
  if (!validateUsername() || !validateEmail() || !validatePassword()) {
    return alert('입력값을 확인하세요.');
  }

  if (member.password !== member.password2) {
    return alert('비밀번호가 일치하지 않습니다.');
  }

  if (avatar.value.files.length > 0) {
    member.avatar = avatar.value.files[0];
  }

  try {
    await authApi.create(member);
    router.push({ name: 'home' });
  } catch (e) {
    console.error(e);
  }
};

const goBack = () => {
  router.go(-1); // 이전 페이지로 이동
};
</script>

<template>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card shadow-lg">
          <div class="card-header bg-primary text-white text-center py-3">
            <h2 class="mb-0">회원 가입</h2>
          </div>
          <div class="card-body p-5">
            <form @submit.prevent="join">
              <div class="mb-4">
                <label for="username" class="form-label">
                  <i class="fa-solid fa-user me-2"></i>사용자 ID
                </label>
                <div class="input-group">
                  <input type="text" class="form-control" placeholder="사용자 ID" id="username" @input="validateUsername" v-model="member.username" />
                  <button type="button" class="btn btn-outline-primary" @click="checkUsername">ID 중복 확인</button>
                </div>
                <small class="text-danger">{{ validationErrors.username }}</small>
              </div>

              <div class="mb-4">
                <label for="fullName" class="form-label">
                  <i class="fa-solid fa-user me-2"></i>이름
                </label>
                <input type="text" class="form-control" placeholder="전체 이름" id="fullName" v-model="member.fullName" />
              </div>

              <div class="mb-4">
                <label for="avatar" class="form-label">
                  <i class="fa-solid fa-user-astronaut me-2"></i>아바타 이미지
                </label>
                <input type="file" class="form-control" ref="avatar" id="avatar" accept="image/png, image/jpeg" />
              </div>

              <div class="mb-4">
                <label for="email" class="form-label">
                  <i class="fa-solid fa-envelope me-2"></i>이메일
                </label>
                <input type="email" class="form-control" placeholder="Email" id="email" @input="validateEmail" v-model="member.email" />
                <small class="text-danger">{{ validationErrors.email }}</small>
              </div>

              <div class="mb-4">
                <label for="password" class="form-label">
                  <i class="fa-solid fa-lock me-2"></i>비밀번호
                </label>
                <input type="password" class="form-control" placeholder="비밀번호" id="password" @input="validatePassword" v-model="member.password" />
                <small class="text-danger">{{ validationErrors.password }}</small>
              </div>

              <div class="mb-4">
                <label for="password2" class="form-label">
                  <i class="fa-solid fa-lock me-2"></i>비밀번호 확인
                </label>
                <input type="password" class="form-control" placeholder="비밀번호 확인" id="password2" v-model="member.password2" />
              </div>

              <div class="d-flex justify-content-between mt-4">
                <button type="button" class="btn btn-secondary btn-lg" @click="goBack">
                  <i class="fa-solid fa-arrow-left me-2"></i>뒤로가기
                </button>
                <button type="submit" class="btn btn-primary btn-lg" :disabled="disableSubmit">
                  <i class="fa-solid fa-user-plus me-2"></i>가입하기
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.card {
  border: none;
  border-radius: 1rem;
  overflow: hidden;
}

.card-header {
  background-color: #00C4D1;
}

.form-control:focus {
  border-color: #00C4D1;
  box-shadow: 0 0 0 0.2rem rgba(0, 196, 209, 0.25);
}

.btn-primary {
  background-color: #00C4D1;
  border-color: #00C4D1;
}

.btn-primary:hover, .btn-primary:focus {
  background-color: #00a8b3;
  border-color: #00a8b3;
}

.btn-outline-primary {
  color: #00C4D1;
  border-color: #00C4D1;
}

.btn-outline-primary:hover {
  background-color: #00C4D1;
  color: white;
}

.btn-secondary {
  background-color: #6c757d;
  border-color: #6c757d;
}

.btn-secondary:hover, .btn-secondary:focus {
  background-color: #5a6268;
  border-color: #545b62;
}
</style>
