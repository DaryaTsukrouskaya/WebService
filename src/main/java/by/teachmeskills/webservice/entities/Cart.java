package by.teachmeskills.webservice.entities;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Builder
@Data
public class Cart {
    @NotNull
    private List<Product> products;

    @NotNull
    private BigDecimal totalPrice;

    public void addProduct(Product product) {
        products.add(product);
        BigDecimal changedPrice = totalPrice.add(product.getPrice());
        totalPrice = changedPrice;
    }

    public void removeProduct(int id) {
        Optional<Product> product = products.stream().filter(p -> p.getId() == id).findFirst();
        product.ifPresent(p -> products.remove(p));
        totalPrice.subtract(product.get().getPrice());
    }

    public List<Product> getProducts() {
        return products;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void clear() {
        products.clear();
        totalPrice = BigDecimal.valueOf(0);
    }
}
