<template>
  <div class="p-6 max-w-6xl mx-auto font-sans">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <div>
        <h1 class="text-3xl font-extrabold">
          Admin • Thêm / Chỉnh sửa Sản phẩm
        </h1>
      </div>
    </div>
    <form
      id="productForm"
      @submit.prevent="onSubmit"
      class="grid grid-cols-1 lg:grid-cols-3 gap-6"
    >
      <!-- Left column: images / gallery -->
      <aside class="col-span-1 space-y-4">
        <div class="bg-white rounded-2xl p-4 shadow-sm">
          <label class="text-sm font-medium text-gray-700"
            >Trạng thái & Giá</label
          >
          <div class="mt-3 space-y-3">
            <div>
              <label class="text-xs text-gray-500">Giá (VND)</label>
              <input
                v-model.number="form.gia"
                type="number"
                min="0"
                class="w-full mt-1 px-3 py-2 rounded-lg border focus:ring-1 focus:ring-blue-300"
              />
            </div>
            <div>
              <label class="text-xs text-gray-500">Trạng thái</label>
              <select
                v-model="form.trangThai"
                class="w-full mt-1 px-3 py-2 rounded-lg border"
              >
                <option value="">-- Chọn trạng thái --</option>
                <option value="ACTIVE">Hoạt động</option>
                <option value="INACTIVE">Ngưng bán</option>
              </select>
            </div>
          </div>
        </div>
      </aside>

      <!-- Middle column: main product fields -->
      <main class="col-span-1 lg:col-span-2 space-y-4">
        <div class="bg-white rounded-2xl p-6 shadow-sm">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700"
                >Tên sản phẩm</label
              >
              <input
                v-model="form.tenSp"
                required
                class="mt-2 w-full px-3 py-2 rounded-lg border focus:outline-none focus:ring-1 focus:ring-indigo-200"
              />
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700"
                >Đối tượng</label
              >
              <select
                v-model="selectedDoiTuong"
                class="mt-2 w-full px-3 py-2 rounded-lg border"
              >
                <option value="">-- Chọn đối tượng --</option>
                <option v-for="dt in doiTuongOptions" :key="dt" :value="dt">
                  {{ dt }}
                </option>
              </select>
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700"
                >Tên loại</label
              >
              <select
                v-model="selectedTenLoai"
                :disabled="!selectedDoiTuong"
                class="mt-2 w-full px-3 py-2 rounded-lg border"
              >
                <option value="">-- Chọn tên loại --</option>
                <option v-for="ten in tenLoaiOptions" :key="ten" :value="ten">
                  {{ ten }}
                </option>
              </select>
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700"
                >Chi tiết loại</label
              >
              <select
                v-model="form.maLoai"
                :disabled="!selectedTenLoai"
                class="mt-2 w-full px-3 py-2 rounded-lg border"
              >
                <option value="">-- Chọn chi tiết --</option>
                <option
                  v-for="t in chiTietLoaiOptions"
                  :key="t.maLoai"
                  :value="t.maLoai"
                >
                  {{ t.chiTietLoai }}
                </option>
              </select>
            </div>
          </div>

          <!-- Rich editor -->
          <div class="mt-6">
            <label class="block text-sm font-medium text-gray-700"
              >Mô tả sản phẩm</label
            >
            <div class="mt-2 bg-gray-50 rounded-lg border p-3">
              <div class="flex gap-2 mb-3">
                <button
                  type="button"
                  @click="execCmd('bold')"
                  class="px-3 py-1 border rounded"
                >
                  B
                </button>
                <button
                  type="button"
                  @click="execCmd('italic')"
                  class="px-3 py-1 border rounded"
                >
                  I
                </button>
                <button
                  type="button"
                  @click="insertHeading"
                  class="px-3 py-1 border rounded"
                >
                  H2
                </button>
                <label class="px-3 py-1 border rounded cursor-pointer">
                  <input
                    type="file"
                    accept="image/*"
                    @change="insertImageToEditor"
                    class="hidden"
                  />
                  Ảnh
                </label>
              </div>

              <!-- <div
                ref="editor"
                contenteditable
                class="min-h-[220px] p-4 bg-white rounded shadow-inner overflow-auto"
                @input="syncEditor"
              > -->
              <div
                ref="editor"
                contenteditable
                class="editor-content min-h-55 p-4 bg-white rounded shadow-inner overflow-auto"
                @input="syncEditor"
              >
                <h2><b>ĐẶC ĐIỂM SẢN PHẨM</b></h2>
                <br><br></br>
              </div>
            </div>
          </div>
        </div>

        <!-- Variants (Colors & Sizes) -->
        <div class="bg-white rounded-2xl p-6 shadow-sm">
          <div class="flex items-center justify-between mb-4">
            <h3 class="text-lg font-semibold">Biến thể (Màu & Size)</h3>
            <button
              type="button"
              @click="addColor"
              class="px-3 py-1 border rounded bg-gray-50"
            >
              Thêm màu
            </button>
          </div>

          <div class="space-y-4">
            <div
              v-for="(color, idx) in form.colors"
              :key="idx"
              class="border rounded-lg p-4"
            >
              <div class="flex items-start justify-between gap-4">
                <div class="flex-1">
                  <label class="text-sm text-gray-600">Màu</label>
                  <select
                    v-model="color.maMs"
                    class="mt-2 w-full px-3 py-2 rounded-lg border"
                  >
                    <option value="">Chọn màu</option>
                    <option v-for="t in Colors" :key="t.maMs" :value="t.maMs">
                      {{ t.tenMs }}
                    </option>
                  </select>
                </div>

