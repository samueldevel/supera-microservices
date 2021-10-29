package samueldev.projects.auth.token;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import samueldev.projects.auth.security.user.UserDetailsServiceImpl;
import samueldev.projects.core.domain.ApplicationUser;
import samueldev.projects.core.property.JwtConfiguration;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenCreatorNonAuthentication {
    private final JwtConfiguration jwtConfiguration;
    private final UserDetailsServiceImpl userDetailsService;

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

    public String encryptToken(SignedJWT signedJWT) throws JOSEException {
        log.info("Starting the encryptToken method");

        DirectEncrypter directEncrypter = new DirectEncrypter(jwtConfiguration.getPrivateKey().getBytes());

        JWEObject jweObject = new JWEObject(new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256)
                .contentType("JWT")
                .build(), new Payload(signedJWT));

        log.info("Encrypting token with private key");

        jweObject.encrypt(directEncrypter);

        log.info("Token encrypted");

        return jweObject.serialize();
    }
}
