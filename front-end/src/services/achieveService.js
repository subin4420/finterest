import api from './api';

export const getAchieve = async () => {
  const response = await api.get('/api/achieve');
  return response.data;
};
