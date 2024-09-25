export default {
  title: 'finterest',
  subtitle: '(Vue+Spring)',
  menus: [
    {
      title: '학습자료',
      url: '/archive/ArchivePage',
      icon: 'fa-solid fa-book-open', // 책 펼친 모양의 아이콘
    },
    {
      title: '퀴즈',
      url: '/quiz/QuizPage',
      icon: 'fa-solid fa-question-circle', // 물음표 아이콘
    },
    {
      title: '모의투자',
      url: '/trade',
      icon: 'fa-solid fa-chart-line', // 상승 차트 아이콘
    },
  ],

  accoutMenus: {
    login: {
      url: '/auth/login',
      title: '로그인',
      icon: 'fa-solid fa-right-to-bracket',
    },
    join: {
      url: '/auth/join',
      title: '회원가입',
      icon: 'fa-solid fa-user-plus',
    },
    myLearning: {
      title: '나의학습',
      url: '/myLearning/MyLearningPage',
    },
    news: {
      title: '소식',
      url: '/notice/NoticePage',
    },
  },
};
