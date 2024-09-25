export default [

    {
        path: '/trade',
        name: 'trade',
      component: () => import('../pages/trade/TradePage.vue'),
    },
    {
        path: '/trade/stocksimulator',
        name: 'stocksimulator',
        component: () => import('../pages/trade/StockSimulatorPage.vue'),
      },
  ];
  