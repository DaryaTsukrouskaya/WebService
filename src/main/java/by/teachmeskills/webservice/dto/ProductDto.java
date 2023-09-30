package by.teachmeskills.webservice.dto;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class ProductDto {
    private int id;
    @NotNull(message = "имя продукта не должно быть пустым")
    @Size(min = 1, max = 60, message = "пустое имя продукта или длиннее 50 символов")
    @CsvBindByName
    private String name;

    @NotNull(message = "описание картинки продукта не должно быть пустым")
    @CsvBindByName
    private String description;
    @NotNull(message = "поле путь к картинке продукта не должно быть пустым")
    @CsvBindByName
    private String imagePath;
    @NotNull
    @Min(value = 1, message = " айди категории продукта не должно быть короче 1")
    @CsvBindByName
    private int categoryId;
    @NotNull(message = "цена продукта не должна быть пустой")
    @Digits(integer = 6, fraction = 3, message = "не соответствует формату цены")
    @CsvBindByName
    private BigDecimal price;

}
