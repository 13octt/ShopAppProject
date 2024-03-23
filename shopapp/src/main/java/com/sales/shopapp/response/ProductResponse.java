package com.sales.shopapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sales.shopapp.entity.Product;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse {
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private float price;
    @JsonProperty("thumbnail")
    private String thumbnail;
    @JsonProperty("description")
    private String description;
    @JsonProperty("category_id")
    private String categoryId;

    public static ProductResponse fromProduct(Product productDto) {
        ProductResponse productResponse = ProductResponse.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .thumbnail(productDto.getThumbnail())
                .description(productDto.getDescription())
                .categoryId(productDto.getCategoryId().getCategoryId().toString())
                .build();
        productResponse.setCreateTime(productDto.getCreatedTime());
        productResponse.setUpdatedTime(LocalDate.from(productDto.getUpdatedTime()));
        return productResponse;
    }

}
