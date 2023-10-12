package by.teachmeskills.webservice.repositories;

import by.teachmeskills.webservice.dto.SearchParamsDto;
import by.teachmeskills.webservice.entities.Category;
import by.teachmeskills.webservice.entities.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductSearchSpecification implements Specification<Product> {
    private final SearchParamsDto searchParams;

    public ProductSearchSpecification(SearchParamsDto searchParams) {
        this.searchParams = searchParams;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicate = new ArrayList<>();
        if (Optional.ofNullable(searchParams.getKeywords()).isPresent() && !searchParams.getKeywords().isBlank()) {
            predicate.add(criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%" + searchParams.getKeywords() + "%"),
                    criteriaBuilder.like(root.get("description"), "%" + searchParams.getKeywords() + "%")));
        }
        if (Optional.ofNullable(searchParams.getPriceFrom()).isPresent()) {
            predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), searchParams.getPriceFrom()));
        }
        if (Optional.ofNullable(searchParams.getPriceTo()).isPresent()) {
            predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), searchParams.getPriceTo()));
        }
        if (Optional.ofNullable(searchParams.getCategoryName()).isPresent() && !searchParams.getCategoryName().isBlank()) {
            Join<Product, Category> productCategoryJoin = root.join("category");
            predicate.add(criteriaBuilder.and(criteriaBuilder.like(productCategoryJoin.get("name"), "%" + searchParams.getCategoryName() + "%")));
        }
        return criteriaBuilder.and(predicate.toArray(new Predicate[0]));
    }
}
