package by.teachmeskills.webservice.dto;

import by.teachmeskills.webservice.entities.BaseEntity;
import by.teachmeskills.webservice.entities.Order;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
public class UserDto {
    @NotNull(message = "id поле в UserDto не должно быть null")
    @Min(value = 1, message = "Id поле в UserDto меньше 1")
    private Integer id;
    @NotNull(message = "имя не должно быть null")
    @NotBlank(message = "имя не должно быть пустым")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z]+$", message = "некорректное имя")
    private String name;
    @NotNull(message = "фамилия не должна быть null")
    @NotBlank(message = "фамилия не должна быть пустой")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z]+$", message = "некорректная фамилия")
    private String surname;
    @NotNull(message = "заполните поле")
    @Past(message = "указанная дата еще не наступила")
    private LocalDate birthDate;
    @Email(message = "некорректный пароль")
    @NotBlank(message = "email не должен быть пустым")
    @NotNull(message = "email не должен быть null")
    private String email;
    @NotNull(message = "пароль не должен быть null")
    @Size(min = 6, max = 10, message = "длина пароля должна быть от 6 до 10 символов")
    @Pattern(regexp = "\\S+",
            message = "пароль не должен содержать пробелы")
    private String password;

    private List<OrderDto> orders;
}

