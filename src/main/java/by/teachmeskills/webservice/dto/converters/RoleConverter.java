package by.teachmeskills.webservice.dto.converters;

import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.dto.RoleDto;
import by.teachmeskills.webservice.entities.Role;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RoleConverter {
    public RoleDto toDto(Role role) {
        return Optional.ofNullable(role).map(r -> RoleDto.builder().id(r.getId()).name(r.getName()).build()).orElse(null);
    }

    public Role fromDto(RoleDto role) {
        return Optional.ofNullable(role).map(r -> Role.builder().name(r.getName()).id(r.getId()).build()).orElse(null);
    }

}
