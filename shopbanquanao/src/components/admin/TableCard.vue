<template>
  <div class="bg-white rounded-2xl shadow-sm overflow-hidden">
    <!-- TITLE -->
    <div class="px-5 py-4">
      <h3 class="font-semibold text-gray-800 uppercase">
        {{ title }}
      </h3>
    </div>

    <!-- TABLE -->
<table class="w-full text-sm border border-gray-200 border-collapse">
  <!-- HEADER -->
  <thead class="bg-gray-100">
    <tr>
      <th class="px-5 py-3 text-left font-medium text-gray-500 border-b border-gray-200">
        {{ col1 }}
      </th>
      <th class="px-5 py-3 text-right font-medium text-gray-500 border-b border-gray-200">
        {{ col2 }}
      </th>
    </tr>
  </thead>

  <!-- BODY -->
  <tbody>
    <tr
      v-for="(r, i) in rows"
      :key="i"
      class="hover:bg-gray-50 transition"
    >
      <td class="px-5 py-3 text-gray-700 border-b border-gray-200">
        {{ formatLabel(r[field1]) }}
      </td>
      <td class="px-5 py-3 text-right font-semibold text-gray-900 border-b border-gray-200">
        {{ formatValue(r[field2]) }}
      </td>
    </tr>

    <!-- NO DATA -->
    <tr v-if="!rows || !rows.length">
      <td colspan="2" class="py-6 text-center text-gray-400 border-b border-gray-200">
        Không có dữ liệu
      </td>
    </tr>
  </tbody>

      <!-- FOOTER: TỔNG -->
<tfoot v-if="rows && rows.length && showTotal">
  <tr class="border-t font-semibold bg-gray-50">
    <td class="px-5 py-3">Tổng</td>
    <td class="px-5 py-3 text-right">
      {{ rows.reduce((sum, r) => sum + r[field2], 0).toLocaleString() }}
    </td>
  </tr>
</tfoot>

    </table>
  </div>
</template>

<script setup>
defineProps({
  title: String,
  rows: Array,
  col1: String,
  col2: String,
  field1: String,
  field2: String,
  showTotal: {
    type: Boolean,
    default: true
  }
});


const formatLabel = (val) => {
  if (val === "DA_GIAO") return "Đã giao";
  return val;
};

const formatValue = (val) => {
  if (typeof val === "number")
    return val.toLocaleString();
  return val;
};
</script>

<style scoped>
tbody tr:hover {
  background-color: #f9fafb;
}
</style>
