package by.teachmeskills.webservice.dto.converters;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.entities.User;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UserConverter {
    private final OrderConverter orderConverter;

    public UserConverter(OrderConverter orderConverter) {
        this.orderConverter = orderConverter;
    }

    public UserDto toDto(User user) {
        return Optional.ofNullable(user).map(u ->
                UserDto.builder().id(u.getId()).name(u.getName()).surname(u.getSurname()).birthDate(u.getBirthDate())
                        .email(u.getEmail()).password(u.getPassword()).repPassword(u.getPassword()).orders(u.getOrders().stream().map(orderConverter::toDto).toList()).build()).orElse(null);
    }

    public User fromDto(UserDto user) {
        return Optional.ofNullable(user).map(u ->
                User.builder().id(u.getId()).name(u.getName()).surname(u.getSurname()).birthDate(u.getBirthDate())
                        .email(u.getEmail()).password(u.getPassword()).orders(u.getOrders().stream().map(orderConverter::fromDto).toList()).build()).orElse(null);
    }
}
