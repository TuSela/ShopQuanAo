<template>
  <div class="space-y-8">
    <!-- TITLE -->
    <div>
      <h1 class="text-2xl font-bold tracking-tight">
        Thống kê bán hàng
      </h1>
    </div>

    <!-- STATS -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-6">
      <StatCard
        title="Đơn hàng"
        :value="totalOrders"
        :icon="FileText"
        bg="bg-green-50"
        iconBg="bg-green-100"
      />

      <StatCard
        title="Doanh thu"
        :value="totalRevenue.toLocaleString() + ' ₫'"
        :icon="CreditCard"
        bg="bg-yellow-50"
        iconBg="bg-yellow-100"
      />

      <StatCard
        title="Khách hàng"
        :value="totalCustomers"
        :icon="User"
        bg="bg-purple-50"
        iconBg="bg-purple-100"
      />

      <StatCard
        title="Đánh giá TB"
        :value="avgRating + ' / 5'"
        :icon="Star"
        bg="bg-pink-50"
        iconBg="bg-pink-100"
      />
    </div>

    <!-- CHART -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
      <!-- BAR CHART -->
<div class="bg-white rounded-2xl shadow-sm p-6">
  <h3 class="font-semibold mb-4">
    Doanh thu theo tháng
  </h3>

  <div class="h-64">
    <Bar
      v-if="revenueByMonth.length"
      :data="revenueBarData"
      :options="revenueBarOptions"
    />
  </div>
</div>


<div class="bg-white rounded-2xl shadow-sm p-6">
  <h3 class="font-semibold mb-4">
    Doanh thu theo phương thức
  </h3>

  <div class="relative h-64 flex justify-center items-center">
    <!-- Doughnut -->
    
      <Doughnut
        v-if="revenueByPayment.length"
        :data="paymentDoughnutData"
        :options="paymentDoughnutOptions"
      />
    

  </div>
</div>


    </div>

    <!-- TABLE -->
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
<TableCard
  title="Đơn hàng theo trạng thái"
  col1="Trạng thái"
  col2="Số đơn"
  field1="trangThai"
  field2="soDon"
  :rows="sortedOrderStatus"
/>

<TableCard
  title="Top sản phẩm bán chạy"
  col1="Sản phẩm"
  col2="Số lượng bán"
  field1="tenSp"
  field2="soLuongBan"
  :rows="statistic?.topProductDTO"
  :showTotal="false"
/>


<!-- REVENUE BY MONTH -->
<div class="bg-white rounded-2xl shadow-sm p-6">
  <h3 class="font-semibold mb-4">
    Đơn hàng theo tháng
  </h3>

  <div class="h-64">
    <Bar
      v-if="orderByMonth.length"
      :data="orderByMonthBarData"
      :options="orderByMonthBarOptions"
    />
  </div>
</div>



    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import api from "@/api";
import StatCard from "@/components/admin/StatCard.vue";
import TableCard from "@/components/admin/TableCard.vue";
import { FileText, CreditCard, User, Star } from "lucide-vue-next";

/* ===== CHART.JS (BẮT BUỘC) ===== */
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
} from "chart.js";
import { Bar } from "vue-chartjs";
import {
  LineElement,
  PointElement
} from "chart.js";
import { Doughnut } from "vue-chartjs";
import { ArcElement } from "chart.js";

const centerTextPlugin = {
  id: "centerText",
  beforeDraw(chart) {
    if (chart.config.type !== "doughnut") return;
    const { width, height, ctx } = chart;
    const total = chart.config.options.totalValue || 0;

    ctx.restore();
    ctx.save();

    ctx.textAlign = "center";
    ctx.textBaseline = "middle";

    // Số tiền
    ctx.font = "bold 20px sans-serif";
    ctx.fillStyle = "#111827";
    ctx.fillText(
      total.toLocaleString(),
      width / 2-160,
      height / 2 - 8
    );

    // VND
    ctx.font = "12px sans-serif";
    ctx.fillStyle = "#6b7280";
    ctx.fillText(
      "VND",
      width / 2-100,
      height / 2-6 
    );
  }
};

ChartJS.register(
  Title,
  Tooltip,
  Legend,

  // BAR
  BarElement,

  // LINE
  LineElement,
  PointElement,

  // DOUGHNUT
  ArcElement,

  CategoryScale,
  LinearScale,
  centerTextPlugin
);




/* ===== DATE FILTER ===== */
const fromDate = "2025-01-01T00:00:00";
const toDate   = "2027-01-31T23:59:59";


const formatTrangThai = (status) => {
  return status === "DA_GIAO" ? "Đã giao" : status;
};

const statusOrder = {
  "Đang xử lý": 1,
  "Đang giao": 2,
  "DA_GIAO": 3,
  "Đã hủy": 4
};

