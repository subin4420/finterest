import { defineStore } from 'pinia';
import {
  getUserBalance,
  getLatestConversionRate,
  getConversionTransactions,
  convertPointsToMoney,
  convertMoneyToPoints,
} from '../services/conversionService';

export const useConversionStore = defineStore('conversion', {
  state: () => ({
    conversionRate: null,
    userBalance: {
      money: 0,
      totalPoints: 0,
    },
    transactions: [],
    loading: false,
    error: null,
  }),
  actions: {
    async fetchConversionRate() {
      this.loading = true;
      this.error = null;
      try {
        const rate = await getLatestConversionRate();
        this.conversionRate = rate;
      } catch (error) {
        console.error('환전 비율 조회 중 오류 발생:', error);
        this.error = '환전 비율 정보를 가져오는데 실패했습니다.';
      } finally {
        this.loading = false;
      }
    },
    async fetchUserBalance() {
      this.loading = true;
      this.error = null;
      try {
        const balance = await getUserBalance();
        console.log('Fetched balance in store:', balance);
        this.userBalance = {
          money: Number(balance.money),
          totalPoints: Number(balance.totalPoints),
        };
        console.log('Updated userBalance in store:', this.userBalance);
      } catch (error) {
        console.error('사용자 잔액 조회 중 오류 발생:', error);
        this.error = '잔액 정보를 가져오는데 실패했습니다.';
      } finally {
        this.loading = false;
      }
    },
    async fetchTransactions() {
      this.loading = true;
      this.error = null;
      try {
        const transactions = await getConversionTransactions();
        this.transactions = transactions;
      } catch (error) {
        console.error('환전 내역 조회 중 오류 발생:', error);
        this.error = '환전 내역을 가져오는데 실패했습니다.';
      } finally {
        this.loading = false;
      }
    },
    async executePointsToMoney(points) {
      this.loading = true;
      this.error = null;
      try {
        console.log('포인트를 모의투자금으로 전환 시작:', points);
        const result = await convertPointsToMoney(points);
        console.log('전환 결과:', result);
        await this.fetchUserBalance();
        console.log('갱신된 잔액:', this.userBalance);
        await this.fetchTransactions();
        return result;
      } catch (error) {
        console.error('포인트를 모의투자금으로 전환 중 오류 발생:', error);
        this.error = '포인트 전환에 실패했습니다.';
        throw error;
      } finally {
        this.loading = false;
      }
    },

    async executeMoneyToPoints(money) {
      this.loading = true;
      this.error = null;
      try {
        const result = await convertMoneyToPoints(money);
        await this.fetchUserBalance();
        await this.fetchTransactions();
        return result;
      } catch (error) {
        console.error('모의투자금을 포인트로 전환 중 오류 발생:', error);
        this.error = '모의투자금 전환에 실패했습니다.';
        throw error;
      } finally {
        this.loading = false;
      }
    },
  },
});
