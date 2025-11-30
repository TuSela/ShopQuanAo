<template>
  <div class="relative max-w-6xl mx-auto overflow-hidden">
    <!-- ẢNH -->
    <div
      class="flex transition-transform duration-500"
      :style="{ transform: `translateX(-${currentIndex * 100}%)` }"
    >
      <div
        v-for="(img, index) in images"
        :key="index"
        class="min-w-full h-[350px] md:h-[450px] lg:h-[500px]"
      >
        <img :src="img" alt="" class="w-full h-full object-cover" />
      </div>
    </div>

    <!-- NÚT TRÁI -->
    <button
      @click="prev"
      class="absolute top-1/2 left-4 -translate-y-1/2 bg-white/70 p-2 rounded-full shadow hover:bg-white"
    >
      ‹
    </button>

    <!-- NÚT PHẢI -->
    <button
      @click="next"
      class="absolute top-1/2 right-4 -translate-y-1/2 bg-white/70 p-2 rounded-full shadow hover:bg-white"
    >
      ›
    </button>

    <!-- DOTS -->
    <div class="absolute bottom-4 w-full flex justify-center gap-2">
      <div
        v-for="(img, index) in images"
        :key="index"
        class="w-3 h-3 rounded-full cursor-pointer transition-all"
        :class="currentIndex === index ? 'bg-white' : 'bg-white/40'"
        @click="goTo(index)"
      ></div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const props = defineProps({
  images: {
    type: Array,
    required: true
  }
});

const currentIndex = ref(0);

const next = () => {
  currentIndex.value =
    currentIndex.value === props.images.length - 1
      ? 0
      : currentIndex.value + 1;
};

const prev = () => {
  currentIndex.value =
    currentIndex.value === 0
      ? props.images.length - 1
      : currentIndex.value - 1;
};

const goTo = (i) => {
  currentIndex.value = i;
};
</script>
