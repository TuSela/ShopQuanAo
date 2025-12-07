<template>
  <div class="max-w-5xl mx-auto p-6 bg-gray-50 rounded-2xl shadow-lg">
    <div class="flex items-center justify-between mb-6">
      <h2 class="text-2xl font-semibold">Thêm sản phẩm mới (Admin)</h2>
      <span
        v-if="message"
        :class="message.type === 'error' ? 'text-red-600' : 'text-green-600'"
        class="text-sm font-medium"
      >
        {{ message.text }}
      </span>
    </div>

    <form @submit.prevent="handleSubmit" class="space-y-6">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label class="form-label"
            >Tên sản phẩm <span class="text-red-500">*</span></label
          >
          <input
            class="form-input"
            v-model="tenSp"
            placeholder="Nhập tên sản phẩm"
            aria-label="Tên sản phẩm"
          />
          <p v-if="!tenSp && submitting" class="text-xs text-red-500 mt-1">
            Tên sản phẩm bắt buộc
          </p>
        </div>

        <div>
          <label class="form-label">Giá</label>
          <input
            type="number"
            class="form-input"
            v-model.number="gia"
            min="0"
            placeholder="0"
          />
        </div>

        <div>
          <label class="form-label"
            >Loại sản phẩm <span class="text-red-500">*</span></label
          >
          <select
            class="form-input"
            v-model.number="maLoai"
            aria-label="Loại sản phẩm"
          >
            <option :value="null">-- chọn --</option>
            <option v-for="t in types" :key="t.ma_loai" :value="t.ma_loai">
              {{ t.ten_loai }}
            </option>
          </select>
        </div>

        <div class="md:col-span-2">
          <label class="form-label">Mô tả</label>
          <textarea
            class="form-input"
            rows="4"
            v-model="chiTiet"
            placeholder="Mô tả ngắn về sản phẩm"
          ></textarea>
        </div>
      </div>

      <hr class="border-t" />

      <div class="flex items-center justify-between">
        <h3 class="text-lg font-medium">Biến thể (Variants)</h3>
        <button type="button" @click="addVariant" class="btn-primary">
          + Thêm biến thể
        </button>
      </div>

      <div class="space-y-4">
        <div
          v-for="(v, idx) in variants"
          :key="v.id"
          class="bg-white border rounded-lg p-4 shadow-sm"
        >
          <div class="flex items-start justify-between gap-4">
            <div class="flex-1 grid grid-cols-1 sm:grid-cols-3 gap-3">
              <div>
                <label class="block text-sm text-gray-600">Màu</label>
                <select class="form-input" v-model.number="v.maMs">
                  <option :value="null">-- màu --</option>
                  <option v-for="c in colors" :key="c.ma_ms" :value="c.ma_ms">
                    {{ c.ten_ms }}
                  </option>
                </select>
              </div>

              <div>
                <label class="block text-sm text-gray-600">Kích cỡ</label>
                <select class="form-input" v-model.number="v.maKc">
                  <option :value="null">-- kích cỡ --</option>
                  <option v-for="s in sizes" :key="s.ma_kc" :value="s.ma_kc">
                    {{ s.ten_kc }}
                  </option>
                </select>
              </div>

              <div>
                <label class="block text-sm text-gray-600">Số lượng</label>
                <input
                  type="number"
                  class="form-input"
                  v-model.number="v.soluong"
                  min="0"
                />
              </div>
            </div>

            <div class="ml-4 text-right flex-shrink-0">
              <button
                type="button"
                @click="removeVariant(idx)"
                class="text-sm text-red-600 hover:underline"
              >
                Xóa
              </button>
            </div>
          </div>

          <div class="mt-4">
            <label class="form-label">Ảnh cho biến thể</label>

            <div class="flex items-center gap-3">
              <label
                class="inline-flex items-center gap-2 px-3 py-2 border rounded-md cursor-pointer bg-white hover:shadow-sm"
              >
                <input
                  type="file"
                  accept="image/*"
                  multiple
                  class="hidden"
                  @change="(e) => handleFilesChange(idx, e)"
                />
                <span class="text-sm">Chọn ảnh</span>
              </label>
              <span class="text-xs text-gray-500"
                >(Bạn có thể chọn nhiều ảnh cho mỗi biến thể)</span
              >
            </div>

            <div class="mt-3 grid grid-cols-2 sm:grid-cols-4 gap-3">
              <div
                v-for="(file, fi) in v.images"
                :key="fi"
                class="relative border rounded overflow-hidden"
              >
                <img
                  :src="file.preview"
                  alt="preview"
                  class="w-full h-28 object-cover"
                  @load="revokeObjectURL(file)"
                />

                <div class="p-2">
                  <div class="text-xs truncate">{{ file.file.name }}</div>

                  <div class="mt-2 flex items-center justify-between">
                    <label
                      class="inline-flex items-center gap-1 text-xs bg-white/80 px-2 py-1 rounded"
                    >
                      <input
                        type="radio"
                        name="rep"
                        :value="`image_${idx}_${fi}`"
                        v-model="repField"
                      />
                      <span>Ảnh đại diện</span>
                    </label>

                    <button
                      type="button"
                      @click="removeImage(idx, fi)"
                      class="text-xs text-red-600"
                    >
                      Xóa
                    </button>
                  </div>
                </div>

                <div
                  v-if="repField === `image_${idx}_${fi}`"
                  class="absolute top-2 left-2 bg-green-600 text-white text-xs px-2 py-0.5 rounded"
                >
                  Đại diện
                </div>
              </div>

              <div
                v-if="!v.images.length"
                class="col-span-full text-xs text-gray-500"
              >
                Chưa có ảnh cho biến thể này
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="flex items-center gap-3">
        <button :disabled="submitting" class="btn-success">
          {{ submitting ? "Đang gửi..." : "Tạo sản phẩm" }}
        </button>

        <button
          type="button"
          @click="resetVariants"
          class="px-4 py-2 border rounded-md text-sm"
        >
          Reset biến thể
        </button>

        <div class="ml-auto text-sm text-gray-500">
          Tổng biến thể: <strong>{{ variants.length }}</strong>
        </div>
      </div>
    </form>

    <div class="mt-6 text-sm text-gray-500 bg-white p-4 rounded-md border">
      <strong>Lưu ý:</strong>
      Component này gửi <code>multipart/form-data</code> với field
      <code>product</code> (JSON) và các file có tên
      <code>image_&lt;variantIndex&gt;_&lt;fileIndex&gt;</code>. Kèm theo
      <code>images_meta</code> JSON mô tả mapping file → variant + chỉ số
      <code>dai_dien</code>.
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

