package com.tutego.date4u.interfaces.rest;

import com.tutego.date4u.core.Unicorn;
import com.tutego.date4u.core.UnicornService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private UnicornService unicornService;

    public AuthController(UnicornService unicornService) {
        this.unicornService = unicornService;
    }
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login(){
        System.out.println("login");
        return "login";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto("","","",null);
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        System.out.println("Register save");
        Unicorn existingUser = unicornService.getUnicornByEmail(userDto.email());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        unicornService.createNewUser(userDto);
        return "redirect:/register?success";
    }
}