package jobs.com.SmartJobPortal.service;

import jobs.com.SmartJobPortal.UserRepo;
import jobs.com.SmartJobPortal.entity.JobPortalUser;

import jobs.com.SmartJobPortal.model.LoginRequest;
import jobs.com.SmartJobPortal.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    public ResponseEntity<?> addUser(JobPortalUser user) {
        Optional<JobPortalUser> tempUser=userRepo.findByUsername(user.getUsername());
        if(tempUser.isPresent())
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Already Exist");

        user.setRole(user.getRole());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Registered Successfully");

    }

    public String login(LoginRequest user) {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        if(authentication.isAuthenticated())
        {
            JobPortalUser portalUser =userRepo.findByEmailOrUsername(user.getUsername(), user.getUsername()).orElse(null);
            return jwtService.generateToken(portalUser.getUsername());
        }
        return "loing failed";
    }
}
