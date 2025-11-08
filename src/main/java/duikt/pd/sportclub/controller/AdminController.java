package duikt.pd.sportclub.controller;

import duikt.pd.sportclub.dto.MembershipTypeDto;
import duikt.pd.sportclub.dto.NewsDto;
import duikt.pd.sportclub.entity.MembershipType;
import duikt.pd.sportclub.entity.News;
import duikt.pd.sportclub.entity.User;
import duikt.pd.sportclub.repository.RoleRepository;
import duikt.pd.sportclub.repository.UserRepository;
import duikt.pd.sportclub.service.MembershipTypeService;
import duikt.pd.sportclub.service.NewsService;
import duikt.pd.sportclub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin-panel")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final NewsService newsService;
    private final MembershipTypeService membershipTypeService;
    private final RoleRepository roleRepository;

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin-users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin-panel/users";
    }

    @GetMapping("/news")
    public String listNews(Model model) {
        model.addAttribute("newsList", newsService.findAllSorted());
        return "admin-news";
    }

    @GetMapping("/news/new")
    public String showCreateNewsForm(Model model) {
        model.addAttribute("newsDto", new NewsDto());
        return "admin-news-form";
    }

    @PostMapping("/news/new")
    public String createNews(@ModelAttribute("newsDto") NewsDto newsDto, Principal principal) {
        newsService.createNews(newsDto, principal.getName());
        return "redirect:/admin-panel/news";
    }

    @GetMapping("/news/edit/{id}")
    public String showEditNewsForm(@PathVariable Long id, Model model) {
        News news = newsService.findById(id);
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setContent(news.getContent());
        newsDto.setImageUrl(news.getImageUrl());
        model.addAttribute("newsDto", newsDto);
        return "admin-news-form";
    }

    @PostMapping("/news/edit/{id}")
    public String updateNews(@PathVariable Long id, @ModelAttribute("newsDto") NewsDto newsDto) {
        newsService.updateNews(id, newsDto);
        return "redirect:/admin-panel/news";
    }

    @GetMapping("/news/delete/{id}")
    public String deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return "redirect:/admin-panel/news";
    }

    @GetMapping("/services")
    public String listServices(Model model) {
        model.addAttribute("serviceList", membershipTypeService.findAll());
        return "admin-services";
    }

    @GetMapping("/services/new")
    public String showCreateServiceForm(Model model) {
        model.addAttribute("serviceDto", new MembershipTypeDto());
        return "admin-services-form";
    }

    @GetMapping("/services/edit/{id}")
    public String showEditServiceForm(@PathVariable Long id, Model model) {
        MembershipType entity = membershipTypeService.findById(id);
        MembershipTypeDto dto = membershipTypeService.mapEntityToDto(entity);
        model.addAttribute("serviceDto", dto);
        return "admin-services-form";
    }

    @PostMapping("/services/save")
    public String saveService(@ModelAttribute MembershipTypeDto dto) {
        if (dto.getId() == null) {
            membershipTypeService.create(dto);
        } else {
            membershipTypeService.update(dto.getId(), dto);
        }
        return "redirect:/admin-panel/services";
    }

    @GetMapping("/services/delete/{id}")
    public String deleteService(@PathVariable Long id) {
        membershipTypeService.deleteById(id);
        return "redirect:/admin-panel/services";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserRolesForm(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleRepository.findAll());
        return "admin-users-edit";
    }

    @PostMapping("/users/save")
    public String saveUserRoles(@RequestParam("userId") Long userId,
                                @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {

        userService.updateUserRoles(userId, roleIds);
        return "redirect:/admin-panel/users";
    }
}