<div class="w-48">
  <label class="text-sm text-gray-600 block mb-2">
    Ảnh màu
  </label>

  <label
    class="flex items-center justify-center gap-2
           px-3 py-2 text-sm
           border border-dashed border-gray-600
           rounded-lg cursor-pointer
           text-black hover:bg-gray-50 transition"
  >
    📷 Chọn ảnh
    <input
      type="file"
      multiple
      class="hidden"
      @change="(e) => onColorImagesChange(e, idx)"
    />
  </label>

  <p class="text-xs text-gray-400 mt-1">
    Có thể chọn nhiều ảnh
  </p>
</div>


                <div class="shrink-0 flex flex-col items-end gap-2">
                  <button
                    type="button"
                    @click="removeColor(idx)"
                    class="px-2 py-1 text-sm border rounded text-red-600"
                  >
                    Xóa
                  </button>
                </div>
              </div>
              <div class="mt-3 grid grid-cols-3 gap-3">
              <div
                v-for="(img, i) in color.urlImages"
                :key="i"
                class="relative aspect-square rounded-lg overflow-hidden border border-gray-200 bg-gray-50"
              >
                <img
                  :src="img.url"
                  class="absolute inset-0 w-full h-full object-cover"
                  loading="lazy"
                />

                <!-- Badge đại diện -->
<!-- Ảnh đại diện SP -->
<div
  v-if="img.daiDien"
  class="absolute top-1 left-1 bg-emerald-500
         text-xs px-2 py-0.5 rounded text-white font-semibold shadow"
>
  SP
</div>

<!-- Ảnh đại diện màu -->
<div
  v-if="img.daiDienMau"
  class="absolute top-1 right-1 bg-amber-400
         text-xs px-2 py-0.5 rounded text-white font-semibold shadow"
>
  Màu
</div>


                <!-- Action buttons -->
                <div class="absolute bottom-1 left-1 right-1 flex gap-1">
  <!-- Ảnh đại diện SP -->
  <button
    type="button"
    @click="setDaiDienSanPham(idx, i)"
    class="flex-1 px-2 py-1 text-xs rounded
           bg-emerald-50 hover:bg-emerald-100
           border shadow-sm text-emerald-700"
  >
    Đại diện SP
  </button>

  <!-- Ảnh đại diện màu -->
  <button
    type="button"
    @click="setDaiDienMau(idx, i)"
    class="flex-1 px-2 py-1 text-xs rounded
           bg-amber-50 hover:bg-amber-100
           border shadow-sm text-amber-700"
  >
    Đại diện màu
  </button>

  <!-- Xóa -->
  <button
    type="button"
    @click="color.urlImages.splice(i, 1)"
    class="flex-1 px-2 py-1 text-xs rounded
           bg-white hover:bg-red-50
           border shadow-sm text-red-600"
  >
    Xóa
  </button>
