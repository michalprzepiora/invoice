package pl.com.przepiora.invoice.controller;

import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.com.przepiora.invoice.model.dto.NewUserDTO;
import pl.com.przepiora.invoice.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SignUpController {

    private UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public ModelAndView signupGet(ModelAndView modelAndView) {
        modelAndView.setViewName("signup");
        modelAndView.addObject("newUserDTO", new NewUserDTO());
        return modelAndView;
    }

    @PostMapping("/signup")
    public ModelAndView saveUser(ModelAndView modelAndView, @Valid @ModelAttribute NewUserDTO newUserDTO, Errors errors) {
        errors.addAllErrors(userService.validateRetypingPassword(newUserDTO));
        if (errors.hasErrors()) {
            modelAndView.addObject("errorMessages", getErrorMessages(errors));
            modelAndView.setStatus(HttpStatusCode.valueOf(400));
            modelAndView.setViewName("signup");
        } else {
            userService.addNewUser(newUserDTO);
            modelAndView.setStatus(HttpStatusCode.valueOf(201));
            modelAndView.setViewName("redirect:login");
        }
        return modelAndView;
    }

    private List<String> getErrorMessages(Errors errors) {
        if (errors.hasErrors()) {
            return errors.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        }
        return Collections.emptyList();
    }
}
