package duikt.pd.sportclub.controller;

import duikt.pd.sportclub.dto.UserRegistrationDto;
import duikt.pd.sportclub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDto") UserRegistrationDto registrationDto) {

        try {
            userService.registerUser(registrationDto);
        } catch (RuntimeException e) {
            return "redirect:/auth/register?error";
        }

        return "redirect:/auth/register?success";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
