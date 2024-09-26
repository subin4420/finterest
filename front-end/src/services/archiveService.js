import api from './api';
export const getArchive = async () => {
  const response = await api.get('/api/archive');
  console.log("in service response.data: ", response.data); // 객체를 직접 출력
  // 또는
  console.log("in service response.data: " + JSON.stringify(response.data, null, 2)); // JSON 문자열로 출력
  return response.data;
};
export const postArchive = async () => {
  const response = await api.post('/api/archive');
  return response.data;
};
