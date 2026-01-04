<template>
  <div class="page">
    <!-- ===== PAGE HEADER ===== -->
    <h1>🛠 Cập nhật sản phẩm</h1>

    <!-- ================================================= -->
    <!-- =============== THÔNG TIN CƠ BẢN ================ -->
    <!-- ================================================= -->
    <section class="card">
      <h2>Thông tin cơ bản</h2>

      <div class="grid">
        <!-- MÃ SẢN PHẨM -->
        <div>
          <label>Mã sản phẩm</label>
          <input disabled :value="productForm.maSp" />
        </div>

        <!-- TÊN SẢN PHẨM -->
        <div>
          <label>Tên sản phẩm</label>
          <input v-model="productForm.tenSp" />
        </div>

        <!-- ĐỐI TƯỢNG -->
        <div>
          <label>Đối tượng</label>
          <select v-model="selectedDoiTuong">
            <option disabled value="">-- Chọn đối tượng --</option>
            <option v-for="dt in doiTuongOptions" :key="dt" :value="dt">
              {{ dt }}
            </option>
          </select>
        </div>

        <!-- TÊN LOẠI -->
        <div>
          <label>Tên loại</label>
          <select v-model="selectedTenLoai" :disabled="!selectedDoiTuong">
            <option disabled value="">-- Chọn tên loại --</option>
            <option v-for="t in tenLoaiOptions" :key="t" :value="t">
              {{ t }}
            </option>
          </select>
        </div>

        <!-- CHI TIẾT LOẠI -->
        <div>
          <label>Chi tiết loại</label>
          <select v-model="productForm.maLoai" :disabled="!selectedTenLoai">
            <option disabled value="">-- Chọn chi tiết --</option>
            <option
              v-for="t in chiTietLoaiOptions"
              :key="t.maLoai"
              :value="t.maLoai"
            >
              {{ t.chiTietLoai }}
            </option>
          </select>
        </div>

        <!-- GIÁ -->
        <div>
          <label>Giá</label>
          <input type="number" v-model="productForm.gia" />
        </div>
      </div>
    </section>

    <!-- ================================================= -->
    <!-- ============== CHI TIẾT SẢN PHẨM ================ -->
    <!-- ================================================= -->
    <section class="card">
      <h2>Chi tiết sản phẩm</h2>

      <div class="editor-wrapper">
        <div
          class="editor"
          contenteditable="true"
          ref="editorRef"
          @input="onEditorInput"
        ></div>
      </div>

      <p class="hint">
        ✍️ Bạn có thể click trực tiếp vào nội dung để chỉnh sửa (giống Word).
      </p>
    </section>

    <!-- ================================================= -->
    <!-- =================== ACTION ====================== -->
    <!-- ================================================= -->
    <section class="card">
      <div class="action-right">
        <button class="btn-update" @click="updateBasicInfo">
          💾 Cập nhật thông tin cơ bản
        </button>
      </div>
    </section>

    <!-- ================================================= -->
    <!-- ================= VARIANTS ===================== -->
    <!-- ================================================= -->
    <section class="card">
      <div class="row-between">
        <h2>Biến thể sản phẩm</h2>
        <button class="btn-update" @click="openAddColorOverlay">
          ➕ Thêm màu
        </button>
      </div>

      <!-- ===== VARIANT TABLE (GIỮ NGUYÊN) ===== -->
      <div
        v-for="color in variantsByColor"
        :key="color.mau"
        class="variant-color"
      >
        <!-- COLOR HEADER -->
        <div class="color-header">
          <img :src="color.image" class="color-image" alt="Ảnh màu" />

          <div class="color-info">
            <h3>Màu: {{ color.mau }}</h3>
          </div>
        </div>

        <!-- SIZE TABLE -->
        <table class="variant-table">
          <thead>
            <tr>
              <th>Mã biến thể</th>
              <th>Size</th>
              <th>Số lượng</th>
              <th>Trạng thái</th>
              <th>Hành Động</th>
            </tr>
          </thead>

          <tbody>
            <tr v-for="size in color.sizes" :key="size.maBienThe">
              <td>{{ size.maBienThe }}</td>
              <td>{{ size.size }}</td>

              <td>
                <span
                  :class="{
                    'text-danger': size.soLuong === 0,
                    'text-success': size.soLuong > 0,
                  }"
                >
                  {{ size.soLuong }}
                </span>
              </td>

              <td>
                <span class="status" :class="size.trangThai ? 'on' : 'off'">
                  {{ size.trangThai ? "Hoạt động" : "Ngưng bán" }}
                </span>
              </td>
              <td>
                <button class="btn-edit" @click="openEditVariant(size)">
                  ✏️ Chỉnh sửa
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div v-if="showEditVariantModal" class="overlay">
        <div class="modal">
          <h3>✏️ Sửa biến thể</h3>

          <label>Số lượng</label>
          <input type="number" v-model="editVariantForm.soluong" min="0" />

          <label>Trạng thái</label>
          <select v-model="editVariantForm.trangThai">
            <option :value="true">Hoạt động</option>
            <option :value="false">Ngưng bán</option>
          </select>

          <div class="actions">
            <button class="primary" @click="updateVariant">Lưu</button>
            <button class="danger" @click="showEditVariantModal = false">
              Hủy
            </button>
          </div>
        </div>
      </div>
    </section>

    <!-- ================= ADD COLOR OVERLAY ================= -->
    <transition name="fade">
      <div
        v-if="showAddColorOverlay"
        class="fixed inset-0 bg-black/60 flex items-start justify-center p-6 z-50"
      >
        <div
          class="bg-white rounded-2xl w-full max-w-4xl p-6 overflow-auto max-h-[90vh]"
        >
          <!-- Header -->
          <div class="flex items-center justify-between mb-4">
            <h3 class="text-xl font-semibold">Thêm màu & size</h3>
            <button
              type="button"
              @click="showAddColorOverlay = false"
              class="px-3 py-1 border rounded"
            >
              Đóng
            </button>
          </div>

          <!-- BODY -->
          <div class="space-y-6">
            <div
              v-for="(color, idx) in newColorForm.colors"
              :key="idx"
              class="border rounded-lg p-4"
            >
              <!-- Màu -->
              <div>
                <label class="text-sm text-gray-600">Màu</label>
                <select
                  v-model="color.maMs"
                  class="mt-2 w-full px-3 py-2 rounded-lg border"
                >
                  <option value="">Chọn màu</option>
                  <option v-for="c in Colors" :key="c.maMs" :value="c.maMs">
                    {{ c.tenMs }}
                  </option>
                </select>
              </div>

              <!-- Upload ảnh -->
              <div class="mt-3">
                <label class="text-sm text-gray-600">Ảnh màu</label>
                <input
                  type="file"
                  class="mt-2"
                  @change="(e) => onSelectImage(e, color)"
                />
              </div>

              <!-- Preview ảnh -->
              <div class="mt-3 grid grid-cols-3 gap-3">
                <div
                  v-for="(img, i) in color.urlImages"
                  :key="i"
                  class="relative aspect-square rounded-lg overflow-hidden border bg-gray-50"
                >
                  <img
                    :src="img.url"
                    class="absolute inset-0 w-full h-full object-cover"
                  />

                  <div
                    v-if="img.daiDienMau"
                    class="absolute top-1 right-1 bg-amber-400 text-xs px-2 py-0.5 rounded text-white font-semibold"
                  >
                    Màu
                  </div>

                  <div class="absolute bottom-1 left-1 right-1 flex gap-1">
                    <button
                      type="button"
                      @click="setDaiDienMau(color, i)"
                      class="flex-1 px-2 py-1 text-xs rounded bg-amber-50 border text-amber-700"
                    >
                      Đại diện màu
                    </button>
                    <button type="button" @click="setDaiDienSP(color, idx)">
                      Đại diện SP
                    </button>

                    <button
                      type="button"
                      @click="removeImage(color, i)"
                      class="flex-1 px-2 py-1 text-xs rounded bg-white border text-red-600"
                    >
                      Xóa
                    </button>
                  </div>
                </div>
              </div>

              <!-- Sizes -->
              <div class="mt-4">
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
                      class="w-28 px-2 py-2 rounded-lg border"
                    />

                    <button
                      type="button"
                      @click="removeSize(color, sidx)"
                      class="px-3 py-1 border rounded text-red-600"
                    >
                      Xóa
                    </button>
                  </div>

                  <button
                    type="button"
                    @click="addSize(color)"
                    class="mt-2 px-3 py-1 border rounded"
                  >
                    Thêm size
                  </button>
                </div>
              </div>
            </div>

            <button
              type="button"
              @click="addColor"
              class="px-3 py-1 border rounded"
            >
              Thêm màu
            </button>
          </div>

          <!-- Footer -->
          <div class="mt-6 flex justify-end gap-3">
            <button
              type="button"
              @click="showAddColorOverlay = false"
              class="px-4 py-2 border rounded"
            >
              Hủy
            </button>
            <button
              type="button"
              @click="submitAddColor"
              class="px-4 py-2 bg-blue-600 text-white rounded"
            >
              Xác nhận
            </button>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>
