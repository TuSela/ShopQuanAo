package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.DTO.Request.Customer.AuthenticaitonRequest;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.AuthenticationResponse;
import com.Nhom19.shopQuanAo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("login")
    public ApiResponse<AuthenticationResponse> login (@RequestBody AuthenticaitonRequest request){
       var result= authenticationService.authenticate(request);
        AuthenticationResponse authenticationResponse=new AuthenticationResponse();
        authenticationResponse.setToken(result.getToken());
        authenticationResponse.setSuccess(result.isSuccess());

        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(authenticationResponse);
        return apiResponse;
    }
}
