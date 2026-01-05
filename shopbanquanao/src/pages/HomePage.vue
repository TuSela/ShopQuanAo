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
  { name: "Áo khoác lông vũ", image: "/src/assets/image/cate1.png",maLoai:51,doiTuong:"Nữ" },
  { name: "Áo khoác phao", image: "/src/assets/image/cate2.png",maLoai:1,doiTuong:"Nam" },
  { name: "Áo khoác lông cừu", image: "/src/assets/image/cate3.png",maLoai:24,doiTuong:"Nữ" },
  { name: "Áo khoác lông cừu", image: "/src/assets/image/cate4.png",maLoai:5,doiTuong:"Nam" },
  { name: "Áo giữ nhiệt", image: "/src/assets/image/2.png",maLoai:28,doiTuong:"Nữ" },
  { name: "Áo giữ nhiệt", image: "/src/assets/image/1b.png",maLoai:6,doiTuong:"Nam" },
  { name: "Áo chống nắng", image: "/src/assets/image/3.png",maLoai:26,doiTuong:"Nữ" },
  { name: "Áo chống nắng", image: "/src/assets/image/1c.png",maLoai:4,doiTuong:"Nam" },
  { name: "Áo polo & thun", image: "/src/assets/image/4.png",tenLoai:"Áo",doiTuong:"Nữ"  },
  { name: "Áo polo & thun", image: "/src/assets/image/1d.png",tenLoai:"Áo",doiTuong:"Nam" },
  { name: "Đồ lót", image: "/src/assets/image/5.png",tenLoai:"Đồ lót",doiTuong:"Nữ" },
  { name: "Đồ lót", image: "/src/assets/image/1e.png",tenLoai:"Đồ lót",doiTuong:"Nam" },
  { name: "Quần Jeans", image: "/src/assets/image/6.png",maLoai:31,doiTuong:"Nữ" },
  { name: "Quần dài", image: "/src/assets/image/1g.png",maLoai:11,doiTuong:"Nam" },
  { name: "Quần short & chân váy", image: "/src/assets/image/7.png",maLoai:33,doiTuong:"Nữ"  },
  { name: "Quần short", image: "/src/assets/image/1f.png",maLoai:12,doiTuong:"Nam" }
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
  <!-- Banner -->
  <Homebanner :images="imgList" />

  <!-- Category -->
  <div class="mt-10">
    <CategorySlider
      :categories="categories"
      title="HÔM NAY MUA GÌ?"
      @select-category="goCategory"
    />
  </div>

  <!-- Tiêu đề sản phẩm hot -->
<div class="mt-16 text-center">
  <h2 class="text-2xl font-bold text-[#c92127] mb-12">
    SẢN PHẨM ĐƯỢC YÊU THÍCH NHẤT
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
  <!-- Danh sách sản phẩm -->
  <div class="max-w-[1200px] mx-auto px-4 mt-10">
    <ProductList :products="products" />
  </div>

  <!-- Xem tất cả -->
  <div class="flex justify-center mt-12">
    <router-link
      :to="{ path: '/category', query: { banChay: 'true' } }"
    >
      <button
        class="px-12 py-3 bg-[#c92127] text-white font-semibold rounded-lg hover:opacity-90 transition"
      >
        Xem tất cả sản phẩm
      </button>
    </router-link>
  </div>

  <!-- Homeviews / Banner nhỏ -->
  <div class="space-y-12 mt-16">
    <Homeview image="/src/assets/image/bc.png" :to="{ path: '/category', query: { maLoai:5,doiTuong:'Nam' } }" />
    <Homeview image="/src/assets/image/cd.png" :to="{ path: '/category', query: { maLoai:28,doiTuong:'Nữ' } }"/>
    <Homeview image="/src/assets/image/de.png" :to="{ path: '/category', query: { maLoai:25,doiTuong:'Nữ' } }" />
    <Homeview image="/src/assets/image/ef.png" :to="{ path: '/category', query: { maLoai:10,doiTuong:'Nam' } }"/>
    <Homeview image="/src/assets/image/gh.png" :to="{ path: '/category', query: { tenLoai:'Đồ lót' } }" />
  </div>
</template>

