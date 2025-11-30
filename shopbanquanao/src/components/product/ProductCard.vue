<template>
  <div class="group w-full cursor-pointer">
    <!-- IMAGE -->
    <div class="relative overflow-hidden rounded-md">
      <img
        :src="product.image"
        :alt="product.name"
        class="w-full h-auto object-cover transition-transform duration-300 group-hover:scale-105"
      />
    </div>

    <!-- NAME -->
    <p class="mt-3 text-sm font-medium text-gray-800">
      {{ product.name }}
    </p>

    <!-- PRICE -->
    <p class="text-red-600 font-semibold text-sm mt-1">
      {{ product.price }}
    </p>
  </div>
</template>

<script setup>
defineProps({
  product: {
    type: Object,
    required: true
  }
});
</script>
