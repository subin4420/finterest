import api from './api';

export const getArchive = async (params = {}) => {
  const response = await api.get('/api/archive', { params }); // params를 쿼리로 전달
  console.log("in service response.data: ", response.data);
  return response.data;
};

export const postArchive = async () => {
  const response = await api.post('/api/archive');
  return response.data;
};
