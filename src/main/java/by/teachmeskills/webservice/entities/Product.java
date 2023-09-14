package by.teachmeskills.webservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Column(name="name")
    @NotNull
    @Size(min = 1, max = 60, message = "Пустое или длиннее 50 символов")
    private String name;
    @Column(name="description")
    @NotNull(message = "поле описание картинки продукта не должно быть пустым")
    private String description;
    @Column(name="imagePath")
    @NotNull(message = "поле путь к картинке продукта не должно быть пустым")
    private String imagePath;
    @ManyToOne(optional = false)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;
    @NotNull
    @Column(name="price")
    @Digits(integer = 6, fraction = 3, message = "не соответствует формату цены")
    private BigDecimal price;

}