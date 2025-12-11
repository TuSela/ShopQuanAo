import { defineStore } from "pinia";
import { ref } from "vue";

export const useAuthStore = defineStore("auth", () => {
  const token = ref(localStorage.getItem("token") || null);

  function setToken(t) {
    token.value = t;
    localStorage.setItem("token", t);
  }

  function logout() {
    token.value = null;
    localStorage.removeItem("token");
  }

  return { token, setToken, logout };
});
