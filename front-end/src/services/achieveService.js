import api from './api';

export const getAchieve = async () => {
  const response = await api.get('/api/achieve/all');
  return response.data;
};
export const postAchieve = async () => {
  const response = await api.post('/api/achieve');
  return response.data;
};
