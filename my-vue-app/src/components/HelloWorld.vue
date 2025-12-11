<template>
  <div>
    <h2>Thêm Sản Phẩm</h2>

    <!-- Chọn ảnh + chọn ảnh đại diện -->
    <div v-for="(variant, vIndex) in variants" :key="vIndex" class="variant-block">
      <h3>Biến thể {{ vIndex + 1 }}</h3>

      <input type="file" multiple @change="onSelectImages($event, vIndex)" />

      <div class="preview-list">
        <div
          v-for="(img, iIndex) in variant.images"
          :key="iIndex"
          class="preview-item"
        >
          <img :src="img.url" class="preview" />

          <!-- NÚT CHỌN ẢNH ĐẠI DIỆN -->
          <label>
            <input
              type="radio"
              :name="`daiDien-${vIndex}`"
              :checked="variant.daiDienIndex === iIndex"
              @change="setRepresentative(vIndex, iIndex)"
            />
            Ảnh đại diện
          </label>
        </div>
      </div>
    </div>

    <button @click="submitProduct">Thêm sản phẩm</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      variants: [
        {
          images: [],
          daiDienIndex: null // chỉ 1 ảnh đại diện / biến thể
        }
      ]
    };
  },

  methods: {
    // Chọn ảnh
    onSelectImages(event, vIndex) {
      const files = event.target.files;
      for (const f of files) {
        const url = URL.createObjectURL(f);
        this.variants[vIndex].images.push({ file: f, url });
      }
    },

    // Chọn ảnh đại diện (1 ảnh duy nhất)
    setRepresentative(vIndex, iIndex) {
      this.variants[vIndex].daiDienIndex = iIndex;
    },

    // Submit sản phẩm
    async submitProduct() {
      const formData = new FormData();

      // JSON product
      const productData = {
        variants: this.variants.map((v) => ({
          dai_dien: v.daiDienIndex, // gửi chỉ số ảnh đại diện lên backend
        }))
      };

      formData.append("product", JSON.stringify(productData));

      // Đính kèm file ảnh
      this.variants.forEach((variant, vIndex) => {
        variant.images.forEach((img, iIndex) => {
          const fieldName = `image_${vIndex}_${iIndex}`;
          formData.append(fieldName, img.file);
        });
      });

      // Gửi mapping meta
      const meta = {};
      this.variants.forEach((variant, vIndex) => {
        meta[vIndex] = {
          dai_dien: variant.daiDienIndex
        };
      });
      formData.append("images_meta", JSON.stringify(meta));

      await fetch("/api/products", {
        method: "POST",
        body: formData
      });

      alert("Thêm sản phẩm thành công");
    }
  }
};
</script>

<style>
.preview-list {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}
.preview-item {
  border: 1px solid #ccc;
  padding: 5px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.preview {
  width: 70px;
  height: 70px;
  object-fit: cover;
}
</style>
