<template>
  <div class="p-8 max-w-4xl mx-auto bg-white shadow-lg rounded-xl">
    <h1 class="text-2xl font-bold mb-6">Thêm sản phẩm mới</h1>

    <!-- Product info -->
    <div class="grid grid-cols-2 gap-4 mb-8">
      <div>
        <label class="font-semibold">Tên sản phẩm</label>
        <input v-model="product.tenSp" class="input" type="text" />
      </div>

      <div>
        <label class="font-semibold">Giá</label>
        <input v-model="product.gia" class="input" type="number" />
      </div>

      <div>
        <label class="font-semibold">Loại sản phẩm</label>
        <select v-model="product.maLoai" class="input">
          <option v-for="t in types" :value="t.ma_loai">
            {{ t.ten_loai }} - {{ t.chi_tiet_loai }}
          </option>
        </select>
      </div>

      <div class="col-span-2">
        <label class="font-semibold">Mô tả</label>
        <textarea v-model="product.chiTiet" class="input h-24"></textarea>
      </div>
    </div>

    <!-- Variants -->
    <div class="mb-8">
      <h2 class="font-semibold text-lg mb-2">Biến thể sản phẩm</h2>

      <div class="grid grid-cols-3 gap-4">
        <div>
          <label>Màu sắc</label>
          <select v-model="variant.maMs" class="input">
            <option v-for="c in colors" :value="c.ma_ms">
              {{ c.ten_mau }}
            </option>
          </select>
        </div>

        <div>
          <label>Kích cỡ</label>
          <select v-model="variant.maKc" class="input">
            <option v-for="s in sizes" :value="s.ma_kc">
              {{ s.ten_kc }}
            </option>
          </select>
        </div>

        <div>
          <label>Số lượng</label>
          <input v-model="variant.soluong" type="number" class="input" />
        </div>
      </div>

      <button @click="addVariant" class="btn-primary mt-3">
        + Thêm biến thể
      </button>

      <ul class="mt-3">
        <li v-for="v in variants" class="text-gray-600">
          Màu: {{ v.maMs }} - Size: {{ v.maKc }} - SL: {{ v.soluong }}
        </li>
      </ul>
    </div>

    <!-- Upload images -->
    <div class="mb-8">
      <h2 class="font-semibold text-lg mb-2">Hình ảnh sản phẩm</h2>

      <input type="file" multiple @change="handleFiles" class="input" />
    </div>

    <button @click="saveProduct" class="btn-primary w-full">
      Lưu sản phẩm
    </button>
  </div>
</template>

<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";

const product = ref({
  tenSp: "",
  gia: "",
  chiTiet: "",
  maLoai: null,
});

const variant = ref({});
const variants = ref([]);

const types = ref([]);
const colors = ref([]);
const sizes = ref([]);

const files = ref([]);

const addVariant = () => variants.value.push({ ...variant.value });

const handleFiles = (e) => (files.value = [...e.target.files]);

const saveProduct = async () => {
  // 1. Save product
  const res = await axios.post("/api/products", product.value);
  const ma_sp = res.data.ma_sp;

  // 2. Save variants
  for (const v of variants.value) {
    const r = await axios.post("/api/product-variants", { ...v, maSp: ma_sp });
    v.maBienThe = r.data.ma_bien_the;

    // 3. Upload images
    for (const f of files.value) {
      const form = new FormData();
      form.append("ma_bien_the", v.maBienThe);
      form.append("file", f);
      await axios.post("/api/product-images/upload", form);
    }
  }

  alert("Thêm sản phẩm thành công!");
};

onMounted(async () => {
  types.value = (await axios.get("/api/product-types")).data;
  colors.value = (await axios.get("/api/product-colors")).data;
  sizes.value = (await axios.get("/api/product-sizes")).data;
});
</script>

<style>
.input {
  @apply w-full border rounded-lg px-3 py-2 shadow-sm;
}
.btn-primary {
  @apply bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700;
}
</style>
