<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import authApi from '@/api/authApi';

const router = useRouter();
const avatar = ref(null);
const avatarError = ref('');
const checkError = ref('');
const passwordError = ref('');  // 이 줄을 추가합니다.

const member = reactive({
  username: '',
  fullName: '',  // 추가된 부분
  email: '',
  password: '',
  password2: '',
  avatar: null,
});

const disableSubmit = ref(true);

// 유효성 검사 상태
const validationErrors = reactive({
  username: '',
  fullName: '',  // 추가
  email: '',
  password: ''
});

// 아이디 유효성 검사 (영어와 숫자만 가능, 길이 제한)
const validateUsername = () => {
  const usernameRegex = /^[a-zA-Z0-9]{4,20}$/;
  if (!usernameRegex.test(member.username)) {
    validationErrors.username = '아이디는 4~20자의 영문자와 숫자 조합이어야 합니다.';
    return false;
  }
  validationErrors.username = '';
  return true;
};

// 전체 이름 유효성 검사 (한글과 영어만 가능, 길이 제한)
const validateFullName = () => {
  const fullNameRegex = /^[가-힣a-zA-Z\s]{2,50}$/;
  if (!fullNameRegex.test(member.fullName)) {
    validationErrors.fullName = '이름은 2~50자의 한글 또는 영문자여야 합니다.';
    return false;
  }
  validationErrors.fullName = '';
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
const passwordConditions = reactive({
  length: false,
  uppercase: false,
  lowercase: false,
  number: false,
  special: false
});

const updatePasswordConditions = (password) => {
  passwordConditions.length = password.length >= 8;
  passwordConditions.uppercase = /[A-Z]/.test(password);
  passwordConditions.lowercase = /[a-z]/.test(password);
  passwordConditions.number = /[0-9]/.test(password);
  passwordConditions.special = /[!@#$%^&*]/.test(password);
};

const validatePassword = () => {
  updatePasswordConditions(member.password);
  
  if (member.password.length < 8) {
    passwordError.value = '비밀번호는 최소 8자 이상이어야 합니다.';
    return false;
  }

  let conditionsMet = Object.values(passwordConditions).filter(Boolean).length;

  if (conditionsMet < 3) {
    passwordError.value = '비밀번호는 대문자, 소문자, 숫자, 특수문자(!@#$%^&*) 중 3가지 이상을 포함해야 합니다.';
    return false;
  }

  passwordError.value = '';
  return true;
};

// 아바타 파일 이름을 저장할 ref 추가
const avatarFileName = ref('');

// 파일 입력 트리거 함수
const triggerFileInput = () => {
  avatar.value.click();
};

// 아바타 유효성 검사 함수 수정
const validateAvatar = (event) => {
  const file = event.target.files[0];
  if (file) {
    if (file.type !== 'image/png') {
      avatarError.value = 'PNG 파일만 업로드 가능합니다.';
      avatarFileName.value = '';
      avatar.value.value = ''; // 파일 선택 초기화
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

const checkUsernameResult = ref('');

const checkUsername = async () => {
  if (!validateUsername()) return;

  try {
    const isDuplicate = await authApi.checkUsername(member.username);
    disableSubmit.value = isDuplicate;
    checkUsernameResult.value = isDuplicate ? '이미 사용중인 ID입니다.' : '사용가능한 ID입니다.';
  } catch (error) {
    console.error('ID 중복 확인 중 오류 발생:', error);
    checkUsernameResult.value = 'ID 중복 확인 중 오류가 발생했습니다.';
  }
};

const join = async () => {
  // 입력 값 유효성 검사
  if (!validateUsername() || !validateFullName() || !validateEmail() || !validatePassword()) {
    return alert('입력값을 확인하세요.');
  }

  if (member.password !== member.password2) {
    return alert('비밀번호가 일치하지 않습니다.');
  }

  if (avatarError.value) {
    return alert('올바른 아바타 이미지를 선택하세요.');
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

const goToHome = () => {
  router.push({ name: 'home' });
};
</script>

<template>
  <div class="join-page container d-flex flex-column align-items-center justify-content-center" style="min-height: 100vh;">
    <div class="w-100" style="max-width: 500px;">
      <h1 class="mb-3 fw-bold logo text-center" @click="goToHome">finterest</h1>
      <p class="mb-4 text-center text-muted">새로운 계정을 만들어 finterest를 시작하세요.</p>
      <form @submit.prevent="join">
        <!-- 아이디 입력 필드 -->
        <div class="mb-4">
          <label for="username" class="form-label">아이디</label>
          <div class="input-group">
            <input type="text" id="username" class="form-control form-control-lg" placeholder="아이디를 입력하세요" v-model="member.username" @input="validateUsername" required />
            <button type="button" class="btn btn-outline-primary" @click="checkUsername">중복 확인</button>
          </div>
          <small class="text-danger">{{ validationErrors.username }}</small>
          <small :class="checkUsernameResult.includes('사용가능') ? 'text-success' : 'text-danger'">{{ checkUsernameResult }}</small>
        </div>

        <!-- 이름 입력 필드 -->
        <div class="mb-4">
          <label for="fullName" class="form-label">이름</label>
          <input type="text" id="fullName" class="form-control form-control-lg" placeholder="이름을 입력하세요" v-model="member.fullName" @input="validateFullName" required />
          <small class="text-danger">{{ validationErrors.fullName }}</small>
        </div>

        <!-- 이메일 입력 필드 -->
        <div class="mb-4">
          <label for="email" class="form-label">이메일</label>
          <input type="email" id="email" class="form-control form-control-lg" placeholder="이메일을 입력하세요" v-model="member.email" @input="validateEmail" required />
          <small class="text-danger">{{ validationErrors.email }}</small>
        </div>

        <!-- 비밀번호 입력 필드 -->
        <div class="mb-4">
          <label for="password" class="form-label">비밀번호</label>
          <input type="password" id="password" class="form-control form-control-lg" placeholder="비밀번호를 입력하세요" v-model="member.password" @input="validatePassword" required />
          <small class="text-danger">{{ passwordError }}</small>
        </div>

        <!-- 비밀번호 확인 입력 필드 -->
        <div class="mb-4">
          <label for="password2" class="form-label">비밀번호 확인</label>
          <input type="password" id="password2" class="form-control form-control-lg" placeholder="비밀번호를 다시 입력하세요" v-model="member.password2" required />
        </div>

        <!-- 아바타 이미지 업로드 -->
        <div class="mb-4">
          <label for="avatar" class="form-label">프로필 이미지</label>
          <div class="input-group">
            <input type="text" class="form-control form-control-lg" :value="avatarFileName" placeholder="PNG 파일을 선택하세요" readonly />
            <button class="btn btn-outline-secondary" type="button" @click="triggerFileInput">파일 선택</button>
          </div>
          <input type="file" id="avatar" class="form-control form-control-lg" ref="avatar" accept="image/png" @input="validateAvatar" style="display: none;" />
          <small class="text-danger">{{ avatarError }}</small>
        </div>

        <!-- 비밀번호 조건 표시 -->
        <div class="mb-4">
          <div class="password-conditions card">
            <div class="card-body">
              <h6 class="card-title mb-2">비밀번호 조건:</h6>
              <ul class="list-unstyled mb-2">
                <li :class="passwordConditions.length ? 'text-success' : 'text-muted'">
                  <i :class="passwordConditions.length ? 'fas fa-check-circle' : 'far fa-circle'"></i> 8자 이상
                </li>
                <li :class="passwordConditions.uppercase ? 'text-success' : 'text-muted'">
                  <i :class="passwordConditions.uppercase ? 'fas fa-check-circle' : 'far fa-circle'"></i> 대문자 포함
                </li>
                <li :class="passwordConditions.lowercase ? 'text-success' : 'text-muted'">
                  <i :class="passwordConditions.lowercase ? 'fas fa-check-circle' : 'far fa-circle'"></i> 소문자 포함
                </li>
                <li :class="passwordConditions.number ? 'text-success' : 'text-muted'">
                  <i :class="passwordConditions.number ? 'fas fa-check-circle' : 'far fa-circle'"></i> 숫자 포함
                </li>
                <li :class="passwordConditions.special ? 'text-success' : 'text-muted'">
                  <i :class="passwordConditions.special ? 'fas fa-check-circle' : 'far fa-circle'"></i> 특수문자(!@#$%^&*) 포함
                </li>
              </ul>
              <div :class="Object.values(passwordConditions).filter(Boolean).length >= 3 ? 'text-success' : 'text-muted'">
                <i :class="Object.values(passwordConditions).filter(Boolean).length >= 3 ? 'fas fa-check-circle' : 'far fa-circle'"></i>
                3개 이상의 조건 충족 ({{ Object.values(passwordConditions).filter(Boolean).length }}/3)
              </div>
            </div>
          </div>
        </div>

        <!-- 가입하기 버튼 -->
        <button type="submit" class="btn btn-primary btn-lg w-100 mb-3" :disabled="disableSubmit">가입하기</button>

        <!-- 로그인 페이지로 이동 -->
        <div class="text-center">
          <router-link :to="{ name: 'login' }" class="text-primary no-underline">이미 계정이 있으신가요? 로그인</router-link>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
.join-page {
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

.btn-outline-primary {
  color: #00C4D1;
  border-color: #00C4D1;
}

.btn-outline-primary:hover {
  background-color: #00C4D1;
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

.password-conditions.card {
  border: 1px solid #e0e0e0;
  border-radius: 0.5rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.password-conditions .card-body {
  padding: 1rem;
}

.password-conditions ul {
  margin-bottom: 0.5rem;
}

.password-conditions li {
  margin-bottom: 0.25rem;
}

.password-conditions i {
  margin-right: 5px;
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