package me.dongqinglin.auto.controller;

import me.dongqinglin.auto.bean.AuthenticationRequest;
import me.dongqinglin.auto.bean.AuthenticationResponse;
import me.dongqinglin.auto.bean.SignUpResponse;
import me.dongqinglin.auto.entity.User;
import me.dongqinglin.auto.service.UserService;
import me.dongqinglin.auto.serviceImplement.MyUserDetailService;
import me.dongqinglin.auto.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api")
public class MainController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailService userDetailService;
    @Autowired
    private JwtUtil jwtTokenUtils;
    @Autowired
    private UserService userService;

    @PostMapping("signIn")
    public ResponseEntity<?> signIn(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        AuthenticationResponse result;
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new Exception("incoorect username or passWord", e);
        }
        final UserDetails userDetails = userDetailService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtils.generateToken(userDetails);
        result = new AuthenticationResponse(jwt);
        return ResponseEntity.ok(result);
    }

    @PostMapping("signUp")
    public ResponseEntity<?> signUp(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        SignUpResponse result;

        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()){
            result = new SignUpResponse(false, "该用户已存在");
        }else {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setEnable(true);
            newUser.setRoles("ROLES_USER");
            userService.save(newUser);
            result = new SignUpResponse(true, "注册成功");
        }

        return ResponseEntity.ok(result);
    }

//    @PostMapping("/signOut")
//    public ResponseEntity<?> signOut(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
//        System.out.println(authenticationRequest.getUsername());
//        System.out.println(authenticationRequest.getPassword());
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
//            );
//        }catch (BadCredentialsException e){
//            throw new Exception("incoorect username or PassWord", e);
//        }
//        final UserDetails userDetails = userDetailService
//                .loadUserByUsername(authenticationRequest.getUsername());
//
//        final String jwt = jwtTokenUtils.generateToken(userDetails);
//
//        return ResponseEntity.ok(new AuthenticationResponse(jwt));
//    }
}
