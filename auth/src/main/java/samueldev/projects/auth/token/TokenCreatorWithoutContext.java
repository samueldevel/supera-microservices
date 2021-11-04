package samueldev.projects.auth.token;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import samueldev.projects.auth.security.user.UserDetailsServiceImpl;
import samueldev.projects.core.domain.ApplicationUser;
import samueldev.projects.core.property.JwtConfiguration;
import samueldev.projects.security.token.creator.TokenCreator;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TokenCreatorWithoutContext extends TokenCreator {
    private final UserDetailsServiceImpl userDetailsService;

    public TokenCreatorWithoutContext(JwtConfiguration jwtConfiguration, UserDetailsServiceImpl userDetailsService) {
        super(jwtConfiguration);
        this.userDetailsService = userDetailsService;
    }

    public SignedJWT createSignedJWT(ApplicationUser applicationUser) throws JOSEException, NoSuchAlgorithmException {
        log.info("Creating the signed JWT ...");
        JWTClaimsSet jwtClaimsSet = createJwtClaimsSet(applicationUser);

        KeyPair rsaKeys = generateKeyPair();

        RSAKey jwk = buildingJwk(rsaKeys);

        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256)
                .jwk(jwk)
                .type(JOSEObjectType.JWT)
                .build(), jwtClaimsSet);

        log.info("Signing the token with the private RSA key");

        RSASSASigner signer = new RSASSASigner(rsaKeys.getPrivate());

        signedJWT.sign(signer);

        log.info("Serialized token '{}'", signedJWT.serialize());

        return signedJWT;
    }

    private JWTClaimsSet createJwtClaimsSet(ApplicationUser applicationUser) {
        log.info("Creating the JwtClaimSet object for '{}'", applicationUser.toString());

        UserDetails userDetails = userDetailsService.loadUserByUsername(applicationUser.getUsername());

        return new JWTClaimsSet.Builder()
                .subject(applicationUser.getUsername())
                .claim("authorities", userDetails.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .claim("userId", applicationUser.getId())
                .issuer("samueldev")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + (3600 * 1000)))
                .build();
    }

}
