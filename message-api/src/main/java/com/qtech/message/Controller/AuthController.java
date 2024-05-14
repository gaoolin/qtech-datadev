package com.qtech.message.Controller;

import com.qtech.message.config.jwt.JwtRequest;
import com.qtech.message.config.jwt.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.qtech.message.config.jwt.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 09:54:18
 * desc   :
 */


@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest, HttpServletRequest httpServletRequest) {
        try {
            authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
            Map<String, String> response = new HashMap<>();
            String token = jwtUtils.generateToken(new UserPrincipal(jwtRequest.getUsername()));
            response.put("token", "Bearer " + token);
            return ResponseEntity.ok(response);
        } catch (DisabledException e) {
            return ResponseEntity.unprocessableEntity().body("User is disabled");
        } catch (BadCredentialsException e) {
            return ResponseEntity.unprocessableEntity().body("Invalid credentials");
        }
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized");
        }
    }
}
