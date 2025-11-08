package duikt.pd.sportclub.controller;

import duikt.pd.sportclub.entity.Membership;
import duikt.pd.sportclub.entity.User;
import duikt.pd.sportclub.repository.MembershipTypeRepository;
import duikt.pd.sportclub.service.MembershipService;
import duikt.pd.sportclub.service.NewsService;
import duikt.pd.sportclub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final NewsService newsService;
    private final MembershipTypeRepository membershipTypeRepository;
    private final MembershipService membershipService;
    private final UserService userService;

    @GetMapping("/")
    public String homePage(Model model, Principal principal) {
        model.addAttribute("latestNews", newsService.findLatest3News());

        if (principal != null) {
            String phoneNumber = principal.getName();
            User currentUser = userService.findUserByPhoneNumber(phoneNumber);
            List<Membership> memberships = membershipService.findMembershipsByUserId(currentUser.getId());

            model.addAttribute("user", currentUser);
            model.addAttribute("memberships", memberships);
        }

        return "index";
    }

    @GetMapping("/news")
    public String listPublicNews(Model model) {
        model.addAttribute("newsList", newsService.findAllSorted());
        return "news-list";
    }

    @GetMapping("/news/{id}")
    public String showNewsDetail(@PathVariable Long id, Model model) {
        model.addAttribute("news", newsService.findById(id));
        return "news-detail";
    }

    @GetMapping("/services")
    public String showServicesPage(Model model) {
        model.addAttribute("membershipTypes", membershipTypeRepository.findAll());
        return "services-list";
    }
}
