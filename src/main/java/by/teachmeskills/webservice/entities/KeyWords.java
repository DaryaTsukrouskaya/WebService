package by.teachmeskills.webservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class KeyWords {
    private String keyWords;
    private int currentPageNumber;

    public KeyWords() {
        this.currentPageNumber = 1;
    }

}