</div>
              </div>
            </div>
              <div class="mt-3">
                <label class="text-sm text-gray-600">Sizes</label>
                <div class="mt-2 space-y-2">
                  <div
                    v-for="(s, sidx) in color.sizes"
                    :key="sidx"
                    class="flex items-center gap-3"
                  >
                    <select
                      v-model="s.maKc"
                      class="px-3 py-2 rounded-lg border w-full md:w-72"
                    >
                      <option value="">Chọn size</option>
                      <option v-for="t in Sizes" :key="t.maKc" :value="t.maKc">
                        {{ t.tenKc }}
                      </option>
                    </select>
                    <input
                      v-model.number="s.soluong"
                      type="number"
                      min="0"
                      placeholder="Số lượng"
                      class="w-28 px-2 py-2 rounded-lg border"
                    />
                    <button
                      type="button"
                      @click="removeSize(idx, sidx)"
                      class="px-3 py-1 border rounded text-red-600"
                    >
                      Xóa
                    </button>
                  </div>
                  <button
                    type="button"
                    @click="addSize(idx)"
                    class="mt-2 px-3 py-1 border rounded"
                  >
                    Thêm size
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Actions (mobile) -->
        <div class="hidden lg:flex items-center gap-3">
          <button
            @click="preview"
            class="flex-1 px-4 py-2 bg-emerald-600 text-black rounded-lg"
          >
            Xem trước
          </button>
          <button
            form="productForm"
            type="submit"
            class="flex-1 px-4 py-2 bg-blue-600 text-black rounded-lg"
          >
            Lưu
          </button>
        </div>
      </main>
    </form>

    <!-- Preview modal -->
    <transition name="fade">
      <div
        v-if="showPreview"
        class="fixed inset-0 bg-black/60 flex items-start justify-center p-6 z-50"
      >
        <div
          class="bg-white rounded-2xl w-full max-w-4xl p-6 overflow-auto max-h-[90vh]"
        >
          <div class="flex items-center justify-between mb-4">
            <h3 class="text-xl font-semibold">Xem trước sản phẩm</h3>
            <div class="flex gap-2">
              <button
                @click="downloadPreviewJson"
                class="px-3 py-1 border rounded"
              >
                Tải JSON
              </button>
              <button
                @click="showPreview = false"
                class="px-3 py-1 border rounded"
              >
                Đóng
              </button>
            </div>
          </div>
          <pre class="whitespace-pre-wrap mt-2 bg-gray-50 p-3 rounded">{{
            previewData
          }}</pre>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed, watch } from "vue";
import api from "@/api"; 

// Data sources
const allTypes = ref([]);
const Colors = ref([]);
const Sizes = ref([]);




onMounted(() => {
  loadAll();
});

async function loadAll() {
  await Promise.all([loadTypes(), loadColors(), loadSizes()]);
}

const loadTypes = async () => {
  try {
    const res = await api.get("/types");
    allTypes.value = res.data.result || [];
  } catch (e) {
    console.error("loadTypes", e);
  }
};
const loadColors = async () => {
  try {
    const res = await api.get("/colors");
    Colors.value = res.data.result || [];
  } catch (e) {
    console.error("loadColors", e);
  }
};
const loadSizes = async () => {
  try {
    const res = await api.get("/sizes");
    Sizes.value = res.data.result || [];
  } catch (e) {
    console.error("loadSizes", e);
  }
};

// selects for types
const selectedDoiTuong = ref("");
const selectedTenLoai = ref("");

const doiTuongOptions = computed(() => [
  ...new Set(allTypes.value.map((x) => x.doiTuong?.trim())),
]);
const tenLoaiOptions = computed(() => {
  if (!selectedDoiTuong.value) return [];
  return [
    ...new Set(
      allTypes.value
        .filter((x) => x.doiTuong?.trim() === selectedDoiTuong.value.trim())
        .map((x) => x.tenLoai?.trim())
    ),
  ];
});
const chiTietLoaiOptions = computed(() => {
  if (!selectedTenLoai.value) return [];
  return allTypes.value.filter(
    (x) =>
      x.tenLoai?.trim() === selectedTenLoai.value.trim() &&
      x.doiTuong?.trim() === selectedDoiTuong.value.trim()
  );
});

watch(selectedDoiTuong, () => {
  selectedTenLoai.value = "";
  form.maLoai = "";
});
watch(selectedTenLoai, () => {
  form.maLoai = "";
});

// form
const form = reactive({
  tenSp: "",
  maLoai: "",
  gia: 0,
  chiTiet: "",
  colors: [
    {
      maMs: "",
      urlImages: [],
      sizes: []
    }
  ],
  trangThai:"ACTIVE"
});


const mainImagesPreview = ref([]);

// editor
const editor = ref(null);
function execCmd(cmd) {
  document.execCommand(cmd);
}
function insertHeading() {
  document.execCommand("formatBlock", false, "h2");
}
async function insertImageToEditor(e) {
  const file = e.target.files?.[0];
  if (!file) return;
  const url = await uploadImage(file);
  if (!url) return;
  const img = document.createElement("img");
  img.src = url;
  img.style.maxWidth = "100%";
  img.style.height = "auto";
  insertNodeAtCaret(img);
  syncEditor();
}