const sortedOrderStatus = computed(() => {
  return (statistic.value?.orderStatusStatisticDTO || []).slice().sort((a, b) => {
    return (statusOrder[a.trangThai] || 99) - (statusOrder[b.trangThai] || 99);
  });
});


/* ===== DATA ===== */
const statistic = ref(null);

const fetchStatistic = async () => {
  const res = await api.get("/thongke", {
    params: { fromDate, toDate }
  });
  statistic.value = res.data.result;
};

onMounted(fetchStatistic);

/* ===== COMPUTED ===== */
//
const revenueByPayment = computed(() =>
  statistic.value?.revenueByPaymentMethodDTO || []
);
//
const orderByMonth = computed(() =>
  statistic.value?.orderByMonthDTO || []
);

// Tổng số đơn
const totalOrders = computed(() =>
  statistic.value?.customerStatisticDTO
    ?.reduce((sum, c) => sum + c.soDon, 0) || 0
);

// Tổng doanh thu
const totalRevenue = computed(() =>
  statistic.value?.customerStatisticDTO
    ?.reduce((sum, c) => sum + c.tongChi, 0) || 0
);

// Tổng khách hàng
const totalCustomers = computed(() =>
  statistic.value?.customerStatisticDTO?.length || 0
);

// Đánh giá trung bình
const avgRating = computed(() => {
  const list = statistic.value?.ratingStatisticDTO || [];
  let total = 0, count = 0;
  list.forEach(r => {
    total += r.soSao * r.soLuot;
    count += r.soLuot;
  });
  return count ? (total / count).toFixed(1) : "0";
});

/* ===== BAR CHART DATA ===== */


const orderByMonthBarData = computed(() => ({
  labels: orderByMonth.value.map(i => `T${i.thang}/${i.nam}`),
  datasets: [
    {
      label: "Số đơn",
      data: orderByMonth.value.map(i => i.soDon),
      backgroundColor: "#3b82f6",
      borderRadius: 8
    }
  ]
}));

const revenueByMonth = computed(() =>
  statistic.value?.revenueByMonthDTO || []
);

const revenueBarData = computed(() => ({
  labels: revenueByMonth.value.map(i => `T${i.thang}/${i.nam}`),
  datasets: [
    {
      label: "Doanh thu (₫)",
      data: revenueByMonth.value.map(i => i.doanhThu),
      backgroundColor: "#22c55e",
      borderRadius: 8
    }
  ]
}));

const paymentDoughnutData = computed(() => ({
  labels: revenueByPayment.value.map(i => i.tenPhuongThuc),
  datasets: [
    {
      data: revenueByPayment.value.map(i => Number(i.doanhThu)), // 👈 QUAN TRỌNG
      backgroundColor: [
        "#fcd34d", // Chuyển khoản
        "#60a5fa"  // COD
      ],
      borderWidth: 0,
      cutout: "70%"
    }
  ]
}));


const paymentDoughnutOptions = computed(() => {
  const total = revenueByPayment.value.reduce(
    (sum, i) => sum + Number(i.doanhThu),
    0
  );

  return {
    responsive: true,
    maintainAspectRatio: false,
    totalValue: total,
    plugins: {
      legend: {
        position: "right",
        labels: {
          usePointStyle: true,
          padding: 20,
          generateLabels(chart) {
            const data = chart.data;
            return data.labels.map((label, i) => {
              const value = data.datasets[0].data[i];
              return {
                text: `${label}: ${Number(value).toLocaleString()} ₫`,
                fillStyle: data.datasets[0].backgroundColor[i],
                strokeStyle: data.datasets[0].backgroundColor[i],
                index: i
              };
            });
          }
        }
      },
      tooltip: {
        callbacks: {
          label(ctx) {
            const value = Number(ctx.raw);
            const percent = ((value / total) * 100).toFixed(1);
            return `${value.toLocaleString()} ₫ (${percent}%)`;
          }
        }
      }
    }
  };
});



const revenueBarOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: { display: false },
    tooltip: {
      callbacks: {
        label: (ctx) => ctx.raw.toLocaleString() + " ₫"
      }
    }
  },
  scales: {
    y: {
      ticks: { callback: (v) => v.toLocaleString() },
      grid: { color: "#eee" }
    },
    x: {
      grid: { display: false },
       ticks: { autoSkip: false },
      // giới hạn độ rộng cột
      maxBarThickness: 50
    }
  }
};



const orderByMonthBarOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: { display: false },
    tooltip: {
      callbacks: {
        label: (ctx) => ctx.raw.toLocaleString() + " đơn"
      }
    }
  },
  scales: {
    y: {
      beginAtZero: true,
      ticks: { callback: (v) => v.toLocaleString() },
      grid: { color: "#eee" }
    },
    x: {
      grid: { display: false },
      ticks: { autoSkip: false },
      maxBarThickness: 50
    }
  }
};


</script>
