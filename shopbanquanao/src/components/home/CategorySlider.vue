<template>
  <div class="w-full py-6 relative">
    <h2 class="text-center text-xl font-bold text-[#c92127] mb-6">
      {{ title }}
    </h2>

    <!-- Nút Prev -->
    <button
      @click="scrollLeft"
      class="absolute left-2/12 top-1/2 -translate-y-1/2 bg-white shadow rounded-full p-2 z-10"
    >
      <slot name="prev-icon">
        <!-- Mũi tên trái -->
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-5">
          <path fill-rule="evenodd"
            d="M11.78 5.22a.75.75 0 0 1 0 1.06L8.06 10l3.72 3.72a.75.75 0 1 1-1.06 1.06l-4.25-4.25a.75.75 0 0 1 0-1.06l4.25-4.25a.75.75 0 0 1 1.06 0Z"
            clip-rule="evenodd" />
        </svg>
      </slot>
    </button>

    <!-- Khung scroll -->
    <div
      ref="scrollContainer"
      class="overflow-x-auto no-scrollbar px-12 mx-auto snap-x snap-mandatory"
      :style="{ width: containerWidth + 'px' }"
    >
      <div class="grid grid-rows-2 grid-flow-col gap-x-10 gap-y-10">
        <div
          v-for="(item, index) in categories"
          :key="index"
          ref="itemRef"
          class="flex flex-col items-center w-[140px] snap-start"
        >
          <div class="w-32 h-32 rounded-full overflow-hidden bg-neutral-100">
            <img :src="item.image" class="w-full h-full object-cover" />
          </div>
          <p class="mt-3 text-center font-medium text-gray-700">
            {{ item.name }}
          </p>
        </div>
      </div>
    </div>

    <!-- Nút Next -->
    <button
      @click="scrollRight"
      class="absolute right-2/12 top-1/2 -translate-y-1/2 bg-white shadow rounded-full p-2 z-10"
    >
      <slot name="next-icon">
        <!-- Mũi tên phải -->
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-5">
          <path fill-rule="evenodd"
            d="M8.22 5.22a.75.75 0 0 1 1.06 0l4.25 4.25a.75.75 0 0 1 0 1.06l-4.25 4.25a.75.75 0 0 1-1.06-1.06L11.94 10 8.22 6.28a.75.75 0 0 1 0-1.06Z"
            clip-rule="evenodd" />
        </svg>
      </slot>
    </button>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";

defineProps({
  title: { type: String, default: "HÔM NAY MUA GÌ?" },
  categories: { type: Array, required: true }
});

const scrollContainer = ref(null);
const itemRef = ref([]);
const containerWidth = ref(0);

onMounted(() => {
  const item = itemRef.value[0].getBoundingClientRect();
  const itemWidth = item.width;
  const gap = 40;
  containerWidth.value = itemWidth * 5 + gap * 4;
});

const scrollLeft = () => {
  const item = itemRef.value[0].getBoundingClientRect();
  scrollContainer.value.scrollBy({
    left: -(item.width + 40),
    behavior: "smooth",
  });
};

const scrollRight = () => {
  const item = itemRef.value[0].getBoundingClientRect();
  scrollContainer.value.scrollBy({
    left: item.width + 40,
    behavior: "smooth",
  });
};
</script>

<style scoped>
.no-scrollbar::-webkit-scrollbar {
  display: none;
}
</style>
