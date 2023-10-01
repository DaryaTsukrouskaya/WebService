package by.teachmeskills.webservice.dto;


import com.opencsv.bean.CsvBindByName;
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
    private int id;
    @NotNull(message = "имя категории не должно быть пустым")
    @Size(min = 1, max = 60, message = "имя категории пустое или длиннее 60 символов")
    @CsvBindByName
    private String name;
    @NotNull(message = "список продуктов не должно быть пустым")
    List<ProductDto> products;
}
