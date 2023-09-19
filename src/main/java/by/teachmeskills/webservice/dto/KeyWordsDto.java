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
    @NotNull(message = "для поиска нужно ввести хотя бы одно слово не короче 3-х символов")
    @Min(value = 3, message = "длина слова для поиска меньше 3-х символов")
    private String keyWords;
    private int currentPageNumber;

    public KeyWordsDto() {
        this.currentPageNumber = 1;
    }

}
