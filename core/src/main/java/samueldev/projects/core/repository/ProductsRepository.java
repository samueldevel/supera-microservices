package samueldev.projects.core.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import samueldev.projects.core.domain.Products;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Products, Long> {
    List<Products> findByName(String name);
}
