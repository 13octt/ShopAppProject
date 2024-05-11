package com.sales.shopapp.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    @NotEmpty(message = "Name can not null")
    private String name;
}
