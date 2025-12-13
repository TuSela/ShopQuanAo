<template>
  <div class="p-6 max-w-4xl mx-auto font-sans">
    <h1 class="text-2xl font-bold mb-4">Admin — Thêm / Chỉnh sửa Sản phẩm</h1>

    <form @submit.prevent="onSubmit" class="space-y-6">
      <section class="grid grid-cols-2 gap-4">
        <div>
          <label class="block font-medium">Tên sản phẩm</label>
          <input
            v-model="form.tenSp"
            required
            class="w-full border rounded px-3 py-2"
          />
        </div>

        <div class="space-y-4">
          <!-- 1) Chọn đối tượng -->
          <div>
            <label class="block font-medium">Đối tượng</label>
            <select
              v-model="selectedDoiTuong"
              class="w-full border rounded px-3 py-2"
            >
              <option value="">-- Chọn đối tượng --</option>
              <option v-for="dt in doiTuongOptions" :key="dt" :value="dt">
                {{ dt }}
              </option>
            </select>
          </div>

          <!-- 2) Chọn tên loại -->
          <div>
            <label class="block font-medium">Tên loại</label>
            <select
              v-model="selectedTenLoai"
              class="w-full border rounded px-3 py-2"
              :disabled="!selectedDoiTuong"
            >
              <option value="">-- Chọn tên loại --</option>
              <option v-for="ten in tenLoaiOptions" :key="ten" :value="ten">
                {{ ten }}
              </option>
            </select>
          </div>

          <!-- 3) Chọn chi tiết loại -->
          <div>
            <label class="block font-medium">Chi tiết loại</label>
            <select
              v-model="form.maLoai"
              class="w-full border rounded px-3 py-2"
              :disabled="!selectedTenLoai"
            >
              <option value="">-- Chọn chi tiết loại --</option>
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

        <div>
          <label class="block font-medium">Giá</label>
          <input
            v-model.number="form.gia"
            type="number"
            min="0"
            class="w-full border rounded px-3 py-2"
          />
        </div>
      </section>

      <section>
        <label class="block font-medium mb-2"
          >Mô tả sản phẩm (hỗ trợ ảnh, tiêu đề, đoạn)</label
        >
        <!-- Simple rich editor using contenteditable + controls -->
        <div class="mb-2 space-x-2">
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
          <input
            type="file"
            accept="image/*"
            @change="insertImageToEditor"
            class="inline-block"
          />
        </div>
        <div
          ref="editor"
          contenteditable
          class="min-h-[160px] p-3 border rounded"
          @input="syncEditor"
        >
          <!-- initial html -->
          <h2>ĐẶC ĐIỂM SẢN PHẨM<br /><br /><br /><br /><br /><br /></h2>
        </div>
      </section>

      <section>
        <h2 class="font-semibold mb-2">Biến thể (màu & size)</h2>

        <div
          v-for="(color, idx) in form.colors"
          :key="idx"
          class="mb-4 border rounded p-3"
        >
          <!-- CHỌN MÀU -->
          <div class="flex items-center gap-2 mb-2">
            <label class="block font-medium">Chọn màu</label>
            <select
              v-model="color.maMs"
              class="w-full border rounded px-3 py-2"
            >
              <option disabled value="">Chọn màu</option>
              <option v-for="t in Colors" :key="t.maMs" :value="t.maMs">
                {{ t.tenMs }}
              </option>
            </select>

            <!-- UPLOAD ẢNH MÀU -->
            <input
              type="file"
              multiple
              @change="(e) => onColorImagesChange(e, idx)"
            />

            <button
              type="button"
              @click="removeColor(idx)"
              class="px-2 py-1 border rounded text-red-600"
            >
              Xóa
            </button>
          </div>

          <!-- LIST ẢNH MÀU -->
          <div class="flex gap-2 mb-2">
            <div v-for="(img, i) in color.urlImages" :key="i">
              <img :src="img" class="w-full h-full object-cover" />
            </div>
            <div class="absolute left-1 bottom-1 flex gap-1">
              <button
                type="button"
                @click="setDaiDien(idx, i)"
                class="px-2 py-1 text-xs border rounded bg-white/90"
              >
                Đại diện
              </button>
              <button
                type="button"
                @click="color.urlImages.splice(i, 1)"
                class="px-2 py-1 text-xs border rounded bg-white/90"
              >
                Xóa
              </button>
            </div>
          </div>

          <!-- SIZE CỦA TỪNG MÀU -->
          <div class="space-y-2">
            <div
              v-for="(s, sidx) in color.sizes"
              :key="sidx"
              class="flex gap-2 items-center"
            >
              <label class="block font-medium">Chọn size</label>
              <select v-model="s.maKc" class="w-full border rounded px-3 py-2">
                <option disabled value="">Chọn size</option>
                <option v-for="t in Sizes" :key="t.maKc" :value="t.maKc">
                  {{ t.tenKc }}
                </option>
              </select>

              <input
                v-model.number="s.soluong"
                type="number"
                min="0"
                placeholder="soluong"
                class="w-24 border rounded px-2 py-1"
              />

              <button
                type="button"
                @click="removeSize(idx, sidx)"
                class="px-2 py-1 border rounded text-red-600"
              >
                Xóa
              </button>
            </div>

            <button
              type="button"
              @click="addSize(idx)"
              class="px-3 py-1 border rounded"
            >
              Thêm size
            </button>
          </div>
        </div>

        <button
          type="button"
          @click="addColor"
          class="px-4 py-2 border rounded bg-gray-100"
        >
          Thêm màu
        </button>
      </section>

      <section>
        <h2 class="font-semibold">Ảnh đại diện / Gallery sản phẩm</h2>
        <input type="file" accept="image/*" @change="onMainImagesChange" />
        <div class="mt-2 flex gap-2 flex-wrap">
          <div
            v-for="(img, i) in mainImagesPreview"
            :key="i"
            class="w-28 h-28 border rounded overflow-hidden"
          >
            <img :src="img" class="w-full h-full object-cover" />
          </div>
        </div>
      </section>

      <div class="flex gap-3">
        <button type="submit" class="px-6 py-2 bg-blue-600 text-white rounded">
          Lưu sản phẩm
        </button>
        <button type="button" @click="preview" class="px-6 py-2 border rounded">
          Xem trước
        </button>
      </div>
    </form>

    <!-- Preview modal (simple) -->
    <div
      v-if="showPreview"
      class="fixed inset-0 bg-black/40 flex items-start justify-center p-6"
    >
      <div
        class="bg-white rounded w-full max-w-3xl p-6 overflow-auto max-h-[90vh]"
      >
        <button
          @click="showPreview = false"
          class="mb-4 px-3 py-1 border rounded"
        >
          Đóng
        </button>
        <h3 class="text-xl font-bold">Xem trước sản phẩm</h3>
        <pre class="whitespace-pre-wrap mt-2 bg-gray-50 p-3 rounded">{{
          previewData
        }}</pre>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed, watch } from "vue";
