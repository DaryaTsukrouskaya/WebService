package by.teachmeskills.webservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SearchParamsDto {
    private String keywords;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private String categoryName;

}
