package duikt.pd.sportclub.service.impl;

import duikt.pd.sportclub.dto.NewsDto;
import duikt.pd.sportclub.entity.News;
import duikt.pd.sportclub.entity.User;
import duikt.pd.sportclub.exception.ResourceNotFoundException;
import duikt.pd.sportclub.repository.NewsRepository;
import duikt.pd.sportclub.repository.UserRepository;
import duikt.pd.sportclub.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    @Override
    public News createNews(NewsDto newsDto, String phoneNumber) {
        User author = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with phone: " + phoneNumber));

        News news = new News();
        news.setTitle(newsDto.getTitle());
        news.setContent(newsDto.getContent());
        news.setImageUrl(newsDto.getImageUrl());
        news.setAuthor(author);

        return newsRepository.save(news);
    }

    @Override
    public News updateNews(Long newsId, NewsDto newsDto) {
        News existingNews = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResourceNotFoundException("News not found with id: " + newsId));

        existingNews.setTitle(newsDto.getTitle());
        existingNews.setContent(newsDto.getContent());
        existingNews.setImageUrl(newsDto.getImageUrl());

        return newsRepository.save(existingNews);
    }

    @Override
    public void deleteNews(Long newsId) {
        if (!newsRepository.existsById(newsId)) {
            throw new ResourceNotFoundException("News not found with id: " + newsId);
        }
        newsRepository.deleteById(newsId);
    }

    @Override
    public List<News> findAllSorted() {
        return newsRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public News findById(Long newsId) {
        return newsRepository.findById(newsId)
                .orElseThrow(() -> new ResourceNotFoundException("News not found with id: " + newsId));
    }

    @Override
    public List<News> findLatest3News() {
        return newsRepository.findFirst3ByOrderByCreatedAtDesc();
    }
}
