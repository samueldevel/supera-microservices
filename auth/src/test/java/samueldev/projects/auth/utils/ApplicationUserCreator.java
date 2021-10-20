package samueldev.projects.auth.utils;

import org.assertj.core.api.Assertions;
import samueldev.projects.core.domain.ApplicationUser;

public class ApplicationUserCreator {

    public static ApplicationUser createApplicationUser() {
        return ApplicationUser.builder()
                .username("samueldev")
                .password("$2a$10$0mEaFV.rStE3LVmOyO97j.UyrCoksbp5AmuMRjrgqglri3kr4/iTa")
                .role("ADMIN")
                .build();
    }

    public static ApplicationUser invalidApplicationUser(String username, String password, String role) {
        return ApplicationUser.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
    }


    public static void verifyFieldsApplicationUser(ApplicationUser entityOne, ApplicationUser entityTwo) {
        Assertions.assertThat(entityOne)
                .isNotNull()
                .isEqualTo(entityTwo);

        Assertions.assertThat(entityOne.getId())
                .isNotNull()
                .isEqualTo(entityTwo.getId());

        Assertions.assertThat(entityOne.getUsername())
                .isNotEmpty()
                .isNotNull()
                .isEqualTo(entityTwo.getUsername());

        Assertions.assertThat(entityOne.getPassword())
                .isNotNull()
                .isEqualTo(entityTwo.getPassword());

        Assertions.assertThat(entityOne.getRole())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(entityTwo.getRole());
    }
}
