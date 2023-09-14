package by.teachmeskills.webservice.dto.converters;

import by.teachmeskills.webservice.dto.CartDto;
import by.teachmeskills.webservice.entities.Cart;
import lombok.Data;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;
@Component
public class CartConverter {
    private final ProductConverter productConverter;

    public CartConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    public CartDto toDto(Cart cart) {
        return Optional.ofNullable(cart).map(c -> CartDto.builder().totalPrice(c.getTotalPrice())
                        .products(Optional.ofNullable(c.getProducts()).map(products -> products.stream().map(productConverter::toDto).toList()).orElse(List.of()))
                        .build())
                .orElse(null);
    }

    public Cart fromDto(CartDto cartDto) {
        return Optional.ofNullable(cartDto).map(c -> Cart.builder().totalPrice(c.getTotalPrice())
                        .products(Optional.ofNullable(c.getProducts()).map(products -> products.stream().map(productConverter::fromDto).toList()).orElse(List.of()))
                        .build())
                .orElse(null);
    }
}
