<template>
    <div>
      <h2>Quiz Management</h2>
      <button @click="createQuiz">Create Quiz</button>
      <ul>
        <li v-for="quiz in quizzes" :key="quiz.quiz_id">
          {{ quiz.question }} - Correct Answer: {{ quiz.correct_choice }}
          <button @click="editQuiz(quiz)">Edit</button>
          <button @click="deleteQuiz(quiz.quiz_id)">Delete</button>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  import QuizService from '@/services/QuizService'
  
  export default {
    data() {
      return {
        quizzes: []
      }
    },
    created() {
      this.fetchQuizzes()
    },
    methods: {
      async fetchQuizzes() {
        const response = await QuizService.getQuizzes()
        this.quizzes = response.data
      },
      createQuiz() {
        this.$router.push('/quizzes/create')
      },
      editQuiz(quiz) {
        this.$router.push(`/quizzes/edit/${quiz.quiz_id}`)
      },
      async deleteQuiz(quizId) {
        await QuizService.deleteQuiz(quizId)
        this.fetchQuizzes()
      }
    }
  }
  </script>
  