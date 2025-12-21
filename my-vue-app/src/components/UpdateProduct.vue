<template>
  <div class="p-6 max-w-7xl mx-auto space-y-8">
    <!-- HEADER -->
    <div class="flex items-center justify-between">
      <h1 class="text-3xl font-bold">✏ Chỉnh sửa sản phẩm</h1>
      <button class="px-5 py-2 bg-blue-600 text-white rounded-lg">
        💾 Lưu sản phẩm
      </button>
    </div>

    <!-- ===== THÔNG TIN SẢN PHẨM ===== -->
    <section class="bg-white rounded-xl shadow p-6 space-y-4">
      <h2 class="text-lg font-semibold">📦 Thông tin sản phẩm</h2>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label class="block text-sm font-medium mb-1">Tên sản phẩm</label>
          <input class="w-full border rounded px-3 py-2" />
        </div>

        <div>
          <label class="label">Giá</label>
          <input type="number" v-model.number="product.gia" class="input" />
        </div>

        <div>
          <label class="label">Trạng thái</label>
          <select v-model="product.trangThai" class="input">
            <option value="ACTIVE">Đang bán</option>
            <option value="INACTIVE">Ngừng bán</option>
          </select>
        </div>

        <div>
          <label class="label">Loại sản phẩm</label>
          <select v-model="product.maLoai" class="input">
            <option value="L01">Áo nam</option>
            <option value="L02">Quần nữ</option>
          </select>
        </div>
      </div>

      <div>
        <label class="label">Mô tả</label>
        <textarea v-model="product.moTa" class="input h-28"></textarea>
      </div>
    </section>

    <!-- ===== MÀU + ẢNH ===== -->
    <section class="bg-white rounded-xl shadow p-6 space-y-6">
      <div class="flex justify-between items-center">
        <h2 class="text-lg font-semibold">🎨 Màu & Hình ảnh</h2>
        <button class="px-3 py-2 border rounded" @click="addColor">
          ➕ Thêm màu
        </button>
      </div>

      <div
        v-for="(color, cIdx) in product.colors"
        :key="cIdx"
        class="border rounded-lg p-4 space-y-4"
      >
        <!-- MÀU -->
        <div class="flex justify-between items-center">
          <div class="flex gap-4">
            <input
              v-model="color.tenMau"
              placeholder="Tên màu"
              class="input w-40"
            />
          </div>
          <button class="text-red-600" @click="removeColor(cIdx)">
            🗑 Xóa màu
          </button>
        </div>

        <!-- ẢNH -->
        <div class="flex gap-4 overflow-x-auto">
          <div
            v-for="(img, iIdx) in color.images"
            :key="iIdx"
            class="relative w-36 border rounded-lg p-2 shrink-0"
          >
            <img :src="img.url" class="w-full h-24 object-cover rounded" />

            <!-- BADGE -->
            <span
              v-if="img.daiDien"
              class="absolute top-1 left-1 text-xs bg-emerald-600 text-white px-2 rounded"
            >
              SP
            </span>
            <span
              v-if="img.daiDienMau"
              class="absolute top-1 right-1 text-xs bg-amber-500 text-white px-2 rounded"
            >
              Màu
            </span>

            <!-- ACTION -->
            <div class="mt-2 space-y-1 text-xs">
              <button
                class="w-full border rounded"
                @click="setDaiDienSP(cIdx, iIdx)"
              >
                Đại diện SP
              </button>
              <button
                class="w-full border rounded"
                @click="setDaiDienMau(cIdx, iIdx)"
              >
                Đại diện màu
              </button>
              <button
                class="w-full border text-red-600 rounded"
                @click="color.images.splice(iIdx, 1)"
              >
                Xóa
              </button>
            </div>
          </div>

          <!-- ADD IMAGE -->
          <button
            class="w-36 h-36 border-dashed border rounded-lg flex items-center justify-center text-gray-400"
            @click="addImage(cIdx)"
          >
            ➕
          </button>
        </div>

        <!-- SIZE -->
        <div class="space-y-2">
          <h4 class="font-medium">Size</h4>

          <div
            v-for="(s, sIdx) in color.sizes"
            :key="sIdx"
            class="flex gap-3 items-center"
          >
            <input v-model="s.tenSize" placeholder="Size" class="input w-20" />
            <input
              type="number"
              v-model.number="s.soLuong"
              placeholder="SL"
              class="input w-24"
            />
            <button class="text-red-600" @click="color.sizes.splice(sIdx, 1)">
              Xóa
            </button>
          </div>

          <button class="px-3 py-1 border rounded" @click="addSize(cIdx)">
            ➕ Thêm size
          </button>
        </div>
      </div>
    </section>

    <!-- PREVIEW JSON -->
    <section class="bg-gray-50 rounded-xl p-6">
      <h2 class="font-semibold mb-2">🔍 Preview dữ liệu</h2>
      <pre class="text-sm bg-white p-4 rounded overflow-auto max-h-96">{{
        product
      }}</pre>
    </section>
  </div>
</template>

<script setup>
import { reactive } from "vue";

/* ===== MOCK PRODUCT ===== */
const product = reactive({
  maSp: 1,
  tenSp: "Áo thun basic",
  gia: 199000,
  trangThai: "ACTIVE",
  maLoai: "L01",
  moTa: "Áo thun cotton mềm mịn",

  colors: [
    {
      tenMau: "Đen",
      images: [
        {
          url: "https://picsum.photos/200?1",
          daiDien: true,
          daiDienMau: true,
        },
        {
          url: "https://picsum.photos/200?2",
          daiDien: false,
          daiDienMau: false,
        },
      ],
      sizes: [
        { tenSize: "M", soLuong: 10 },
        { tenSize: "L", soLuong: 5 },
      ],
    },
  ],
});

/* ===== ACTIONS ===== */
function addColor() {
  product.colors.push({
    tenMau: "",
    images: [],
    sizes: [],
  });
}

function removeColor(idx) {
  product.colors.splice(idx, 1);
}

function addImage(cIdx) {
  product.colors[cIdx].images.push({
    url: "https://picsum.photos/200?" + Math.random(),
    daiDien: false,
    daiDienMau: false,
  });
}

function setDaiDienSP(cIdx, iIdx) {
  product.colors.forEach((c) =>
    c.images.forEach((img) => (img.daiDien = false))
  );
  product.colors[cIdx].images[iIdx].daiDien = true;
}

function setDaiDienMau(cIdx, iIdx) {
  product.colors[cIdx].images.forEach(
    (img, i) => (img.daiDienMau = i === iIdx)
  );
}

function addSize(cIdx) {
  product.colors[cIdx].sizes.push({
    tenSize: "",
    soLuong: 0,
  });
}
</script>
