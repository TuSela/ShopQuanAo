import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import tailwindcss from "@tailwindcss/vite";

export default defineConfig({
  plugins: [vue(),tailwindcss()],
  server: {
    proxy: {
      "/users": {
        target: "http://localhost:8081/nhom19",
        changeOrigin: true,
        secure: false,
      },
    },
  },
});
