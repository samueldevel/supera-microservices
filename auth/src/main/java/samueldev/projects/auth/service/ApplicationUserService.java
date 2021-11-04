package samueldev.projects.auth.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import samueldev.projects.auth.mappers.ApplicationUserMapper;
import samueldev.projects.auth.requests.RequestApplicationUserToCreateToken;
import samueldev.projects.auth.token.TokenCreatorWithoutContext;
import samueldev.projects.core.domain.ApplicationUser;
import samueldev.projects.core.property.JwtConfiguration;
import samueldev.projects.core.repository.ApplicationUserRepository;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ApplicationUserService {
    private final ApplicationUserRepository applicationUserRepository;
    private final TokenCreatorWithoutContext tokenCreatorWithoutContext;
    private final JwtConfiguration jwtConfiguration;

    public ApplicationUser findApplicationUserByUserAndPass(String username, String password) {

        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(password, applicationUser.getPassword()))
            throw new UsernameNotFoundException("Unable to retrieve password");

        return applicationUser;
    }

    public ApplicationUser getUserInfo(Principal principal) {
        return (ApplicationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    }

    public String getUserToken(RequestApplicationUserToCreateToken requestApplicationUserToCreateToken) throws NoSuchAlgorithmException, JOSEException {
        ApplicationUser applicationUser = ApplicationUserMapper.INSTANCE.toApplicationUser(requestApplicationUserToCreateToken);

        ApplicationUser applicationUserByUserAndPass = findApplicationUserByUserAndPass
                (applicationUser.getUsername(), applicationUser.getPassword());

        SignedJWT signedJWT = tokenCreatorWithoutContext.createSignedJWT(applicationUserByUserAndPass);

        String encryptToken = tokenCreatorWithoutContext.encryptToken(signedJWT);

        return jwtConfiguration.getHeader().getPrefix() + encryptToken;
    }
}
