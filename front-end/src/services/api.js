import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080',  // API 기본 URL
  timeout: 5000,                       // 요청 제한 시간
  headers: { 'Content-Type': 'application/json' }
})

export default api
