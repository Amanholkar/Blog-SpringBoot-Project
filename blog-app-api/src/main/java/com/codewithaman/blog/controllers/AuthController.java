package com.codewithaman.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithaman.blog.exceptions.ApiException;
import com.codewithaman.blog.payloads.ApiResponse;
import com.codewithaman.blog.payloads.JwtAuthRequest;
import com.codewithaman.blog.payloads.JwtAuthResponse;
import com.codewithaman.blog.payloads.UserDto;
import com.codewithaman.blog.security.JWTTokenHelper;
import com.codewithaman.blog.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    

    @Autowired
    private UserService userService;


    // login api 
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> createToken(
        @RequestBody JwtAuthRequest request
    ) throws Exception{


        this.authenticate(request.getUsername(), request.getPassword());


        UserDetails userDetails =  this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtTokenHelper.generateToken(userDetails);

       JwtAuthResponse authResponse =  new JwtAuthResponse();
       
       authResponse.setToken(token);

        return new ResponseEntity<ApiResponse>(new ApiResponse(authResponse,"success","1"), HttpStatus.OK) ;

    }


    //Register Api 
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserDto userDto){

        // User user = this.modelMapper.map(userDto, User.class);

        UserDto updateUser =  this.userService.registerNewUser(userDto);
        return new  ResponseEntity<>(new ApiResponse(updateUser,"success","1"),HttpStatus.CREATED);
    }








    private void authenticate(String username, String password) throws Exception {


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);


        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
          System.out.println("Invalid Detials!!");
          throw new ApiException("Invalid Username and Password!!");
        }
    
           
        
      
    }

    
}
