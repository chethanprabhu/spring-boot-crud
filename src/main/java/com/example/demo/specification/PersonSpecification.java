package com.example.demo.specification;

import com.example.demo.entity.Person;
import org.springframework.data.jpa.domain.Specification;

public class PersonSpecification {
    public static Specification<Person> hasMarried(Boolean married) {
        if(married == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> cb.equal(root.get("married"), married);
    }

    public static Specification<Person> ageFilter(Integer minAge, Integer maxAge) {
        if(minAge == null && maxAge == null) {
            return Specification.unrestricted();
        }

        if(minAge == null) {
            return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("age"), maxAge);
        }

        if(maxAge == null) {
            return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("age"), minAge);
        }

        return (root, query, cb) -> cb.and(cb.greaterThanOrEqualTo(root.get("age"), minAge), cb.lessThanOrEqualTo(root.get("age"), maxAge));
    }

    public static Specification<Person> searchSpec(String search) {
        if(search == null) {
            return Specification.unrestricted();
        }

        String pattern = "%" + search.toLowerCase() + "%";

        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("name")), "%" + pattern + "%"),
                cb.like(root.get("phoneNumber"), "%" + pattern + "%"));
    }
}
