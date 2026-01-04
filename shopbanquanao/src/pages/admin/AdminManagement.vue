<template>
  <div class="p-6 space-y-10">

    <!-- ===== DANH SÁCH ADMIN ===== -->
    <section>
      <h2 class="text-2xl font-bold mb-4">Quản lý Admin</h2>
      <div class="overflow-x-auto border rounded-lg">
        <table class="min-w-full bg-white">
          <thead class="bg-gray-100">
            <tr>
              <th class="px-4 py-2 text-left">ID</th>
              <th class="px-4 py-2 text-left">Username</th>
              <th class="px-4 py-2 text-left">Roles</th>
              <th class="px-4 py-2 text-left">Trạng thái</th>
              <th class="px-4 py-2 text-left">Hành động</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="admin in admins" :key="admin.maTk" class="border-t hover:bg-gray-50">
              <td class="px-4 py-2">{{ admin.maTk }}</td>
              <td class="px-4 py-2">{{ admin.username }}</td>
              <td class="px-4 py-2 flex flex-wrap gap-1">
                <span v-for="role in admin.roles" :key="role.name" class="px-2 py-1 rounded-full bg-blue-100 text-blue-800 text-xs font-semibold">
                  {{ role.name }}
                </span>
              </td>
              <td class="px-4 py-2">
  <span 
    :class="admin.trangThai ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'" 
    class="px-2 py-1 rounded-full text-sm font-medium"
  >
    {{ admin.trangThai ? 'Hoạt động' : 'Đã khóa' }}
  </span>
</td>
<td class="px-4 py-2 space-x-1 flex flex-wrap">
  <button 
    v-if="admin.trangThai" 
    @click="disableAdmin(admin.maTk)" 
    class="px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600 transition"
  >
    Khóa
  </button>
  <button 
    v-else 
    @click="enableAdmin(admin.maTk)" 
    class="px-2 py-1 bg-green-500 text-white rounded hover:bg-green-600 transition"
  >
    Mở
  </button>
  <button 
    @click="editAdmin(admin)" 
    class="px-2 py-1 bg-blue-500 text-white rounded hover:bg-blue-600 transition"
  >
    Sửa
  </button>
</td>

            </tr>
          </tbody>
        </table>
      </div>
    </section>

    <!-- ===== FORM ADMIN + ROLES CẠNH NHAU ===== -->
<section class="flex flex-col md:flex-row gap-6">
  <!-- Form Admin 50% -->
  <div class="bg-white p-6 rounded-lg shadow flex-1 flex flex-col">
    <h3 class="text-xl font-semibold mb-4">{{ editingAdmin ? 'Cập nhật' : 'Tạo mới' }} Admin</h3>
    <input v-model="form.username" placeholder="Username" class="w-full border px-3 py-2 rounded mb-2 focus:outline-none focus:ring-2 focus:ring-blue-400"/>
    <input v-model="form.password" type="password" placeholder="Password" class="w-full border px-3 py-2 rounded mb-2 focus:outline-none focus:ring-2 focus:ring-blue-400"/>

    <label class="font-semibold mb-1">Roles:</label>
    <div class="flex flex-wrap gap-2 mb-4">
      <label v-for="role in roles" :key="role.name" class="flex items-center gap-2 px-2 py-1 border rounded cursor-pointer hover:bg-gray-100">
        <input type="checkbox" :value="role.name" v-model="form.roles"/>
        <span class="text-sm">{{ role.name }}</span>
      </label>
    </div>

    <div class="flex gap-2">
      <button @click="saveAdmin" class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition">
        {{ editingAdmin ? 'Cập nhật' : 'Tạo mới' }}
      </button>
      <button v-if="editingAdmin" @click="cancelEdit" class="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400 transition">Hủy</button>
    </div>
  </div>

  <!-- Roles 50% -->
