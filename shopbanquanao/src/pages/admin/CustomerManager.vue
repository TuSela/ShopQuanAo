<script setup>
import { ref, onMounted } from "vue";
import api from "@/api"; 
import defaultAvatar from "@/assets/avatar/default-avatar.png";
import { User, Loader2, Lock, Unlock } from 'lucide-vue-next';


const users = ref([]);
const loading = ref(false);
const error = ref(null);

const loadUsers = async () => {
  loading.value = true;
  try {
    const res = await api.get("/users");
    users.value = res.data.result;
  } catch (err) {
    console.error("Lỗi load users:", err);
    error.value = "Không thể tải danh sách người dùng";
  } finally {
    loading.value = false;
  }
};
const disableUser = async (id) => {
  if (!confirm("Bạn chắc chắn muốn KHÓA tài khoản này?")) return;

  try {
    await api.put(`/users/${id}/disable`);
    alert("Đã khóa tài khoản");
    loadUsers(); // reload danh sách
  } catch (err) {
    console.error(err);
    alert("Khóa tài khoản thất bại");
  }
};

const enableUser = async (id) => {
  if (!confirm("Bạn chắc chắn muốn MỞ tài khoản này?")) return;

  try {
    await api.put(`/users/${id}/enable`);
    alert("Đã mở tài khoản");
    loadUsers();
  } catch (err) {
    console.error(err);
    alert("Mở tài khoản thất bại");
  }
};


onMounted(() => {
  loadUsers();
});
</script>


<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-6 flex items-center gap-2">
      <User class="w-6 h-6" /> Quản lý khách hàng
    </h1>

    <!-- LOADING -->
    <div v-if="loading" class="py-10 text-center text-gray-500 flex justify-center items-center gap-2">
      <Loader2 class="w-5 h-5 animate-spin" /> Đang tải danh sách khách hàng...
    </div>

    <!-- ERROR -->
    <div v-else-if="error" class="py-6 text-center text-red-500">
      {{ error }}
    </div>

    <!-- TABLE -->
    <div v-else class="bg-white rounded-xl shadow overflow-hidden">
      <table class="w-full text-sm">
        <thead class="bg-gray-50 text-gray-700">
          <tr>
            <th class="px-4 py-3">ID</th>
            <th class="px-4 py-3">Khách hàng</th>
            <th class="px-4 py-3">Email</th>
            <th class="px-4 py-3">SĐT</th>
            <th class="px-4 py-3">Giới tính</th>
            <th class="px-4 py-3">Ngày sinh</th>
            <th class="px-4 py-3">Trạng thái</th>
            <th class="px-4 py-3 text-center">Hành động</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="u in users" :key="u.maTk" class="border-t hover:bg-gray-50 transition">
            <td class="px-4 py-3 text-center font-semibold">#{{ u.maTk }}</td>

            <!-- USER -->
            <td class="px-4 py-3 flex items-center gap-3">
              <img
                :src="u.avatar || defaultAvatar"
                @error="($event.target.src = defaultAvatar)"
                class="w-10 h-10 rounded-full object-cover border shrink-0 bg-gray-100"
              />
              <div>
                <div class="font-medium">{{ u.hoten }}</div>
                <div class="text-xs text-gray-500">{{ u.gioiTinh }}</div>
              </div>
            </td>

            <td class="px-4 py-3">{{ u.email }}</td>
            <td class="px-4 py-3">{{ u.sdt }}</td>
            <td class="px-4 py-3 text-center">{{ u.gioiTinh }}</td>
            <td class="px-4 py-3 text-center">{{ u.ngaySinh }}</td>

            <!-- STATUS -->
            <td class="px-4 py-3 text-center">
              <span
                class="px-3 py-1 rounded-full text-xs font-semibold"
                :class="u.trangThai ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'"
              >
                {{ u.trangThai ? 'Hoạt động' : 'Khóa' }}
              </span>
            </td>

            <!-- ACTION -->
            <td class="px-4 py-3 text-center">
              <button
                v-if="u.trangThai"
                @click="disableUser(u.maTk)"
                class="px-3 py-1.5 text-xs rounded-lg bg-red-500 hover:bg-red-600 text-white transition flex items-center gap-1 justify-center"
              >
                <Lock class="w-4 h-4" /> Khóa
              </button>

              <button
                v-else
                @click="enableUser(u.maTk)"
                class="px-3 py-1.5 text-xs rounded-lg bg-green-500 hover:bg-green-600 text-white transition flex items-center gap-1 justify-center"
              >
                <Unlock class="w-4 h-4" /> Mở
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

