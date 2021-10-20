package samueldev.projects.gateway.util;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import samueldev.projects.gateway.converter.TokenConverterGateway;

import java.text.ParseException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final TokenConverterGateway tokenConverterGateway;

    public JWTClaimsSet getAllClaimsFromToken(String token) throws ParseException {
        String signedToken = tokenConverterGateway.decryptToken(token);
        SignedJWT signedJWT = tokenConverterGateway.validateTokenSignature(signedToken);

        return signedJWT.getJWTClaimsSet();
    }

    public boolean isRoleValid(String token) throws ParseException {
        return this.getAllClaimsFromToken(token).getClaim("authorities").equals("ROLE_ADMIN");
    }

    private boolean isTokenExpired(String token) throws ParseException {
        return this.getAllClaimsFromToken(token).getExpirationTime().before(new Date());
    }

    public boolean isInvalid(String token) throws ParseException {
        return this.isTokenExpired(token);
    }

}