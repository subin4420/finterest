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
      <div class="content-section">
        <div class="title-section">
          <h2>{{ cardData.title }}</h2>
          <p><i class="fas fa-folder"></i> {{ cardData.categoryName }}</p>
        </div>
        <div class="video-container">
          <iframe
            :src="'https://www.youtube.com/embed/' + cardData.materialImg"
            frameborder="0"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
            allowfullscreen
          ></iframe>
        </div>
        <div class="archive-content" v-html="cardData.content"></div>
      </div>
      <button 
        class="complete-button" 
        @click="markComplete" 
        :disabled="cardData.status === 'completed'"
        :class="{ 'completed': cardData.status === 'completed' }"
      >
        <i class="fas fa-check"></i> 학습완료
      </button>
    </div>
  </div>
</template>

<script>
import { useArchiveStore } from '@/stores/archiveStore';

export default {
  props: {
    isVisible: Boolean,
    cardData: {
      type: Object,
      required: true
    }
  },
  computed: {
    statusText() {
      return this.cardData.status === 'completed' ? '완료된 학습' : 
             this.cardData.status === 'incomplete' ? '진행중인 학습' : 
             this.cardData.status || 'N/A';
    },
    statusClass() {
      return {
        'status-completed': this.cardData.status === 'completed',
        'status-incomplete': this.cardData.status === 'incomplete'
      };
    },
    statusIcon() {
      return this.cardData.status === 'completed' ? 'fas fa-check-circle' : 'fas fa-clock';
    }
  },
  mounted() {
    if (!this.cardData || !this.cardData.materialId) {
      console.error('Material ID is undefined or cardData is missing.');
      return;
    }

    console.log('Video Modal mounted with cardData:', this.cardData);
    console.log('Material ID:', this.cardData.materialId);

    if (this.cardData.status == 'N/A' || !this.cardData.status) {
      this.cardData.status = null;
      console.log('Setting status to null.');
    }

    this.checkAndAddStatus();
  },
  methods: {
    async checkAndAddStatus() {
      console.log('Checking status in checkAndAddStatus function:', this.cardData.status);
      if (!this.cardData.status || this.cardData.status === null) {
        try {
          console.log('Status is null, adding archive status.');
          const archiveStore = useArchiveStore();
          await archiveStore.addArchiveStatus(this.cardData.materialId, 'incomplete');
          console.log('Successfully added archive status.');
          this.cardData.status = 'incomplete';
        } catch (error) {
          console.error('Error adding archive status:', error);
        }
      } else {
        console.log('Status is not null, skipping status addition.');
      }
    },
    closeModal() {
      this.$emit('close');
    },
    async markComplete() {
      if (this.cardData.status !== 'completed') {
        try {
          const archiveStore = useArchiveStore();
          await archiveStore.changeArchiveStatus(this.cardData.materialId, 'completed');
          this.cardData.status = 'completed';
          this.$emit('status-updated', this.cardData);
          this.closeModal();
        } catch (error) {
          console.error('Error updating archive status:', error);
        }
      }
    }
  }
}
</script>

<style scoped>
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

.title-section {
  margin-bottom: 20px;
}

.title-section h2 {
  margin: 0 0 10px 0;
  color: #2c3e50;
}

.title-section p {
  margin: 0;
  color: #7f8c8d;
}

.video-container {
  position: relative;
  padding-bottom: 56.25%; /* 16:9 비율 */
  height: 0;
  overflow: hidden;
  margin-bottom: 20px;
}

.video-container iframe {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.archive-content {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 10px;
  margin-bottom: 20px;
  overflow-wrap: break-word;
  word-wrap: break-word;
  word-break: break-word;
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

/* 반응형 스타일 */
@media (max-width: 768px) {
  .modal-content {
    max-width: 95%;
    padding: 15px;
  }

  .title-section h2 {
    font-size: 18px;
  }

  .title-section p {
    font-size: 14px;
  }

  .complete-button {
    font-size: 14px;
    padding: 8px 16px;
  }
}
</style>

