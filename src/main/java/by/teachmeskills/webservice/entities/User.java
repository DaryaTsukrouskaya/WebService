package by.teachmeskills.webservice.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User extends BaseEntity {
    @NotNull
    @NotBlank(message = "имя не должно быть пустым")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z]+$", message = "некорректное имя")
    @Column(name = "name")
    private String name;
    @NotNull
    @NotBlank(message = "фамилия не должна быть пустой")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z]+$", message = "некорректная фамилия")
    @Column(name = "surname")
    private String surname;
    @NotNull(message = "заполните поле")
    @Past(message = "указанная дата еще не наступила")
    @Column(name = "birthDate")
    private LocalDate birthDate;
    @Email(message = "некорректный пароль")
    @NotBlank(message = "email не должен быть пустым")
    @NotNull
    @Column(name = "email")
    private String email;
    @NotNull(message = "пароль не должен быть пустым")
    @Size(min = 6, max = 10, message = "длина пароля должна быть от 6 до 10 символов")
    @Pattern(regexp = "\\S+",
            message = "пароль не должен содержать пробелы")
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Order> orders;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<Role> roles;
}
