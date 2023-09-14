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
    @NotNull(message = "id поле в OrderConverter не должно быть null")
    @Min(value = 1, message = "Id поле в UserDto меньше 1")
    private Integer id;
    @NotNull
    @Digits(integer = 6, fraction = 3, message = "не соответствует формату цены")
    private BigDecimal price;
    @Past(message = "дата еще не наступила")
    @NotNull(message = "дата заказа не должна быть пустой")
    private LocalDateTime orderDate;
    @NotNull(message = "userId поле в OrderConverter не должно быть null")
    @Min(value = 1, message = "userId поле в UserDto меньше 1")
    private int userId;
    @NotNull(message = "дата заказа не должна быть пустой")
    private String address;
    @NotNull(message = "список продуктов в orderDto не должен быть null")
    @Size(min = 1, max = 10000, message = "Out of bounds orderDto product list")
    private List<ProductDto> products;

}
