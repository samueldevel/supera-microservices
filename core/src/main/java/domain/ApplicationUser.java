package domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "the field username cannot be empty")
    private String username;

    @ToString.Exclude
    @NotEmpty(message = "the field password cannot be empty")
    private String password;

    @NotEmpty(message = "the field role cannot be empty")
    private String role = "USER";

    public ApplicationUser(@NotEmpty ApplicationUser applicationUser) {
        this.id = applicationUser.getId();
        this.username = applicationUser.getUsername();
        this.password = applicationUser.getPassword();
        this.role = applicationUser.getRole();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ApplicationUser products = (ApplicationUser) o;
        return id != null && Objects.equals(id, products.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
