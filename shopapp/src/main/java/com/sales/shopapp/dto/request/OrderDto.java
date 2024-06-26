package com.sales.shopapp.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    @JsonProperty("user_id")
    @Min(value = 1, message = "User's ID must be greater than 0")
    private Long userId;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @JsonProperty("note")
    private String note;
    @JsonProperty("order_date")
    private Date orderDate;
    @JsonProperty("status")
    private String status;
    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be greater or equal than 0")
    private float totalMoney;
    @JsonProperty("shipping_method")
    private String shippingMethod;
    @JsonProperty("shipping_address")
    private String shippingAddress;
    @JsonProperty("shipping_date")
    private LocalDate shippingDate;
    @JsonProperty("tracking_number")
    private int trackingNumber;
    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("active")
    private int active;
}
