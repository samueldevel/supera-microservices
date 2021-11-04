package samueldev.projects.auth.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import samueldev.projects.auth.utils.ApplicationUserCreator;
import samueldev.projects.core.domain.ApplicationUser;
import samueldev.projects.core.repository.ApplicationUserRepository;

import javax.validation.ConstraintViolationException;

@DataJpaTest
@Slf4j
@DisplayName("Tests for application user repository")
class ApplicationUserRepositoryTest {


    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Test
    @DisplayName("save persists the returned data when successful ")
    void save_PersistsTheReturnedData_WhenSuccessful() {
        ApplicationUser applicationUser = ApplicationUserCreator.createApplicationUser();

        ApplicationUser applicationSaved = applicationUserRepository.save(applicationUser);

        ApplicationUserCreator.verifyFieldsApplicationUser(applicationUser, applicationSaved);

    }

    @Test
    @DisplayName("findByUsername returns the user saved before when successful")
    void findByUsername_ReturnsTheUserSavedBefore_WhenSuccessful() {
        ApplicationUser applicationUser = ApplicationUserCreator.createApplicationUser();

        ApplicationUser applicationSaved = applicationUserRepository.save(applicationUser);

        ApplicationUser samueldev = applicationUserRepository.findByUsername(applicationUser.getUsername());

        boolean verifyBcrypt = new BCryptPasswordEncoder().matches("senha123", samueldev.getPassword());

        Assertions.assertThat(verifyBcrypt).isTrue();

        ApplicationUserCreator.verifyFieldsApplicationUser(samueldev, applicationSaved);
    }

    // ---------------------------------------- EDGE CASES ----------------------------------------

    @Test
    @DisplayName("save throw ConstraintViolationException when the username field is null and empty ")
    void save_ThrowConstraintViolationExceptionWhenTheUsernameFieldIsNullAndEmpty() {
        ApplicationUser invalidUsernameEmpty =
                ApplicationUserCreator.invalidApplicationUser("", "password", "ADMIN");

        ApplicationUser invalidUsernameNull =
                ApplicationUserCreator.invalidApplicationUser(null, "password", "ADMIN");

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() ->
                        applicationUserRepository.save(invalidUsernameEmpty))
                .withMessageContaining("the field username cannot be empty");

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() ->
                        applicationUserRepository.save(invalidUsernameNull))
                .withMessageContaining("the field username cannot be empty");
    }

    @Test
    @DisplayName("save throw ConstraintViolationException when the password field is null and empty ")
    void save_ThrowConstraintViolationExceptionWhenThePasswordFieldIsNullAndEmpty() {
        ApplicationUser invalidPasswordEmpty =
                ApplicationUserCreator.invalidApplicationUser("username", "", "ADMIN");

        ApplicationUser invalidPasswordNull =
                ApplicationUserCreator.invalidApplicationUser("username", null, "ADMIN");

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() ->
                        applicationUserRepository.save(invalidPasswordEmpty))
                .withMessageContaining("the field password cannot be empty");

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() ->
                        applicationUserRepository.save(invalidPasswordNull))
                .withMessageContaining("the field password cannot be empty");
    }

    @Test
    @DisplayName("save throw ConstraintViolationException when the role field is null and empty ")
    void save_ThrowConstraintViolationExceptionWhenTheRoleFieldIsNullAndEmpty() {
        ApplicationUser invalidRoleEmpty =
                ApplicationUserCreator.invalidApplicationUser("username", "password", "");

        ApplicationUser invalidRoleNull =
                ApplicationUserCreator.invalidApplicationUser("username", "password", null);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() ->
                        applicationUserRepository.save(invalidRoleEmpty))
                .withMessageContaining("the field role cannot be empty");

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() ->
                        applicationUserRepository.save(invalidRoleNull))
                .withMessageContaining("the field role cannot be empty");
    }

    @Test
    @DisplayName("findByUsername Returns null when username not found")
    void findAllByName_ReturnsEmptyList_WhenNameNotFound() {
        ApplicationUser userSaved = this.applicationUserRepository.findByUsername("application user");

        Assertions.assertThat(userSaved)
                .isNull();
    }

}
