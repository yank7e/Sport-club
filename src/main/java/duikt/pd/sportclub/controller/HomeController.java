package duikt.pd.sportclub.controller;

import duikt.pd.sportclub.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class HomeController {

    private final NewsService newsService;

    @GetMapping("/")
    public String homePage() {
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
}
