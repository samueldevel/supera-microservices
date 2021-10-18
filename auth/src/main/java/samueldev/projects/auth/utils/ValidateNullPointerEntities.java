package samueldev.projects.auth.utils;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ValidateNullPointerEntities {

    private ValidateNullPointerEntities() {
    }

    public static void validateIfEntityIsNull(Object entity, String message) {
        if (entity == null) {
            throw new UsernameNotFoundException(message);
        }
    }
}
