import axios from 'axios';

const BASE_URL = '/api/conversion';

export const getLatestConversionRate = async () => {
  const response = await axios.get(`${BASE_URL}/rate/latest`);
  return response.data;
};

// 포인트를 모의투자금으로 전환하는 함수
export const convertPointsToMoney = async (pointAmount, username) => {
  try {
    const response = await axios.post(`${BASE_URL}/points-to-money`, {
      username,
      pointAmount: - Math.abs(pointAmount), // 음수로 전송
    });
    return response.data;
  } catch (error) {
    console.error('Error converting points to money:', error);
    throw error;
  }
};

// 모의투자금을 포인트로 전환하는 함수
export const convertMoneyToPoints = async (moneyAmount, username) => {
  try {
    const response = await axios.post(`${BASE_URL}/money-to-points`, {
      username,
      pointAmount: Math.abs(moneyAmount), // 양수로 전송
    });
    return response.data;
  } catch (error) {
    console.error('Error converting money to points:', error);
    throw error;
  }
};
