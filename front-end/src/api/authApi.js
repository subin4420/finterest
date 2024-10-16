import api from '@/api';

const BASE_URL = '/api/users'; // 변경됨: '/api/member'에서 '/api/users'로 수정
const headers = { 'Content-Type': 'multipart/form-data' };

export default {
//////////////  회원 정보（ａｕｔｈ） 조회   ///////////////////////

//   async getList(params) {
//       const { data } = await api.get(BASE_URL, { params });
//     console.log('AUTH GET LIST: ', data);
//     return data;
//   },

 ///////////////  회원 id중복 체크   ////////////////////////
  async checkUsername(username) {
    const { data } = await api.get(`${BASE_URL}/checkusername/${username}`);
    console.log('AUTH GET CHECKUSERNAME', data);
    return data;
  },

 ///////////////  회원 정보 조회（ｕｓｅｒｎａｍｅ ＝＝ ｉｄ） ////////////////////////
  async get(username) {
    const { data } = await api.get(`${BASE_URL}/${username}`);
    console.log('AUTH GET', data);
    return data;
  },

 ///////////////// 회원 가입 //////////////////////////
  async create(member) {
    const formData = new FormData();
    formData.append('username', member.username);
    formData.append('fullName', member.fullName);  // 추가된 부분
    formData.append('email', member.email);
    formData.append('password', member.password);

    if (member.avatar) {
      formData.append('avatar', member.avatar);
    }

     // --------> 회원 가입 정보 post 방식으로 전송  //////////////////////////
    const { data } = await api.post(`${BASE_URL}/join`, formData, headers); // 변경됨: '/join' 경로 추가

    console.log('AUTH POST (회원가입): ', data);
    return data;
  },

 /////////////// 회원 정보 수정 ///////////////////////////////
  
  async update(member) {
    const formData = new FormData();
    formData.append('username', member.username);
    formData.append('fullName', member.fullName);
    formData.append('password', member.password);
    formData.append('email', member.email);

    if (member.avatar) {
      formData.append('avatar', member.avatar);
    }

    const { data } = await api.put(`${BASE_URL}/${member.username}`, formData, headers);
    console.log('AUTH PUT (회원 수정): ', data);
    return data;
  },

 /////////////// 회원 탈퇴 ///////////////////////////////
  
  async delete(username) {
    const { data } = await api.delete(`${BASE_URL}/${username}`);
    console.log('AUTH DELETE (회원탈퇴): ', data);
    return data;
  },

 /////////////// 회원 비밀번호 변경 ///////////////////////////////

  async changePassword(changePasswordDTO) {
    const { data } = await api.put(`${BASE_URL}/${changePasswordDTO.username}/changepassword`, changePasswordDTO); // 변경됨: 비밀번호 변경 경로 추가
    console.log('AUTH PUT (비밀번호 변경): ', data);
    return data;
  },
};
