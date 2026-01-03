package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Customer.AuthenticaitonRequest;
import com.Nhom19.shopQuanAo.DTO.Response.AuthenticationResponse;
import com.Nhom19.shopQuanAo.entity.Admin;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private AdminRepository adminRepository;

    public AuthenticationResponse authenticate (AuthenticaitonRequest request)
    {
        System.out.println("sdt: "+ request.getSdt());
        Admin admin = adminRepository.findByUsername(request.getSdt()).orElse(null);
        if (admin == null) {
            Users user = userRepository.findBySdt(request.getSdt());
            if (user == null) {
                throw new AppException(ErrorCode.USER_NOT_EXISTED);
            }
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            boolean authentication = passwordEncoder.matches(request.getPassword(), user.getPassword())&& user.getTrangThai() == true;
//            boolean authentication = request.getPassword().equals(user.getPassword()) && user.getTrangThai() == true;
            if (!authentication) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setToken(generateTokenUsers(user));
            authenticationResponse.setSuccess(true);
            return authenticationResponse;
        }
        else {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            boolean authentication = passwordEncoder.matches(request.getPassword(), admin.getPassword())&& admin.getTrangThai() == true;

            if (!authentication) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setToken(generateTokenAdmin(admin));
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
                .claim("scope",Role.USER.toString())
                .claim("avatar",user.getAvatar())
                .claim("giohang",soluong)
                .build();
        Payload payload =new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header,payload);

        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return  jwsObject.serialize();
        }
        catch(JOSEException e){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
    public String generateTokenAdmin(Admin admin) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(admin.getUsername())
                .issuer("admin.shopquanao.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(admin))
                .build();
        Payload payload =new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header,payload);
        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return  jwsObject.serialize();
        }
        catch(JOSEException e){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
    private String buildScope(Admin admin) {
        StringJoiner stringJoiner =new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(admin.getRoles()))
            admin.getRoles().forEach(role ->{
                stringJoiner.add("ROLE_" + role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions()
                            .forEach(permission ->stringJoiner.add(permission.getName()));
            });
        return stringJoiner.toString();
    }
}

