<template>
  <div
    :class="['p-6 rounded-2xl flex justify-between items-center shadow-sm', bg]"
  >
    <div>
      <p class="text-sm text-gray-500">{{ title }}</p>
      <p class="text-2xl font-bold mt-1">{{ value }}</p>
      <p v-if="sub" class="text-xs text-green-600 mt-1">{{ sub }}</p>
    </div>

    <div
      :class="[
        'w-12 h-12 rounded-xl flex items-center justify-center text-xl',
        iconBg
      ]"
    >
      <!-- render icon component -->
      <component :is="icon" class="w-6 h-6 text-gray-700" />
    </div>
  </div>
</template>

<script setup>
defineProps({
  title: String,
  value: String,
  sub: String,
  icon: Object,  // ✅ đổi từ String sang Object
  bg: String,
  iconBg: String,
});
</script>
