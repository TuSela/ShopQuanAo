<script setup>
import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"
import api from "@/api"
import CategorySlider from '@/components/home/CategorySlider.vue';
import Homeview from '@/components/home/Homeview.vue';
import Homebanner from '@/components/home/Homebanner.vue';
import ProductList from '@/components/product/ProductList.vue';
const imgList=[
"/src/assets/image/Bannernu.png"
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
    name:"Áo Khoác",image:"/src/assets/image/nu1.png",tenLoai:"Áo khoác",doiTuong:"Nữ"},
    {name:"Quần dài & short",image:"/src/assets/image/nu5.png",tenLoai:"Quần & đầm váy",doiTuong:"Nữ"},
    {name:"Áo chống nắng",image:"/src/assets/image/nu3.png",maLoai:26,doiTuong:"Nữ"},
    {name:"Đầm & chân váy",image:"/src/assets/image/nu6.png",tenLoai:"Quần & đầm váy",doiTuong:"Nữ"},
    {name:"Áo giữ nhiệt",image:"/src/assets/image/nu2.png",maLoai:28,doiTuong:"Nữ"},
    {name:"Đồ lót",image:"/src/assets/image/nu7.png",tenLoai:"Đồ lót",doiTuong:"Nữ" },
    {name:"Áo polo & thun",image:"/src/assets/image/nu4.png",tenLoai:"Áo",doiTuong:"Nữ" 
}

];
onMounted(async () => {
  try {
    const res = await api.get("/products/by-doi-tuong?doiTuong=Nữ")
    products.value = res.data.result
  } catch (e) {
    console.error("Load products failed", e)
  }
})

</script>
<template>
  <!-- Banner -->
  <Homebanner :images="imgList" />

  <!-- Category Slider -->
  <div class="mt-10">
    <CategorySlider
      :categories="categories"
      title="HOT NHẤT TẠI ĐÂY!"
      @select-category="goCategory"
    />
  </div>

  <!-- Tiêu đề sản phẩm -->
  <div class="mt-16">
    <h2 class="text-center text-3xl font-bold text-[#c92127] mb-8">
      MUA NGAY KẺO LỠ
    </h2>
  

    <hr class="border-t-2 border-[#c92127]/40 w-3/4 mx-auto">
</div>

  <!-- Product List -->
  <div class="max-w-[1200px] mx-auto px-4 mt-8">
    <ProductList :products="products" />
  </div>

      <div class="flex justify-center mt-12">
    <router-link
      :to="{ path: '/category', query: { doiTuong: 'Nữ' } }"
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
    <Homeview image="/src/assets/image/b1nu.png" :to="{ path: '/category', query: { tenLoai:'Áo khoác',doiTuong:'Nữ' } }" />
    <Homeview image="/src/assets/image/b2nu.png" :to="{ path: '/category', query: { maLoai:28,doiTuong:'Nữ' } }" />
    <Homeview image="/src/assets/image/b3nu.png" :to="{ path: '/category', query: { tenLoai:'Đồ lót',doiTuong:'Nữ' } }" />
    <Homeview image="/src/assets/image/b4nu.png" :to="{ path: '/category', query: { maLoai:31,doiTuong:'Nữ' } }" />
  </div>
</template>