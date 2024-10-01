import axios from 'axios';

const BASE_URL = '/api/conversion';

export const getLatestConversionRate = async () => {
  const response = await axios.get(`${BASE_URL}/rate/latest`);
  return response.data;
};

export const convertPointsToMoney = async (pointAmount) => {
  const response = await axios.post(`${BASE_URL}/points-to-money`, {
    username: '사용자이름', // 실제 사용자 이름으로 대체
    pointAmount,
  });
  return response.data;
};

export const convertMoneyToPoints = async (moneyAmount) => {
  const response = await axios.post(`${BASE_URL}/money-to-points`, {
    username: '사용자이름', // 실제 사용자 이름으로 대체
    moneyAmount,
  });
  return response.data;
};