import axios from "axios";

/* ============================================================
    LOAD DỮ LIỆU BAN ĐẦU
============================================================ */
const allTypes = ref([]);
const Colors = ref([]);
const Sizes = ref([]);

onMounted(async () => {
  await Promise.all([loadTypes(), loadColors(), loadSizes()]);
});

const loadTypes = async () => {
  try {
    const res = await axios.get("http://localhost:8081/nhom19/types");
    allTypes.value = res.data.result || [];
  } catch (err) {
    console.error("Lỗi tải types:", err);
  }
};

const loadColors = async () => {
  try {
    const res = await axios.get("http://localhost:8081/nhom19/colors");
    Colors.value = res.data.result || [];
  } catch (err) {
    console.error("Lỗi tải colors:", err);
  }
};

const loadSizes = async () => {
  try {
    const res = await axios.get("http://localhost:8081/nhom19/sizes");
    Sizes.value = res.data.result || [];
  } catch (err) {
    console.error("Lỗi tải sizes:", err);
  }
};

/* ============================================================
    SELECT TỪ LOẠI - ĐỐI TƯỢNG
============================================================ */

const selectedDoiTuong = ref("");
const selectedTenLoai = ref("");

const doiTuongOptions = computed(() => {
  return [...new Set(allTypes.value.map((x) => x.doiTuong?.trim()))];
});

const tenLoaiOptions = computed(() => {
  if (!selectedDoiTuong.value) return [];
  return [
    ...new Set(
      allTypes.value
        .filter((x) => x.doiTuong.trim() === selectedDoiTuong.value.trim())
        .map((x) => x.tenLoai.trim())
    ),
  ];
});

const chiTietLoaiOptions = computed(() => {
  if (!selectedTenLoai.value) return [];
  return allTypes.value.filter(
    (x) =>
      x.tenLoai.trim() === selectedTenLoai.value.trim() &&
      x.doiTuong.trim() === selectedDoiTuong.value.trim()
  );
});

