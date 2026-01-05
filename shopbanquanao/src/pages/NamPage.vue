<script setup>
import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"
import api from "@/api"
import CategorySlider from '@/components/home/CategorySlider.vue';
import Homeview from '@/components/home/Homeview.vue';
import Homebanner from '@/components/home/Homebanner.vue';
import ProductList from '@/components/product/ProductList.vue';
const imgList=[
"/src/assets/image/Bannernam.png"
];
const products = ref([])
const router = useRouter()
const goCategory = (cat) => {
  router.push({
    path: "/category",
    query: {
      maLoai: cat.maLoai,
      doiTuong: cat.doiTuong,
      tenLoai: cat.tenLoai
    }
  })
}
const categories=[
{
    name:"Áo Khoác",image:"/src/assets/image/nam1.png",tenLoai:"Áo khoác",doiTuong:"Nam"},
    {name:"Áo giữ nhiệt",image:"/src/assets/image/nam2.png",maLoai:6,doiTuong:"Nam"},
    {name:"Áo chống nắng",image:"/src/assets/image/nam3.png",maLoai:4,doiTuong:"Nam"},
    {name:"Áo polo & thun",image:"/src/assets/image/nam4.png",tenLoai:"Áo",doiTuong:"Nam" },
    {name:"Áo điều hòa làm mát",image:"/src/assets/image/nam5.png"},
    {name:"Quần dài",image:"/src/assets/image/nam6.png",maLoai:11,doiTuong:"Nam"},
    {name:"Quần short",image:"/src/assets/image/nam7.png",maLoai:12,doiTuong:"Nam"},
    {name:"Đồ lót",image:"/src/assets/image/nam8.png",tenLoai:"Đồ lót",doiTuong:"Nam"
}

];
onMounted(async () => {
  try {
    const res = await api.get("/products/by-doi-tuong?doiTuong=Nam")
    products.value = res.data.result
  } catch (e) {
    console.error("Load products failed", e)
  }
})
</script>
<template>
  <!-- Banner -->
  <Homebanner :images="imgList" />

  <!-- Category -->
  <div class="mt-10">
    <CategorySlider
      :categories="categories"
      title="HOT NHẤT TẠI ĐÂY!"
      @select-category="goCategory"
    />
  </div>

  <!-- Tiêu đề sản phẩm -->
<div class="mt-16 text-center">
  <h2 class="text-2xl font-bold text-[#c92127] mb-12">
    MUA NGAY KẺO LỠ
  </h2>

  <!-- đường kẻ -->
  <div class="relative w-3/4 mx-auto">
    <!-- line xám -->
    <div class="h-[3px] bg-gray-200"></div>

    <!-- gạch đỏ ở giữa -->
    <div
      class="absolute left-1/2 -translate-x-1/2 -top-px
             w-24 h-1 bg-[#c92127]"
    ></div>
  </div>
</div>

  <!-- Product List -->
  <div class="max-w-[1200px] mx-auto px-4 mt-8">
    <ProductList :products="products" />
  </div>

    <div class="flex justify-center mt-12">
    <router-link
      :to="{ path: '/category', query: { doiTuong: 'Nam' } }"
    >
      <button
        class="px-12 py-3 bg-[#c92127] text-white font-semibold rounded-lg hover:opacity-90 transition"
      >
        Xem tất cả sản phẩm
      </button>
    </router-link>
  </div>

  <!-- Banner quảng cáo / Homeview -->
  <div class="space-y-12 mt-16">
    <Homeview image="/src/assets/image/b1nam.png" :to="{ path: '/category', query: { tenLoai:'Áo khoác',doiTuong:'Nam' } }" />
    <Homeview image="/src/assets/image/b2nam.png" :to="{ path: '/category', query: { maLoai:6,doiTuong:'Nam' } }" />
    <Homeview image="/src/assets/image/b3nam.png" :to="{ path: '/category', query: { maLoai:10,doiTuong:'Nam' } }" />
    <Homeview image="/src/assets/image/b4nam.png" :to="{ path: '/category', query: { tenLoai:'Đồ lót',doiTuong:'Nam' } }" />
  </div>
</template>