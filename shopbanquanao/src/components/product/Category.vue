<script setup>
import { ref, watch, onMounted } from "vue"
import { useRoute, useRouter } from "vue-router"
import axios from "axios"
import ProductList from "./ProductList.vue"

const route = useRoute()
const router = useRouter()

const products = ref([])
const danhMuc = ref("")
const chiTietLoai = ref([])

const page = ref(0)
const sort = ref("danhGia")
const direction = ref("desc")

const doiTuongs = [
  { label: "Nam", value: "Nam" },
  { label: "Nữ", value: "Nữ" },
  { label: "Trẻ em", value: "Trẻ em" },
]


const fetchData = async () => {
  const res = await axios.get(
    "http://localhost:8081/nhom19/products/categories",
    {
      params: {
        keyword: route.query.keyword || null,
        banChay: route.query.banChay === 'true' ? true : null,
        maLoai: route.query.maLoai || null,
        tenLoai: route.query.tenLoai || null,
        doiTuong: route.query.doiTuong || null,
        page: page.value,
        sort: sort.value,
        direction: direction.value,
      },
    }
  )

  const data = res.data.result

  products.value = data.items || []
  danhMuc.value = route.query.keyword
    ? `Kết quả tìm kiếm: "${route.query.keyword}"`
    : route.query.banChay
      ? "Sản phẩm bán chạy"
      : data.danhMuc

  chiTietLoai.value = data.chiTietLoai || []
}

const changeDoiTuong = (doiTuong) => {
  router.push({
    path: "/category",
    query: {
      ...route.query,
      doiTuong,
      page: 0, // reset page nếu cần
    },
  })
}


onMounted(fetchData)

// tự gọi lại API khi query thay đổi
watch(
  () => route.query,
  () => fetchData(),
  { deep: true }
)

// đổi loại trong sidebar
const changeLoai = (maLoai) => {
  router.push({
    path: "/category",
    query: {
      ...route.query,
      maLoai,
    },
  })
}
</script>

<template>
  <div class="max-w-[1500px] mx-auto px-2 grid grid-cols-20 gap-2 py-6">

    <!-- ===== SIDEBAR ===== -->
    <aside class="col-span-3 pr-2 text-sm">

      <!-- Danh mục -->
      <div>
        <h3 class=" border-t border-gray-300 pt-3.5 font-semibold text-xl mb-3 flex justify-between items-center">
          Danh mục
          <span class="text-lg">−</span>
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
            <span class=" text-base hover:text-[#c92127]">
              {{ loai.chiTietLoai }}
            </span>
          </li>
        </ul>
      </div>
      <!-- ===== ĐỐI TƯỢNG ===== -->
<div class="mt-6">
  <h3 class="border-t border-gray-300 pt-3.5 font-semibold text-xl mb-3 flex justify-between items-center">
    Đối tượng
    <span class="text-lg">−</span>
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
        name="doiTuong"
        class="accent-[#c92127] scale-125"
        :checked="route.query.doiTuong === dt.value"
      />
      <span class="text-base hover:text-[#c92127]">
        {{ dt.label }}
      </span>
    </li>
  </ul>
</div>



    </aside>

    <!-- ===== MAIN CONTENT ===== -->
    <section class="col-span-17">

      <!-- Title -->
      <div class="mb-6 border-b border-dashed border-gray-500 pb-3">
        <h1 class="text-2xl font-semibold">
          {{ danhMuc }}
        </h1>
      </div>

      <!-- Sort -->
      <div class="mb-6 flex items-center gap-4 text-base">
        <span class="text-black">Sắp xếp theo</span>

        <select
          v-model="sort"
          class=" border px-4 py-1 rounded"
        >
          <option value="danhGia">Mặc định</option>
          <option value="gia">Giá từ thấp đến cao</option>
          <option value="tenSp">Giá từ cao đến thấp</option>
          <option value="">Đánh giá từ thấp đến cao</option>
        </select>
      </div>

      <!-- Product grid -->
       <ProductList :products="products"/>
    </section>
  </div>
</template>
