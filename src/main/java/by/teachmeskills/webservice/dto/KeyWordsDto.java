package by.teachmeskills.webservice.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class KeyWordsDto {
    @NotNull(message = "поле KeyWordsDto не должно быть null")
    @Min(value = 3, message = "длина меньше 3")
    private String keyWords;
    @NotNull(message = "поле номер текущей страницы не должно быть null")
    private int currentPageNumber;

    public KeyWordsDto() {
        this.currentPageNumber = 1;
    }

}
