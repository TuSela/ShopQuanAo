<template>
  <div class="container">
    <h1 class="title">Quản lý tài khoản</h1>

    <table class="user-table">
      <thead>
        <tr>
          <th>Avatar</th>
          <th>Họ tên</th>
          <th>Email</th>
          <th>SĐT</th>
          <th>Giới tính</th>
          <th>Ngày sinh</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.maTk">
          <td>
            <img :src="getAvatar(user.avatar)" class="avatar" alt="avatar" />
          </td>
          <td>{{ user.hoten }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.sdt }}</td>
          <td>{{ user.gioiTinh }}</td>
          <td>{{ user.ngaySinh }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { getUsers } from "../api/userApi.js";

const users = ref([]);

const fetchUsers = async () => {
  try {
    const res = await getUsers();
    users.value = res.result || [];
  } catch (err) {
    console.error("Lỗi khi tải user", err);
  }
};

const getAvatar = (avatarPath) => {
  if (!avatarPath) return "/default-avatar.png"; // fallback
  return avatarPath;
};

onMounted(() => fetchUsers());
</script>

<style scoped>
.container {
  width: 90%;
  margin: 20px auto;
}
.title {
  text-align: center;
  margin-bottom: 20px;
}
.user-table {
  width: 100%;
  border-collapse: collapse;
}
.user-table th,
.user-table td {
  border: 1px solid #ddd;
  padding: 10px;
  text-align: center;
}
.avatar {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 50%;
}
</style>
