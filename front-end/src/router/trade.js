export default [
    {
      path: '/trade/simulatorstock',
      name: 'simulatorstock',
      component: () => import('../pages/trade/SimulatorStockPage.vue'), // 시뮬레이터 주식 페이지
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
      name: 'community',
      component: () => import('../pages/trade/CommunityPage.vue'), // 커뮤니티 페이지
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