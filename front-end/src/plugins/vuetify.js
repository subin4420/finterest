// Vuetify 설정 파일 (vuetify.js)
import 'vuetify/styles'; // Vuetify 스타일 가져오기
import { createVuetify } from 'vuetify';
import { aliases, mdi } from 'vuetify/iconsets/mdi'; // 선택 사항: 아이콘 사용 시 필요

const vuetify = createVuetify({
  theme: {
    defaultTheme: 'light', // 기본 테마 설정
  },
  icons: {
    defaultSet: 'mdi', // 아이콘 세트 설정
    aliases,
    sets: {
      mdi,
    },
  },
});

export default vuetify;
