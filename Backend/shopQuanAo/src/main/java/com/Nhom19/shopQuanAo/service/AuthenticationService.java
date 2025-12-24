package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Customer.AuthenticaitonRequest;
import com.Nhom19.shopQuanAo.DTO.Response.AuthenticationResponse;
import com.Nhom19.shopQuanAo.entity.Admins;
import com.Nhom19.shopQuanAo.entity.Users;
import com.Nhom19.shopQuanAo.enums.Role;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.repository.AdminRepository;
import com.Nhom19.shopQuanAo.repository.CartItemRepo;
import com.Nhom19.shopQuanAo.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class AuthenticationService {
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;

    public AuthenticationResponse authenticate (AuthenticaitonRequest request)
    {
        System.out.println("sdt: "+ request.getSdt());
        Admins admins = adminRepository.findByUsername(request.getSdt());
        if (admins == null) {
            Users user = userRepository.findBySdt(request.getSdt());
            if (user == null) {
                throw new AppException(ErrorCode.USER_NOT_EXISTED);
            }
            boolean authentication = request.getPassword().equals(user.getPassword());

            if (!authentication) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setToken(generateTokenUsers(user));
            authenticationResponse.setSuccess(true);
            return authenticationResponse;
        }
        else {
            boolean authentication = request.getPassword().equals(admins.getPassword());

            if (!authentication) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setToken(generateTokenAdmin(admins));
            authenticationResponse.setSuccess(true);
            return authenticationResponse;
        }
    }

    @Autowired
    CartItemRepo  cartItemRepo;
    public String generateTokenUsers(Users user) {

        Integer soluong = cartItemRepo.countCartItemByUser(user.getMaTk());

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
                .claim("giohang",soluong)
                .build();
        Payload payload =new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header,payload);

        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            System.out.println("token= "+ jwsObject.serialize());
            return  jwsObject.serialize();
        }
        catch(JOSEException e){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
    public String generateTokenAdmin(Admins admins) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(admins.getUsername())
                .issuer("admin.shopquanao.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", Role.ADMIN.toString())
                .build();
        Payload payload =new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header,payload);
        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            System.out.println("token= "+ jwsObject.serialize());
            return  jwsObject.serialize();
        }
        catch(JOSEException e){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
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

