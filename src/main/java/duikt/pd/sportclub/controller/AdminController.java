package duikt.pd.sportclub.controller;

import duikt.pd.sportclub.repository.UserRepository;
import duikt.pd.sportclub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin-users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }
}