/* Reset */
watch(selectedDoiTuong, () => {
  selectedTenLoai.value = "";
  form.maLoai = "";
});
watch(selectedTenLoai, () => {
  form.maLoai = "";
});

/* ============================================================
    FORM SẢN PHẨM
============================================================ */

const form = reactive({
  tenSp: "",
  maLoai: "",
  gia: 0,
  chiTiet: "",
  colors: [], // [{ maMs, urlImages[], sizes: [] }]
  daiDien: "",
});

const mainImagesPreview = ref([]);

/* ============================================================
    RICH TEXT EDITOR
============================================================ */
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

/* ============================================================
    UPLOAD ẢNH (DÙNG URL, KHÔNG BASE64)
============================================================ */
async function uploadImage(file) {
  try {
    const fd = new FormData();
    fd.append("file", file);

    const res = await axios.post(
      "http://localhost:8081/nhom19/files/images",
      fd,
      {
        headers: { "Content-Type": "multipart/form-data" },
      }
    );

    return res.data.url;
  } catch (err) {
    console.error("Lỗi upload ảnh:", err);
    alert("Không thể upload ảnh!");
    return null;
  }
}

/* ============================================================
    ẢNH GALLERY CHÍNH
============================================================ */
// async function onMainImagesChange(e) {
//   const files = Array.from(e.target.files || []);

//   mainImagesPreview.value = [];

//   for (const f of files) {
//     const url = await uploadImage(f);
//     if (url) mainImagesPreview.value.push(url);
//   }
// }
async function onMainImagesChange(e) {
  const files = Array.from(e.target.files || []);

  if (files.length === 0) return;

  // chỉ lấy file cuối cùng
  const lastFile = files[files.length - 1];

  const url = await uploadImage(lastFile);

  if (url) {
    form.daiDien = url;
    mainImagesPreview.value = [url]; // RESET về đúng 1 ảnh
  }
}
/* ============================================================
    ẢNH MÀU
============================================================ */
async function onColorImagesChange(e, idx) {
  const files = Array.from(e.target.files || []);

  for (const f of files) {
    const formData = new FormData();
    formData.append("file", f);

    const res = await axios.post(
      "http://localhost:8081/nhom19/files/images",
      formData,
      { headers: { "Content-Type": "multipart/form-data" } }
    );
    const url = res.data.url;

    form.colors[idx].urlImages.push(url);
  }
}

function setDaiDien(colorIndex, imgIndex) {
  const imgs = form.colors[colorIndex].urlImages;

  // 1) Tìm ảnh đại diện hiện tại
  const oldIndex = imgs.findIndex((img) => img.daiDien);

  // 2) Nếu có ảnh đại diện cũ và KHÁC ảnh mới → bỏ chọn nó
  if (oldIndex !== -1 && oldIndex !== imgIndex) {
    imgs[oldIndex].daiDien = false;
  }

  // 3) Chọn ảnh mới nhất
  imgs[imgIndex].daiDien = true;
}

/* ============================================================
    COLORS + SIZES
============================================================ */

function addColor() {
  form.colors.push({
    maMs: "",
    urlImages: [],
    sizes: [],
  });
}

function removeColor(i) {
  form.colors.splice(i, 1);
}

function addSize(i) {
  form.colors[i].sizes.push({
    maKc: "",
    soluong: 0,
  });
}

function removeSize(i, si) {
  form.colors[i].sizes.splice(si, 1);
}

/* ============================================================
    SUBMIT
============================================================ */

async function onSubmit() {
  const payload = JSON.parse(JSON.stringify(form));

  try {
    const res = await axios.post(
      "http://localhost:8081/nhom19/products",
      payload
    );

    alert("Lưu sản phẩm thành công!");
  } catch (err) {
    console.error(err);
    alert("Lỗi khi lưu sản phẩm!");
  }
}

/* ============================================================
    PREVIEW JSON
============================================================ */
const showPreview = ref(false);
const previewData = ref("");

function preview() {
  previewData.value = JSON.stringify({ ...form }, null, 2);
  showPreview.value = true;
}

onMounted(() => {
  if (editor.value) form.chiTiet = editor.value.innerHTML;
});
</script>

<style scoped>
/* small utility styles (you can replace by tailwind in real project) */
.font-sans {
  font-family: Inter, ui-sans-serif, system-ui, -apple-system, "Segoe UI",
    Roboto, "Helvetica Neue", Arial;
}
</style>
