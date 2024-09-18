import api from './api';

export const getQuizList = async () => {
  const response = await api.get('/quizzes');
  return response.data;
};