<script setup>
import { ref, reactive, computed, onMounted, watch, nextTick } from "vue";
import api from "@/api"; 
import { useRoute } from "vue-router";
/* ================== ADD COLOR OVERLAY ================== */
function addImage(color) {
  color.urlImages.push({
    url: "",
    daiDien: false,
    daiDienMau: false,
  });
}

function removeImage(color, index) {
  color.urlImages.splice(index, 1);
}

const newColorForm = ref({
  colors: [
    {
      maMs: null,
      urlImages: [],
      sizes: [],
    },
  ],
});
function addSize(color) {
  color.sizes.push({
    maKc: null,
    soluong: 0,
  });
}

function removeSize(color, index) {
  color.sizes.splice(index, 1);
}
function addColor() {
  newColorForm.value.colors.push({
    maMs: null,
    urlImages: [],
    sizes: [],
  });
}
async function submitAddColor() {
  try {
    const payload = {
      colors: newColorForm.value.colors,
    };

    const res = await api.post(
      `/variants/${maSp.value}`,
      payload
    );

    if (res.data.code === 1000) {
      alert("✅ Thêm biến thể thành công");

      // reload biến thể
      await loadVariants(maSp.value);

      showAddColorOverlay.value = false;

      // reset form
      newColorForm.value = {
        colors: [
          {
            maMs: null,
            urlImages: [],
            sizes: [],
          },
        ],
      };
    }
  } catch (err) {
    alert(err.response?.data?.message || "❌ Có lỗi xảy ra");
  }
}
async function onSelectImage(event, color) {
  const file = event.target.files[0];
  if (!file) return;

  try {
    const formData = new FormData();
    formData.append("file", file);

    const res = await api.post(
      "/files/images",
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }
    );

    if (res.data?.url) {
      color.urlImages.push({
        url: res.data.url,
        daiDien: false,
        daiDienMau: false,
      });
    } else {
      alert("❌ Upload ảnh thất bại");
    }
  } catch (err) {
    alert("❌ Lỗi upload ảnh");
    console.error(err);
  } finally {
    event.target.value = "";
  }
}
function setDaiDienMau(color, index) {
  color.urlImages.forEach((img, i) => {
    img.daiDienMau = i === index;
  });
}

