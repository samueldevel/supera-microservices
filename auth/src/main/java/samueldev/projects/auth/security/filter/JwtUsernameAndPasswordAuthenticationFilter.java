package samueldev.projects.auth.security.filter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import domain.ApplicationUser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import property.JwtConfiguration;
import samueldev.projects.auth.mappers.ApplicationUserMapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static samueldev.projects.auth.utils.ValidateNullPointerEntities.validateIfEntityIsNull;

@RequiredArgsConstructor
@Slf4j
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("Attempting authenticate. . 1.");
        ApplicationUser applicationUser = ApplicationUserMapper.INSTANCE.toApplicationUser(request.getInputStream());

        validateIfEntityIsNull(applicationUser, "Unable to retrieve the username or password");

        log.info("Creating the authentication object for the user '{}' and calling loadUserByUsername",
                applicationUser.getUsername());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken
                (applicationUser.getUsername(), applicationUser.getPassword(), emptyList());

        usernamePasswordAuthenticationToken.setDetails(applicationUser);

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        log.info("user '{}' has been successfully authenticated, generating JWE token...", auth.getName());
    }

    private SignedJWT createSignedJWT(Authentication auth) throws JOSEException, NoSuchAlgorithmException {
        log.info("Creating the signed JWT ...");
        ApplicationUser applicationUser = (ApplicationUser) auth.getPrincipal();
        JWTClaimsSet jwtClaimsSet = createJwtClaimsSet(auth, applicationUser);

        KeyPair rsaKeys = generateKeyPair();

        RSAKey jwk = buildingJwk(rsaKeys);

        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256)
                .jwk(jwk)
                .type(JOSEObjectType.JWT)
                .build(), jwtClaimsSet);

        log.info("Signing the token with the private RSA key");

        RSASSASigner signer = new RSASSASigner(rsaKeys.getPrivate());

        signedJWT.sign(signer);

        return signedJWT;
    }

    private JWTClaimsSet createJwtClaimsSet(Authentication auth, ApplicationUser applicationUser) {
        log.info("Creating the JwtClaimSet object for '{}'", applicationUser.toString());
        return new JWTClaimsSet.Builder()
                .subject(applicationUser.getUsername())
                .claim("authorities", auth.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .issuer("samueldev")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + (jwtConfiguration.getExpiration() * 1000L)))
                .build();
    }

    private KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        log.info("Generating RSA 2048 bits Keys");

        KeyPairGenerator rsaKey = KeyPairGenerator.getInstance("RSA");

        rsaKey.initialize(2048);

        return rsaKey.genKeyPair();
    }

    private RSAKey buildingJwk(KeyPair rsaKeys) {
        log.info("Building JWK from the RSA Keys");

        return new RSAKey.Builder((RSAPublicKey) rsaKeys.getPublic())
                .keyID(UUID.randomUUID().toString())
                .build();
    }
}
