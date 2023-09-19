package by.teachmeskills.webservice.dto.converters;

import by.teachmeskills.webservice.dto.KeyWordsDto;
import by.teachmeskills.webservice.entities.KeyWords;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KeyWordConverter {

    public KeyWordsDto toDto(KeyWords keyWords) {
        return Optional.ofNullable(keyWords).map(k -> KeyWordsDto.builder().keyWords(k.getKeyWords()).currentPageNumber(k.getCurrentPageNumber()).build()).orElse(null);
    }

    public KeyWords fromDto(KeyWordsDto keyWords) {
        return Optional.ofNullable(keyWords).map(k -> KeyWords.builder().keyWords(k.getKeyWords()).currentPageNumber(k.getCurrentPageNumber()).build()).orElse(null);
    }
}
