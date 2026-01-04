<template>
  <router-link
    :to="to"
    class="flex items-center gap-3 px-3 py-2 rounded-xl text-gray-600 hover:bg-gray-100 transition"
    active-class="bg-blue-50 text-blue-600 font-medium"
  >
    <div class="w-8 h-8 flex items-center justify-center bg-gray-100 rounded-lg">
      <component :is="icon" class="w-5 h-5 text-gray-600" />
    </div>
    <span>{{ label }}</span>
  </router-link>
</template>

<script setup>
defineProps({
  to: { type: String, required: true },
  label: { type: String, required: true },
  icon: { type: Object, required: true }
});
</script>
