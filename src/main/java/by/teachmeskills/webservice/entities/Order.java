package by.teachmeskills.webservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column(name = "price")
    @NotNull
    @Digits(integer = 6, fraction = 3, message = "не соответствует формату цены")
    private BigDecimal price;
    @Past(message = "дата еще не наступила")
    @NotNull(message = "дата заказа не должна быть пустой")
    @Column(name = "orderDate")
    private LocalDateTime orderDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private User user;
    @Column(name = "address")
    @NotNull(message = "дата заказа не должна быть пустой")
    private String address;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "orders_products",
            joinColumns = {@JoinColumn(name = "orderId")},
            inverseJoinColumns = {@JoinColumn(name = "productId")})
    private List<Product> products;
}
