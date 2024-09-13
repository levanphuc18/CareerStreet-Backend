package com.careerstreet.user_service.controller;

import com.careerstreet.user_service.dto.*;
import com.careerstreet.user_service.entity.Account;
import com.careerstreet.user_service.exception.GlobalCode;
import com.careerstreet.user_service.service.AccountService;
import com.careerstreet.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/")
public class UserController {

    private final AccountService accountService;
    private final AuthService authService;


    @PostMapping("register")
    public ResponseEntity<ApiResponse<AccountResponse>> registerAccount(@RequestBody RegisterRequest registerRequest){
//        registerRequest.setRole(3L);
        registerRequest.setRole(registerRequest.getRole());
        AccountResponse response = accountService.registerAccount(registerRequest);
        TokenResponse tokenResponse = authService.createToken(registerRequest.getUsername(),registerRequest.getPassword());

//        System.out.println(response.getUsername()+"haha");
        response.setToken(tokenResponse.getToken());
//        Long candidate_id = accountService.getUserIdByUserName(response.getUsername());
//        response.setUserId(999l);
        System.out.println(response.getUsername()+" user");
        System.out.println(response.getUserId()+" kaka");

        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Đăng kí tài khoản ứng viên thành công",response);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<AccountResponse>> loginAccount(@RequestBody LoginRequest loginRequest){


        TokenResponse tokenResponse = authService.createToken(loginRequest.getUsername(),loginRequest.getPassword());
        Long candidate_id = accountService.getCandidateIdByUsername(loginRequest.getUsername());
        System.out.println(" rsp login + ID CANDIADTE " + candidate_id);
        AccountResponse accountResponse = new AccountResponse(loginRequest.getUsername(),tokenResponse.getToken(),candidate_id);
        ApiResponse<AccountResponse> response = new ApiResponse<>(GlobalCode.SUCCESS,"Đăng nhập thành công",accountResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("getroleid/byusername/{username}")
    public ResponseEntity<ApiResponse<Long>> getRoleIdByUserName(@PathVariable String username){
        System.out.println("username "+username);
        Long roleid = accountService.getRoleIdByUsername(username);
        String roleString = (roleid == 1) ? "admin" :
                (roleid == 2) ? "employer" :
                        (roleid == 3) ? "candidate" : "Không xác định";
        System.out.println("roleId is "+roleString);
        ApiResponse<Long> response = new ApiResponse<>(GlobalCode.SUCCESS,roleString,roleid);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("getaccount/{username}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountByUserName(@PathVariable String username){
        System.out.println("username "+username);
        Account accountResponse = accountService.getAccount(username);

        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS,"thanh cong",accountResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
