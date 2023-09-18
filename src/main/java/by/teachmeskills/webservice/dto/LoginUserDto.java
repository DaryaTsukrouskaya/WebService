package by.teachmeskills.webservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
public class LoginUserDto {
    @Email(message = "некорректный email")
    @NotBlank(message = "email не должен быть пустым")
    @NotNull(message = "email не должен быть пустым")
    private String email;
    @NotNull(message = "пароль не должен быть пустым")
    @Size(min = 6, max = 10, message = "длина пароля должна быть от 6 до 10 символов")
    @Pattern(regexp = "\\S+", message = "пароль не должен содержать пробелы")
    private String password;
}
