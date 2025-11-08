package duikt.pd.sportclub.controller;

import duikt.pd.sportclub.dto.ProfileEditDto;
import duikt.pd.sportclub.entity.User;
import duikt.pd.sportclub.service.MembershipService;
import duikt.pd.sportclub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final MembershipService membershipService;

    @GetMapping
    public String showProfilePage(Model model, Principal principal) {

        String phoneNumber = principal.getName();
        User currentUser = userService.findUserByPhoneNumber(phoneNumber);

        model.addAttribute("user", currentUser);
        model.addAttribute("memberships", membershipService.findMembershipsByUserId(currentUser.getId()));

        return "profile";
    }

    @GetMapping("/edit")
    public String showEditProfileForm(Model model, Principal principal) {
        User currentUser = userService.findUserByPhoneNumber(principal.getName());

        ProfileEditDto profileDto = new ProfileEditDto();
        profileDto.setFirstName(currentUser.getFirstName());
        profileDto.setLastName(currentUser.getLastName());
        profileDto.setEmail(currentUser.getEmail());

        model.addAttribute("profileDto", profileDto);
        return "profile-edit";
    }

    @PostMapping("/edit")
    public String updateProfile(
            @ModelAttribute("profileDto") ProfileEditDto profileDto,
            Principal principal) {

        userService.updateProfile(principal.getName(), profileDto);

        return "redirect:/profile";
    }
}
