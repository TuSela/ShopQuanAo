<template>
  <div class="max-w-6xl mx-auto grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-5 gap-6">
    <ProductCard
      v-for="item in products"
      :key="item.id"
      :product="item"
    />
  </div>
</template>

<script setup>
import ProductCard from './ProductCard.vue';

const products = [
  {
    id: 1,
    name: "Áo khoác lông cừu nhân tạo nam dáng suông",
    price: "259.000đ",
    image: "/src/assets/image/11.png"
  },
  {
    id: 2,
    name: "Áo khoác gió nữ đa năng 2 lớp",
    price: "339.000đ",
    image: "/src/assets/image/2.png"
  },
  {
    id: 3,
    name: "Áo giữ nhiệt nam WarmMax Extra cổ cao",
    price: "169.000đ",
    image: "/src/assets/image/3.png"
  },
  {
    id: 4,
    name: "Áo bra tàng hình, nâng đỡ êm ái",
    price: "179.000đ",
    image: "/src/assets/image/4.png"
  },
  {
    id: 5,
    name: "Áo giữ nhiệt nữ WarmMax cổ cao 3cm",
    price: "169.000đ",
    image: "/src/assets/image/5.png"
  },
  {
    id: 6,
    name: "Áo giữ nhiệt nữ WarmMax cổ cao 3cm",
    price: "169.000đ",
    image: "/src/assets/image/3.png"
  }
];
</script>