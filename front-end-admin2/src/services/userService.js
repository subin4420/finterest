import api from './api'

export const getUserInfo = async () => {
  const response = await api.get('/user/info');
  return response.data;
};
