package samueldev.projects.auth.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import samueldev.projects.auth.mappers.ApplicationUserMapper;
import samueldev.projects.auth.requests.RequestApplicationUserToCreateToken;
import samueldev.projects.auth.token.TokenCreatorWithoutContext;
import samueldev.projects.core.domain.ApplicationUser;
import samueldev.projects.core.property.JwtConfiguration;
import samueldev.projects.core.repository.ApplicationUserRepository;
import samueldev.projects.security.token.converter.TokenConverter;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class ApplicationUserService {
    private final ApplicationUserRepository applicationUserRepository;
    private final TokenCreatorWithoutContext tokenCreatorWithoutContext;
    private final TokenConverter tokenConverter;
    private final JwtConfiguration jwtConfiguration;

    public ApplicationUser findApplicationUserByUserAndPass(String username, String password) {

        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(password, applicationUser.getPassword()))
            throw new UsernameNotFoundException("Unable to retrieve password");

        return applicationUser;
    }

    public JWTClaimsSet getUserInfo(String token) throws ParseException {
        if (!token.startsWith(jwtConfiguration.getHeader().getPrefix()))
            throw new ParseException("Bearer not found", 1);

        String validToken = token.replace(jwtConfiguration.getHeader().getPrefix(), "");
        String decryptedToken = tokenConverter.decryptToken(validToken);
        tokenConverter.validateTokenSignature(decryptedToken);

        return JWTParser.parse(decryptedToken).getJWTClaimsSet();
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
