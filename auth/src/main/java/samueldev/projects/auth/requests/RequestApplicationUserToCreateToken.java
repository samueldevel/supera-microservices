package samueldev.projects.auth.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RequestApplicationUserToCreateToken {
    @NotEmpty(message = "the field username cannot be empty")
    private String username;

    @NotEmpty(message = "the field password cannot be empty")
    private String password;
}
