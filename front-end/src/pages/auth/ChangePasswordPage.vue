<script setup>
import { computed, reactive, ref } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useRouter } from 'vue-router';
import authApi from '@/api/authApi';

const router = useRouter();
const auth = useAuthStore();

const changePassword = reactive({
  username: auth.username,
  oldPassword: '',
  newPassword: '',
  newPassword2: '',
});

const disableSubmit = computed(() => !changePassword.oldPassword || !changePassword.newPassword || !changePassword.newPassword2);

const error = ref('');
const passwordError = ref('');

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
  updatePasswordConditions(changePassword.newPassword);
  
  if (changePassword.newPassword.length < 8) {
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

const inputPassword = () => {
  error.value = '';
  validatePassword();
};

const reset = () => {
  error.value = '';
  validatePassword();
};

const onSubmit = async () => {
  if (!validatePassword()) {
    return;
  }

  if (changePassword.newPassword != changePassword.newPassword2) {
    error.value = '새 비밀번호가 일치하지 않습니다.';
    return;
  }

  try {
    const passwordData = {
      username: changePassword.username,
      oldPassword: changePassword.oldPassword,
      newPassword: changePassword.newPassword
    };

    await authApi.changePassword(passwordData);
    alert('비밀번호를 수정했습니다.');
    router.push({ name: 'profile' });
  } catch (e) {
    error.value = e.response.data;
  }
};
</script>

<template>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card shadow">
          <div class="card-header text-white">
            <h2 class="mb-0"><i class="fa-solid fa-lock me-2"></i>비밀번호 변경</h2>
          </div>
          <div class="card-body">
            <form @submit.prevent="onSubmit">
              <div class="mb-3">
                <label for="oldPassword" class="form-label">
                  <i class="fa-solid fa-lock me-2"></i>이전 비밀번호
                </label>
                <input type="password" class="form-control" id="oldPassword" placeholder="이전 비밀번호" v-model="changePassword.oldPassword" @input="inputPassword" />
              </div>

              <div class="mb-3">
                <label for="newPassword" class="form-label">
                  <i class="fa-solid fa-lock me-2"></i>새 비밀번호
                </label>
                <input type="password" class="form-control" id="newPassword" placeholder="새 비밀번호" v-model="changePassword.newPassword" @input="reset" />
                <!-- <small class="text-danger">{{ passwordError }}</small> -->
              </div>

              <div class="mb-3">
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

              <div class="mb-3">
                <label for="newPassword2" class="form-label">
                  <i class="fa-solid fa-lock me-2"></i>새 비밀번호 확인
                </label>
                <input type="password" class="form-control" id="newPassword2" placeholder="새 비밀번호 확인" v-model="changePassword.newPassword2" @input="reset" />
              </div>

              <div v-if="error" class="alert alert-danger">{{ error }}</div>

              <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-primary" :disabled="disableSubmit">
                  <i class="fa-solid fa-check me-2"></i>확인
                </button>
                <router-link class="btn btn-outline-secondary" to="/auth/profile">
                  <i class="fa-solid fa-arrow-left me-2"></i>프로필로 돌아가기
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

.btn-outline-secondary {
  color: #6c757d;
  border-color: #6c757d;
}

.btn-outline-secondary:hover {
  background-color: #6c757d;
  color: white;
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
</style>