package com.survey.web.rest.auth;


import com.survey.config.JwtTokenUtil;
import com.survey.domain.auth.JwtRequest;
import com.survey.domain.auth.JwtResponse;
import com.survey.response.ActionResponse;
import com.survey.service.auth.JwtUserDetailsService;
import com.survey.service.auth.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok().body(new JwtResponse(token));
        } catch (UsernameNotFoundException usernameNotFoundException) {
            ActionResponse response = new ActionResponse();
            response.setSuccessful(false);
            response.setResult(usernameNotFoundException.getLocalizedMessage());
            response.setException(true);
            return ResponseEntity.ok().body(response);
        } catch (Exception exception) {
            ActionResponse response = new ActionResponse();
            response.setSuccessful(false);
            response.setResult(exception.getLocalizedMessage());
            response.setException(true);
            return ResponseEntity.ok().body(response);
        }

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
