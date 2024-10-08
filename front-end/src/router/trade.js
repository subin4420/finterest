import { isAuthenticated } from '@/util/guards';

export default [
  {
    path: '/trade/stocksimulator',
    name: 'simulatorstock',
    component: () => import('../pages/trade/StockSimulatorPage.vue'), // 시뮬레이터 주식 페이지
  },
  {
    path: '/trade/portfolio',
    name: 'portfolio',
    component: () => import('../pages/trade/PortfolioPage.vue'),
  },
  {
    path: '/trade/news',
    name: 'tradeNews',
    component: () => import('../pages/trade/TradeNewsPage.vue'), // 뉴스 페이지
  },
  {
    path: '/trade/settlement',
    name: 'settlement',
    component: () => import('../pages/trade/SettlementPage.vue'), // 결산 페이지
  },
  {
    path: '/trade/community',
    name: 'board/list',
    component: () => import('../pages/trade/BoardListPage.vue'), // 커뮤니티 페이지
  },
  {
    path: '/trade/community/:no',
    name: 'board/detail',
    component: () => import('../pages/trade/BoardDetailPage.vue'), // 게시글 상세보기 페이지 경로 추가
  },
  {
    path: '/trade/community/create',
    name: 'board/create',
    component: () => import('../pages/trade/BoardCreatePage.vue'), // BoardCreatePage 컴포넌트 연결
    beforeEnter: isAuthenticated,
  },
  {
    path: '/trade/community/update/:no',
    name: 'board/update',
    component: () => import('../pages/trade/BoardUpdatePage.vue'), // 게시글 상세보기 컴포넌트 연결
    beforeEnter: isAuthenticated,
  },
  {
    path: '/board/:id', // 게시글 상세보기 페이지 경로 추가
    name: 'boardDetail', // 라우트 이름
    component: () => import('../pages/trade/BoardDetailPage.vue'), // 게시글 상세보기 컴포넌트 연결
    props: true, // ID를 props로 전달
  },
  {
    path: '/trade/pointconversion',
    name: 'pointconversion',
    component: () => import('../pages/trade/PointConversionPage.vue'), // 포인트 전환 페이지
  },
  {
    path: '/trade/turnbasedsimulation',
    name: 'turnbasedsimulation',
    component: () => import('../pages/trade/TurnBasedSimulationPage.vue'), // 턴제 모의투자 페이지
  },
];
