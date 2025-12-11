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

        <div>
          <label class="block font-medium">Số lượng tổng</label>
          <input
            v-model.number="form.soLuong"
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
          <p>Nhập mô tả ở đây...</p>
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
              <option disabled value="">Chọn màu</option>
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
            <div
              v-for="(img, i) in color.urlImages"
              :key="i"
              class="w-20 h-20 overflow-hidden border rounded"
            >
              <img :src="img" class="w-full h-full object-cover" />
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
                <option disabled value="">Chọn màu</option>
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
        <input
          type="file"
          accept="image/*"
          multiple
          @change="onMainImagesChange"
        />
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

const allTypes = ref([]);

// selections
const selectedDoiTuong = ref("");
const selectedTenLoai = ref("");

// load API
onMounted(async () => {
  const res = await axios.get("http://localhost:8081/nhom19/types");
  allTypes.value = res.data.result;
});

// 1) danh sách đối tượng
const doiTuongOptions = computed(() => {
  const set = new Set(allTypes.value.map((x) => x.doiTuong?.trim()));
  return [...set];
});

// 2) danh sách tên loại theo đối tượng
const tenLoaiOptions = computed(() => {
  if (!selectedDoiTuong.value) return [];

  const list = allTypes.value
    .filter((x) => x.doiTuong?.trim() === selectedDoiTuong.value.trim())
    .map((x) => x.tenLoai.trim());

  return [...new Set(list)];
});

// 3) danh sách chi tiết loại theo tên loại
const chiTietLoaiOptions = computed(() => {
  if (!selectedTenLoai.value) return [];

  return allTypes.value.filter(
    (x) =>
      x.tenLoai.trim() === selectedTenLoai.value.trim() &&
      x.doiTuong.trim() === selectedDoiTuong.value.trim()
  );
});

// reset logic
watch(selectedDoiTuong, () => {
  selectedTenLoai.value = "";
  form.maLoai = "";
});

watch(selectedTenLoai, () => {
  form.maLoai = "";
});

const Colors = ref([]);
onMounted(async () => {
  await loadColors();
});

const loadColors = async () => {
  try {
    const res = await axios.get("http://localhost:8081/nhom19/colors");

    // API trả về dạng: { code, message, result: [...] }
    Colors.value = res.data.result;
  } catch (error) {
    console.error("Lỗi khi tải màu sản phẩm:", error);
  }
};

const Sizes = ref([]);
onMounted(async () => {
  await loadSizes();
});

const loadSizes = async () => {
  try {
    const res = await axios.get("http://localhost:8081/nhom19/sizes");

    // API trả về dạng: { code, message, result: [...] }
    Sizes.value = res.data.result;
  } catch (error) {
    console.error("Lỗi khi tải size sản phẩm:", error);
  }
};
// form
const form = reactive({
  tenSp: "",
  maLoai: "", // giá trị cuối chọn từ chi tiết loại
  gia: 0,
  soLuong: 0,
  chiTiet: "",
  colors: [],
});

// editor
const editor = ref(null);
const mainImages = ref([]);
const mainImagesPreview = ref([]);
const showPreview = ref(false);
const previewData = ref("");

function execCmd(cmd) {
  document.execCommand(cmd);
}

function insertHeading() {
  document.execCommand("formatBlock", false, "h2");
}

function insertImageToEditor(e) {
  const f = e.target.files?.[0];
  if (!f) return;
  const reader = new FileReader();
  reader.onload = () => {
    document.execCommand("insertImage", false, reader.result);
    syncEditor();
  };
  reader.readAsDataURL(f);
}

function syncEditor() {
  form.descriptionHtml = editor.value?.innerHTML || "";
}

function addColor() {
  form.colors.push({ maMs: null, tenMs: "", urlImages: [], sizes: [] });
}

function removeColor(i) {
  form.colors.splice(i, 1);
}

function addSize(i) {
  form.colors[i].sizes.push({ maKc: null, tenKc: "", soluong: 0 });
}

function removeSize(ci, si) {
  form.colors[ci].sizes.splice(si, 1);
}

function onMainImagesChange(e) {
  const files = Array.from(e.target.files || []);
  mainImages.value = files;
  mainImagesPreview.value = [];
  files.forEach((f) => {
    const r = new FileReader();
    r.onload = () => mainImagesPreview.value.push(r.result);
    r.readAsDataURL(f);
  });
}

function onColorImagesChange(e, idx) {
  const files = Array.from(e.target.files || []);
  files.forEach((f) => {
    const r = new FileReader();
    r.onload = () => form.colors[idx].urlImages.push(r.result);
    r.readAsDataURL(f);
  });
}

async function onSubmit() {
  const payload = {
    tenSp: form.tenSp,
    maLoai: form.maLoai,
    gia: form.gia,
    soLuong: form.soLuong,
    chiTiet: form.descriptionHtml,
    colors: form.colors,
    gallery: mainImagesPreview.value,
  };

  try {
    const res = await fetch("/api/admin/products", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });

    if (!res.ok) throw new Error(await res.text());

    alert("Đã lưu sản phẩm");
  } catch (err) {
    alert("Lỗi khi lưu: " + err.message);
  }
}

function preview() {
  previewData.value = JSON.stringify(
    { ...form, gallery: mainImagesPreview.value },
    null,
    2
  );
  showPreview.value = true;
}

onMounted(() => {
  if (editor.value) form.descriptionHtml = editor.value.innerHTML;
});
</script>

<style scoped>
/* small utility styles (you can replace by tailwind in real project) */
.font-sans {
  font-family: Inter, ui-sans-serif, system-ui, -apple-system, "Segoe UI",
    Roboto, "Helvetica Neue", Arial;
}
</style>
