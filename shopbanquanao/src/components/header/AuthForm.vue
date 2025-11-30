<template>
  <div class="max-w-6xl mx-auto grid grid-cols-1 md:grid-cols-2 gap-10 py-10 px-4">
    <!-- ĐĂNG NHẬP -->
    <div>
      <h2 class="text-2xl font-bold mb-4">Đăng nhập</h2>
      <p class="text-gray-600 text-sm mb-6">
        Nhập số điện thoại của Quý Khách để đăng nhập tài khoản.
      </p>

      <form @submit.prevent="login" class="space-y-5">

        <!-- SĐT -->
        <div>
          <label class="font-medium">SĐT *</label>
          <input
            v-model="loginData.phone"
            type="text"
            placeholder="Vui lòng nhập SĐT"
            class="w-full border rounded-lg px-3 py-2 mt-1 "
          />
        </div>

        <!-- Mật khẩu -->
        <div>
          <label class="font-medium">Mật khẩu *</label>
          <div class="relative">
            <input
              v-model="loginData.password"
              :type="showLoginPassword ? 'text' : 'password'"
              placeholder="Nhập mật khẩu"
              class="w-full border rounded-lg px-3 py-2 mt-1 "
            />
            <button
              type="button"
              @click="showLoginPassword = !showLoginPassword"
              class="absolute right-3 top-4 text-gray-500"
            >
            <svg v-if="!showLoginPassword" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-5">
  <path fill-rule="evenodd" d="M3.28 2.22a.75.75 0 0 0-1.06 1.06l14.5 14.5a.75.75 0 1 0 1.06-1.06l-1.745-1.745a10.029 10.029 0 0 0 3.3-4.38 1.651 1.651 0 0 0 0-1.185A10.004 10.004 0 0 0 9.999 3a9.956 9.956 0 0 0-4.744 1.194L3.28 2.22ZM7.752 6.69l1.092 1.092a2.5 2.5 0 0 1 3.374 3.373l1.091 1.092a4 4 0 0 0-5.557-5.557Z" clip-rule="evenodd" />
  <path d="m10.748 13.93 2.523 2.523a9.987 9.987 0 0 1-3.27.547c-4.258 0-7.894-2.66-9.337-6.41a1.651 1.651 0 0 1 0-1.186A10.007 10.007 0 0 1 2.839 6.02L6.07 9.252a4 4 0 0 0 4.678 4.678Z" />
</svg>
<svg v-else xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-5">
  <path d="M10 12.5a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z" />
  <path fill-rule="evenodd" d="M.664 10.59a1.651 1.651 0 0 1 0-1.186A10.004 10.004 0 0 1 10 3c4.257 0 7.893 2.66 9.336 6.41.147.381.146.804 0 1.186A10.004 10.004 0 0 1 10 17c-4.257 0-7.893-2.66-9.336-6.41ZM14 10a4 4 0 1 1-8 0 4 4 0 0 1 8 0Z" clip-rule="evenodd" />
</svg>


            </button>
          </div>
        </div>

        <!-- Checkbox -->
        <div class="flex items-center gap-2">
          <input type="checkbox" v-model="loginData.remember" />
          <span>Giữ trạng thái đăng nhập</span>
          <a href="#" class="ml-auto text-[#c92127] text-sm">Quên mật khẩu?</a>
        </div>

        <!-- Button -->
        <button
          class="w-full bg-[#c92127] text-white py-2 rounded-lg hover:bg-red-800"
        >
          Đăng nhập
        </button>
      </form>
    </div>

    <!-- ĐĂNG KÝ -->
    <div>
      <h2 class="text-2xl font-bold mb-4">Đăng ký</h2>
      <p class="text-gray-600 text-sm mb-6">
        Tạo tài khoản và khám phá các lợi ích dành riêng cho người dùng.
      </p>

      <form @submit.prevent="register" class="space-y-5">

        <!-- Họ tên -->
        <div class="grid grid-cols-2 gap-4">
          <div>
            <label class="font-medium">Họ *</label>
            <input
              v-model="registerData.lastName"
              class="w-full border px-3 py-2 rounded-lg"
            />
          </div>
          <div>
            <label class="font-medium">Tên *</label>
            <input
              v-model="registerData.firstName"
              class="w-full border px-3 py-2 rounded-lg"
            />
          </div>
        </div>

        <!-- SĐT -->
        <div>
          <label class="font-medium">SĐT *</label>
          <input
            v-model="registerData.phone"
            class="w-full border px-3 py-2 rounded-lg"
            placeholder="Nhập số điện thoại"
          />
        </div>

        <!-- Giới tính -->
        <div>
          <label class="font-medium block">Giới tính</label>
          <div class="flex gap-5 mt-1">
            <label class="flex items-center gap-2">
              <input type="radio" value="Nữ" v-model="registerData.gender" /> Nữ
            </label>
            <label class="flex items-center gap-2">
              <input type="radio" value="Nam" v-model="registerData.gender" /> Nam
            </label>
            <label class="flex items-center gap-2">
              <input type="radio" value="Khác" v-model="registerData.gender" /> Khác
            </label>
          </div>
        </div>

        <!-- Email -->
        <div>
          <label class="font-medium">Email *</label>
          <input v-model="registerData.email" class="w-full border px-3 py-2 rounded-lg" />
        </div>

        <!-- Ngày sinh -->
        <div>
          <label class="font-medium">Ngày sinh</label>
          <input
            v-model="registerData.birthday"
            type="date"
            class="w-full border px-3 py-2 rounded-lg"
          />
        </div>

        <!-- Mật khẩu -->
        <div>
          <label class="font-medium">Mật khẩu *</label>
          <input
            v-model="registerData.password"
            type="password"
            class="w-full border px-3 py-2 rounded-lg"
          />
        </div>

        <!-- Xác nhận -->
        <div>
          <label class="font-medium">Xác nhận mật khẩu *</label>
          <input
            v-model="registerData.confirmPassword"
            type="password"
            class="w-full border px-3 py-2 rounded-lg"
          />
        </div>

        <!-- Button -->
        <button
          class="w-full bg-[#c92127] text-white py-2 rounded-lg hover:bg-red-700"
        >
          Đăng ký
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

/* --- LOGIN --- */
const loginData = ref({
  phone: "",
  password: "",
  remember: false,
});
const showLoginPassword = ref(false);

const login = () => {
  console.log("Login:", loginData.value);
};

/* --- REGISTER --- */
const registerData = ref({
  lastName: "",
  firstName: "",
  phone: "",
  gender: "Nữ",
  email: "",
  birthday: "",
  password: "",
  confirmPassword: "",
});

const register = () => {
  console.log("Register:", registerData.value);
};
</script>
