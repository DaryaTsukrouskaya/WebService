package by.teachmeskills.webservice.dto.converters;

import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.entities.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserConverter {
    private final OrderConverter orderConverter;
    private final RoleConverter roleConverter;

    public UserConverter(OrderConverter orderConverter, RoleConverter roleConverter) {
        this.orderConverter = orderConverter;
        this.roleConverter = roleConverter;
    }

    public UserDto toDto(User user) {
        return Optional.ofNullable(user).map(u ->
                UserDto.builder().id(u.getId()).name(u.getName()).surname(u.getSurname()).birthDate(u.getBirthDate())
                        .email(u.getEmail()).password(u.getPassword()).repPassword(u.getPassword()).orders(u.getOrders().stream().map(orderConverter::toDto).toList()).
                        roles(u.getRoles().stream().map(roleConverter::toDto).toList()).build()).orElse(null);
    }

    public User fromDto(UserDto user) {
        return Optional.ofNullable(user).map(u ->
                User.builder().id(u.getId()).name(u.getName()).surname(u.getSurname()).birthDate(u.getBirthDate())
                        .email(u.getEmail()).password(u.getPassword()).orders(u.getOrders().stream().map(orderConverter::fromDto).toList()).
                        roles(u.getRoles().stream().map(roleConverter::fromDto).toList()).
                        build()).orElse(null);
    }
}
