<template>
  <div class="max-w-6xl mx-auto grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-5 gap-6">
    <ProductCard
      v-for="item in products"
      :key="item.maSp"
      :product="item"
    />
  </div>
</template>

<script setup>
import ProductCard from "./ProductCard.vue"

defineProps({
  products: {
    type: Array,
    required: true
  }
})
</script>
