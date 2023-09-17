package by.teachmeskills.webservice.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class CategoryDto {
    @NotNull(message = "поле id категории не должно быть null")
    private int id;
    @NotNull(message = "поле имя категории не должно быть null")
    @Size(min = 1, max = 60, message = "пустое или длиннее 60 символов")
    private String name;
    @NotNull(message = "поле список продуктов не должно быть null")
    List<ProductDto> products;
}
