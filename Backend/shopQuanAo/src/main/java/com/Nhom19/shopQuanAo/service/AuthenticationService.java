package com.Nhom19.shopQuanAo.service;

import ch.qos.logback.classic.Logger;
import com.Nhom19.shopQuanAo.DTO.Request.AuthenticaitonRequest;
import com.Nhom19.shopQuanAo.DTO.Response.AuthenticationResponse;
import com.Nhom19.shopQuanAo.entity.Users;
import com.Nhom19.shopQuanAo.enums.Role;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
public class AuthenticationService {
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    @Autowired
    private UserRepository userRepository;
    public AuthenticationResponse authenticate (AuthenticaitonRequest request)
    {
        var user = userRepository.findByUsername(request.getUsername());
        if (user == null)
        {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        boolean authentication = userRepository.existsByPassword(request.getPassword());
        if (!authentication)
        {
            throw new AppException(ErrorCode.UNUATHENTICATION);
        }
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(generateToken(user));
        authenticationResponse.setSuccess(true);
        return authenticationResponse;
    }
    private String generateToken(Users user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("shopquanao.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", Role.USER.toString())
                .build();
        Payload payload =new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header,payload);

        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            System.out.println("token= "+ jwsObject.serialize());
            return  jwsObject.serialize();
        }
        catch(JOSEException e){
            throw new AppException(ErrorCode.UNUATHENTICATION);
        }
    }
//    private String buildScope(Users user) {
//        StringJoiner stringJoiner =new StringJoiner(" ");
//        if(!CollectionUtils.isEmpty(user.getRoles())) {
//            user.getRoles().forEach(stringJoiner::add);
//        }
//        return stringJoiner.toString();
//    }
}