function cryptoRandomId() {
  return Math.random().toString(36).slice(2, 9);
}

// product basic
const tenSp = ref("");
const gia = ref(0);
const maLoai = ref(null);
const chiTiet = ref("");

// lookups (mock). Thay bằng API call nếu cần
const types = ref([
  { ma_loai: 1, ten_loai: "Áo nam" },
  { ma_loai: 2, ten_loai: "Quần nam" },
  { ma_loai: 3, ten_loai: "Phụ kiện" },
]);
const colors = ref([
  { ma_ms: 1, ten_ms: "Đen" },
  { ma_ms: 2, ten_ms: "Trắng" },
  { ma_ms: 3, ten_ms: "Xanh" },
]);
const sizes = ref([
  { ma_kc: 1, ten_kc: "S" },
  { ma_kc: 2, ten_kc: "M" },
  { ma_kc: 3, ten_kc: "L" },
]);

// variants
const variants = ref([
  { id: cryptoRandomId(), maMs: null, maKc: null, soluong: 0, images: [] },
]);

const repField = ref(null);
const submitting = ref(false);
const message = ref(null);

// helper: add/remove variant
function addVariant() {
  variants.value.push({
    id: cryptoRandomId(),
    maMs: null,
    maKc: null,
    soluong: 0,
    images: [],
  });
}
function removeVariant(index) {
  const v = variants.value[index];
  v.images.forEach((f) => URL.revokeObjectURL(f.preview));
  if (repField.value && repField.value.startsWith(`image_${index}_`))
    repField.value = null;
  variants.value.splice(index, 1);
}
function resetVariants() {
  variants.value.forEach((v) =>
    v.images.forEach((f) => URL.revokeObjectURL(f.preview))
  );
  variants.value = [
    { id: cryptoRandomId(), maMs: null, maKc: null, soluong: 0, images: [] },
  ];
  repField.value = null;
}

