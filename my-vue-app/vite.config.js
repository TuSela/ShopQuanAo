import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

export default defineConfig({
  plugins: [vue()],
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
