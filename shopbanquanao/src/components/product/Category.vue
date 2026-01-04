<script setup>
import { ref, watch, onMounted } from "vue"
import { useRoute, useRouter } from "vue-router"
import axios from "axios"
import ProductList from "./ProductList.vue"

const route = useRoute()
const router = useRouter()

/* ================= STATE ================= */
const products = ref([])
const danhMuc = ref("")
const chiTietLoai = ref([])

const page = ref(0)
const totalPages = ref(0)

/* ================= SORT ================= */
const sortOptions = [
  { label: "Mặc định", sort: "danhGia", direction: "desc" },
  { label: "Giá tăng dần", sort: "gia", direction: "asc" },
  { label: "Giá giảm dần", sort: "gia", direction: "desc" },
  { label: "Đánh giá tăng dần", sort: "danhGia", direction: "asc" },
  { label: "Đánh giá giảm dần", sort: "danhGia", direction: "desc" },
]
const selectedSort = ref(sortOptions[0])

/* ================= ĐỐI TƯỢNG ================= */
const doiTuongs = [
  { label: "Nam", value: "Nam" },
  { label: "Nữ", value: "Nữ" },
  { label: "Trẻ em", value: "Trẻ em" },
]

/* ================= API ================= */
const fetchData = async () => {
  const res = await axios.get(
    "http://localhost:8081/nhom19/products/categories",
    {
      params: {
        keyword: route.query.keyword || null,
        banChay: route.query.banChay === "true" ? true : null,
        maLoai: route.query.maLoai || null,
        tenLoai: route.query.tenLoai || null,
        doiTuong: route.query.doiTuong || null,
        page: page.value,
        sort: selectedSort.value.sort,
        direction: selectedSort.value.direction,
      },
    }
  )

  const data = res.data.result
  products.value = data.items || []
  totalPages.value = data.totalPages || 0
  chiTietLoai.value = data.chiTietLoai || []

  danhMuc.value = route.query.keyword
    ? `Kết quả tìm kiếm: "${route.query.keyword}"`
    : route.query.banChay
      ? "Sản phẩm bán chạy"
      : data.danhMuc
}

/* ================= ACTION ================= */
const changePage = (p) => {
  page.value = p
  fetchData()
}

const changeDoiTuong = (doiTuong) => {
  router.push({
    path: "/category",
    query: { ...route.query, doiTuong, page: 0 },
  })
}

const changeLoai = (maLoai) => {
  router.push({
    path: "/category",
    query: { ...route.query, maLoai, page: 0 },
  })
}

/* ================= WATCH ================= */
onMounted(fetchData)

watch(
  [() => route.query, selectedSort],
  () => {
    page.value = 0
    fetchData()
  },
  { deep: true }
)
</script>

<template>
  <div class="max-w-[1500px] mx-auto px-2 grid grid-cols-20 gap-4 py-6">

    <!-- ===== SIDEBAR ===== -->
    <aside class="col-span-3 text-sm">

      <!-- Danh mục -->
      <div>
        <h3 class="border-t border-gray-300 pt-4 font-semibold text-xl mb-3">
          Danh mục
        </h3>

        <ul class="space-y-2">
          <li
            v-for="loai in chiTietLoai"
            :key="loai.maLoai"
            class="flex items-center gap-2 cursor-pointer"
            @click="changeLoai(loai.maLoai)"
          >
            <input
              type="checkbox"
              class="accent-[#c92127] scale-125"
              :checked="route.query.maLoai == loai.maLoai"
            />
            <span class="hover:text-[#c92127]">
              {{ loai.chiTietLoai }}
            </span>
          </li>
        </ul>
      </div>

      <!-- Đối tượng -->
      <div class="mt-6">
        <h3 class="border-t border-gray-300 pt-4 font-semibold text-xl mb-3">
          Đối tượng
        </h3>

        <ul class="space-y-2">
          <li
            v-for="dt in doiTuongs"
            :key="dt.value"
            class="flex items-center gap-2 cursor-pointer"
            @click="changeDoiTuong(dt.value)"
          >
            <input
              type="checkbox"
              class="accent-[#c92127] scale-125"
              :checked="route.query.doiTuong === dt.value"
            />
            <span class="hover:text-[#c92127]">
              {{ dt.label }}
            </span>
          </li>
        </ul>
      </div>
    </aside>

    <!-- ===== MAIN CONTENT ===== -->
    <section class="col-span-17">

      <!-- Title -->
      <div class="mb-6 border-b border-dashed border-gray-400 pb-3">
        <h1 class="text-2xl font-semibold">{{ danhMuc }}</h1>
      </div>

      <!-- Sort -->
      <div class="mb-6 flex items-center gap-4">
        <span>Sắp xếp theo</span>
        <select
          v-model="selectedSort"
          class="border px-4 py-1 rounded"
        >
          <option
            v-for="opt in sortOptions"
            :key="opt.label"
            :value="opt"
          >
            {{ opt.label }}
          </option>
        </select>
      </div>

      <!-- Product list -->
      <ProductList :products="products" />

      <!-- Pagination -->
      <div
        v-if="totalPages > 1"
        class="flex justify-center mt-10 gap-2"
      >
        <button
          class="px-3 py-1 border rounded"
          :disabled="page === 0"
          @click="changePage(page - 1)"
        >
          ‹
        </button>

        <button
          v-for="p in totalPages"
          :key="p"
          class="px-3 py-1 border rounded"
          :class="page === p - 1 ? 'bg-[#c92127] text-white' : ''"
          @click="changePage(p - 1)"
        >
          {{ p }}
        </button>

        <button
          class="px-3 py-1 border rounded"
          :disabled="page >= totalPages - 1"
          @click="changePage(page + 1)"
        >
          ›
        </button>
      </div>
    </section>
  </div>
</template>
