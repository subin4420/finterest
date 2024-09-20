import api from './api';

export const getArchive = async () => {
  const response = await api.get('/archive/all');
  return response.data;
};
