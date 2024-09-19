import axios from 'axios'

const api = axios.create({
  baseURL: 'https://api.example.com',  // API 기본 URL
  timeout: 5000,                       // 요청 제한 시간
  headers: { 'Content-Type': 'application/json' }
})
console.log("dd");

export default api
