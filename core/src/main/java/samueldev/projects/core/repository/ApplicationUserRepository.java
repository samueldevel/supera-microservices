package samueldev.projects.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samueldev.projects.core.domain.ApplicationUser;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
}
