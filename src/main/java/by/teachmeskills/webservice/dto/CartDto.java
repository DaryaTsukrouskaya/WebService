package by.teachmeskills.webservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder

public class CartDto {
    @NotNull(message = "список продуктов CartDto не должен быть null")
    private List<ProductDto> products;

    @NotNull(message = "итоговая стоимость не должна быть null")
    private BigDecimal totalPrice;
    
    public void addProduct(ProductDto product) {
        products.add(product);
        BigDecimal changedPrice = totalPrice.add(product.getPrice());
        totalPrice = changedPrice;
    }

    public void removeProduct(int id) {
        Optional<ProductDto> product = products.stream().filter(p -> p.getId() == id).findFirst();
        product.ifPresent(p -> products.remove(p));
        totalPrice.subtract(product.get().getPrice());
    }

    public List<ProductDto> getProducts() {
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

