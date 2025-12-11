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
import { ref, onMounted } from 'vue';
import api from "@/api"; 
import ProductCard from './ProductCard.vue';

const products = ref([])

onMounted(async () => {
  try {
    const res = await api.get("/products")
    console.log("API RESPONSE:", res.data)

    // backend trả dạng:
    // { code: 1000, result: [ { maSp, tenSp, gia, urlImage } ] }
    products.value = res.data.result
     
  } catch (e) {
    console.error("Error loading products", e)
  }
})
</script>
