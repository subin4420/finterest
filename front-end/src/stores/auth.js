import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const initState = {
  token: '',
  user: {
    username: '',
    email: '',
    roles: [],
  },
  avatarUpdated: 0,
};

export const useAuthStore = defineStore('auth', () => {
  const state = ref({ ...initState });

  const isLogin = computed(() => !!state.value.user.username);
  const username = computed(() => state.value.user.username);
  const email = computed(() => state.value.user.email);
  const avatarUpdated = computed(() => state.value.avatarUpdated);

  // state.value.user.username이 존재하면 그 값은 truthy이므로, 
  // ---> !!state.value.user.username은 true를 반환
  // 만약 state.value.user.username이 null, undefined, ''(빈 문자열)과 같이 falsy한 값이라면, 
  // --->!!state.value.user.username은 false를 반환
  // 따라서 computed 함수는 사용자가 로그인 상태인지 (username이 있는지) 여부를 계산하여 
  // isLogin이라는 계산된 속성에 할당

  const updateAvatar = () => {
    state.value.avatarUpdated = Date.now();
    localStorage.setItem('auth', JSON.stringify(state.value));
  };

  const load = () => {
    const auth = localStorage.getItem('auth');
    console.log(auth);
    if (auth != null) {
      state.value = JSON.parse(auth);
    }
  };

// localStorage에 저장된 사용자 인증 정보를 불러와서 state에 저장하는 역할
// localStorage에서 auth라는 항목을 가져와, 그 값이 존재하면 이를 파싱하여 state.value에 저장하는 함수
//  localStorage.getItem('auth'): localStorage에서 'auth'라는 키에 저장된 값을 가져온다.
//  if (auth != null): auth 값이 존재하면 (null이 아니면),
//  state.value = JSON.parse(auth);: auth 문자열을 JSON 객체로 변환한 후 state.value에 할당

  const login = async (loginData) => {
    try {
      // api 호출
      console.log("loginData", loginData);
      const { data } = await axios.post('/api/auth/login', loginData);
      state.value = { ...data };
      localStorage.setItem('auth', JSON.stringify(state.value));
      return true;
    } catch (error) {
      console.log("loginData", loginData);
      console.error('로그인 실패:', error);
      throw error;
    }
  };                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        

  const logout = () => {
    localStorage.clear();
    state.value = { ...initState };
  };

  const getToken = () => state.value.token;

  const changeProfile = (member) => {
    state.value.user.email = member.email;
    state.value.user.fullName = member.fullName; // fullName 업데이트 추가
    state.value.avatarUpdated += 1; // 아바타 업데이트 시 증가
    localStorage.setItem('auth', JSON.stringify(state.value));
  };

  load();

// 토큰을 가져오고, 사용자의 이메일을 업데이트하며, 초기 상태를 불러오는 기능을 수행
// getToken(): 현재 상태(state.value)에서 token 값을 반환합니다.
// changeProfile(member): 사용자의 이메일을 주어진 member.email로 변경하고, 변경된 상태를 localStorage에 저장합니다.
// load(): 페이지가 로드될 때 localStorage에서 저장된 인증 정보를 불러와 state에 설정

  return { 
    state, 
    username, 
    email, 
    isLogin, 
    changeProfile, 
    login, 
    logout, 
    getToken,
    avatarUpdated,
    updateAvatar
  };
});
