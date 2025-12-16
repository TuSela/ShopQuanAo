<template>
  <div class="group w-full cursor-pointer"
  @click="goToDetail"
  >
    <!-- IMAGE -->
    <div class="relative overflow-hidden rounded-md">
      <img
  :src="product.urlImage"
  :alt="product.tenSp"
  class="w-full h-auto object-cover transition-transform duration-300 group-hover:scale-105"
/>
    </div>
<p class="mt-3 text-sm font-medium text-gray-800">
  {{ product.tenSp }}
</p>

<p class="text-red-600 font-semibold text-sm mt-1">
  {{ product.gia.toLocaleString() }}đ
</p>
  </div>
</template>

<script setup>
  import { useRouter } from "vue-router";

const router = useRouter();
const goToDetail = () => {
  router.push(`/products/${props.product.maSp}`);
};
const props = defineProps({
  product: {
    type: Object,
    required: true
  }
});
</script>
