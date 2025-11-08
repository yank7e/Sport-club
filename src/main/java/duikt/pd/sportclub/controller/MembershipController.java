package duikt.pd.sportclub.controller;

import duikt.pd.sportclub.entity.User;
import duikt.pd.sportclub.service.MembershipService;
import duikt.pd.sportclub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;
    private final UserService userService;

    @GetMapping("/services/subscribe/{typeId}")
    public String subscribe(@PathVariable Long typeId, Principal principal) {

        String phoneNumber = principal.getName();
        User user = userService.findUserByPhoneNumber(phoneNumber);

        membershipService.subscribeUserToMembership(user.getId(), typeId);

        return "redirect:/profile";
    }
}
