package by.teachmeskills.webservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
public class UserDto {
    private int id;
    @NotNull(message = "имя не должно быть пустым")
    @NotBlank(message = "имя не должно быть пустым")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z]+$", message = "некорректное имя")
    private String name;
    @NotNull(message = "фамилия не должна быть пустым")
    @NotBlank(message = "фамилия не должна быть пустой")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z]+$", message = "некорректная фамилия")
    private String surname;
    @NotNull(message = "дата рождения не должна быть пустой")
    @Past(message = "указанная дата еще не наступила")
    private LocalDate birthDate;
    @Email(message = "некорректный формат email")
    @NotBlank(message = "email не должен быть пустым")
    @NotNull(message = "email не должен быть пустым")
    private String email;
    @NotNull(message = "пароль не должен быть пустым")
    @Size(min = 6, max = 10, message = "длина пароля должна быть от 6 до 10 символов")
    @Pattern(regexp = "\\S+",
            message = "пароль не должен содержать пробелы")
    private String password;
    @NotNull(message = "повторный пароль не должен быть пустым")
    @Size(min = 6, max = 10, message = "длина пароля должна быть от 6 до 10 символов")
    @Pattern(regexp = "\\S+",
            message = "пароль не должен содержать пробелы")
    private String repPassword;
    private List<OrderDto> orders;
    @NotNull(message = "Empty user roles")
    private List<RoleDto> roles;
}


