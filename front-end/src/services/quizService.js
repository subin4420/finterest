import api from './api';

export const getQuizList = async () => {
  const response = await api.get('/quizzes');
  console.log("hello");
  return response.data;
};
