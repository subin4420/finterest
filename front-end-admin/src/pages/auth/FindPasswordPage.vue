<template>
    <div class="find-password-page container d-flex flex-column align-items-center justify-content-center" style="min-height: 100vh;">
        <div class="w-100" style="max-width: 400px;">
            <h1 class="mb-3 fw-bold logo text-center" @click="goToHome">finterest</h1>
            <!-- <h2 class="mb-2 text-left">비밀번호 찾기</h2> -->
            <p class="mb-4 text-center text-muted">회원 정보를 입력하여 비밀번호를 재설정하세요.</p>
            <form @submit.prevent="findPassword">
                <!-- 아이디 입력 필드 -->
                <div class="mb-4">
                    <input type="text" class="form-control form-control-lg" placeholder="아이디" v-model="username" required @input="validateUsername" />
                    <small class="text-danger">{{ usernameError }}</small>
                </div>

                <!-- 이름 입력 필드 -->
                <div class="mb-4">
                    <input type="text" class="form-control form-control-lg" placeholder="이름" v-model="fullName" required @input="validateFullName" />
                    <small class="text-danger">{{ fullNameError }}</small>
                </div>

                <!-- 이메일 입력 필드 -->
                <div class="mb-4">
                    <input type="email" class="form-control form-control-lg" placeholder="이메일" v-model="email" required @input="validateEmail" />
                    <small class="text-danger">{{ emailError }}</small>
                </div>

                <!-- 에러 메시지 출력 -->
                <div v-if="error" class="text-danger mb-3">{{ error }}</div>
                
                <!-- 비밀번호 찾기 버튼 -->
                <button type="submit" class="btn btn-primary btn-lg w-100 mb-3" :disabled="isLoading || !isFormValid">
                    {{ isLoading ? '처리 중...' : '비밀번호 찾기' }}
                </button>

                <!-- 로그인으로 돌아가기 링크 -->
                <div class="text-center">
                    <router-link :to="{ name: 'login' }" class="text-primary no-underline">로그인으로 돌아가기</router-link>
                </div>
            </form>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import authApi from '@/api/authApi';

const router = useRouter();
const username = ref('');
const fullName = ref('');
const email = ref('');
const isLoading = ref(false);
const error = ref('');

const usernameError = ref('');
const fullNameError = ref('');
const emailError = ref('');

const validateUsername = () => {
    const usernameRegex = /^[a-zA-Z0-9]{4,20}$/;
    if (!usernameRegex.test(username.value)) {
        usernameError.value = '아이디는 4~20자의 영문자와 숫자 조합이어야 합니다.';
    } else {
        usernameError.value = '';
    }
};

const validateFullName = () => {
    const fullNameRegex = /^[가-힣a-zA-Z\s]{2,50}$/;
    if (!fullNameRegex.test(fullName.value)) {
        fullNameError.value = '이름은 2~50자의 한글 또는 영문자여야 합니다.';
    } else {
        fullNameError.value = '';
    }
};

const validateEmail = () => {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailRegex.test(email.value)) {
        emailError.value = '유효한 이메일 주소를 입력하세요.';
    } else {
        emailError.value = '';
    }
};

const isFormValid = computed(() => {
    return username.value && fullName.value && email.value && 
           !usernameError.value && !fullNameError.value && !emailError.value;
});

const findPassword = async () => {
    if (!isFormValid.value) return;

    isLoading.value = true;
    error.value = '';

    try {
        const response = await authApi.findPassword({ username: username.value, fullName: fullName.value, email: email.value });
        if (response.success) {
            router.push({ name: 'ChangePassword', params: { email: email.value } });
        } else {
            error.value = '일치하는 사용자 정보를 찾을 수 없습니다.';
        }
    } catch (err) {
        console.error('비밀번호 찾기 오류:', err);
        error.value = '비밀번호 찾기 중 오류가 발생했습니다.';
    } finally {
        isLoading.value = false;
    }
};

const goToHome = () => {
    router.push('/');
};
</script>

<style scoped>
.find-password-page {
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

h2 {
    font-size: 1.5rem;
    color: #333;
    text-align: left;
}

.text-muted {
    color: #6c757d;
    text-align: left;
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
    font-size: 0.9rem;
}
</style>