// file handling: we store objects { file: File, preview: objectURL }
function handleFilesChange(variantIndex, evt) {
  const files = Array.from(evt.target.files || []);
  if (!files.length) return;
  const wrapped = files.map((f) => ({
    file: f,
    preview: URL.createObjectURL(f),
  }));
  variants.value[variantIndex].images.push(...wrapped);
}

// remove image
function removeImage(variantIndex, fileIndex) {
  const img = variants.value[variantIndex].images[fileIndex];
  if (img) URL.revokeObjectURL(img.preview);
  const field = `image_${variantIndex}_${fileIndex}`;
  if (repField.value === field) repField.value = null;
  variants.value[variantIndex].images.splice(fileIndex, 1);
}

// revoke after image loaded (safe)
function revokeObjectURL(wrapped) {
  if (wrapped && wrapped.preview) {
    // keep for a bit; we already revoke on removeVariant/removeImage
  }
}

function setRepField(field) {
  repField.value = field;
}

// submit
async function handleSubmit() {
  message.value = null;
  if (!tenSp.value) {
    message.value = { type: "error", text: "Tên sản phẩm bắt buộc" };
    return;
  }
  if (!maLoai.value) {
    message.value = { type: "error", text: "Chọn loại sản phẩm" };
    return;
  }

  submitting.value = true;

  const productJson = {
    tenSp: tenSp.value,
    gia: gia.value,
    ma_loai: maLoai.value,
    chi_tiet: chiTiet.value,
    variants: variants.value.map((v) => ({
      ma_ms: v.maMs,
      ma_kc: v.maKc,
      soluong: v.soluong,
    })),
  };

  const fd = new FormData();
  fd.append("product", JSON.stringify(productJson));

  const imagesMeta = [];

  variants.value.forEach((v, variantIndex) => {
    v.images.forEach((wrapped, fileIndex) => {
      const field = `image_${variantIndex}_${fileIndex}`;
      fd.append(field, wrapped.file, wrapped.file.name);
      imagesMeta.push({
        field,
        variantIndex,
        dai_dien: field === repField.value ? 1 : 0,
      });
    });
  });

  fd.append("images_meta", JSON.stringify(imagesMeta));

  try {
    const res = await fetch("/admin/products", { method: "POST", body: fd });

    if (!res.ok) {
      const text = await res.text();
      throw new Error(text || "Lỗi server");
    }

    const data = await res.json();
    message.value = {
      type: "success",
      text: "Tạo sản phẩm thành công (id: " + (data.maSp || "n/a") + ")",
    };

    // reset
    tenSp.value = "";
    gia.value = 0;
    maLoai.value = null;
    chiTiet.value = "";
    resetVariants();
  } catch (err) {
    console.error(err);
    message.value = { type: "error", text: "Lỗi khi gửi: " + err.message };
  } finally {
    submitting.value = false;
  }
}
</script>

<style scoped>
.form-label {
  @apply block text-sm font-medium text-gray-700 mb-1;
}
.form-input {
  @apply w-full border border-gray-200 rounded-lg p-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent bg-white;
}
.btn-primary {
  @apply px-3 py-2 rounded bg-blue-600 text-white text-sm hover:bg-blue-700;
}
.btn-success {
  @apply px-5 py-2 rounded bg-green-600 text-white text-sm hover:bg-green-700 disabled:opacity-60;
}
</style>