function insertNodeAtCaret(node) {
  const sel = window.getSelection();
  if (!sel || sel.rangeCount === 0) return;
  const range = sel.getRangeAt(0);
  range.insertNode(node);
  range.collapse(false);
}
function syncEditor() {
  form.chiTiet = editor.value?.innerHTML || "";
}

async function uploadImage(file) {
  try {
    const fd = new FormData();
    fd.append("file", file);
    const res = await api.post(
      "/files/images",
      fd,
      { headers: { "Content-Type": "multipart/form-data" } }
    );
    return res.data.url;
  } catch (err) {
    console.error("Lỗi upload ảnh:", err);
    alert("Không thể upload ảnh!");
    return null;
  }
}

// main image change: keep only last selected image
async function onMainImagesChange(e) {
  const files = Array.from(e.target.files || []);
  if (!files.length) return;
  const last = files[files.length - 1];
  const url = await uploadImage(last);
  if (url) {
    form.daiDien = url;
    mainImagesPreview.value = [url];
  }
}
// function downloadImage(url) {
//   const a = document.createElement("a");
//   a.href = url;
//   a.download = "image";
//   a.click();
// }

// color images
async function onColorImagesChange(e, idx) {
  const files = Array.from(e.target.files || []);
  for (const f of files) {
    const fd = new FormData();
    fd.append("file", f);

    const res = await api.post(
      "/files/images",
      fd,
      { headers: { "Content-Type": "multipart/form-data" } }
    );

    form.colors[idx].urlImages.push({
      url: res.data.url,
      daiDien: false, // ✅ mặc định false
      daiDienMau: false
    });
  }
}

function setDaiDienMau(colorIndex, imgIndex) {
  const imgs = form.colors[colorIndex].urlImages;
  imgs.forEach((img, i) => {
    img.daiDienMau = i === imgIndex;
  });
}

function setDaiDienSanPham(colorIndex, imgIndex) {
  // reset toàn bộ ảnh trong mọi màu
  form.colors.forEach(color => {
    color.urlImages.forEach(img => {
      img.daiDien = false;
    });
  });

  // set ảnh được chọn
  form.colors[colorIndex].urlImages[imgIndex].daiDien = true;
}


function addColor() {
  form.colors.push({
    maMs: "",
    urlImages: [], // sẽ chứa object { url, daiDien }
    sizes: [],
  });
}

function removeColor(i) {
  form.colors.splice(i, 1);
}
function addSize(i) {
  form.colors[i].sizes.push({ maKc: "", soluong: 0 });
}
function removeSize(i, si) {
  form.colors[i].sizes.splice(si, 1);
}

function validateDaiDien() {
  let hasDaiDienSP = false;

  for (const color of form.colors) {
    let hasDaiDienMau = false;

    for (const img of color.urlImages) {
      if (img.daiDien) hasDaiDienSP = true;
      if (img.daiDienMau) hasDaiDienMau = true;
    }

    if (!hasDaiDienMau) {
      alert("Mỗi màu phải có 1 ảnh đại diện màu");
      return false;
    }
  }

  if (!hasDaiDienSP) {
    alert("Phải chọn 1 ảnh đại diện sản phẩm");
    return false;
  }

  return true;
}



async function onSubmit() {
  if (!validateDaiDien()) return;
  const payload = JSON.parse(JSON.stringify(form));
  try {
    const res = await api.post(
      "/products",
      payload
    );
    alert("Lưu sản phẩm thành công!");
  } catch (err) {
    console.error(err);
    alert("Lỗi khi lưu sản phẩm!");
  }
}

// preview
const showPreview = ref(false);
const previewData = ref("");
function preview() {
  previewData.value = JSON.stringify(JSON.parse(JSON.stringify(form)), null, 2);
  showPreview.value = true;
}
function downloadPreviewJson() {
  const blob = new Blob([previewData.value], { type: "application/json" });
  const url = URL.createObjectURL(blob);
  const a = document.createElement("a");
  a.href = url;
  a.download = "product-preview.json";
  a.click();
  URL.revokeObjectURL(url);
}

onMounted(() => {
  if (editor.value) form.chiTiet = editor.value.innerHTML;
});
</script>

<style scoped>
.font-sans {
  font-family: Inter, ui-sans-serif, system-ui, -apple-system, "Segoe UI",
    Roboto, "Helvetica Neue", Arial;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
.editor-content :deep(img) {
  max-width: 400px;     /* KÍCH THƯỚC CHUẨN */
  width: 100%;
  height: auto;
  display: block;
  margin: 12px auto;
  border-radius: 8px;
}
</style>