<div class="bg-white p-4 rounded-lg shadow flex-1 flex flex-col">
  <h3 class="font-semibold mb-2">Roles</h3>
  <ul class="space-y-2 flex-1 overflow-y-auto max-h-[400px]">
    <li v-for="role in roles" :key="role.name" class="flex justify-between items-center bg-gray-50 px-2 py-1 rounded hover:bg-gray-100">
      <span class="text-sm">{{ role.name }}</span>
      <div class="flex gap-2">
        <button 
          @click="editRole(role)" 
          class="px-3 py-1 bg-blue-500 text-white text-sm rounded hover:bg-blue-600 transition"
        >Sửa</button>
        <button 
          @click="deleteRole(role.name)" 
          class="px-3 py-1 bg-red-500 text-white text-sm rounded hover:bg-red-600 transition"
        >Xóa</button>
      </div>
    </li>
  </ul>
</div>

</section>


    <!-- ===== DANH SÁCH PERMISSIONS ===== -->
    <section>
      <h2 class="text-2xl font-bold mb-4">Danh sách Permissions</h2>
      <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3">
        <div v-for="perm in permissions" :key="perm.name" class="bg-gray-50 border px-3 py-2 rounded text-sm font-medium text-gray-800">
          {{ perm.name }} - {{ perm.description }}
        </div>
      </div>
    </section>

  </div>
</template>


<script setup>
import { ref, onMounted } from 'vue';
import api from "@/api";

const token = localStorage.getItem('token');
const headers = { Authorization: `Bearer ${token}` };

const admins = ref([]);
const roles = ref([]);
const permissions = ref([]);

const form = ref({ username: '', password: '', roles: [] });
const editingAdmin = ref(null);

const fetchAdmins = async () => {
  const res = await api.get('/admins', { headers });
  admins.value = res.data.result; // dùng luôn trangThai từ API
};

const fetchRoles = async () => {
  const res = await api.get('/roles', { headers });
  roles.value = res.data.result || [];
};

const fetchPermissions = async () => {
  const res = await api.get('/permissions', { headers });
  permissions.value = res.data.result || [];
};

// Khóa / Mở admin
const disableAdmin = async (maTk) => {
  await api.put(`/admins/${maTk}/disable`, {}, { headers });
  // cập nhật trạng thái trực tiếp trong UI
  const admin = admins.value.find(a => a.maTk === maTk);
  if (admin) admin.trangThai = false;
};

const enableAdmin = async (maTk) => {
  await api.put(`/admins/${maTk}/enable`, {}, { headers });
  const admin = admins.value.find(a => a.maTk === maTk);
  if (admin) admin.trangThai = true;
};


// Tạo / Cập nhật admin
const saveAdmin = async () => {
  if (editingAdmin.value) {
    await api.put(`/admins/${editingAdmin.value.maTk}`, form.value, { headers });
  } else {
    await api.post(`/admins`, form.value, { headers });
  }
  form.value = { username: '', password: '', roles: [] };
  editingAdmin.value = null;
  fetchAdmins();
};

const editAdmin = (admin) => {
  editingAdmin.value = admin;
  form.value = { username: admin.username, password: '', roles: admin.roles.map(r => r.name) };
};
const cancelEdit = () => {
  editingAdmin.value = null;
  form.value = { username: '', password: '', roles: [] };
};

// Role
const editRole = async (role) => {
  const newName = prompt("Tên Role:", role.name) || role.name;
  const newDesc = prompt("Mô tả:", role.description) || role.description;
  await api.put(`/roles/${role.name}`, { name: newName, description: newDesc, permissions: role.permissions.map(p=>p.name) }, { headers });
  fetchRoles();
};
const deleteRole = async (name) => {
  if (confirm("Bạn có chắc muốn xóa Role này?")) {
    await api.delete(`/roles/${name}`, { headers });
    fetchRoles();
  }
};

onMounted(() => {
  fetchAdmins();
  fetchRoles();
  fetchPermissions();
});
</script>
