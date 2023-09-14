package by.teachmeskills.webservice.dto;

import by.teachmeskills.webservice.entities.BaseEntity;
import by.teachmeskills.webservice.entities.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class ProductDto {
    @NotNull(message = "поле id продукта не должно быть null")
    @Min(value = 1, message = "Поле айди продукта не должно быть короче 1")
    private int id;
    @NotNull(message = "поле имя продукта не должно быть null")
    @Size(min = 1, max = 60, message = "Пустое имя продукта или длиннее 50 символов")
    private String name;
    @NotNull(message = "поле описание картинки продукта не должно быть пустым")
    private String description;
    @NotNull
    @Min(value = 1, message = "Поле айди категории продукта не должно быть короче 1")
    private int categoryId;
    @NotNull(message = "поле цена продукта не должно быть null")
    @Digits(integer = 6, fraction = 3, message = "не соответствует формату цены")
    private BigDecimal price;

}
