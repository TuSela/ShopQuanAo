<script setup>
import { ref, onMounted } from "vue";
import api from "@/api";
import { Star } from 'lucide-vue-next';

const comments = ref([]);

const fetchComments = async () => {
  const res = await api.get("/comments");
  comments.value = res.data.result;
};

const showComment = async (id) => {
  try {
    await api.put(`/comments/${id}/enable`);
    fetchComments();
  } catch (e) {
    console.error("Lỗi hiện comment", e);
  }
};

const hideComment = async (id) => {
  try {
    await api.put(`/comments/${id}/disable`);
    fetchComments();
  } catch (e) {
    console.error("Lỗi ẩn comment", e);
  }
};

const deleteComment = async (id) => {
  if (!confirm("Xóa bình luận này?")) return;
  try {
    await api.delete(`/comments/${id}`);
    fetchComments();
  } catch (e) {
    console.error("Lỗi xóa comment", e);
  }
};

const statusLabel = (status) => {
  return status === "SHOW" ? "Hiện" : "Ẩn";
};

onMounted(fetchComments);
</script>

<template>
  <div class="bg-white rounded-xl shadow p-6">
    <h2 class="text-xl font-semibold mb-4">Quản lý bình luận</h2>

    <div class="overflow-x-auto">
      <table class="w-full text-sm border border-gray-200 rounded-lg overflow-hidden">
        <thead class="bg-gray-50 text-gray-700">
          <tr>
            <th class="px-4 py-3 text-left">ID</th>
            <th class="px-4 py-3 text-left">Người dùng</th>
            <th class="px-4 py-3 text-left">Nội dung</th>
<th class="px-4 py-3 text-center">
  <div class="inline-flex items-center gap-1 justify-center">
    <Star class="w-4 h-4 text-yellow-500" />
    <span class="text-sm">Số sao</span>
  </div>
</th>


            <th class="px-4 py-3 text-center">Trạng thái</th>
            <th class="px-4 py-3 text-center">Hành động</th>
          </tr>
        </thead>

        <tbody>
          <tr
            v-for="c in comments"
            :key="c.maBl"
            class="border-t hover:bg-gray-50 transition"
          >
            <!-- ID -->
            <td class="px-4 py-3 font-medium text-gray-700">
              #{{ c.maBl }}
            </td>

            <!-- USER -->
            <td class="px-4 py-3">
              <div class="flex items-center gap-3">
                <img
                  :src="c.users.avatar || '/avatar-default.png'"
                  class="w-9 h-9 rounded-full border object-cover"
                />
                <span class="font-medium">{{ c.users.hoten }}</span>
              </div>
            </td>

            <!-- CONTENT -->
            <td class="px-4 py-3 max-w-xs">
              <p
                class="truncate text-gray-600 cursor-pointer"
                :title="c.noiDung"
              >
                {{ c.noiDung }}
              </p>
            </td>

            <!-- RATE -->
            <td class="px-4 py-3 text-center font-semibold">
              {{ c.diemDanhGia }}
            </td>

            <!-- STATUS -->
            <td class="px-4 py-3 text-center">
<span
  class="inline-flex justify-center items-center
         min-w-[70px] px-3 py-1
         rounded-full text-xs font-semibold
         text-center"
  :class="c.trangThai === 'SHOW'
    ? 'bg-green-100 text-green-700'
    : 'bg-red-100 text-red-700'"
>
  {{ statusLabel(c.trangThai) }}
</span>

            </td>

            <!-- ACTION -->
            <td class="px-4 py-3 text-center space-x-2">
              <button
  v-if="c.trangThai === 'SHOW'"
  @click="hideComment(c.maBl)"
  class="min-w-[70px] px-3 py-1
         bg-yellow-100 text-yellow-700 rounded"
>
  Ẩn
</button>

<button
  v-else
  @click="showComment(c.maBl)"
  class="min-w-[70px] px-3 py-1
         bg-green-100 text-green-700 rounded"
>
  Hiện
</button>

<button
  @click="deleteComment(c.maBl)"
  class="min-w-[70px] px-3 py-1
          text-red-700 bg-red-100 rounded"
>
  Xóa
</button>
            </td>
          </tr>

          <tr v-if="comments.length === 0">
            <td colspan="6" class="text-center py-6 text-gray-400">
              Không có bình luận
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
