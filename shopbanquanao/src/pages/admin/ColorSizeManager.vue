<script setup>
import { ref, onMounted } from "vue";
import api from "@/api";

// ==== Lucide icons ====
import { Edit2, Save, PlusCircle, Palette, Ruler } from "lucide-vue-next";

/* ===== MÀU SẮC ===== */
const colors = ref([]);
const editingColorId = ref(null);
const editColorName = ref("");

const fetchColors = async () => {
  const res = await api.get("/colors");
  colors.value = res.data.result;
};
const newColorName = ref("");

const addColor = async () => {
  const name = newColorName.value.trim();
  if (!name) return alert("Tên màu không được để trống");

  if (colors.value.some(c => c.tenMs.toLowerCase() === name.toLowerCase()))
    return alert("Màu sắc đã tồn tại");

  try {
    await api.post("/colors", { tenMs: name });
    newColorName.value = "";
    fetchColors();
  } catch {
    alert("Thêm màu thất bại");
  }
};

const editColor = (c) => {
  editingColorId.value = c.maMs;
  editColorName.value = c.tenMs;
};

const saveColor = async (id) => {
  const newName = editColorName.value.trim();
  if (!newName) return alert("Tên màu không được để trống");

  if (colors.value.some(c => c.tenMs.toLowerCase() === newName.toLowerCase() && c.maMs !== id))
    return alert("Màu sắc đã tồn tại");

  try {
    await api.put(`/colors/${id}`, { tenMs: newName });
    editingColorId.value = null;
    fetchColors();
  } catch {
    alert("Sửa màu thất bại");
  }
};

/* ===== KÍCH CỠ ===== */
const sizes = ref([]);
const editingSizeId = ref(null);
const editSizeName = ref("");

const fetchSizes = async () => {
  const res = await api.get("/sizes");
  sizes.value = res.data.result;
};
const newSizeName = ref("");

const addSize = async () => {
  const name = newSizeName.value.trim();
  if (!name) return alert("Kích cỡ không được để trống");

  if (sizes.value.some(s => s.tenKc.toLowerCase() === name.toLowerCase()))
    return alert("Kích cỡ đã tồn tại");

  try {
    await api.post("/sizes", { tenKc: name });
    newSizeName.value = "";
    fetchSizes();
  } catch {
    alert("Thêm kích cỡ thất bại");
  }
};

const editSize = (s) => {
  editingSizeId.value = s.maKc;
  editSizeName.value = s.tenKc;
};

const saveSize = async (id) => {
  const newName = editSizeName.value.trim();
  if (!newName) return alert("Kích cỡ không được để trống");

  if (sizes.value.some(s => s.tenKc.toLowerCase() === newName.toLowerCase() && s.maKc !== id))
    return alert("Kích cỡ đã tồn tại");

  try {
    await api.put(`/sizes/${id}`, { tenKc: newName });
    editingSizeId.value = null;
    fetchSizes();
  } catch {
    alert("Sửa kích cỡ thất bại");
  }
};

onMounted(() => {
  fetchColors();
  fetchSizes();
});
</script>

