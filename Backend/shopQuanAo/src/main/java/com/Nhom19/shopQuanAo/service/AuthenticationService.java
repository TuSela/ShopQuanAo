package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Customer.AuthenticaitonRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.AuthenticationResponse;
import com.Nhom19.shopQuanAo.entity.Users;
import com.Nhom19.shopQuanAo.enums.Role;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class AuthenticationService {
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    @Autowired
    private UserRepository userRepository;
    public AuthenticationResponse authenticate (AuthenticaitonRequest request)
    {
        System.out.println("sdt: "+ request.getSdt());
        Users user = userRepository.findBySdt(request.getSdt());
        if (user == null)
        {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        boolean authentication = request.getPassword().equals(user.getPassword());

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
                .subject(user.getSdt())
                .issuer("shopquanao.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("id",user.getMaTk())
                .claim("hoten",user.getHoten())
                .claim("scope", Role.USER.toString())
                .claim("avatar",user.getAvatar())
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

