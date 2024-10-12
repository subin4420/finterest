<template>
  <div v-if="isVisible" class="modal-overlay" @click.self="closeModal">
    <div class="modal-content">
      <div class="modal-header">
        <button class="close-button" @click="closeModal">
          <i class="fas fa-times"></i>
        </button>
        <span class="learning-status" v-if="cardData.status" :class="['status', statusClass]">
          <i :class="statusIcon"></i> {{ statusText }}
        </span>
      </div>
      <div v-if="isYouTubeVideo" class="video-container">
        <iframe
          :src="'https://www.youtube.com/embed/' + videoId"
          frameborder="0"
          allow="autoplay; encrypted-media"
          allowfullscreen
        ></iframe>
      </div>
      <div v-else class="image-container">
        <img :src="imageUrl" alt="학습 자료 이미지" />
      </div>
      <div class="content-info">
        <h2>{{ cardData.title }}</h2>
        <p><i class="fas fa-folder"></i> {{ cardData.categoryName }}</p>
        <div v-html="cardData.content"></div>
      </div>
      <button 
        class="complete-button" 
        @click="markComplete" 
        :disabled="cardData.status === 'completed'"
        :class="{ 'completed': cardData.status === 'completed' }"
      >
        <i class="fas fa-check"></i> <span style="margin-left: 5px;">학습완료</span>
      </button>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import { IMAGE_PATHS } from '@/constants/imagePaths';
import { useArchiveStore } from '@/stores/archiveStore';

export default {
  name: 'VideoModal',
  props: {
    isVisible: Boolean,
    cardData: Object,
  },
  setup(props, { emit }) {
    const archiveStore = useArchiveStore();

    const isYouTubeVideo = computed(() => {
      return props.cardData.materialImg && props.cardData.materialImg.length === 11;
    });

    const videoId = computed(() => {
      return isYouTubeVideo.value ? props.cardData.materialImg : null;
    });

    const imageUrl = computed(() => {
      if (!isYouTubeVideo.value && props.cardData.materialImg) {
        return IMAGE_PATHS.ARCHIVE_IMG + props.cardData.materialImg;
      }
      return null;
    });

    const statusText = computed(() => {
      return props.cardData.status === 'completed' ? '완료된 학습' : 
             props.cardData.status === 'incomplete' ? '진행중인 학습' : 
             props.cardData.status || 'N/A';
    });

    const statusClass = computed(() => ({
      'status-completed': props.cardData.status === 'completed',
      'status-incomplete': props.cardData.status === 'incomplete'
    }));

    const statusIcon = computed(() => 
      props.cardData.status === 'completed' ? 'fas fa-check-circle' : 'fas fa-clock'
    );

    const closeModal = () => {
      emit('update:isVisible', false);
    };

    const markComplete = async () => {
      if (props.cardData.status !== 'completed') {
        try {
          await archiveStore.changeArchiveStatus(props.cardData.materialId, 'completed');
          props.cardData.status = 'completed'; // 로컬 상태 업데이트
          closeModal(); // 모달 닫기
        } catch (error) {
          console.error('Error updating archive status:', error);
        }
      }
    };

    onMounted(() => {
      if (!props.cardData || !props.cardData.materialId) {
        console.error('Material ID is undefined or cardData is missing.');
        return;
      }

      console.log('Modal mounted with cardData:', props.cardData);
      console.log('Material ID:', props.cardData.materialId);

      if (props.cardData.status == 'N/A' || !props.cardData.status) {
        props.cardData.status = null;
        console.log('Setting status to null.');
      }

      checkAndAddStatus();
    });

    const checkAndAddStatus = async () => {
      console.log('Checking status in checkAndAddStatus function:', props.cardData.status);
      if (!props.cardData.status || props.cardData.status === null) {
        try {
          console.log('Status is null, adding archive status.');
          await archiveStore.addArchiveStatus(props.cardData.materialId, 'incomplete');
          console.log('Successfully added archive status.');
          props.cardData.status = 'incomplete'; // 로컬 상태 업데이트
        } catch (error) {
          console.error('Error adding archive status:', error);
        }
      } else {
        console.log('Status is not null, skipping status addition.');
      }
    };

    return {
      isYouTubeVideo,
      videoId,
      imageUrl,
      closeModal,
      statusText,
      statusClass,
      statusIcon,
      markComplete,
    };
  },
};
</script>

<style scoped>
/* ArchiveModal.vue의 스타일을 그대로 가져와서 사용합니다 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background-color: white;
  padding: 30px;
  border-radius: 15px;
  max-width: 80%;
  max-height: 80%;
  overflow-y: auto;
  z-index: 1001;
  box-shadow: 0 5px 15px rgba(0,0,0,0.3);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.close-button {
  color: #333;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 24px;
  display: flex;
  align-items: center;
  transition: color 0.3s ease;
}

.close-button:hover {
  color: #00c4d1;
}

.learning-status {
  font-size: 16px;
  font-weight: bold;
  padding: 5px 10px;
  border-radius: 15px;
}

.status-completed {
  background-color: #00c4d1;
  color: white;
}

.status-incomplete {
  background-color: #f39c12;
  color: white;
}

.video-container, .image-container {
  margin-bottom: 20px;
}

.video-container {
  position: relative;
  padding-bottom: 56.25%; /* 16:9 비율 */
  height: 0;
  overflow: hidden;
}

.video-container iframe {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.image-container img {
  max-width: 100%;
  height: auto;
}

.content-info {
  margin-bottom: 20px;
}

.complete-button {
  background-color: #00c4d1;
  color: white;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
  border-radius: 20px;
  font-size: 16px;
  transition: background-color 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.complete-button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.complete-button.completed {
  background-color: #2ecc71;
}
</style>
