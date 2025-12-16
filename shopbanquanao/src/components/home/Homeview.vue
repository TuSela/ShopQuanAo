<template>
  <div class="relative max-w-7xl mx-auto">
    <router-link
      :to="to"
      class="block"
    >
      <img
        :src="image"
        class="w-full object-cover h-[520px]"
      />
    </router-link>
  </div>
</template>

<script setup>
defineProps({
  image: {
    type: String,
    required: true
  },
  to: {
    type: String,
    required: true
  }
});
</script>

<style scoped></style>
