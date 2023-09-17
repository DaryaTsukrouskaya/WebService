package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.dto.CartDto;
import by.teachmeskills.webservice.dto.OrderDto;
import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.exceptions.NoOrderAddressException;


import java.util.List;

public interface OrderService {
    public List<OrderDto> read();

    void create(OrderDto order);

    public void delete(int id);

    void update(OrderDto order);

    OrderDto findById(int id);

    List<OrderDto> findByUserId(int id);

    OrderDto createUserOrder(UserDto user, CartDto cart, String address) throws NoOrderAddressException;
}
