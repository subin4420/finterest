import api from './api';

export const getPortfolio = async () => {
  const response = await api.get('/trading/portfolio');
  return response.data;
};
