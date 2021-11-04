package samueldev.projects.auth.controller;

import com.nimbusds.jose.JOSEException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samueldev.projects.auth.requests.RequestApplicationUserToCreateToken;
import samueldev.projects.auth.service.ApplicationUserService;
import samueldev.projects.core.domain.ApplicationUser;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
@Api(value = "Endpoints to manage auth users")
@CrossOrigin(value = "*")
public class ApplicationUserController {
    private final ApplicationUserService applicationUserService;

    @GetMapping(path = "/info")
    @ApiOperation(value = "Get principal user information after set token on header request", response = ApplicationUser.class)
    public ResponseEntity<ApplicationUser> getUserInfo(Principal principal) {
        return new ResponseEntity<>(applicationUserService.getUserInfo(principal), HttpStatus.OK);
    }

    @PostMapping(path = "/token")
    @ApiOperation(value = "Manages token after set exist user on request body", response = ApplicationUser.class)
    public ResponseEntity<String> getUserToken(@RequestBody RequestApplicationUserToCreateToken requestApplicationUserToCreateToken) throws NoSuchAlgorithmException, JOSEException {

        return new ResponseEntity<>(applicationUserService.getUserToken(requestApplicationUserToCreateToken), HttpStatus.OK);
    }

}
