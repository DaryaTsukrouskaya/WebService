package by.teachmeskills.webservice.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class OrderDto {
    private int id;
    @NotNull
    @Digits(integer = 6, fraction = 3, message = "не соответствует формату цены")
    private BigDecimal price;
    @Past(message = "дата еще не наступила")
    @NotNull(message = "дата заказа не должна быть пустой")
    private LocalDateTime orderDate;
    @NotNull(message = "id пользователя не должно быть путсым")
    @Min(value = 1, message = "id пользователя не должно быть меньше 1")
    private int userId;
    @NotNull(message = "дата заказа не должна быть пустой")
    private String address;
    @NotNull(message = "список продуктов не должен быть пустым")
    @Size(min = 1, max = 10000, message = "количество продуктов в заказе должно быть от 1 до 10000")
    private List<ProductDto> products;

}