function setDaiDienSP(color, index) {
  form.colors.forEach((color) => {
    color.urlImages.forEach((img) => {
      img.daiDien = false;
    });
  });

  form.colors[color].urlImages[index].daiDien = true;
}

/* ================== ROUTE ================== */
const route = useRoute();
const maSp = computed(() => route.params.id);

/* ================== STATE ================== */
const variants = ref([]); // danh sách biến thể phẳng
const allTypes = ref([]);

const selectedDoiTuong = ref("");
const selectedTenLoai = ref("");
const isInitializing = ref(true);

/* ===== Editor ===== */
const editorRef = ref(null);

/* ===== Variant Modal ===== */
const showAddColorOverlay = ref(false);
const Colors = ref([]);
const Sizes = ref([]);
const newVariantForm = ref({
  maMs: null,
  maKc: null,
  soLuong: 0,
  giaBan: 0,
});
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
const openAddColorOverlay = async () => {
  showAddColorOverlay.value = true;
  await loadColors();
  await loadSizes();
};

/* ================== PRODUCT FORM ================== */
const productForm = reactive({
  maSp: null,
  tenSp: "",
  gia: 0,
  moTa: "",
  maLoai: null,
});

/* ================== LOAD PRODUCT ================== */
async function loadProduct(id) {
  const res = await api.get(`/products/${id}`);

  const p = res.data.result;

  productForm.maSp = p.maSp;
  productForm.tenSp = p.tenSp;
  productForm.gia = p.gia;
  productForm.maLoai = p.maLoai;
  productForm.moTa = p.chiTiet;
}

