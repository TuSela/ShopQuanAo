
import { ref } from "vue";

export const user = ref({
  name: null,
  avatar: null,
  sdt: null
});

export const setUser = (newUser) => {
  user.value = { ...user.value, ...newUser };
  localStorage.setItem("user", JSON.stringify(user.value));
};

export const clearUser = () => {
  user.value = { name: null, avatar: null, sdt: null };
  localStorage.removeItem("user");
  localStorage.removeItem("token");
};
