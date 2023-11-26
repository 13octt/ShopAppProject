package com.sales.shopapp.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    @JsonProperty("order_id")
    @Min(value = 1)
    private Long orderId;
    @JsonProperty("product_id")
    @Min(value = 1)
    private Long productId;
    @JsonProperty("price")
    @Min(value = 0, message = "Price must be greater than 0")
    private float price;
    @JsonProperty("number_of_product")
    @Min(value = 1)
    private int numberOfProduct;
    @JsonProperty("total_money")
    @Min(value = 0)
    private float totalMoney;
    @JsonProperty("color")
    private String color;
}
