package pl.com.przepiora.invoice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.przepiora.invoice.model.User;
import pl.com.przepiora.invoice.service.UserService;

@RestController
public class Test {
    private UserService userService;

    public Test(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("test")
//    public String test(){
//         userService.save(User.builder().email("xfhgdfg").password("qwerty").build());
//        return "test";
//    }

    @GetMapping("admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("all")
    public String all(){
        return "all";
    }

//    @GetMapping("h2-console")
//    public String h2(){
//        return "H2H2H2H2H2";
//    }
}