/* ================== LOAD VARIANTS ================== */
async function loadVariants(maSp) {
  const res = await api.get(`/variants/${maSp}`);

  const list = res.data.result || [];

  variants.value = list.map((v) => ({
    maBienThe: v.maBienThe,
    mau: v.tenMs,
    image: v.urlImage,
    size: v.tenKc,
    soLuong: v.soluong,
    trangThai: v.trangThai,
    maMs: v.maMs,
    maKc: v.maKc,
  }));
}

/* ================== LOAD TYPES ================== */
async function loadTypes() {
  const res = await api.get("/types");
  allTypes.value = res.data.result || [];
}

/* ================== MAP TYPE ================== */
async function mapTypeToSelect() {
  if (!productForm.maLoai || !allTypes.value.length) return;

  const found = allTypes.value.find((t) => t.maLoai === productForm.maLoai);
  if (!found) return;

  selectedDoiTuong.value = found.doiTuong;
  await nextTick();

  selectedTenLoai.value = found.tenLoai;
  await nextTick();
}

/* ================== COMPUTED ================== */
const doiTuongOptions = computed(() => [
  ...new Set(allTypes.value.map((x) => x.doiTuong)),
]);

const tenLoaiOptions = computed(() => {
  if (!selectedDoiTuong.value) return [];
  return [
    ...new Set(
      allTypes.value
        .filter((x) => x.doiTuong === selectedDoiTuong.value)
        .map((x) => x.tenLoai)
    ),
  ];
});

const chiTietLoaiOptions = computed(() => {
  if (!selectedDoiTuong.value || !selectedTenLoai.value) return [];
  return allTypes.value.filter(
    (x) =>
      x.doiTuong === selectedDoiTuong.value &&
      x.tenLoai === selectedTenLoai.value
  );
});

/* ================== GROUP VARIANTS BY COLOR ================== */
const variantsByColor = computed(() => {
  if (!variants.value.length) return [];

  const map = {};

  variants.value.forEach((v) => {
    if (!map[v.mau]) {
      map[v.mau] = {
        mau: v.mau,
        image: v.image,
        sizes: [],
      };
    }

    map[v.mau].sizes.push({
      maBienThe: v.maBienThe,
      size: v.size,
      soLuong: v.soLuong,
      trangThai: v.trangThai, // 👈 THÊM
      maKc: v.maKc,
    });
  });

  return Object.values(map);
});

/* ================== WATCH ================== */
watch(selectedDoiTuong, () => {
  if (isInitializing.value) return;
  selectedTenLoai.value = "";
  productForm.maLoai = null;
});

watch(selectedTenLoai, () => {
  if (isInitializing.value) return;
  productForm.maLoai = null;
});

/* ================== EDITOR ================== */
watch(
  () => productForm.moTa,
  (html) => {
    if (editorRef.value && editorRef.value.innerHTML !== html) {
      editorRef.value.innerHTML = html || "";
    }
  },
  { immediate: true }
);

const onEditorInput = () => {
  productForm.moTa = editorRef.value.innerHTML;
};

/* ================== VARIANT FORM (REUSE CREATE) ================== */
function openAddColorModal() {
  newColorForm.value = {
    colors: [
      {
        maMs: null,
        urlImages: [],
        sizes: [],
      },
    ],
  };
  showAddColorOverlay.value = true;
}

/* ================== UPDATE BASIC INFO ================== */
const updateBasicInfo = async () => {
  try {
    if (!productForm.maSp || !productForm.maLoai) {
      alert("❌ Vui lòng nhập đầy đủ thông tin");
      return;
    }

    const payload = {
      tenSp: productForm.tenSp,
      maLoai: productForm.maLoai,
      gia: productForm.gia,
      chiTiet: productForm.moTa,
    };

    const res = await api.put(
      `/products/${productForm.maSp}`,
      payload
    );

    if (res.data.code === 1000) {
      alert("✅ Cập nhật sản phẩm thành công");
    } else {
      alert("❌ Cập nhật thất bại");
    }
  } catch (err) {
    console.error(err);
    alert("❌ Có lỗi xảy ra");
  }
};

