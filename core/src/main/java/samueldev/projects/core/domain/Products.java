package domain;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "the field name cannot be empty")
    private String name;

    @NotNull(message = "the field price cannot be null")
    @Range(min = 1, message = "the field price need be more than one")
    private double price;

    @NotNull(message = "the field score cannot be null")
    @Range(min = 1, message = "the field score need be more than one")
    private int score;

    @NotEmpty(message = "the field image cannot be empty")
    private String image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Products products = (Products) o;
        return id != null && Objects.equals(id, products.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
