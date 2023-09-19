package by.teachmeskills.webservice.dto.converters;


import by.teachmeskills.webservice.dto.OrderDto;
import by.teachmeskills.webservice.entities.Order;
import by.teachmeskills.webservice.repositories.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderConverter {
    private final ProductConverter productConverter;
    private final UserRepository userRepository;

    public OrderConverter(ProductConverter productConverter, UserRepository userRepository) {
        this.productConverter = productConverter;
        this.userRepository = userRepository;
    }


    public OrderDto toDto(Order order) {
        return Optional.ofNullable(order).map(o -> OrderDto.builder().id(o.getId()).price(o.getPrice()).
                orderDate(o.getOrderDate()).userId(o.getUser().getId()).address(o.getAddress())
                .products(Optional.ofNullable(o.getProducts()).map(p -> p.stream().map(productConverter::toDto).toList()).orElse(List.of())).build()).orElse(null);
    }

    public Order fromDto(OrderDto order) {
        return Optional.ofNullable(order).map(o -> Order.builder().id(o.getId()).price(o.getPrice()).
                orderDate(o.getOrderDate()).user(userRepository.findById(o.getUserId())).address(o.getAddress())
                .products(Optional.ofNullable(o.getProducts()).map(p -> p.stream().map(productConverter::fromDto).toList()).orElse(List.of())).build()).orElse(null);
    }
}
