<template>
  <div>
    <h1>Quản lý khách hàng</h1>

    <div v-if="errorMessage" class="error">{{ errorMessage }}</div>

    <!-- Form thêm / sửa user -->
    <form @submit.prevent="editingUser ? handleUpdateUser() : handleAddUser()">
      <input v-model="userForm.username" placeholder="Username" required />
      <input v-model="userForm.email" placeholder="Email" required />
      <input v-model="userForm.hoten" placeholder="Họ tên" required />
      <input v-model="userForm.sdt" placeholder="SĐT" required />
      <input v-model="userForm.diachi" placeholder="Địa chỉ" required />
      <input
        v-model="userForm.password"
        placeholder="Mật khẩu"
        :required="!editingUser"
      />
      <button type="submit">{{ editingUser ? "Cập nhật" : "Thêm" }}</button>
      <button type="button" v-if="editingUser" @click="cancelEdit">Huỷ</button>
    </form>

    <!-- Danh sách user -->
    <table border="1" cellpadding="5">
      <thead>
        <tr>
          <th>Username</th>
          <th>Email</th>
          <th>Họ tên</th>
          <th>SĐT</th>
          <th>Địa chỉ</th>
          <th>Hành động</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.maTk">
          <td>{{ user.username }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.hoten }}</td>
          <td>{{ user.sdt }}</td>
          <td>{{ user.diachi }}</td>
          <td>
            <button @click="startEdit(user)">Sửa</button>
            <button @click="handleDeleteUser(user.maTk)">Xoá</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import {
  getUsers,
  createUser,
  updateUser,
  deleteUser,
} from "../api/userApi.js";

const users = ref([]);
const errorMessage = ref("");

const editingUser = ref(false);
const userForm = ref({
  username: "",
  email: "",
  hoten: "",
  sdt: "",
  diachi: "",
  password: "",
});

// Lấy danh sách user
const fetchUsers = async () => {
  try {
    const res = await getUsers();
    users.value = res.result || [];
  } catch (e) {
    console.error(e);
    errorMessage.value = "Lỗi khi tải danh sách người dùng";
  }
};

// Thêm user
const handleAddUser = async () => {
  try {
    const res = await createUser(userForm.value);
    if (res.result.success) {
      alert("Thêm thành công!");
      fetchUsers();
      userForm.value = {
        username: "",
        email: "",
        hoten: "",
        sdt: "",
        diachi: "",
        password: "",
      };
    } else {
      alert("Thêm thất bại");
    }
  } catch (e) {
    console.error(e);
    alert("Lỗi khi thêm user");
  }
};

// Bắt đầu sửa user
const startEdit = (user) => {
  editingUser.value = true;
  userForm.value = { ...user, password: "" }; // password không bắt buộc khi sửa
  userForm.value.id = user.maTk; // lưu id để cập nhật
};

// Huỷ sửa
const cancelEdit = () => {
  editingUser.value = false;
  userForm.value = {
    username: "",
    email: "",
    hoten: "",
    sdt: "",
    diachi: "",
    password: "",
  };
};

// Cập nhật user
const handleUpdateUser = async () => {
  try {
    const id = userForm.value.id;
    const { id: _, ...data } = userForm.value; // loại bỏ id khỏi body
    const res = await updateUser(id, data);
    if (res.result) {
      alert("Cập nhật thành công!");
      editingUser.value = false;
      userForm.value = {
        username: "",
        email: "",
        hoten: "",
        sdt: "",
        diachi: "",
        password: "",
      };
      fetchUsers();
    } else {
      alert("Cập nhật thất bại");
    }
  } catch (e) {
    console.error(e);
    alert("Lỗi khi cập nhật user");
  }
};

// Xoá user
const handleDeleteUser = async (id) => {
  if (!confirm("Bạn có chắc muốn xoá?")) return;
  try {
    const res = await deleteUser(id);
    if (res.result) {
      alert("Xoá thành công!");
      fetchUsers();
    } else {
      alert("Xoá thất bại");
    }
  } catch (e) {
    console.error(e);
    alert("Lỗi khi xoá user");
  }
};

onMounted(() => {
  fetchUsers();
});
</script>

<style scoped>
.error {
  color: red;
  margin-bottom: 10px;
}
table {
  margin-top: 20px;
  width: 100%;
}
input {
  margin-right: 5px;
  margin-bottom: 5px;
}
</style>
