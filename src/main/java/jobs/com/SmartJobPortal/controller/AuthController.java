package jobs.com.SmartJobPortal.controller;

import jobs.com.SmartJobPortal.entity.JobPortalUser;
import jobs.com.SmartJobPortal.model.LoginRequest;
import jobs.com.SmartJobPortal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/public")
public class AuthController {

    @Autowired
    UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody JobPortalUser user)
    {
        return userService.addUser(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest user)
    {
        return userService.login(user);

    }
    

}