/* ================== LIFECYCLE ================== */
onMounted(async () => {
  await loadTypes();

  if (!maSp.value) {
    console.error("❌ Không tìm thấy mã sản phẩm");
    return;
  }

  await loadProduct(maSp.value);
  await loadVariants(maSp.value);
  await mapTypeToSelect();

  isInitializing.value = false;
});
/* ================== ?? ================== */
const showEditVariantModal = ref(false);
const editingVariant = ref(null);

const editVariantForm = reactive({
  maBienThe: null,
  soluong: 0,
  trangThai: true,
});

function openEditVariant(size) {
  editingVariant.value = size;

  editVariantForm.maBienThe = size.maBienThe;
  editVariantForm.soluong = size.soLuong;
  editVariantForm.trangThai = size.trangThai;

  showEditVariantModal.value = true;
}

/* ================== ?? ================== */
async function updateVariant() {
  try {
    const res = await api.put(
      `/variants/${editVariantForm.maBienThe}`,
      {
        soluong: editVariantForm.soluong,
        trangThai: editVariantForm.trangThai,
      }
    );

    if (res.data.code === 1000) {
      // update lại UI
      editingVariant.value.soLuong = editVariantForm.soluong;
      editingVariant.value.trangThai = editVariantForm.trangThai;
      alert("✅ Cập nhật biến thể thành công");
      showEditVariantModal.value = false;
    } else {
      alert("❌ Cập nhật thất bại");
    }
  } catch (err) {
    if (err.response?.data?.message) {
      alert(`❌ ${err.response.data.message}`);
    } else {
      alert("❌ Có lỗi xảy ra, vui lòng thử lại");
    }
  }
}
</script>

<style scoped>
.page {
  max-width: 1000px;
  margin: auto;
  padding: 24px;
  font-family: Arial, sans-serif;
}
h1 {
  margin-bottom: 20px;
}
.card {
  background: #fff;
  padding: 16px;
  border: 1px solid #ddd;
  border-radius: 8px;
  margin-bottom: 20px;
}
.grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;
}
label {
  font-size: 13px;
  color: #555;
}
input,
textarea,
select {
  width: 100%;
  padding: 6px;
  margin-top: 4px;
}
.row-between {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.table-wrapper {
  max-height: 300px;
  overflow: auto;
}
table {
  width: 100%;
  border-collapse: collapse;
}
th,
td {
  border: 1px solid #ddd;
  padding: 6px;
  text-align: center;
}
tbody tr:hover {
  background: #f5f5f5;
  cursor: pointer;
}
img {
  width: 40px;
  height: 40px;
}
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
}
.modal {
  background: white;
  padding: 20px;
  width: 320px;
  border-radius: 8px;
}
.actions {
  display: flex;
  justify-content: space-between;
  margin-top: 12px;
}
.primary {
  background: #2563eb;
  color: white;
}
.danger {
  background: #dc2626;
  color: white;
}
.preview {
  border: 1px solid #ddd;
  padding: 12px;
  border-radius: 6px;
  max-height: 400px;
  overflow: auto;
}
.preview img {
  max-width: 100%;
}
.variant-color {
  border: 1px solid #e5e7eb;
  border-radius: 10px;
  padding: 16px;
  margin-bottom: 20px;
}

.color-header {
  display: flex;
  gap: 16px;
  align-items: center;
  margin-bottom: 12px;
}

.color-image {
  width: 90px;
  height: 90px;
  object-fit: cover;
  border-radius: 8px;
  border: 1px solid #ddd;
}

.color-info {
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.variant-table {
  width: 100%;
  border-collapse: collapse;
}

.variant-table th,
.variant-table td {
  padding: 10px;
  border-bottom: 1px solid #eee;
  text-align: center;
}

.status.on {
  color: #16a34a;
  font-weight: 600;
}

.status.off {
  color: #dc2626;
  font-weight: 600;
}

.btn-edit {
  background: #2563eb;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
}
.btn-update {
  background: #16a34a;
  color: white;
  border: none;
  padding: 10px 18px;
  border-radius: 8px;
  font-size: 14px;
  cursor: pointer;
}

.btn-update:hover {
  background: #15803d;
}
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal {
  background: #fff;
  padding: 20px;
  width: 400px;
  border-radius: 8px;
}
</style>
