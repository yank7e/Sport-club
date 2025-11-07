package duikt.pd.sportclub.controller;

import duikt.pd.sportclub.dto.NewsDto;
import duikt.pd.sportclub.entity.News;
import duikt.pd.sportclub.repository.UserRepository;
import duikt.pd.sportclub.service.NewsService; // 1. ПЕРЕВІР, ЩО ЦЕЙ ІМПОРТ Є
import duikt.pd.sportclub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*; // 2. ПЕРЕВІР, ЩО ЦЕЙ ІМПОРТ Є

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final NewsService newsService;

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
    public String createNews(@ModelAttribute("newsDto") NewsDto newsDto,
                             Authentication authentication) {
        newsService.createNews(newsDto, authentication.getName());
        return "redirect:/admin/news";
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
        return "redirect:/admin/news";
    }

    @GetMapping("/news/delete/{id}")
    public String deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return "redirect:/admin/news";
    }
}
