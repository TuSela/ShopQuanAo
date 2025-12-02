package com.Nhom19.shopQuanAo.service;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JwtUtils {
    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    public JWTClaimsSet parseToken(String token) {
        try {
            JWSObject object = JWSObject.parse(token);
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

            if (!object.verify(verifier)) {
                throw new RuntimeException("Invalid signature");
            }

            return JWTClaimsSet.parse(object.getPayload().toJSONObject());
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }
}
