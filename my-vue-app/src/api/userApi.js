import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8081/nhom19", // chú ý port + context path
  headers: {
    "Content-Type": "application/json",
  },
});

const API_URL = "/users";

// Lấy danh sách tất cả user
export const getUsers = async () => {
  const res = await axios.get(API_URL);
  return res.data; // { code, message, result: [...] }
};

// Lấy 1 user theo ID
export const getUser = async (id) => {
  const res = await axios.get(`${API_URL}/${id}`);
  return res.data; // { code, message, result: user }
};

// Tạo user mới
export const createUser = async (userData) => {
  const res = await axios.post(API_URL, userData);
  return res.data; // { code, message, result: { success: true } }
};

// Cập nhật user
export const updateUser = async (id, userData) => {
  const res = await axios.put(`${API_URL}/${id}`, userData);
  return res.data; // { code, message, result: updated user }
};

// Xoá user
export const deleteUser = async (id) => {
  const res = await axios.delete(`${API_URL}/${id}`);
  return res.data; // { code, message, result: "success" }
};
