<script setup>
import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"
import api from "@/api"
import Homebanner from "@/components/home/Homebanner.vue"
import CategorySlider from "@/components/home/CategorySlider.vue"
import ProductList from "@/components/product/ProductList.vue"
import Homeview from "@/components/home/Homeview.vue"

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

const categories = [
  { name: "Áo khoác lông vũ", image: "/src/assets/image/cate1.png",maLoai:51,doiTuong:"Nu" },
  { name: "Áo khoác phao", image: "/src/assets/image/cate2.png",maLoai:1,doiTuong:"Nam" },
  { name: "Áo khoác lông cừu", image: "/src/assets/image/cate3.png",maLoai:24,doiTuong:"Nu" },
  { name: "Áo khoác lông cừu", image: "/src/assets/image/cate4.png",maLoai:5,doiTuong:"Nam" },
  { name: "Áo giữ nhiệt", image: "/src/assets/image/2.png",maLoai:28,doiTuong:"Nu" },
  { name: "Áo giữ nhiệt", image: "/src/assets/image/1b.png",maLoai:6,doiTuong:"Nam" },
  { name: "Áo chống nắng", image: "/src/assets/image/3.png",maLoai:26,doiTuong:"Nu" },
  { name: "Áo chống nắng", image: "/src/assets/image/1c.png",maLoai:4,doiTuong:"Nam" },
  { name: "Áo polo & thun", image: "/src/assets/image/4.png" },
  { name: "Áo polo & thun", image: "/src/assets/image/1d.png",tenLoai:"Áo",doiTuong:"Nam" },
  { name: "Đồ lót", image: "/src/assets/image/5.png" },
  { name: "Đồ lót", image: "/src/assets/image/1e.png" },
  { name: "Quần dài", image: "/src/assets/image/6.png" },
  { name: "Quần dài", image: "/src/assets/image/1g.png" },
  { name: "Quần short & chân váy", image: "/src/assets/image/7.png" },
  { name: "Quần short", image: "/src/assets/image/1f.png" }
]

const imgList = [
  "/src/assets/image/anh1.jpg",
  "/src/assets/image/anh2.png",
  "/src/assets/image/anh3.jpg"
]

// 🔥 GỌI API /products Ở HOME
onMounted(async () => {
  try {
    const res = await api.get("/products")
    products.value = res.data.result
  } catch (e) {
    console.error("Load products failed", e)
  }
})
</script>

<template>
  <Homebanner :images="imgList" />

  <CategorySlider
    :categories="categories"
    title="HÔM NAY MUA GÌ?"
    @select-category="goCategory"
  />

  <!-- 🔥 TRUYỀN PRODUCTS -->
  <ProductList :products="products" />
<div class="flex justify-center mt-10">
  <router-link
    :to="{
      path: '/category',
      query: { banChay: 'true' }
    }"
  >
    <button
      class="px-10 py-3 bg-[#c92127] text-white font-semibold rounded hover:opacity-90"
    >
      Xem tất cả sản phẩm
    </button>
  </router-link>
</div>


  <div class="space-y-12">
    <Homeview image="/src/assets/image/bc.png" to="/product/1" />
    <Homeview image="/src/assets/image/cd.png" to="/product/1" />
    <Homeview image="/src/assets/image/de.png" to="/product/1" />
    <Homeview image="/src/assets/image/ef.png" to="/product/1" />
    <Homeview image="/src/assets/image/gh.png" to="/product/1" />
  </div>
</template>