<template>
  <div class="grid grid-cols-1 md:grid-cols-2 gap-6">

    <!-- ===== MÀU SẮC ===== -->
    <div class="bg-white rounded-xl shadow p-6">
      <div class="flex items-center justify-between mb-4">
        <h2 class="text-lg font-semibold flex items-center gap-2">
          <Palette class="w-5 h-5 text-purple-600" />
          Màu sắc
        </h2>

        <div class="flex gap-2">
          <input
            v-model="newColorName"
            placeholder="Tên màu mới"
            class="border rounded-lg px-3 py-1.5 text-sm
                   focus:ring-2 focus:ring-green-500"
          />
          <button
            @click="addColor"
            class="px-4 py-1.5 flex items-center gap-1 bg-green-600 hover:bg-green-700
                   text-white text-sm rounded-lg"
          >
            <PlusCircle class="w-4 h-4" />
            <span>Thêm</span>
          </button>
        </div>
      </div>

      <!-- Table -->
      <table class="w-full text-sm">
        <thead class="text-gray-500 border-b">
          <tr>
            <th class="px-4 py-2 text-left w-16">ID</th>
            <th class="px-4 py-2 text-left">Tên màu</th>
            <th class="px-4 py-2 text-center w-28">Hành động</th>
          </tr>
        </thead>

        <tbody class="divide-y">
          <tr
            v-for="c in colors"
            :key="c.maMs"
            class="hover:bg-gray-50"
            :class="editingColorId === c.maMs ? 'bg-yellow-50' : ''"
          >
            <td class="px-4 py-2 text-gray-600">#{{ c.maMs }}</td>

            <td class="px-4 py-2">
              <input
                v-if="editingColorId === c.maMs"
                v-model="editColorName"
                class="w-full border rounded-lg px-2 py-1 focus:ring-2 focus:ring-blue-500"
              />
              <span v-else>{{ c.tenMs }}</span>
            </td>

            <td class="px-4 py-2 text-center">
              <button
                v-if="editingColorId !== c.maMs"
                @click="editColor(c)"
                class="px-3 py-1 flex items-center justify-center gap-1 text-blue-600 hover:bg-blue-100 rounded"
              >
                <Edit2 class="w-4 h-4" />
                <span>Sửa</span>
              </button>

              <button
                v-else
                @click="saveColor(c.maMs)"
                class="px-3 py-1 flex items-center justify-center gap-1 text-green-700 bg-green-100 rounded"
              >
                <Save class="w-4 h-4" />
                <span>Lưu</span>
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- ===== KÍCH CỠ ===== -->
    <div class="bg-white rounded-xl shadow p-6">
      <div class="flex items-center justify-between mb-4">
        <h2 class="text-lg font-semibold flex items-center gap-2">
          <Ruler class="w-5 h-5 text-blue-600" />
          Kích cỡ
        </h2>

        <div class="flex gap-2">
          <input
            v-model="newSizeName"
            placeholder="Kích cỡ mới"
            class="border rounded-lg px-3 py-1.5 text-sm
                   focus:ring-2 focus:ring-green-500"
          />
          <button
            @click="addSize"
            class="px-4 py-1.5 flex items-center gap-1 bg-green-600 hover:bg-green-700
                   text-white text-sm rounded-lg"
          >
            <PlusCircle class="w-4 h-4" />
            <span>Thêm</span>
          </button>
        </div>
      </div>

      <!-- Table -->
      <table class="w-full text-sm">
        <thead class="text-gray-500 border-b">
          <tr>
            <th class="px-4 py-2 text-left w-16">ID</th>
            <th class="px-4 py-2 text-left">Kích cỡ</th>
            <th class="px-4 py-2 text-center w-28">Hành động</th>
          </tr>
        </thead>

        <tbody class="divide-y">
          <tr
            v-for="s in sizes"
            :key="s.maKc"
            class="hover:bg-gray-50"
            :class="editingSizeId === s.maKc ? 'bg-yellow-50' : ''"
          >
            <td class="px-4 py-2 text-gray-600">#{{ s.maKc }}</td>

            <td class="px-4 py-2">
              <input
                v-if="editingSizeId === s.maKc"
                v-model="editSizeName"
                class="w-full border rounded-lg px-2 py-1 focus:ring-2 focus:ring-blue-500"
              />
              <span v-else>{{ s.tenKc }}</span>
            </td>

            <td class="px-4 py-2 text-center">
              <button
                v-if="editingSizeId !== s.maKc"
                @click="editSize(s)"
                class="px-3 py-1 flex items-center justify-center gap-1 text-blue-600 hover:bg-blue-100 rounded"
              >
                <Edit2 class="w-4 h-4" />
                <span>Sửa</span>
              </button>

              <button
                v-else
                @click="saveSize(s.maKc)"
                class="px-3 py-1 flex items-center justify-center gap-1 text-green-700 bg-green-100 rounded"
              >
                <Save class="w-4 h-4" />
                <span>Lưu</span>
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

  </div>
</template>
