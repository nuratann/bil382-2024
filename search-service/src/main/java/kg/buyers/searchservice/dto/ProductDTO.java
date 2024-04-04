package kg.buyers.searchservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;


public class ProductDTO {
        private String title;
        private String description;
        private Integer stock;
        private Double price;
        private Double oldPrice;
        private String images;

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public Integer getStock() {
                return stock;
        }

        public void setStock(Integer stock) {
                this.stock = stock;
        }

        public Double getPrice() {
                return price;
        }

        public void setPrice(Double price) {
                this.price = price;
        }

        public Double getOldPrice() {
                return oldPrice;
        }

        public void setOldPrice(Double oldPrice) {
                this.oldPrice = oldPrice;
        }

        public String getImages() {
                return images;
        }

        public void setImages(String images) {
                this.images = images;
        }
}
