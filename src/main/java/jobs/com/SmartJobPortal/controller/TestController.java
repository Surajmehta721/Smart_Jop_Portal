package jobs.com.SmartJobPortal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/testApi")
    public String show()
    {
        return "Accessed";
    }
}
